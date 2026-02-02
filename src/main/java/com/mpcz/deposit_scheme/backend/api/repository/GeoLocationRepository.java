package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.GeoLocation;

@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long>{

	GeoLocation findByConsumerApplicationNo(String consumerApplicationNO);

}
