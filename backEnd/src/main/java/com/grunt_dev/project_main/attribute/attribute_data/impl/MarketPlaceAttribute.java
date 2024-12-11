package com.grunt_dev.project_main.attribute.attribute_data.impl;

import com.grunt_dev.project_main.attribute.attribute_class.FindMarketPlace;
import com.grunt_dev.project_main.attribute.attribute_data.InterfaceAttributeData;
import com.grunt_dev.project_main.model.MarketPlace;
import jakarta.persistence.criteria.Predicate;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class MarketPlaceAttribute implements InterfaceAttributeData<FindMarketPlace, MarketPlace> {

    @Override
    public Specification<MarketPlace> getAttribute(FindMarketPlace requestParam) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (requestParam.getName() != null && !requestParam.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + requestParam.getName().toLowerCase() + "%"));
            }

            if (requestParam.getAddress() != null && !requestParam.getAddress().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + requestParam.getAddress().toLowerCase() + "%"));
            }

            if (requestParam.getArea() != null && !requestParam.getArea().isNaN()) {
                double area = requestParam.getArea();
                double tolerance = 5.0;
                predicates.add(criteriaBuilder.between(root.get("area"), area - tolerance, area + tolerance));
            }

            if (requestParam.getRentPrice() != null) {
                switch (requestParam.getRentPrice()) {
                    case "under_2m":
                        predicates.add(criteriaBuilder.lessThan(root.get("salary"), 2000000));
                        break;
                    case "2_to_5m":
                        predicates.add(criteriaBuilder.between(root.get("salary"), 2000000, 5000000));
                        break;
                    case "5_to_10m":
                        predicates.add(criteriaBuilder.between(root.get("salary"), 5000000, 10000000));
                        break;
                    case "above_10m":
                        predicates.add(criteriaBuilder.greaterThan(root.get("salary"), 10000000));
                        break;
                    default:
                        break;
                }
            }

            if (requestParam.getMinStartDate() != null && requestParam.getMaxStartDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("rentDate"), requestParam.getMinStartDate(), requestParam.getMaxStartDate()));
            } else if (requestParam.getMinStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rentDate"), requestParam.getMinStartDate()));
            } else if (requestParam.getMaxStartDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rentDate"), requestParam.getMaxStartDate()));
            }

            if (requestParam.getTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("typeMarketPlace").get("id"), requestParam.getTypeId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
