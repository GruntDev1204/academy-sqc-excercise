
package com.da_thao.project_test.request_param.vaild_request.impl;

import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.model.TypeMarketPlace;
import com.da_thao.project_test.request_param.vaild_request.InterfaceRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class TypeMarketPlaceRequest implements InterfaceRequest<TypeMarketPlace> {
    @Override
    public boolean checkRequest(TypeMarketPlace requestData) {
        Map<String, String> detailError = new HashMap<>();

        if (requestData == null) detailError.put("TypeMarketPlace", "TypeMarketPlace object is null!");

        assert requestData != null;
        if (requestData.getName() == null || requestData.getName().isEmpty())
            detailError.put("TypeMarketPlace", "Name is null!");

        if (!detailError.isEmpty()) {
            throw new ApiException(ErrorCode.CREATE_FAILED, detailError);
        }

        return true;
    }
}
