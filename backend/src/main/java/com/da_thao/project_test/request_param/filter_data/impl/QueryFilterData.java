package com.da_thao.project_test.request_param.filter_data.impl;

import com.da_thao.project_test.model.Employee;
import org.hibernate.query.Query;

import java.util.Map;

public record QueryFilterData(String hql, Map<String, Object> params) {}

