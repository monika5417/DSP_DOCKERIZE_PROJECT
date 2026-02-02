package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.PaymentTypeCharges;

@Repository
public interface PaymentTypeChargesRepository extends JpaRepository<PaymentTypeCharges, Long> {

	List<PaymentTypeCharges> findByPaymentTypePaymentTypeId(Long paymentTypeId);

}
