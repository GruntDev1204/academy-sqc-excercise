
package com.da_thao.project_test.controller.impl;

import com.da_thao.project_test.dto.ApiResponse;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.dto.code_response.impl.SuccessCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.TypeMarketPlace;
import com.da_thao.project_test.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.da_thao.project_test.controller.RestControllerInterface;
import com.da_thao.project_test.dto.response_data.HandleResponseData;

import java.util.List;

@RestController
@RequestMapping("/type-market-places")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeMarketPlaceController implements RestControllerInterface<TypeMarketPlace, Object> {
    InterfaceService<TypeMarketPlace, Object> typeMarketPlaceService;
    HandleResponseData res;

    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<List<TypeMarketPlace>>> getAll(Object requestParam) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, typeMarketPlaceService.getAll(requestParam));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<TypeMarketPlace>> findById(@PathVariable Integer id) {

        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, typeMarketPlaceService.findById(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<TypeMarketPlace>> create(@RequestBody TypeMarketPlace data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, typeMarketPlaceService.create(data));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        Boolean action = typeMarketPlaceService.delete(id);
        if (action) return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        else throw new ApiException(ErrorCode.DELETED_FAILED);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<TypeMarketPlace>> update(@PathVariable Integer id, @RequestBody TypeMarketPlace newData) {
       return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, typeMarketPlaceService.update(id,newData));
    }
}
