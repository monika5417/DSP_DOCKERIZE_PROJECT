package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mpcz.deposit_scheme.backend.api.domain.ChargeRateMaster;

public interface ChargeRateMasterRepository extends JpaRepository<ChargeRateMaster, Long> {
	
	
//	@Query(value = "select * from CHARGE_RATE_MASTER where  CAST(START_DATE AS DATE)  <= CAST(:todayDate AS date)  and CAST(END_DATE AS DATE)  >= CAST(:todayDate AS date)", nativeQuery = true)
	
//	@Query(value = "select * from CHARGE_RATE_MASTER where FORMAT(START_DATE, 'ddMMyyyy')   <=  FORMAT(:todayDate, 'ddMMyyyy')   and FORMAT(END_DATE, 'ddMMyyyy') >= FORMAT(:todayDate, 'ddMMyyyy')", nativeQuery = true)
	
	
	@Query(value = "select * from CHARGE_RATE_MASTER where  TO_DATE(START_DATE, 'YYYY-MM-DD')     <=  :todayDate  and  TO_DATE(END_DATE, 'YYYY-MM-DD')  >=  :todayDate ", nativeQuery = true)
    public ChargeRateMaster findChargeByDate(@Param("todayDate") Date todayDate);

	
	//	ChargeRateMaster   findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date todayDate,Date endDate);
	
	List<ChargeRateMaster> findByChargesTypeChargeTypeId(Long chargeTypeId);
	
	ChargeRateMaster   findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date todayDate,Date endDate);

}
