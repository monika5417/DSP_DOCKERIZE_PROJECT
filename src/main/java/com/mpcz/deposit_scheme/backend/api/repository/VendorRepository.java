package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

	@Query(value = "SELECT v.* FROM Vendor v WHERE v.IS_ACTIVE= 1 ORDER BY v.VENDOR_NAME ASC", nativeQuery = true)
	public List<Vendor> findAllActiveVendor();
	

	public Vendor findByVendorCode(String contractorId);

}
