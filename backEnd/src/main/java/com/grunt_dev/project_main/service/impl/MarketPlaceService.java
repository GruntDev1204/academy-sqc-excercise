package com.grunt_dev.project_main.service.impl;

import com.grunt_dev.project_main.attribute.attribute_class.FindMarketPlace;
import com.grunt_dev.project_main.attribute.attribute_data.InterfaceAttributeData;
import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.model.MarketPlace;
import com.grunt_dev.project_main.repository.IMarketPlaceRepository;
import com.grunt_dev.project_main.request.InterfaceRequest;
import com.grunt_dev.project_main.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarketPlaceService implements InterfaceService<MarketPlace, FindMarketPlace, List<MarketPlace>> {
    IMarketPlaceRepository rp;
    InterfaceRequest<MarketPlace> request;
    InterfaceAttributeData<FindMarketPlace, MarketPlace> attributeData;

    @Override
    public List<MarketPlace> getAll(FindMarketPlace requestParam, Integer page, Integer size) {
        Specification<MarketPlace> attr = attributeData.getAttribute(requestParam);
        return rp.findAll(attr);
    }

    @Override
    public MarketPlace findById(Long id) {
        return rp.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public MarketPlace create(MarketPlace data) {
        String dfAvatar = "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fnha4.jpg?alt=media&token=59431df8-30b9-43a6-8d49-fb7a38be3926";
        String ava = (data.getAvatar() == null || data.getAvatar().isEmpty()) ? dfAvatar : data.getAvatar();
        data.setAvatar(ava);
        if (request.checkRequest(data)) {
            return rp.save(data);
        } else {
            throw new ApiException(ErrorCode.CREATE_FAILED);
        }
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        rp.deleteById(id);
        return true;
    }

    @Override
    public MarketPlace update(Long id, MarketPlace newData) {
        this.findById(id);
        if (request.checkRequest(newData)) {
            newData.setId(id);
            return rp.save(newData);
        } else {
            throw new ApiException(ErrorCode.UPDATE_FAILED);
        }
    }
}
