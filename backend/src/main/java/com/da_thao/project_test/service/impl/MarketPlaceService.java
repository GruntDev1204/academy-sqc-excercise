package com.da_thao.project_test.service.impl;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.dto.data_filter.MarketPlaceFilter;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.MartketPlace;
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
public class MarketPlaceService implements InterfaceService<MartketPlace, MarketPlaceFilter> {
    InterfaceRepository<MartketPlace, MarketPlaceFilter> marketPlaceRepository;
    InterfaceRequest<MartketPlace> request;

    @Override
    public List<MartketPlace> getAll(MarketPlaceFilter requestParam) {
        return marketPlaceRepository.getAll(requestParam);
    }

    @Override
    public MartketPlace findById(Integer id) {
        return marketPlaceRepository.findById(id);
    }

    @Override
    public MartketPlace create(MartketPlace model) {
        if (request.checkRequest(model)) return marketPlaceRepository.create(model);
        else return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return marketPlaceRepository.delete(id);
    }

    @Override
    public MartketPlace update(Integer id, MartketPlace model) {
        if (request.checkRequest(model)) return marketPlaceRepository.update(id, model);
        throw new ApiException(ErrorCode.UPDATE_FAILED);
    }
}
