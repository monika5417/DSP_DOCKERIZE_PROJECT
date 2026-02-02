package com.mpcz.deposit_scheme.backend.api.services;

import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.WorkOrder;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkOrderTypeException;
import com.mpcz.deposit_scheme.backend.api.request.WorkOrderTypeForm;

public interface WorkOrderService {

	WorkOrder saveWorkOrderDetail(
			WorkOrderTypeForm workOrderTypeForm, MultipartFile docWorkOrder)
					throws WorkOrderTypeException, DocumentTypeException, ConsumerApplicationDetailException;

	WorkOrder saveWorkOrderDetail(WorkOrder workOrderDoc) throws WorkOrderTypeException;

	WorkOrder findByConsumersApplicationDetailConsumerApplicationId(Long consumerApplicationId)
			throws WorkOrderTypeException;

	//void saveDemandDetail(WorkOrderTypeForm workOrderTypeForm, MultipartFile workOrderDoc);
	
	
	
	
}
