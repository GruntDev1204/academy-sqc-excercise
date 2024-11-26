
package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.config.HibernateConnection;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.TypeMarketPlace;
import com.da_thao.project_test.repository.InterfaceRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class TypeMarketPlaceRepository implements InterfaceRepository<TypeMarketPlace, Object> {
    HibernateConnection<TypeMarketPlace> connection;

    @Override
    public List<TypeMarketPlace> getAll(Object requestParam) {
        String hql = "from TypeMarketPlace";

        List<TypeMarketPlace> tms = connection.getQuery(hql);
        if (tms == null) throw new ApiException(ErrorCode.GENERAL_GET_FAILED);

        return tms;
    }

    @Override
    public TypeMarketPlace findById(Long id) {
        TypeMarketPlace tm = connection.getQuery(id, TypeMarketPlace.class);
        if (tm == null) throw new ApiException(ErrorCode.GENERAL_NOT_FOUND);

        return tm;
    }

    @Override
    public TypeMarketPlace create(TypeMarketPlace data) {
        connection.useTask("create", data);
        return data;
    }

    @Override
    public Boolean delete(Long id) {
        TypeMarketPlace tm = this.findById(id);
        connection.useTask("delete", tm);

        return true;
    }

    @Override
    public TypeMarketPlace update(Long id, TypeMarketPlace newData) {
        TypeMarketPlace tm = this.findById(id);
        tm.setName(newData.getName());
        connection.useTask("update", tm);

        return tm;
    }
}
