package com.da_thao.project_test.controller.impl;

import com.da_thao.project_test.controller.RestControllerInterface;
import com.da_thao.project_test.dto.ApiResponse;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.dto.code_response.impl.SuccessCode;
import com.da_thao.project_test.dto.filter_information.FindMarketPlace;
import com.da_thao.project_test.dto.response_data.HandleResponseData;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.MarketPlace;
import com.da_thao.project_test.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market-places")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarketPlaceController implements RestControllerInterface<MarketPlace, FindMarketPlace> {
    InterfaceService<MarketPlace, FindMarketPlace> marketPlaceService;
    HandleResponseData res;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<MarketPlace>>> getAll(FindMarketPlace requestParam) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, marketPlaceService.getAll(requestParam));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MarketPlace>> findById(@PathVariable Integer id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, marketPlaceService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<MarketPlace>> create(@RequestBody MarketPlace data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, marketPlaceService.create(data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        Boolean action = marketPlaceService.delete(id);

        if (action) return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        else throw new ApiException(ErrorCode.DELETED_FAILED);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<MarketPlace>> update(@PathVariable Integer id, @RequestBody MarketPlace newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, marketPlaceService.update(id, newData));
    }
}
