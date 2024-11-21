package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.config.DatabaseConnection;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.dto.data_filter.MarketPlaceFilter;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.MartketPlace;
import com.da_thao.project_test.repository.InterfaceRepository;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Primary
@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class MarketPlaceRepository implements InterfaceRepository<MartketPlace, MarketPlaceFilter> {

    private MartketPlace getBuild(ResultSet resultSet) throws SQLException {
        return MartketPlace.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .address(resultSet.getString("address"))
                .typeId(resultSet.getInt("type_id"))
                .area(resultSet.getDouble("area"))
                .rentPrice(resultSet.getDouble("rent_price"))
                .startDate(resultSet.getDate("start_date").toLocalDate())
                .nameType(resultSet.getString("type_name"))
                .build();
    }

    @NotNull
    private StringBuilder getFilterBuilder(MarketPlaceFilter requestParam) {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT mp.*, tmp.name AS type_name " +
                        "FROM market_places mp " +
                        "JOIN type_market_places tmp ON mp.type_id = tmp.id " +
                        "WHERE 1=1"
        );
        System.out.printf(queryBuilder.toString());

        if (requestParam.getId() != null) {
            queryBuilder.append(" AND mp.id = ").append(requestParam.getId());
        }
        if (requestParam.getName() != null && !requestParam.getName().isEmpty()) {
            queryBuilder.append(" AND mp.name LIKE '%").append(requestParam.getName()).append("%'");
        }
        if (requestParam.getAddress() != null && !requestParam.getAddress().isEmpty()) {
            queryBuilder.append(" AND mp.address LIKE '%").append(requestParam.getAddress()).append("%'");
        }
        if (requestParam.getNameType() != null && !requestParam.getNameType().isEmpty()) {
            queryBuilder.append(" AND  tmp.name  LIKE '%").append(requestParam.getNameType()).append("%'");
        }
        if (requestParam.getRentPrice() != null) {
            queryBuilder.append(" AND mp.rent_price <= ").append(requestParam.getRentPrice());
        }
        if (requestParam.getArea() != null) {
            queryBuilder.append(" AND mp.area >=   ").append(requestParam.getArea());
        }
        if (requestParam.getMinStartDate() != null) {
            queryBuilder.append(" AND mp.start_date >= '").append(requestParam.getMinStartDate()).append("'");
        }
        if (requestParam.getMaxStartDate() != null) {
            queryBuilder.append(" AND mp.start_date <= '").append(requestParam.getMaxStartDate()).append("'");
        }
        return queryBuilder;
    }

    @Override
    public List<MartketPlace> getAll(MarketPlaceFilter requestParam) {
        List<MartketPlace> martketPlaces = new ArrayList<>();
        StringBuilder queryBuilder = this.getFilterBuilder(requestParam);

        try (ResultSet resultSet = DatabaseConnection.executeQuery(queryBuilder.toString())) {
            while (resultSet.next()) {
                MartketPlace m = this.getBuild(resultSet);
                martketPlaces.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return martketPlaces;
    }

    @Override
    public MartketPlace findById(Integer id) {
        MartketPlace martketPlace = null;
        String query = "SELECT mp.*, tmp.name AS type_name FROM market_places mp " +
                " JOIN type_market_places tmp ON mp.type_id = tmp.id" +
                "  WHERE mp.id = ?";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(query, id)) {
            if (resultSet.next()) {
                martketPlace = this.getBuild(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (martketPlace == null) throw new ApiException(ErrorCode.DEPARTMENT_NOT_FOUND);
        return martketPlace;
    }

    @Override
    public MartketPlace create(MartketPlace model) {
        String query = "INSERT INTO market_places" +
                " (name, address, type_id, area, rent_price, start_date)" +
                " VALUES (? , ? ,? , ? , ? , ?) ";

        Integer rowsAffected = DatabaseConnection.executeUpdate(query, model.getName()
                , model.getAddress(), model.getTypeId(), model.getArea(), model.getRentPrice(),
                model.getStartDate());

        if (rowsAffected > 0) {
            return findById(rowsAffected);
        } else throw new ApiException(ErrorCode.EMPLOYEES_CREATE_FAILED);
    }

    @Override
    public Boolean delete(Integer id) {
        MartketPlace m = this.findById(id);
        if (m == null) return false;

        String query = "DELETE FROM market_places " +
                "WHERE id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(query, id);

        return true;
    }

    @Override
    public MartketPlace update(Integer id, MartketPlace model) {
        MartketPlace updateMarketPlace = this.findById(id);
        if (updateMarketPlace == null) return null;

        updateMarketPlace.setName(model.getName());
        updateMarketPlace.setAddress(model.getAddress());
        updateMarketPlace.setArea(model.getArea());
        updateMarketPlace.setRentPrice(model.getRentPrice());
        updateMarketPlace.setStartDate(model.getStartDate());
        updateMarketPlace.setTypeId(model.getTypeId());

        String query = "UPDATE market_places SET" +
                " name = ? , address = ?  , area = ?  , " +
                "rent_price = ? , start_date = ? , type_id = ? " +
                " WHERE id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(query,
                updateMarketPlace.getName()
                , updateMarketPlace.getAddress(),
                updateMarketPlace.getArea(),
                updateMarketPlace.getRentPrice(),
                updateMarketPlace.getStartDate(), updateMarketPlace.getTypeId(), id);
        if (rowsAffected > 0) return updateMarketPlace;
        else throw new ApiException(ErrorCode.EMPLOYEES_UPDATE_FAILED);
    }
}
