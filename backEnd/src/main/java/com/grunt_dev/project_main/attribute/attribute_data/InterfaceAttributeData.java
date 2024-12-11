package com.grunt_dev.project_main.attribute.attribute_data;

import org.springframework.data.jpa.domain.Specification;

public interface InterfaceAttributeData<A, M> {
    Specification<M> getAttribute(A requestParam);
}
