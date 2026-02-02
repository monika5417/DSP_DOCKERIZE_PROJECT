package com.mpcz.deposit_scheme.backend.api.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationDetailsFilterDTO;
import com.mpcz.deposit_scheme.backend.api.dto.ErpEstimateCalculatedDto;
import com.mpcz.deposit_scheme.backend.api.dto.GeoPendingStatusConsmersListResponse;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDocumentException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.GeoLocationException;
import com.mpcz.deposit_scheme.backend.api.exception.NatureOfWorkException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.SMSDirectServiceException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.SupplyVoltageException;
import com.mpcz.deposit_scheme.backend.api.exception.TariffCategoryException;
import com.mpcz.deposit_scheme.backend.api.exception.TaskTypeException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailForm;
import com.mpcz.deposit_scheme.backend.api.response.PageConsumerApplicationDetailDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface ConsumerApplicationDetailService {

	public ConsumerApplicationDetail saveConsumerApplicationDetail(
			ConsumerApplicationDetailForm consumerApplicationDetailForm,
			/*
			 * MultipartFile docAadhar, MultipartFile docPan, MultipartFile docEnergyBill,
			 * MultipartFile docRegistoryOrLease, MultipartFile docResidentialProof,
			 * MultipartFile docBullidingPermissonOrTncpApproval,
			 */MultipartFile docRow, MultipartFile docRegistry, MultipartFile docNagarNigamPermission,
			MultipartFile docDicOrGumasta, MultipartFile docShapathPatra, MultipartFile docTestReport,
			MultipartFile docT$cpPermission, MultipartFile docReraPermission, MultipartFile docDrawingNotarized,
			MultipartFile docColonyPrakoshth, MultipartFile docColonyLicence, MultipartFile docLoadSheet,
			MultipartFile docNoc, MultipartFile docAllPaperNotarized03Set, MultipartFile docCommittee,
			MultipartFile docDiversion, MultipartFile docNazul, MultipartFile docMap, MultipartFile docKhasraKhatoni,
			MultipartFile docMapCivilEngineer, MultipartFile docApplicationConsent, MultipartFile docPerforma5A,
			MultipartFile docPerforma5B, MultipartFile docPlotAreaDetailsWithOwner, MultipartFile docIndividualOrGroup,
			MultipartFile docAdministrative, MultipartFile docGst, MultipartFile energyBillDoc,
			MultipartFile docSamagraFile) throws ConsumerApplicationDetailException, DocumentTypeException,
			ConsumerApplicationSurveyException, SMSDirectServiceException, PaymentTypeException,
			ApplicationHeadChargesException, ApplicationDocumentException, SupplyVoltageException,
			TariffCategoryException, TaskTypeException, NatureOfWorkException;

	public Response<PageConsumerApplicationDetailDTO> findAllConsumerApplicationDetailPagination(Integer page,
			Integer size, ConsumerApplicationDetailsFilterDTO filterDTO) throws ConsumerApplicationDetailException;

	public Page<ConsumerApplicationDetail> findConsumerApplicationDetailPaginate(Integer page, Integer size,
			ConsumerApplicationDetailsFilterDTO filterDTO) throws ConsumerApplicationDetailException;

	public ConsumerApplicationDetail findConsumerApplicationDetailByApplicationNo(String consumerApplicationNo)
			throws ConsumerApplicationDetailException;

	public ConsumerApplicationDetail findByConsumerApplicationId(Long consumerApplicationId)
			throws ConsumerApplicationDetailException;

	ConsumerApplicationDetail saveConsumerApplication(ConsumerApplicationDetail consumerApplicationDetail)
			throws ConsumerApplicationDetailException;

	void cleanPreviousStageDataInConsumerApplicationDetail(ConsumerApplicationDetail consumerApplicationDetail)
			throws ConsumerApplicationDetailException;

	public List<ConsumerApplicationDetail> findConsumerApplicationDetailByDcCode(String dcCode)
			throws ConsumerApplicationDetailException, DistributionCenterException;

	public Response<PageConsumerApplicationDetailDTO> findAllConsumerApplicationDetailByApplicationStatusPagination(
			Integer page, Integer size, ConsumerApplicationDetailsFilterDTO filterDTO)
			throws ConsumerApplicationDetailException;

	public Page<ConsumerApplicationDetail> findConsumerApplicationDetailByApplicationStatusPaginate(Integer page,
			Integer size, ConsumerApplicationDetailsFilterDTO filterDTO) throws ConsumerApplicationDetailException;

	List<GeoPendingStatusConsmersListResponse> findConsumerApplicationDetailByConsumerId(Long consumerId)
			throws ConsumerApplicationDetailException, GeoLocationException;

	public ConsumerApplicationDetail updateConsumerApplicationDetail(
			ConsumerApplicationDetailForm consumerApplicationDetailForm, MultipartFile docRow,
			MultipartFile docRegistry, MultipartFile docNagarNigamPermission, MultipartFile docDicOrGumasta,
			MultipartFile docShapathPatra, MultipartFile docTestReport, MultipartFile docT$cpPermission,
			MultipartFile docReraPermission, MultipartFile docDrawingNotarized, MultipartFile docColonyPrakoshth,
			MultipartFile docColonyLicence, MultipartFile docLoadSheet, MultipartFile docNoc,
			MultipartFile docAllPaperNotarized03Set, MultipartFile docCommittee, MultipartFile docDiversion,
			MultipartFile docNazul, MultipartFile docMap, MultipartFile docKhasraKhatoni,
			MultipartFile docMapCivilEngineer, MultipartFile docApplicationConsent, MultipartFile docPerforma5A,
			MultipartFile docPerforma5B, MultipartFile docPlotAreaDetailsWithOwner, MultipartFile docIndividualOrGroup,
			MultipartFile docAdministrative, Long id, MultipartFile docGst,MultipartFile consentletter) throws ConsumerApplicationDetailException,
			DocumentTypeException, ConsumerApplicationSurveyException, SMSDirectServiceException, PaymentTypeException,
			ApplicationHeadChargesException, ApplicationDocumentException, SupplyVoltageException,
			TariffCategoryException, TaskTypeException, NatureOfWorkException ;


	public List<Map<String, String>> findAllConsumerDetail() throws ConsumerApplicationDetailException;

	public Response findConsumerApplicationDetailsByCityCricleAndBhopal(long circleid);

//	O&M ko dikhna hai
	public Response findConsumerApplicationDetailsByCityCricleAndBhopalBasedOnNullValue(long circleid);

	public Response<?> rejectApplication(String consumerApplicationNumber, String rejectedApplicationDate,
			String rejectedRemark, String rejectApplicationGMName, Boolean gmAccepted);

//	public Response<?> jeLoadApplication(String consumerApplicationNumber, String jeLoad, String jeLoadUnitKwYaKva);

	public Response<?> jeLoadApplication(String consumerApplicationNumber, String jeLoad, String jeLoadUnitKwYaKva,
			int goodMaterialOrNot);

//	public ErpEstimateCalculatedDto getConsumerChalan(String consumerApplicationNumber) throws ConsumerApplicationDetailException, SchemeTypeException, Exception;

	Response<?> rejectProposalApplication(String consumerApplicationNumber, String rejectionProposal,
			String rejectionRemark, MultipartFile docRejectProposal, String userId, String userName, String role)
			throws ConsumerApplicationDetailException, DocumentTypeException;

	// public ConsumerApplicationDetail
	// findConsumerApplicationDetailByConsumerId(Long consumerId)throws
	// ConsumerApplicationDetailException, GeoLocationException;

	public Response<PageConsumerApplicationDetailDTO> findAllConsumerApplicationDetailNewPagination(Integer page,
			Integer size, ConsumerApplicationDetailsFilterDTO filterDTO, String filterType, String filterValue)
			throws ConsumerApplicationDetailException;

	public Response<?> jeReturnAmountApplication(String consumerApplicationNumber, BigDecimal jeReturnAmount);


	
	public ConsumerApplicationDetail updateConsumerApplicationDetailChangeSchemeType(
			ConsumerApplicationDetailForm consumerApplicationDetailForm, MultipartFile docRegistry,
			MultipartFile docT$cpPermission, MultipartFile docReraPermission, MultipartFile docNoc,
			MultipartFile docKhasraKhatoni, MultipartFile docIndividualOrGroup, MultipartFile docAdministrative,
			Long id, MultipartFile docGst, MultipartFile docEnergyBill, MultipartFile docSamagraFile,
			MultipartFile docLoadSheet, MultipartFile docMap,MultipartFile docconsentletter)
			throws ConsumerApplicationDetailException, DocumentTypeException, ApplicationDocumentException;

	public Response updateMKMYDocument(MultipartFile samagraFile, MultipartFile khasraFile,
			String consumerApplicationNo) throws DocumentTypeException;

}
