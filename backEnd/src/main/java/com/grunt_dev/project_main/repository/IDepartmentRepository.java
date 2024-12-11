package com.grunt_dev.project_main.repository;

import com.grunt_dev.project_main.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<Department, Long>  {
}
