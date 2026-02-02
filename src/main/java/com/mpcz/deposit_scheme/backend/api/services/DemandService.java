package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.Demand;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.request.DemandTypeForm;


public interface DemandService {

	Demand saveDemandDetail(DemandTypeForm demandTypeForm, MultipartFile docDemand)
			throws DemandDetailException, DocumentTypeException, ConsumerApplicationDetailException;

	Demand updateDemandDetail(Long id, DemandTypeForm demandTypeForm, MultipartFile docDemand)
			throws DemandDetailException, DocumentTypeException, ConsumerApplicationDetailException,
			PreviousStageDetailException;

	Demand saveDemandDetail(Demand demandDoc) throws DemandDetailException;

	Demand findByConsumersApplicationDetailConsumerApplicationId(Long consumerApplicationId)
			throws DemandDetailException;

	Demand findByConsumerApplicationId(Long consumerApplicationId);

	List<Demand> findAll() throws DemandDetailException;

	List<Demand> findConsumerApplicationEstimate()throws DemandDetailException, SchemeTypeException;

	 

}
