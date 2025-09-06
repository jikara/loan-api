package com.softmint.component;

import com.softmint.entity.*;
import com.softmint.enums.StaticRoleType;
import com.softmint.repo.PermissionRepo;
import com.softmint.repo.RoleRepo;
import com.softmint.repo.StaticRoleRepo;
import com.softmint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final PermissionRepo permissionRepo;
    private final ResourceLoader resourceLoader;
    private final RoleRepo roleRepo;
    private final StaticRoleRepo staticRoleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    private String convertToNormalCase(String input) {
        // Replace underscores with spaces
        String modifiedInput = input.replace("_", " ");
        // Capitalize the first letter and make the rest lowercase
        return modifiedInput.substring(0, 1).toUpperCase() + modifiedInput.substring(1).toLowerCase();
    }

    @Override
    @Transactional
    public void run(String... args) {
        initializePermissions();
        initializeSystemAdminRole();
        initializeStaticRoles();
    }

    private void initializePermissions() {
        record PermissionSeed(String code, boolean assignable) {
        }
        var permissions = Set.of(
                new PermissionSeed("apply_loan", false),
                new PermissionSeed("approve_loan", true),
                new PermissionSeed("create_employer", true),
                new PermissionSeed("create_employees", false),
                new PermissionSeed("create_employee", false),
                new PermissionSeed("create_product", true),
                new PermissionSeed("create_permission", true),
                new PermissionSeed("create_role", true),
                new PermissionSeed("create_user", true),
                new PermissionSeed("create_setup", true),
                new PermissionSeed("edit_permission", true),
                new PermissionSeed("edit_role", true),
                new PermissionSeed("list_employers", true),
                new PermissionSeed("list_employees", true),
                new PermissionSeed("list_products", true),
                new PermissionSeed("list_loans", true),
                new PermissionSeed("list_permissions", true),
                new PermissionSeed("list_roles", true),
                new PermissionSeed("list_users", true),
                new PermissionSeed("view_permission", true),
                new PermissionSeed("view_loan", true),
                new PermissionSeed("view_employee", true),
                new PermissionSeed("edit_employee", true),


                new PermissionSeed("list_approval_policies", true),
                new PermissionSeed("create_approval_policy", true),
                new PermissionSeed("view_approval_policy", true),
                new PermissionSeed("edit_approval_policy", true),

                new PermissionSeed("view_audit_logs", false) // 👈 static permission
        );

        permissions.forEach(seed -> {
            permissionRepo.findById(seed.code()).orElseGet(() -> {
                Permission permission = Permission.builder()
                        .code(seed.code())
                        .name(convertToNormalCase(seed.code()))
                        .assignable(seed.assignable())
                        .build();
                return permissionRepo.save(permission);
            });
        });
    }

    private void initializeSystemAdminRole() {
        if (roleRepo.count() < 1) {
            Role role = new Role();
            role.setName("SYSTEM ADMIN");
            role.getPermissions().add(permissionRepo.findById("list_roles").orElse(null));
            role.getPermissions().add(permissionRepo.findById("edit_role").orElse(null));
            initializeSystemAdminUser(roleRepo.save(role));
        }
    }

    private void initializeSystemAdminUser(Role role) {
        User user = new User();
        user.setEmail("info@loanapp.com");
        user.setFirstName("System");
        user.setLastName("Administrator");
        user.setPhone("0700010203");
        user.setRole(role);
        Credential credential = new Credential();
        credential.setPassword(passwordEncoder.encode("12345678"));
        credential.setEmail(user.getEmail());
        credential.setActivated(true);
        //  credential.setAdmin(true);
        user.setCredential(credential);
        userRepo.save(user);
    }

    private void initializeStaticRoles() {
        seedOrUpdateStaticRole(StaticRoleType.EMPLOYER_HR, "HR",
                "Employer HR role with approval permissions",
                Set.of(
                        permissionRepo.findById("create_employee").orElseThrow(),
                        permissionRepo.findById("list_employees").orElseThrow(),
                        permissionRepo.findById("list_loans").orElseThrow(),
                        permissionRepo.findById("approve_loan").orElseThrow()
                )
        );

        seedOrUpdateStaticRole(StaticRoleType.EMPLOYER_FINANCE, "FINANCE",
                "Employer Finance role with financial approval permissions",
                Set.of(
                        permissionRepo.findById("list_loans").orElseThrow(),
                        permissionRepo.findById("approve_loan").orElseThrow()
                )
        );

        seedOrUpdateStaticRole(StaticRoleType.EMPLOYER_ADMIN, "ADMIN",
                "Employer Admin role with full HR and Finance control",
                Set.of(
                        permissionRepo.findById("create_employee").orElseThrow(),
                        permissionRepo.findById("list_employees").orElseThrow(),
                        permissionRepo.findById("view_employee").orElseThrow(),
                        permissionRepo.findById("edit_employee").orElseThrow(),
                        permissionRepo.findById("list_loans").orElseThrow(),
                        permissionRepo.findById("approve_loan").orElseThrow()
                )
        );

        seedOrUpdateStaticRole(StaticRoleType.EMPLOYEE, "EMPLOYEE",
                "Employee role",
                Set.of(
                        permissionRepo.findById("apply_loan").orElseThrow(),
                        permissionRepo.findById("list_loans").orElseThrow(),
                        permissionRepo.findById("view_loan").orElseThrow()
                )
        );

        System.out.println("Initialized Static Roles: HR, FINANCE, ADMIN, EMPLOYEE");
    }

    private void seedOrUpdateStaticRole(
            StaticRoleType type, String name, String description, Set<Permission> permissions) {

        StaticRole role = staticRoleRepo.findStaticRoleByType(type)
                .map(existing -> {
                    existing.setName(name);
                    existing.setDescription(description);
                    existing.getPermissions().addAll(permissions); // merge
                    return existing;
                })
                .orElseGet(() -> StaticRole.builder()
                        .type(type)
                        .name(name)
                        .description(description)
                        .permissions(new HashSet<>(permissions))
                        .build()
                );

        staticRoleRepo.save(role);
    }

}

