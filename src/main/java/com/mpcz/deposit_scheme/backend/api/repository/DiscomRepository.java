package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.Discom;

public interface DiscomRepository extends JpaRepository<Discom, Long> {

	@Query(value = "SELECT d.* FROM discom d WHERE d.IS_ACTIVE= 1 ORDER BY d.DISCOM_NAME ASC", nativeQuery = true)
	public List<Discom> findAllActiveDiscom();

}
