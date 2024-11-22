package com.da_thao.project_test.controller.impl;

import com.da_thao.project_test.controller.RestControllerInterface;
import com.da_thao.project_test.dto.ApiResponse;
import com.da_thao.project_test.dto.code_response.impl.SuccessCode;
import com.da_thao.project_test.dto.data_filter.MarketPlaceFilter;
import com.da_thao.project_test.dto.response_data.HandleResponseData;
import com.da_thao.project_test.model.MartketPlace;
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
public class MarketPlaceController implements RestControllerInterface<MartketPlace, MarketPlaceFilter> {
    InterfaceService<MartketPlace, MarketPlaceFilter> marketPlaceService;
    HandleResponseData res;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<MartketPlace>>> getAll(MarketPlaceFilter requestParam) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, marketPlaceService.getAll(requestParam));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MartketPlace>> findById(@PathVariable Integer id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, marketPlaceService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<MartketPlace>> create(@RequestBody MartketPlace model) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, marketPlaceService.create(model));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        Boolean action = marketPlaceService.delete(id);

        if (action) return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        else return null;
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<MartketPlace>> update(@PathVariable Integer id, @RequestBody MartketPlace model) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, marketPlaceService.update(id, model));
    }
}
