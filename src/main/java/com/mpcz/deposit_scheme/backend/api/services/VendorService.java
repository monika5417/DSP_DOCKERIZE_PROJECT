package com.mpcz.deposit_scheme.backend.api.services; 

import java.util.List;

import javax.validation.Valid;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Vendor;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByApplicationIdException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByVendorIdException;
import com.mpcz.deposit_scheme.backend.api.request.VendorRejectionForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface VendorService {
	
	List<ConsumerApplicationDetail> findAllConsumerApplicationByVendorId(Long id) throws ConsumerNotFoundByVendorIdException;
	
	//Response<List<ConsumerApplicationDetail>> updateConsumerApplicationDetailsByApplicationId(@Valid List<VendorRejectionForm> vendorRejectionForm) throws ConsumerNotFoundByApplicationIdException;
	
	ConsumerApplicationDetail updateConsumerApplicationDetailsByApplicationId(@Valid VendorRejectionForm vendorRejectionForm) throws ConsumerNotFoundByApplicationIdException, ConsumerApplicationDetailException;

	Vendor findByVendorCode(String contractorId);

}
