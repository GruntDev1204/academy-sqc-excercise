package com.da_thao.project_test.Request_param.filter_data;

import java.util.List;

public interface FilterDataInterface<M , R> {
    List<M>  filterAll( List<M> dataModel , R dataFilter);
    M filterById( List<M> dataModel , Integer id);
}
