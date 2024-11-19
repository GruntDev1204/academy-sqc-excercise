package com.da_thao.project_test.Service.impl;

import com.da_thao.project_test.Model.Department;
import com.da_thao.project_test.Resposritory.impl.DepartmentRepository;
import com.da_thao.project_test.Service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true )
public class DepartmentService implements  InterfaceService<Department,Object> {
    DepartmentRepository dR;

    @Override
    public List<Department> getAll(Object requestParam) {
        return dR.getAll(requestParam);
    }

    @Override
    public Department findById(Integer id) {
        return dR.findById(id);
    }

    @Override
    public Department create(Department e) {
        return dR.create(e);
    }

    @Override
    public Boolean delete(Integer id) {
        return dR.delete(id);
    }

    @Override
    public Department update(Integer id, Department e) {
        return dR.update(id ,e);
    }
}
