package com.grunt_dev.project_main.service.impl;

import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.model.TypeMarketPlace;
import com.grunt_dev.project_main.repository.ITypeMarketPlaceRepository;
import com.grunt_dev.project_main.request.InterfaceRequest;
import com.grunt_dev.project_main.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeMarketPlaceService implements InterfaceService<TypeMarketPlace, Object, List<TypeMarketPlace>> {
    InterfaceRequest<TypeMarketPlace> request;
    ITypeMarketPlaceRepository repository;

    @Override
    public List<TypeMarketPlace> getAll(Object requestParam, Integer page, Integer size) {
        return repository.findAll();
    }

    @Override
    public TypeMarketPlace findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public TypeMarketPlace create(TypeMarketPlace data) {
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
    public TypeMarketPlace update(Long id, TypeMarketPlace newData) {
        this.findById(id);
        if (request.checkRequest(newData)) {
            newData.setId(id);
            return repository.save(newData);
        } else throw new ApiException(ErrorCode.UPDATE_FAILED);
    }
}
