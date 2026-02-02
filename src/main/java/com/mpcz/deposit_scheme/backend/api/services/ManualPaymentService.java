package com.mpcz.deposit_scheme.backend.api.services;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface ManualPaymentService {

    List<ManualPayment> getAllManualPayments();

    ResponseEntity<?> createManualPayment(ManualPayment manualPayment);

    Response getPaymentByApplication(String consumerAppNo);

	ManualPayment getManualPaymentByBillRefNo(String billRefNo);

    // Add other service methods as needed
}
