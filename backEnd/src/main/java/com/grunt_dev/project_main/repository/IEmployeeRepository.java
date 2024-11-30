package com.grunt_dev.project_main.repository;

import com.grunt_dev.project_main.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface IEmployeeRepository extends JpaRepository<Employee, Long> , JpaSpecificationExecutor<Employee> {
}
