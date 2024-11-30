package com.grunt_dev.project_main.attribute.attribute_data.impl;

import com.grunt_dev.project_main.attribute.attribute_class.FindEmployee;
import com.grunt_dev.project_main.attribute.attribute_data.InterfaceAttributeData;
import com.grunt_dev.project_main.model.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class EmployeesAttribute implements InterfaceAttributeData<FindEmployee, Employee> {

    @Override
    public Specification<Employee> getAttribute(FindEmployee requestParam) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (requestParam.getFullName() != null && !requestParam.getFullName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + requestParam.getFullName().toLowerCase() + "%"));
            }

            if (requestParam.getDobFrom() != null && requestParam.getDobTo() != null) {
                predicates.add(criteriaBuilder.between(root.get("birthDay"), requestParam.getDobFrom(), requestParam.getDobTo()));
            } else if (requestParam.getDobFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthDay"), requestParam.getDobFrom()));
            } else if (requestParam.getDobTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthDay"), requestParam.getDobTo()));
            }

            if (requestParam.getGender() != null && !requestParam.getGender().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), requestParam.getGender()));
            }

            if (requestParam.getSalaryValue() != null) {
                switch (requestParam.getSalaryValue()) {
                    case "under_10m":
                        predicates.add(criteriaBuilder.lessThan(root.get("salary"), 10000000));
                        break;
                    case "10_to_15m":
                        predicates.add(criteriaBuilder.between(root.get("salary"), 10000000, 15000000));
                        break;
                    case "15_to_20m":
                        predicates.add(criteriaBuilder.between(root.get("salary"), 15000000, 20000000));
                        break;
                    case "above_20m":
                        predicates.add(criteriaBuilder.greaterThan(root.get("salary"), 20000000));
                        break;
                    default:
                        break;
                }
            }

            if (requestParam.getPhoneNumber() != null && !requestParam.getPhoneNumber().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("phoneNumber"), requestParam.getPhoneNumber()));
            }

            if (requestParam.getDepartmentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("department").get("id"), requestParam.getDepartmentId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

