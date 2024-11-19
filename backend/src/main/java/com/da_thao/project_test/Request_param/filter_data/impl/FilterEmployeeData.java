package com.da_thao.project_test.Request_param.filter_data.impl;

import com.da_thao.project_test.Model.Employee;
import com.da_thao.project_test.DTO.data_filter.FindEmployee;
import com.da_thao.project_test.Request_param.filter_data.FilterDataInterface;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class FilterEmployeeData implements FilterDataInterface<Employee, FindEmployee> {

    private boolean filterByName(Employee employee, String name) {
        if (name == null || name.isEmpty()) return true;
        String fullName = employee.getFullName();
        String lowerCaseName = name.toLowerCase();
        return fullName != null && fullName.toLowerCase().contains(lowerCaseName);
    }

    private boolean filterByDob(Employee employee, LocalDate dobFrom, LocalDate dobTo) {
        boolean matchesDobFrom = (dobFrom == null || !employee.getBirthDay().isBefore(dobFrom));
        boolean matchesDobTo = (dobTo == null || !employee.getBirthDay().isAfter(dobTo));
        return matchesDobFrom && matchesDobTo;
    }

    private boolean filterByGender(Employee employee, String gender) {
        if (gender == null || gender.isEmpty()) return true;
        return employee.getGender().equalsIgnoreCase(gender);
    }

    private boolean filterBySalary(Employee employee, Integer salaryRange) {
        if (salaryRange == null) return true;
        return switch (salaryRange) {
            case 1 -> // Dưới 10 triệu
                    employee.getSalary() < 10_000_000;
            case 2 -> // 10-15 triệu
                    employee.getSalary() >= 10_000_000 && employee.getSalary() <= 15_000_000;
            case 3 -> // 15-30 triệu
                    employee.getSalary() > 15_000_000 && employee.getSalary() <= 30_000_000;
            case 4 -> // Trên 30 triệu
                    employee.getSalary() > 30_000_000;
            default -> true;
        };
    }

    private boolean filterByPhoneNumber(Employee employee, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) return true;
        return employee.getPhoneNumber().contains(phoneNumber);
    }

    private boolean filterByDepartmentId(Employee employee, Integer departmentId) {
        if (departmentId == null) return true;
        return employee.getDepartmentId().equals(departmentId);
    }

    @Override
    public List<Employee> filterAll(List<Employee> dataModel, FindEmployee dataFilter) {
        return dataModel.stream()
                .filter(e -> this.filterByName(e, dataFilter.getFullName()))
                .filter(e -> this.filterByDob(e, dataFilter.getDobFrom(), dataFilter.getDobTo()))
                .filter(e -> this.filterByGender(e, dataFilter.getGender()))
                .filter(e -> this.filterBySalary(e, dataFilter.getSalaryValue()))
                .filter(e -> this.filterByPhoneNumber(e, dataFilter.getPhoneNumber()))
                .filter(e -> this.filterByDepartmentId(e, dataFilter.getDepartmentId()))
                .collect(Collectors.toList());
    }

    @Override
    public Employee filterById(List<Employee> e, Integer id) {
        return e.stream()
                .filter(employees -> employees.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
