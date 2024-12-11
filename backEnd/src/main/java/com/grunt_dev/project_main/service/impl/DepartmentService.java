package com.grunt_dev.project_main.service.impl;

import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.model.Department;
import com.grunt_dev.project_main.repository.IDepartmentRepository;
import com.grunt_dev.project_main.request.InterfaceRequest;
import com.grunt_dev.project_main.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Primary
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService implements InterfaceService<Department, Object, Page<Department>> {
    InterfaceRequest<Department> request;
    IDepartmentRepository repository;

    @Override
    public Page<Department> getAll(Object requestParam, Integer index, Integer size) {
        Pageable pageable = PageRequest.of(index, size);
        return repository.findAll(pageable);
    }

    @Override
    public Department findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public Department create(Department data) {
        if (request.checkRequest(data)) {
            return repository.save(data);
        } else {
            throw new ApiException(ErrorCode.CREATE_FAILED);
        }
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
        return true;
    }

    @Override
    public Department update(Long id, Department newData) {
        this.findById(id);
        if (request.checkRequest(newData)) {
            newData.setId(id);
            return repository.save(newData);
        } else {
            throw new ApiException(ErrorCode.UPDATE_FAILED);
        }
    }
}
