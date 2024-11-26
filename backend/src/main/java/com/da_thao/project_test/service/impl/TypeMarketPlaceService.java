
package com.da_thao.project_test.service.impl;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.TypeMarketPlace;
import com.da_thao.project_test.repository.InterfaceRepository;
import com.da_thao.project_test.request_param.vaild_request.InterfaceRequest;
import com.da_thao.project_test.service.InterfaceService;
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
public class TypeMarketPlaceService implements InterfaceService<TypeMarketPlace, Object> {
    InterfaceRepository<TypeMarketPlace, Object> typemarketplaceRepository;
    InterfaceRequest<TypeMarketPlace> request;

    @Override
    public List<TypeMarketPlace> getAll(Object requestParam) {
        return typemarketplaceRepository.getAll(requestParam);
    }

    @Override
    public TypeMarketPlace findById(Integer id) {
        return typemarketplaceRepository.findById(Long.valueOf(id));
    }

    @Override
    public TypeMarketPlace create(TypeMarketPlace data) {
        if (request.checkRequest(data)) return typemarketplaceRepository.create(data);
        else throw new ApiException(ErrorCode.CREATE_FAILED);
    }

    @Override
    public Boolean delete(Integer id) {
        return typemarketplaceRepository.delete(Long.valueOf(id));
    }

    @Override
    public TypeMarketPlace update(Integer id, TypeMarketPlace newData) {
        if (request.checkRequest(newData)) return typemarketplaceRepository.update(Long.valueOf(id), newData);
        else throw new ApiException(ErrorCode.UPDATE_FAILED);
    }
}
