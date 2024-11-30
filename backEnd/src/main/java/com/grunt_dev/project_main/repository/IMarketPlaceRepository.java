package com.grunt_dev.project_main.repository;

import com.grunt_dev.project_main.model.MarketPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IMarketPlaceRepository extends JpaRepository<MarketPlace, Long>, JpaSpecificationExecutor<MarketPlace> {
}
