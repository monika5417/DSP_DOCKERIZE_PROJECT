package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.VendorWorkOrder;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface VendorWorkOrderService {
	
	public VendorWorkOrder save(VendorWorkOrder vendorWorkOrder) ;

	public VendorWorkOrder update(VendorWorkOrder vendorWorkOrder);
	
	public Boolean delete(Long workOrderId);// throws RegionException;

	public VendorWorkOrder findById(Long workOrderId);// throws RegionException;
	
	public List<VendorWorkOrder> findAll();// throws RegionException;
	
	public VendorWorkOrder findByApplicationNo(String applicationNo);

	public ApplicationDocument uploadWorkOrderFile(String consumerApplicationNo, MultipartFile docWorkOrderOptional) throws ConsumerApplicationDetailException, DocumentTypeException;

	public ApplicationDocument uploadDemandNoteFile(String consumerApplicationNo, MultipartFile docDemandNote) throws ConsumerApplicationDetailException, DocumentTypeException;

	public ApplicationDocument uploadDraftAgreement(String consumerApplicationNo, MultipartFile docDraftAgreement) throws ConsumerApplicationDetailException, DocumentTypeException;


} 
