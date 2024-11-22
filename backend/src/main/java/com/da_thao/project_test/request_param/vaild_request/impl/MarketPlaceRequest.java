package com.da_thao.project_test.request_param.vaild_request.impl;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.MartketPlace;
import com.da_thao.project_test.request_param.vaild_request.InterfaceRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class MarketPlaceRequest implements InterfaceRequest<MartketPlace> {


    @Override
    public boolean checkRequest(MartketPlace model) {
        Map<String, String> detailError = new HashMap<>();

        if (model == null) detailError.put("MartketPlace", "Martket Place object is null");
        if (model.getName() == null || model.getName().isEmpty())
            detailError.put("name", "Martket Place name object is null");
        if (model.getAddress() == null || model.getAddress().isEmpty())
            detailError.put("address", "Martket Place address object is null");
        if (model.getTypeId() == null) detailError.put("typeId", "typeId Place name object is null");
        if (model.getArea() == null) detailError.put("name", "Martket Place area name object is null");
        if (model.getRentPrice() == null) detailError.put("rent-price", "Martket Place rentPrice name object is null");
        if (model.getStartDate() == null)
            detailError.put("start date ", "Martket Place area start date object is null");
        if (!detailError.isEmpty()) throw new ApiException(ErrorCode.BAD_REQUEST, detailError);
        return true;
    }
}
