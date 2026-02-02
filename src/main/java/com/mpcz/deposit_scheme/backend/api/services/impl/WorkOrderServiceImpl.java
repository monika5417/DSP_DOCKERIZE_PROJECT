package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.domain.WorkOrder;
import com.mpcz.deposit_scheme.backend.api.enums.StatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkOrderTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.WorkOrderRepository;
import com.mpcz.deposit_scheme.backend.api.request.WorkOrderTypeForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.services.WorkOrderService;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

	Logger logger = LoggerFactory.getLogger(WorkOrderServiceImpl.class);

	@Autowired
	private WorkOrderRepository workOrderRepository;

	@Autowired
	private UploadService uploadService;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Autowired
	ConsumerApplicationDetailService ConsumerApplicationDetailService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public WorkOrder saveWorkOrderDetail(WorkOrderTypeForm workOrderTypeForm, MultipartFile docWorkOrder)
			throws WorkOrderTypeException, DocumentTypeException, ConsumerApplicationDetailException {

		Response<WorkOrder> response = new Response<>();

		if (Objects.isNull(workOrderTypeForm)) {
			logger.error("WorkOrder Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new WorkOrderTypeException(response);
		}

		Upload workOrderUpload = null;

		if (workOrderTypeForm.getConsumerApplicationId() == null
				|| workOrderTypeForm.getConsumerApplicationId().compareTo(0l) <= 0) {

			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application Id not found  ");
			throw new WorkOrderTypeException(response);

		}


		if (docWorkOrder == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Please upload  WorkOrder doc ");
			throw new WorkOrderTypeException(response);

		}
		WorkOrder workOrder = null;

		try {
			workOrder = findByConsumersApplicationDetailConsumerApplicationId(workOrderTypeForm.getConsumerApplicationId());
		} catch (WorkOrderTypeException e) {
			e.printStackTrace();
		}

		if (workOrder != null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Application WorkOrder already submitted !  ");
			throw new WorkOrderTypeException(response);
		}

//		if (!demand.getDemandStatus().equals(StatusEnum.PENDING.getValue())) {
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(" Application demand already submitted !  ");
//			throw new DemandDetailException(response);
//		}

		workOrderUpload = uploadService.uploadFile(docWorkOrder, "WORK_ORDER");

		if (workOrderUpload == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error Uploding WorkOrder Document !  ");
			throw new WorkOrderTypeException(response);

		}

		ApplicationStatus appStatusDb = applicationStatusService.findById(9L);

		ConsumerApplicationDetail consumerApplicationDetail = ConsumerApplicationDetailService
				.findByConsumerApplicationId(workOrderTypeForm.getConsumerApplicationId());
		
		workOrder = new WorkOrder();

		workOrder.setConsumersApplicationDetail(consumerApplicationDetail);
		workOrder.setWorkOrderGenerationDate(workOrderTypeForm.getWorkOrderGenerationDate());
		workOrder.setDocWorkOrder(workOrderUpload);
		workOrder.setRemark(workOrderTypeForm.getRemark());
		workOrder.setWorkOrderStatus(StatusEnum.PENDING.getValue());

		WorkOrder workOrderDb = saveWorkOrderDetail(workOrder);

		consumerApplicationDetail.setApplicationStatus(appStatusDb);
		ConsumerApplicationDetailService.saveConsumerApplication(consumerApplicationDetail);

		return workOrderDb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public WorkOrder saveWorkOrderDetail(WorkOrder workOrderDoc) throws WorkOrderTypeException {
		WorkOrder workOrderDb = workOrderRepository.save(workOrderDoc);

		Response<WorkOrder> response = new Response<>();

		if (workOrderDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error saving WorkOrder !  ");
			throw new WorkOrderTypeException(response);
		}

		return workOrderDb;

	}

	@Override
	public WorkOrder findByConsumersApplicationDetailConsumerApplicationId(Long consumerApplicationId)
			throws WorkOrderTypeException {
		Response<WorkOrder> response = new Response<>();

		WorkOrder workOrderDb = null;
		Optional<WorkOrder> workOrderOptional = null;
		try {
			workOrderOptional = workOrderRepository
					.findByConsumersApplicationDetailConsumerApplicationId(consumerApplicationId);
			if (workOrderOptional.isPresent()) {
				workOrderDb = workOrderOptional.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (workOrderDb == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(" Error fetching  WorkOrder Record !  ");
			throw new WorkOrderTypeException(response);
		}

		return workOrderDb;

	}
}
