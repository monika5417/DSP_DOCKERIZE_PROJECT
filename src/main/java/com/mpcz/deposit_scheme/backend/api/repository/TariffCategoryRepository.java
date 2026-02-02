package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.TariffCategory;

@Repository
public interface TariffCategoryRepository extends JpaRepository<TariffCategory, Long> {

	@Query(value = "SELECT tc.* FROM TARIFF_CATEGORY tc WHERE tc.IS_ACTIVE= 1 ORDER BY tc.TARIFF_CATEGORY_CODE ASC", nativeQuery = true)
	public List<TariffCategory> findAllActiveTariffCategory();
}
