package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.domain.VendorWorkOrder;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VendorWorkOrderRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.services.VendorWorkOrderService;

@Service
public class VendorWorkOrderServiceImpl implements VendorWorkOrderService {

	@Autowired
	private VendorWorkOrderRepository vendorWorkOrderRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Override
	public VendorWorkOrder save(VendorWorkOrder vendorWorkOrder) {
		// TODO Auto-generated method stub
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(vendorWorkOrder.getConsumerApplicationNo());
		ApplicationStatus appStatus = null;
		VendorWorkOrder vendorWorkOrders = new VendorWorkOrder();

		int year = LocalDate.now().getYear();
		String valueOf = String.valueOf(year);
		int previousYear = year - 1;
		vendorWorkOrders.setCurrentYear(valueOf);
		vendorWorkOrders.setPreviousYear(String.valueOf(previousYear));
		vendorWorkOrders.setConsumerApplicationNo(vendorWorkOrder.getConsumerApplicationNo());
		vendorWorkOrders.setDgmStcId(vendorWorkOrder.getDgmStcId());
		vendorWorkOrders.setWorkOrderDate(vendorWorkOrder.getWorkOrderDate());
		vendorWorkOrders.setWorkOrderNo(vendorWorkOrder.getWorkOrderNo());
		vendorWorkOrders.setWorkOrderGeneratedRoleCode(vendorWorkOrder.getWorkOrderGeneratedRoleCode());

		ApplicationStatus applicationStatus = applicationStatusService
				.findById(ApplicationStatusEnum.PENDING_FOR_WORK_COMPLETION.getId());
		consumerApplicationDetail.setApplicationStatus(applicationStatus);
		if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)
				|| consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(2L)) {
			appStatus = applicationStatusService
					.findById(ApplicationStatusEnum.PENDING_FOR_CONTRACTOR_SELECTION_AFTER_TENDER_BY_DGM_STC.getId());
			consumerApplicationDetail.setApplicationStatus(appStatus);
		} else {
			appStatus = applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_WORK_COMPLETION.getId());

		}

		consumerApplicationDetail.setApplicationStatus(appStatus);

		VendorWorkOrder findByApplicationNo = vendorWorkOrderRepository
				.findByApplicationNo(vendorWorkOrder.getConsumerApplicationNo());
		if (findByApplicationNo != null) {
			vendorWorkOrders.setWoVersion("V1");
			appStatus = applicationStatusService.findById(ApplicationStatusEnum.PENDING_FOR_WORK_COMPLETION.getId());
			consumerApplicationDetail.setApplicationStatus(appStatus);
			consumerApplictionDetailRepository.save(consumerApplicationDetail);
			vendorWorkOrderRepository.save(vendorWorkOrders);
		}
		consumerApplictionDetailRepository.save(consumerApplicationDetail);
		return vendorWorkOrderRepository.save(vendorWorkOrders);

	}

	@Override
	public VendorWorkOrder update(VendorWorkOrder vendorWorkOrder) {
		// TODO Auto-generated method stub
		return vendorWorkOrderRepository.save(vendorWorkOrder);
	}

	@Override
	public Boolean delete(Long workOrderId) {
		// TODO Auto-generated method stub
		vendorWorkOrderRepository.deleteById(workOrderId);
		return Boolean.TRUE;
	}

	@Override
	public VendorWorkOrder findById(Long workOrderId) {
		// TODO Auto-generated method stub
		return vendorWorkOrderRepository.findById(workOrderId).orElseThrow(null);
	}

	@Override
	public List<VendorWorkOrder> findAll() {
		// TODO Auto-generated method stub
		return vendorWorkOrderRepository.findAll();
	}

	@Override
	public VendorWorkOrder findByApplicationNo(String applicationNo) {
		// TODO Auto-generated method stub
		return vendorWorkOrderRepository.findByApplicationNo(applicationNo);
	}

	@Override
	public ApplicationDocument uploadWorkOrderFile(String consumerApplicationNo, MultipartFile docWorkOrderOptional)
			throws ConsumerApplicationDetailException, DocumentTypeException {
		ConsumerApplicationDetail consumerApplicationNumberDB = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (consumerApplicationNumberDB == null) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Consumer Application Not Found In Database"));
		}

		Upload workOrderUpload = null;
		ApplicationDocument savedDocument=null;

		if (docWorkOrderOptional != null) {
			workOrderUpload = uploadService.uploadFile(docWorkOrderOptional, "WORK_ORDER_FILE");
			if (workOrderUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Work Order Not Uploaded."));
			}
		}
		ApplicationDocument documentDB = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationNumberDB.getConsumerApplicationId());
		if (Objects.isNull(documentDB)) {
			ApplicationDocument applicationDocument = new ApplicationDocument();
			applicationDocument.setConsumerApplicationDetail(consumerApplicationNumberDB);
			applicationDocument.setDocWorkOrderFile(workOrderUpload);
			 savedDocument = applicationDocumentRepository.save(applicationDocument);
		} else {
			documentDB.setDocWorkOrderFile(workOrderUpload);
			savedDocument=applicationDocumentRepository.save(documentDB);
		}

		return savedDocument;
	}

	@Override
	public ApplicationDocument uploadDemandNoteFile(String consumerApplicationNo, MultipartFile docDemandNote) throws ConsumerApplicationDetailException, DocumentTypeException {
		ConsumerApplicationDetail consumerApplicationNumberDB = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (consumerApplicationNumberDB == null) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Consumer Application Not Found In Database"));
		}

		Upload demandNoteUpload = null;
		ApplicationDocument savedDocument=null;

		if (docDemandNote != null) {
			demandNoteUpload = uploadService.uploadFile(docDemandNote, "DEMAND_NOTE_FILE");
			if (demandNoteUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Demand Note Not Uploaded."));
			}
		}
		ApplicationDocument documentDB = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationNumberDB.getConsumerApplicationId());
		if (Objects.isNull(documentDB)) {
			ApplicationDocument applicationDocument = new ApplicationDocument();
			applicationDocument.setConsumerApplicationDetail(consumerApplicationNumberDB);
			applicationDocument.setDocDemandNoteFile(demandNoteUpload);
			 savedDocument = applicationDocumentRepository.save(applicationDocument);
		} else {
			documentDB.setDocDemandNoteFile(demandNoteUpload);
			savedDocument=applicationDocumentRepository.save(documentDB);
		}

		return savedDocument;
	}

	@Override
	public ApplicationDocument uploadDraftAgreement(String consumerApplicationNo, MultipartFile docDraftAgreement) throws ConsumerApplicationDetailException, DocumentTypeException {
		ConsumerApplicationDetail consumerApplicationNumberDB = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (consumerApplicationNumberDB == null) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Consumer Application Not Found In Database"));
		}

		Upload draftAgreementUpload = null;
		ApplicationDocument savedDocument=null;

		if (docDraftAgreement != null) {
			draftAgreementUpload = uploadService.uploadFile(docDraftAgreement, "DOC_DRAFT_AGREEMENT");
			if (draftAgreementUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "DOC_DRAFT_AGREEMENT Not Uploaded."));
			}
		}
		ApplicationDocument documentDB = applicationDocumentRepository
				.findByConsumerApplicationDetailId(consumerApplicationNumberDB.getConsumerApplicationId());
		if (Objects.isNull(documentDB)) {
			ApplicationDocument applicationDocument = new ApplicationDocument();
			applicationDocument.setConsumerApplicationDetail(consumerApplicationNumberDB);
			applicationDocument.setDocDraftAgreement(draftAgreementUpload);
			 savedDocument = applicationDocumentRepository.save(applicationDocument);
		} else {
			documentDB.setDocDraftAgreement(draftAgreementUpload);
			savedDocument=applicationDocumentRepository.save(documentDB);
		}

		return savedDocument;
	}

}
