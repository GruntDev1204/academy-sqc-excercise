package com.grunt_dev.project_main.request.impl;

import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.request.InterfaceRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import com.grunt_dev.project_main.model.MarketPlace;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class MarketPlaceRequest implements InterfaceRequest<MarketPlace> {

    @Override
    public boolean checkRequest(MarketPlace requestData) {
        Map<String, String> detailError = new HashMap<>();

        if (requestData == null) detailError.put("MarketPlace", "Martket Place object is null");

        assert requestData != null;
        if (requestData.getName() == null || requestData.getName().isEmpty())
            detailError.put("name", "Martket Place name object is null");

        if (requestData.getAddress() == null || requestData.getAddress().isEmpty())
            detailError.put("address", "Martket Place address object is null");

        if (requestData.getArea() == null) detailError.put("name", "Martket Place area name object is null");

        if (requestData.getRentPrice() == null)
            detailError.put("rent-price", "Martket Place rentPrice name object is null");

        if (requestData.getRentDate() == null)
            detailError.put("start date ", "Martket Place area start date object is null");

        if (!detailError.isEmpty()) throw new ApiException(ErrorCode.CREATE_FAILED, detailError);
        return true;
    }
}
