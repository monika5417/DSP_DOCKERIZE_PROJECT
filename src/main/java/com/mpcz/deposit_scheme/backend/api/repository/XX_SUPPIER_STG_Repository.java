package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpcz.deposit_scheme.backend.api.domain.XX_SUPPIER_STG;

public interface XX_SUPPIER_STG_Repository extends JpaRepository<XX_SUPPIER_STG, Long> {

	@Query(value="select * from XXEPIC.XX_SUPPIER_STG@ERP_TEST where VENDOR_ID=:vendorId",nativeQuery = true)
	XX_SUPPIER_STG getSupplierDataByVendorId(Long vendorId);
	
	@Query(value="select * from XXEPIC.XX_SUPP_SITE_STG@ERP_TEST where VENDOR_ID=:vendorId",nativeQuery = true)
	Map<String,Object> getSupplierSiteStgDataByVendorId(Long vendorId);
	
	
	
}
