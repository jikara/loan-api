package com.softmint.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.softmint.entity.Employee;
import lombok.AllArgsConstructor;

import java.util.UUID;

@DgsComponent
@AllArgsConstructor
public class EmployeeResolver {

    @DgsQuery(field = "employee")
    public Employee getEmployee(@InputArgument UUID employeeId) {
        return new Employee();
    }

}
