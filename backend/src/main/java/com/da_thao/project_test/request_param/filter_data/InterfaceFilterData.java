package com.da_thao.project_test.request_param.filter_data;

import com.da_thao.project_test.request_param.filter_data.impl.QueryFilterData;

public interface InterfaceFilterData<F> {
    QueryFilterData getFilterData(F requestParam);
}
