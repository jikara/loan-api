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
        String[] permissions = {
                "apply_loan",
                "create_employer",
                "create_employee",
                "create_product",
                "create_permission",
                "create_role",
                "create_user",
                "create_setup",
                "edit_permission",
                "edit_role",
                "list_employers",
                "list_employees",
                "list_products",
                "list_loans",
                "list_permissions",
                "list_roles",
                "list_users",
                "view_permission",
                "view_loan",
                "view_audit_logs"
        };
        for (String permissionCode : permissions) {
            if (permissionRepo.findById(permissionCode).isEmpty()) {
                Permission permission = new Permission();
                permission.setCode(permissionCode);
                permission.setName(convertToNormalCase(permissionCode));
                permissionRepo.save(permission);
                System.out.println("Initialized permission: " + permissionCode);
            }
        }
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
                        permissionRepo.findById("list_employees").orElseThrow()
                )
        );

        seedOrUpdateStaticRole(StaticRoleType.EMPLOYER_FINANCE, "FINANCE",
                "Employer Finance role with financial approval permissions",
                Set.of(
                        permissionRepo.findById("create_product").orElseThrow(),
                        permissionRepo.findById("list_loans").orElseThrow()
                )
        );

        seedOrUpdateStaticRole(StaticRoleType.EMPLOYER_ADMIN, "ADMIN",
                "Employer Admin role with full HR and Finance control",
                Set.of(
                        permissionRepo.findById("create_employee").orElseThrow(),
                        permissionRepo.findById("list_employees").orElseThrow(),
                        permissionRepo.findById("create_product").orElseThrow(),
                        permissionRepo.findById("list_loans").orElseThrow()
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

    private void seedOrUpdateStaticRole(StaticRoleType type, String name, String description, Set<Permission> permissions) {
        StaticRole role = staticRoleRepo.findStaticRoleByType(type).orElseGet(() -> {
            StaticRole r = new StaticRole();
            r.setPermissions(new HashSet<>()); // ensure initialized
            return r;
        });

        role.setType(type);
        role.setName(name);
        role.setDescription(description);

        // Merge new permissions with existing managed collection
        role.getPermissions().addAll(permissions);

        staticRoleRepo.save(role);
    }

}

