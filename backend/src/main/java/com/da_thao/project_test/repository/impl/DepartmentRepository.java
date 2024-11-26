package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.config.HibernateConnection;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.model.Department;
import com.da_thao.project_test.repository.InterfaceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;


@Primary
@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentRepository implements InterfaceRepository<Department, Object> {
    HibernateConnection<Department> connection;

    @Override
    public List<Department> getAll(Object requestParam) {
        String hql = "FROM Department ";
        return connection.getQuery(hql);
    }

    @Override
    public Department findById(Long id) {
        Department department = connection.getQuery(id, Department.class);
        if (department == null) throw new ApiException(ErrorCode.GENERAL_NOT_FOUND);

        return department;
    }

    @Override
    public Department create(Department data) {
        connection.useTask("create", data);
        return data;
    }

    @Override
    public Boolean delete(Long id) {
        Department d = this.findById(id);
        connection.useTask("delete", d);
        return true;
    }

    @Override
    public Department update(Long id, Department newData) {
        Department updateDepartment = this.findById(id);

        updateDepartment.setName(newData.getName());
        connection.useTask("update", updateDepartment);

        return updateDepartment;
    }

}
