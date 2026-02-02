package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.SupplyVoltage;

@Repository
public interface SupplyVoltageRepository extends JpaRepository<SupplyVoltage, Long> {

	@Query(value = "SELECT sv.* FROM SUPPLY_VOLTAGE sv WHERE sv.IS_ACTIVE= 1 ORDER BY sv.SUPPLY_VOLTAGE_NAME ASC", nativeQuery = true)
	public List<SupplyVoltage> findAllActiveSupplyVoltage();

}
