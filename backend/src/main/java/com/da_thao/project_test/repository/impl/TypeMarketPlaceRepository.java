
package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.config.DatabaseConnection;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.Employee;
import com.da_thao.project_test.model.TypeMarketPlace;
import com.da_thao.project_test.repository.InterfaceRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
public class TypeMarketPlaceRepository implements InterfaceRepository<TypeMarketPlace, Object> {
    private TypeMarketPlace getBuild(ResultSet rs) throws SQLException {
        return TypeMarketPlace.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
    }

    @Override
    public List<TypeMarketPlace> getAll(Object requestParam) {
        List<TypeMarketPlace> tms = new ArrayList<>();

        String sql = "select * from type_market_places";
        try (ResultSet rs = DatabaseConnection.executeQuery(sql)) {
            while (rs.next()) {
                TypeMarketPlace tm = this.getBuild(rs);
                tms.add(tm);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tms;

    }

    @Override
    public TypeMarketPlace findById(Integer id) {

        TypeMarketPlace tm = null;
        String sql = "select * from type_market_places where id = ?";

        try (ResultSet rs = DatabaseConnection.executeQuery(sql, id)) {
            while (rs.next()) {
                tm = this.getBuild(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (tm == null) {
            throw new ApiException(ErrorCode.GENERAL_NOT_FOUND);
        }

        return tm;
    }

    @Override
    public TypeMarketPlace create(TypeMarketPlace typemarketplace) {
        String sql = "INSERT INTO type_market_places (name) VALUES (?)";

        Integer generatedId = DatabaseConnection.executeUpdate(sql, typemarketplace.getName());

        if (generatedId > 0) {
            return this.findById(generatedId);
        } else {
            throw new ApiException(ErrorCode.CREATE_FAILED);
        }

    }

    @Override
    public Boolean delete(Integer id) {
        TypeMarketPlace tm = this.findById(id);

        if (tm == null) throw new ApiException(ErrorCode.GENERAL_NOT_FOUND);

        String sql = "DELETE FROM type_market_places WHERE id = ?";
        Integer deleted = DatabaseConnection.executeUpdate(sql, id);

        return true;

    }

    @Override
    public TypeMarketPlace update(Integer id, TypeMarketPlace data) {
        TypeMarketPlace tm = this.findById(id);

        if (tm == null) throw new ApiException(ErrorCode.GENERAL_NOT_FOUND);

        tm.setName(data.getName());
        String sql = "UPDATE type_market_places SET name = ? WHERE id = ?";
        DatabaseConnection.executeUpdate(sql, data.getName(), id);

        return this.findById(id);

    }
}
