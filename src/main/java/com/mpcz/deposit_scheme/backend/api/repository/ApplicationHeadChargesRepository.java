package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationHeadCharges;

public interface ApplicationHeadChargesRepository extends JpaRepository<ApplicationHeadCharges, Long>{
	
	ApplicationHeadCharges findByConsumerApplicationDetailsConsumerApplicationIdAndChargesTypeChargeTypeIdAndPaymentTypePaymentTypeId(Long consumerApplicationId,Long chargesTypeId,Long paymentTypeId);
	
 	List<ApplicationHeadCharges> findByConsumerApplicationDetailsConsumerApplicationIdAndPaymentTypePaymentTypeId(Long consumerApplicationId,Long paymentTypeId);
	
 
	

}
