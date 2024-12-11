package com.grunt_dev.project_main.controller.impl;

import com.grunt_dev.project_main.controller.RestControllerInterface;
import com.grunt_dev.project_main.dto.ApiResponse;
import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.code_status.impl.SuccessCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.dto.response_json.HandleResponseData;
import com.grunt_dev.project_main.model.TypeMarketPlace;
import com.grunt_dev.project_main.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type-market-places")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeMarketPlaceController implements RestControllerInterface<TypeMarketPlace, Object, List<TypeMarketPlace>> {
    InterfaceService<TypeMarketPlace, Object, List<TypeMarketPlace>> sv;
    HandleResponseData res;

    @Override
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<TypeMarketPlace>>> getAll(
            Object requestParam,
            @RequestParam(required = false) Integer index,
            @RequestParam(required = false) Integer size
    ) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam, 0, 0));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TypeMarketPlace>> findById(@PathVariable Long id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, sv.findById(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ApiResponse<TypeMarketPlace>> create(@RequestBody TypeMarketPlace data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, sv.create(data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = sv.delete(id);
        if (action) {
            return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        } else {
            throw new ApiException(ErrorCode.DELETED_FAILED);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TypeMarketPlace>> update(@PathVariable Long id, @RequestBody TypeMarketPlace newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, sv.update(id, newData));
    }
}
