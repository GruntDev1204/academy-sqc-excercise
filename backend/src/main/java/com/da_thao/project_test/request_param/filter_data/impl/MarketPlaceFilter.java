package com.da_thao.project_test.request_param.filter_data.impl;

import com.da_thao.project_test.dto.filter_information.FindMarketPlace;
import com.da_thao.project_test.request_param.filter_data.InterfaceFilterData;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class MarketPlaceFilter implements InterfaceFilterData<FindMarketPlace> {

    @Override
    public QueryFilterData getFilterData(FindMarketPlace requestParam) {
        StringBuilder queryBuilder = new StringBuilder("WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (requestParam.getName() != null && !requestParam.getName().isEmpty()) {
            queryBuilder.append(" AND name LIKE :name");
            params.put("name", "%" + requestParam.getName() + "%");
        }

        if (requestParam.getAddress() != null && !requestParam.getAddress().isEmpty()) {
            queryBuilder.append(" AND address LIKE :address");
            params.put("address", "%" + requestParam.getAddress() + "%");
        }

        if (requestParam.getTypeId() != null && !requestParam.getTypeId().isEmpty()) {
            queryBuilder.append(" AND type_id LIKE :typeId");
            params.put("typeId", "%" + requestParam.getTypeId() + "%");
        }

        if (requestParam.getRentPrice() != null && !requestParam.getRentPrice().isEmpty()) {
            double min = 0, max = 0;
            switch (requestParam.getRentPrice()) {
                case "under_2m":
                    max = 2_000_000;
                    queryBuilder.append(" AND rent_price < ").append(max);
                    break;
                case "2_to_5m":
                    min = 2_000_000;
                    max = 5_000_000;
                    queryBuilder.append(" AND rent_price BETWEEN ").append(min).append(" AND ").append(max);
                    break;
                case "5_to_10m":
                    min = 5_000_000;
                    max = 10_000_000;
                    queryBuilder.append(" AND rent_price BETWEEN ").append(min).append(" AND ").append(max);
                    break;
                case "above_10m":
                    min = 10_000_000;
                    queryBuilder.append(" AND rent_price > ").append(min);
                    break;
                default:
                    break;
            }
        }

        if (requestParam.getArea() != null && !requestParam.getArea().isNaN()) {
            queryBuilder.append(" AND area >= :area");
            params.put("area", "%" + requestParam.getArea() + "%");
        }

        if (requestParam.getMinStartDate() != null) {
            queryBuilder.append(" AND rent_date >= :maxStartDate");
            params.put("maxStartDate", "%" + requestParam.getMaxStartDate() + "%");
        }

        if (requestParam.getMaxStartDate() != null) {
            queryBuilder.append(" AND rent_date <= :maxStartDate");
            params.put("maxStartDate", "%" + requestParam.getMaxStartDate() + "%");
        }

        return new QueryFilterData(queryBuilder.toString(), params);
    }

}
