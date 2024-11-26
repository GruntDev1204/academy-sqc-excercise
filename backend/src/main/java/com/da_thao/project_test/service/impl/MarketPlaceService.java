package com.da_thao.project_test.service.impl;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.dto.filter_information.FindMarketPlace;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.MarketPlace;
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
public class MarketPlaceService implements InterfaceService<MarketPlace, FindMarketPlace> {
    InterfaceRepository<MarketPlace, FindMarketPlace> marketPlaceRepository;
    InterfaceRequest<MarketPlace> request;

    @Override
    public List<MarketPlace> getAll(FindMarketPlace requestParam) {
        return marketPlaceRepository.getAll(requestParam);
    }

    @Override
    public MarketPlace findById(Integer id) {
        return marketPlaceRepository.findById(Long.valueOf(id));
    }

    @Override
    public MarketPlace create(MarketPlace data) {
        if (request.checkRequest(data)) return marketPlaceRepository.create(data);
        else throw new ApiException(ErrorCode.CREATE_FAILED);
    }

    @Override
    public Boolean delete(Integer id) {
        return marketPlaceRepository.delete(Long.valueOf(id));
    }

    @Override
    public MarketPlace update(Integer id, MarketPlace newData) {
        if (request.checkRequest(newData)) return marketPlaceRepository.update(Long.valueOf(id), newData);
        else throw new ApiException(ErrorCode.UPDATE_FAILED);
    }
}
