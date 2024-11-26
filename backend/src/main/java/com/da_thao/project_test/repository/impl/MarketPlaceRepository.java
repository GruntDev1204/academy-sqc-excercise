package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.config.HibernateConnection;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.dto.filter_information.FindMarketPlace;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.MarketPlace;
import com.da_thao.project_test.repository.InterfaceRepository;

import java.util.List;

import com.da_thao.project_test.request_param.filter_data.InterfaceFilterData;
import com.da_thao.project_test.request_param.filter_data.impl.QueryFilterData;
import org.springframework.context.annotation.Primary;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;


@Primary
@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarketPlaceRepository implements InterfaceRepository<MarketPlace, FindMarketPlace> {
    HibernateConnection<MarketPlace> connection;
    InterfaceFilterData<FindMarketPlace> marketPlaceFilter;

    @Override
    public List<MarketPlace> getAll(FindMarketPlace requestParam) {
        QueryFilterData queryData = marketPlaceFilter.getFilterData(requestParam);
        String hql = "FROM MarketPlace" + queryData.hql();

        List<MarketPlace> mkps = connection.getQuery(queryData, hql, MarketPlace.class);
        if (mkps == null) throw new ApiException(ErrorCode.GENERAL_GET_FAILED);

        return mkps;
    }

    @Override
    public MarketPlace findById(Long id) {
        MarketPlace martketPlace = connection.getQuery(id, MarketPlace.class);
        if (martketPlace == null) throw new ApiException(ErrorCode.GENERAL_NOT_FOUND);

        return martketPlace;
    }

    @Override
    public MarketPlace create(MarketPlace data) {
        String avatarDefault = "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fnha-bo-hoang-1720797256966-17207972572861957193938.webp?alt=media&token=edc717a1-8999-4c35-8868-7ebda1815cb9";
        if(data.getAvatar() == null || data.getAvatar().isEmpty()) data.setAvatar(avatarDefault);
        connection.useTask("create", data);
        return data;
    }

    @Override
    public Boolean delete(Long id) {
        MarketPlace martketPlace = this.findById(id);
        connection.useTask("delete", martketPlace);

        return true;
    }

    @Override
    public MarketPlace update(Long id, MarketPlace newData) {
        MarketPlace updateMarketPlace = this.findById(id);

        updateMarketPlace.setName(newData.getName());
        updateMarketPlace.setAddress(newData.getAddress());
        updateMarketPlace.setRentPrice(newData.getRentPrice());
        updateMarketPlace.setRentDate(newData.getRentDate());
        updateMarketPlace.setArea(newData.getArea());
        updateMarketPlace.setTypeMarketPlace(newData.getTypeMarketPlace());

        connection.useTask("update", updateMarketPlace);

        return updateMarketPlace;
    }
}
