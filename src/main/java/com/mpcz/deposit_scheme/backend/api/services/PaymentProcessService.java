package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Invoice;
import com.mpcz.deposit_scheme.backend.api.domain.Payment;

public interface PaymentProcessService {

	Payment payment(Invoice invoice);

}
