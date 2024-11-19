package com.da_thao.project_test.Resposritory.impl;

import com.da_thao.project_test.Exception.ApiException;
import com.da_thao.project_test.DTO.code_response.impl.ErrorCode;
import com.da_thao.project_test.Model.Department;
import com.da_thao.project_test.Request_param.filter_data.FilterDataInterface;
import com.da_thao.project_test.Request_param.vaild_request.RequestInterface;
import com.da_thao.project_test.Resposritory.InterfaceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Primary
@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentRepository implements InterfaceRepository<Department, Object> {
    FilterDataInterface<Department , Object> filterDataDepartment ;
    RequestInterface<Department> requestDepartment ;

    List<Department> departmentList = new ArrayList<>(
            Arrays.asList(
                    new Department(1, "CEO"),
                    new Department(2, "Làm thinh"),
                    new Department(3, "Làm ông nội"),
                    new Department(4, "CƠ THỦ CHUYÊN NGHIỆP"),
                    new Department(5, "Ăn xin"),
                    new Department(6, "Admin Juventus (Đi tù)")
            )
    );

    //DB
    private Integer autoRandomId() {
        Random random = new Random();
        return random.nextInt(9993) + 7; // random(7->9999)
    }


    //impl method
    @Override
    public List<Department> getAll(Object requestParam) {
        return this.departmentList;
    }

    @Override
    public Department findById(Integer id) {
        Department d = filterDataDepartment.filterById(this.departmentList , id);
        if (d == null) throw new ApiException(ErrorCode.DEPARTMENT_NOT_FOUND);
        return d;
    }

    @Override
    public Department create(Department department) {
        if (requestDepartment.checkRequest(department)) throw new ApiException(ErrorCode.DEPARTMENT_GET_ALL_FAILED);
        department.setId(this.autoRandomId());
        this.departmentList.add(department);

        return department;
    }

    @Override
    public Boolean delete(Integer id) {
        Department d = this.findById(id);
        if (d == null) throw new ApiException(ErrorCode.DEPARTMENT_NOT_FOUND);

        this.departmentList.remove(d);
        return true;
    }

    @Override
    public Department update(Integer id, Department department) {
        Department updateDepartment = this.findById(id);

        if (updateDepartment == null) throw new ApiException(ErrorCode.DEPARTMENT_NOT_FOUND);
        if (requestDepartment.checkRequest(department)) throw new ApiException(ErrorCode.EMPLOYEES_CREATE_FAILED);

        updateDepartment.setName(department.getName());
        return updateDepartment;
    }

}
