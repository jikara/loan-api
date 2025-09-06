package com.softmint.service.impl;

import com.softmint.dto.Menu;
import com.softmint.dto.MenuItem;
import com.softmint.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {
    @Override
    public List<MenuItem> filterMenu(List<MenuItem> menu, Set<String> userPermissions) {
        return menu.stream()
                .map(item -> {
                    // Check if item has permissions
                    List<String> required = item.getPermissions();
                    boolean hasPermission = required == null || required.isEmpty() ||
                            required.stream().anyMatch(userPermissions::contains);

                    if (!hasPermission) return null;

                    // Filter children recursively
                    if (item.getChildren() != null && !item.getChildren().isEmpty()) {
                        item.setChildren(filterMenu(item.getChildren(), userPermissions));
                    }

                    return item;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Menu createMenu(Set<String> permissions) {
        List<MenuItem> fullMenu = List.of(
                MenuItem.builder()
                        .route("dashboard")
                        .name("dashboard")
                        .type("link")
                        .icon("dashboard")
                        .build(),

                MenuItem.builder()
                        .route("employer")
                        .name("employer")
                        .type("sub")
                        .icon("people")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_employers"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_employer"))
                                        .build()
                        ))
                        .permissions(List.of("list_employers"))
                        .build(),

                MenuItem.builder()
                        .route("employee")
                        .name("employee")
                        .type("sub")
                        .icon("directions_car")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_employees"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_employee"))
                                        .build()
                        ))
                        .permissions(List.of("list_employees"))
                        .build(),
                // loan
                MenuItem.builder()
                        .route("loan")
                        .name("loan")
                        .type("sub")
                        .icon("directions_car")
                        .children(List.of(
                                //list all
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_loans"))
                                        .build(),
                                // approvals
                                MenuItem.builder()
                                        .route("approve")
                                        .name("approve")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("approve_loan"))
                                        .build(),
                                //apply
                                MenuItem.builder()
                                        .route("apply")
                                        .name("apply")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("apply_loan"))
                                        .build()

                        ))
                        .permissions(List.of("list_loans", "apply_loan","approve_loan"))
                        .build(),

                MenuItem.builder()
                        .route("vehicle-model")
                        .name("vehicle-model")
                        .type("sub")
                        .icon("local_shipping")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_vehicle_models"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_vehicle_model"))
                                        .build()
                        ))
                        .permissions(List.of("list_vehicle_types"))
                        .build(),

                MenuItem.builder()
                        .route("insurance")
                        .name("insurance")
                        .type("sub")
                        .icon("shield")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_insurances"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_insurance"))
                                        .build()
                        ))
                        .permissions(List.of("list_insurances"))
                        .build(),

                MenuItem.builder()
                        .route("markup")
                        .name("markup")
                        .type("sub")
                        .icon("directions_car")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_markups"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_markup"))
                                        .build()
                        ))
                        .permissions(List.of("list_markups"))
                        .build(),

                MenuItem.builder()
                        .route("tax")
                        .name("tax")
                        .type("sub")
                        .icon("money_off")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_taxes"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_tax"))
                                        .build()
                        ))
                        .permissions(List.of("list_taxes"))
                        .build(),

                MenuItem.builder()
                        .route("material-request")
                        .name("material_request")
                        .type("sub")
                        .icon("receipt")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_material_requests"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_material_request"))
                                        .build(),
                                MenuItem.builder()
                                        .route("workshop-approve")
                                        .name("workshop_approve")
                                        .type("link")
                                        .hidden(true)
                                        .icon("flag")
                                        .permissions(List.of("workshop_approve_material_request"))
                                        .build(),
                                MenuItem.builder()
                                        .route("issuance")
                                        .name("issuance")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("issue_material_request"))
                                        .build()
                        ))
                        .permissions(List.of("list_material_requests"))
                        .build(),

                MenuItem.builder()
                        .route("purchase-request")
                        .name("purchase_request")
                        .type("sub")
                        .icon("receipt")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_purchase_requests"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_purchase_request"))
                                        .build(),
                                MenuItem.builder()
                                        .route("management-approve")
                                        .name("management_approve")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("approve_purchase_request"))
                                        .build()
                        ))
                        .permissions(List.of("list_purchase_requests"))
                        .build(),

                MenuItem.builder()
                        .route("lpo")
                        .name("lpo")
                        .type("sub")
                        .icon("receipt")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .icon("colorize")
                                        .permissions(List.of("list_lpos"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("create_lpo"))
                                        .build(),
                                MenuItem.builder()
                                        .route("management_approve")
                                        .name("management_approve")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("management_approve_lpo"))
                                        .build(),
                                MenuItem.builder()
                                        .route("voucher_approval")
                                        .name("voucher_approval")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of(
                                                "approve_lpo_voucher_below_50k",
                                                "approve_lpo_voucher_between_50k_and_250k",
                                                "approve_lpo_voucher_above_250k"
                                        ))
                                        .build(),
                                MenuItem.builder()
                                        .route("process")
                                        .name("process")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("process_lpo"))
                                        .build(),
                                MenuItem.builder()
                                        .route("process-payment")
                                        .name("payment")
                                        .type("link")
                                        .icon("flag")
                                        .permissions(List.of("process_lpo_payment"))
                                        .build()
                        ))
                        .permissions(List.of("list_lpos"))
                        .build(),

                // vendor-return
                MenuItem.builder()
                        .route("vendor-return")
                        .name("vendor_return")
                        .type("sub")
                        .icon("work")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_vendor_returns"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_vendor_return"))
                                        .build()
                        ))
                        .permissions(List.of("list_vendor_returns"))
                        .build(),

                // quotation
                MenuItem.builder()
                        .route("quotation")
                        .name("quotation")
                        .type("sub")
                        .icon("work")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_quotations"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_quotation"))
                                        .build()
                        ))
                        .permissions(List.of("list_quotations"))
                        .build(),

                // job-card
                MenuItem.builder()
                        .route("job-card")
                        .name("job_card")
                        .type("sub")
                        .icon("work")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_job_cards"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_job_card"))
                                        .build()
                        ))
                        .permissions(List.of("list_job_cards"))
                        .build(),

                // vendor
                MenuItem.builder()
                        .route("vendor")
                        .name("vendor")
                        .type("sub")
                        .icon("store")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_vendors"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_vendor"))
                                        .build()
                        ))
                        .permissions(List.of("list_vendors"))
                        .build(),

                // invoice
                MenuItem.builder()
                        .route("invoice")
                        .name("invoice")
                        .type("link")
                        .icon("receipt")
                        .permissions(List.of("list_invoices"))
                        .build(),

                // payment
                MenuItem.builder()
                        .route("payment")
                        .name("payment")
                        .type("sub")
                        .icon("euro_symbol")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_payments"))
                                        .build(),
                                MenuItem.builder()
                                        .route("process")
                                        .name("process")
                                        .type("link")
                                        .permissions(List.of("create_payment"))
                                        .build()
                        ))
                        .permissions(List.of("list_payments"))
                        .build(),

                // inventory
                MenuItem.builder()
                        .route("inventory")
                        .name("inventory")
                        .type("sub")
                        .icon("store")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_inventories"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_inventory"))
                                        .build()
                        ))
                        .permissions(List.of("list_inventories"))
                        .build(),

                // inspection
                MenuItem.builder()
                        .route("inspection")
                        .name("inspection")
                        .type("sub")
                        .icon("checklist")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_inspections"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_inspection"))
                                        .build()
                        ))
                        .permissions(List.of("list_inspections"))
                        .build(),

                // parts
                MenuItem.builder()
                        .route("parts")
                        .name("part")
                        .type("sub")
                        .icon("build")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_parts"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_part"))
                                        .build()
                        ))
                        .permissions(List.of("list_parts"))
                        .build(),

                // part-category
                MenuItem.builder()
                        .route("part-category")
                        .name("part-category")
                        .type("sub")
                        .icon("category")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_part_categories"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_part_category"))
                                        .build()
                        ))
                        .permissions(List.of("list_part_categories"))
                        .build(),

                // part-make
                MenuItem.builder()
                        .route("part-make")
                        .name("part-make")
                        .type("sub")
                        .icon("category")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_parts"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_part"))
                                        .build()
                        ))
                        .permissions(List.of("list_parts"))
                        .build(),

                // service
                MenuItem.builder()
                        .route("service")
                        .name("service")
                        .type("sub")
                        .icon("local_car_wash")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_services"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_service"))
                                        .build()
                        ))
                        .permissions(List.of("list_services"))
                        .build(),

                // service-type
                MenuItem.builder()
                        .route("service-type")
                        .name("service-type")
                        .type("sub")
                        .icon("category")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_service_types"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_service_type"))
                                        .build()
                        ))
                        .permissions(List.of("list_service_types"))
                        .build(),

                // role
                MenuItem.builder()
                        .route("role")
                        .name("role")
                        .type("sub")
                        .icon("person")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_roles"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_role"))
                                        .build()
                        ))
                        .permissions(List.of("list_roles"))
                        .build(),

                // users
                MenuItem.builder()
                        .route("user")
                        .name("user")
                        .type("sub")
                        .icon("supervised_user_circle")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("list")
                                        .name("list")
                                        .type("link")
                                        .permissions(List.of("list_users"))
                                        .build(),
                                MenuItem.builder()
                                        .route("create")
                                        .name("create")
                                        .type("link")
                                        .permissions(List.of("create_user"))
                                        .build()
                        ))
                        .permissions(List.of("list_users"))
                        .build(),


                // set-ups
                MenuItem.builder()
                        .route("setup")
                        .name("setup")
                        .type("sub")
                        .icon("supervised_user_circle")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("product")
                                        .name("product")
                                        .type("link")
                                        .permissions(List.of("create_setup"))
                                        .build(),
                                MenuItem.builder()
                                        .route("document")
                                        .name("document")
                                        .type("link")
                                        .permissions(List.of("create_setup"))
                                        .build(),
                                MenuItem.builder()
                                        .route("approval")
                                        .name("approval")
                                        .type("link")
                                        .permissions(List.of("create_setup"))
                                        .build()
                        ))
                        .permissions(List.of("create_setup"))
                        .build(),

                // reports
                MenuItem.builder()
                        .route("report")
                        .name("report")
                        .type("sub")
                        .icon("report")
                        .children(List.of(
                                MenuItem.builder()
                                        .route("client-payments")
                                        .name("client_payments_report")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("vendor-payments")
                                        .name("vendor_payments_report")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("reconciliation")
                                        .name("reconciliation")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("withholding-tax")
                                        .name("withholding_tax")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("parts-movement")
                                        .name("parts_movement")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("credit-note")
                                        .name("credit-note")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("sales")
                                        .name("sales_report")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("profit")
                                        .name("profit_report")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("account-payable")
                                        .name("account_payable")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("client-statement")
                                        .name("client_statement")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("vehicle-statement")
                                        .name("vehicle_statement")
                                        .type("link")
                                        .permissions(List.of("view_reports"))
                                        .build(),
                                MenuItem.builder()
                                        .route("audit")
                                        .name("audit")
                                        .type("link")
                                        .permissions(List.of("view_audit_logs"))
                                        .build()
                        ))
                        .permissions(List.of("view_reports"))
                        .build()
        );
        List<MenuItem> menuItems = filterMenu(fullMenu, permissions);
        return new Menu(menuItems);
    }

}
