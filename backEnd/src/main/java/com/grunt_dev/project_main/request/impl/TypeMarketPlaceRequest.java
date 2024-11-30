
package com.grunt_dev.project_main.request.impl;

import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.model.TypeMarketPlace;
import com.grunt_dev.project_main.request.InterfaceRequest;
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
