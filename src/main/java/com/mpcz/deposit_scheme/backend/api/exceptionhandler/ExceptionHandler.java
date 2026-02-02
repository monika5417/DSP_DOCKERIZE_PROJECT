package com.mpcz.deposit_scheme.backend.api.exceptionhandler;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDemandApprovalException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationDocumentException;
import com.mpcz.deposit_scheme.backend.api.exception.ApplicationHeadChargesException;
import com.mpcz.deposit_scheme.backend.api.exception.BillDeskPaymentResponseException;
import com.mpcz.deposit_scheme.backend.api.exception.ChangePasswordException;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.ConnectionPradayException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDcCorrectionException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationSurveyException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByApplicationIdException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerNotFoundByVendorIdException;
import com.mpcz.deposit_scheme.backend.api.exception.ContractorDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ContractorForQcException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DesignationException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DistrictException;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateException;
import com.mpcz.deposit_scheme.backend.api.exception.FeederException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.GeoLocationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvoiceException;
import com.mpcz.deposit_scheme.backend.api.exception.LandAreaUnitException;
import com.mpcz.deposit_scheme.backend.api.exception.LoadRequestedException;
import com.mpcz.deposit_scheme.backend.api.exception.MasterException;
import com.mpcz.deposit_scheme.backend.api.exception.MisExcelDataException;
import com.mpcz.deposit_scheme.backend.api.exception.MmkyCalculationException;
import com.mpcz.deposit_scheme.backend.api.exception.NatureOfWork14Exception;
import com.mpcz.deposit_scheme.backend.api.exception.NatureOfWorkException;
import com.mpcz.deposit_scheme.backend.api.exception.OtpInvalidException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.PreviousStageDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.RefundAmountException;
import com.mpcz.deposit_scheme.backend.api.exception.RegionException;
import com.mpcz.deposit_scheme.backend.api.exception.RoleException;
import com.mpcz.deposit_scheme.backend.api.exception.SanchayPaymentException;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.SubstationException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.exception.VendorAddMaterialException;
import com.mpcz.deposit_scheme.backend.api.exception.VendorException;
import com.mpcz.deposit_scheme.backend.api.exception.WorkOrderTypeException;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 *   The Global Exception Handler class Implementation.
 *   
 *     @author Amit
  @version 1.0
  @since 02-02-2019
 */

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@org.springframework.web.bind.annotation.ExceptionHandler(FormValidationException.class)
	public final ResponseEntity<Response<?>> handleFormValidationException(FormValidationException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside FormValidationException : " + ex.getMessage());
		logger.error(">>>>>>>>>>>>>>>Inside FormValidationException : ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(OtpInvalidException.class)
	public final ResponseEntity<Response<?>> handleOtpInvalidException(OtpInvalidException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside OtpInvalidException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
	public final ResponseEntity<Response<?>> handleBadCredentialsException(BadCredentialsException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside BadCredentialsException : " + ex.getMessage());
		logger.error(" ", ex);
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));

		Response response = new Response();
		response.setCode("607");
		response.setMessage("Exception : " + ex.getMessage());
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		errorList.add(errorDetails);
		response.setError(errorList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(InvalidAuthenticationException.class)
	public final ResponseEntity<Response> handleInvalidAuthenticationException(InvalidAuthenticationException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside InvalidAuthenticationException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<Response> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside DataNotFoundException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ChangePasswordException.class)
	public final ResponseEntity<Response> handleChangePasswordException(ChangePasswordException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ChangePasswordException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(CircleException.class)
	public final ResponseEntity<Response<?>> handleCircleException(CircleException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside CircleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DesignationException.class)
	public final ResponseEntity<Response<?>> handleDesignationException(DesignationException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside DesignationException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DistributionCenterException.class)
	public final ResponseEntity<Response<?>> handleDistributionCenterException(DistributionCenterException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside DistributionCenterException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DivisionException.class)
	public final ResponseEntity<Response<?>> handleDivisionException(DivisionException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside DivisionException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(FeederException.class)
	public final ResponseEntity<Response<?>> handleFeederException(FeederException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside FeederException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(MasterException.class)
	public final ResponseEntity<Response<?>> handleMasterException(MasterException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside MasterException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(RegionException.class)
	public final ResponseEntity<Response<?>> handleRegionException(RegionException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RegionException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(RoleException.class)
	public final ResponseEntity<Response<?>> handleRoleException(RoleException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(SubDivisionException.class)
	public final ResponseEntity<Response<?>> handleSubDivisionException(SubDivisionException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside SubDivisionException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(SubstationException.class)
	public final ResponseEntity<Response<?>> handleSubstationException(SubstationException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside SubstationException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
	public final ResponseEntity<Response> handleNullPointerException(NullPointerException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		logger.error(">>>>>>>>>>>>>>>NullPointerException :", ex);
		Response response = new Response();
		response.setCode("601");
		response.setMessage("Exception : " + ex.getMessage());
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		errorList.add(errorDetails);
		response.setError(errorList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// DataException

	@org.springframework.web.bind.annotation.ExceptionHandler(DataException.class)
	public final ResponseEntity<Response<?>> handleDataException(DataException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		logger.error(">>>>>>>>>>>>>>>DataException :", ex);
		Response response = new Response();
		response.setCode("602");
		response.setMessage("Exception : " + ex.getMessage());
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		errorList.add(errorDetails);
		response.setError(errorList);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// JpaObjectRetrievalFailureException
	@org.springframework.web.bind.annotation.ExceptionHandler(JpaObjectRetrievalFailureException.class)
	public final ResponseEntity<Response<?>> handleJpaObjectRetrievalFailureException(
			JpaObjectRetrievalFailureException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		logger.error(">>>>>>>>>>>>>>>JpaObjectRetrievalFailureException :", ex);

		Response response = new Response();
		response.setCode("603");
		response.setMessage("Exception : " + ex.getMessage());
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		errorList.add(errorDetails);
		response.setError(errorList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
	public final ResponseEntity<Response> handleIOException(IOException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		logger.error(">>>>>>>>>>>>>>>IOException :", ex);

		Response response = new Response();
		response.setCode("604");
		response.setMessage("Exception : " + ex.getMessage());
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		errorList.add(errorDetails);
		response.setError(errorList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ParseException.class)
	public final ResponseEntity<Response> handleParseException(ParseException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ParseException :", ex);
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		Response response = new Response();
		response.setCode("605");
		response.setMessage("Exception : " + ex.getMessage());
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		errorList.add(errorDetails);
		response.setError(errorList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(SQLException.class)
	public final ResponseEntity<Response> handleSQLException(SQLException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside SQLException :", ex);
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		Response response = new Response();
		response.setCode("606");
		response.setMessage("Exception : " + ex.getMessage());
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		errorList.add(errorDetails);
		response.setError(errorList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Response> handleRuntimeException(RuntimeException ex, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		logger.error(">>>>>>>>>>>>>>>Exception :", ex);

		Response response = new Response();
		response.setCode("607");
		response.setMessage("Exception : " + ex.getMessage());
		List<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
		errorList.add(errorDetails);
		response.setError(errorList);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// charitra code start

	@org.springframework.web.bind.annotation.ExceptionHandler(GeoLocationException.class)
	public final ResponseEntity<Response<?>> handleGeoException(GeoLocationException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ConsumerApplicationDetailException.class)
	public final ResponseEntity<Response<?>> handleConsumerApplicationDetailExceptionException(
			ConsumerApplicationDetailException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}
//	charitra code end

	// Monika code start

	@org.springframework.web.bind.annotation.ExceptionHandler(VendorAddMaterialException.class)
	public final ResponseEntity<Response<?>> handleVendorAddMaterialExceptionException(VendorAddMaterialException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(BillDeskPaymentResponseException.class)
	public final ResponseEntity<Response<?>> handleBillDeskPaymentResponseExceptionException(
			BillDeskPaymentResponseException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ApplicationDocumentException.class)
	public final ResponseEntity<Response<?>> handleApplicationDocumentExceptionException(
			ApplicationDocumentException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ConsumerException.class)
	public ResponseEntity<?> handleConsumerException(ConsumerException ex) {
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(RefundAmountException.class)
	public final ResponseEntity<Response<?>> handleRefundAmountException(RefundAmountException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ConnectionPradayException.class)
	public final ResponseEntity<Response<?>> handleConnectionPradayException(ConnectionPradayException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(SanchayPaymentException.class)
	public ResponseEntity<Response> handleSanchayPaymentException(SanchayPaymentException ex) {
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.BAD_REQUEST);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
	public final ResponseEntity<Response<?>> handleUserException(UserException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	// Monika code end

	@org.springframework.web.bind.annotation.ExceptionHandler(ConsumerNotFoundByApplicationIdException.class)
	public final ResponseEntity<Response<?>> handleConsumerNotFoundByApplicationIdException(
			ConsumerNotFoundByApplicationIdException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ConsumerNotFoundByApplicationIdException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ConsumerNotFoundByVendorIdException.class)
	public final ResponseEntity<Response<?>> handleConsumerNotFoundByVendorIdException(
			ConsumerNotFoundByVendorIdException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ConsumerNotFoundByVendorIdException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

//	ConsumerApplicationDetailException
	@org.springframework.web.bind.annotation.ExceptionHandler(ConsumerApplicationSurveyException.class)
	public final ResponseEntity<Response<?>> handleConsumerApplicationSurveyException(
			ConsumerApplicationSurveyException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside RoleException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DemandDetailException.class)
	public final ResponseEntity<Response<?>> handleConsumerNotFoundByVendorIdException(DemandDetailException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside DemandDetailException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(PreviousStageDetailException.class)
	public final ResponseEntity<Response<?>> handlePreviousStageDetailException(DemandDetailException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside DemandDetailException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ConsumerApplicationDcCorrectionException.class)
	public final ResponseEntity<Response<?>> handleConsumerApplicationDcCorrectionException(
			ConsumerApplicationDcCorrectionException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ConsumerApplicationDcCorrectionException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);

	}

	@org.springframework.web.bind.annotation.ExceptionHandler(WorkOrderTypeException.class)
	public final ResponseEntity<Response<?>> handleWorkOrderTypeException(WorkOrderTypeException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside WorkOrderTypeException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ApplicationDemandApprovalException.class)
	public final ResponseEntity<Response<?>> handleApplicationDemandApprovalException(
			ConsumerApplicationDcCorrectionException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ApplicationDemandApprovalException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(PaymentTypeException.class)
	public final ResponseEntity<Response<?>> handlePaymentTypeException(PaymentTypeException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside PaymentTypeException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ApplicationHeadChargesException.class)
	public final ResponseEntity<Response<?>> handleApplicationHeadChargesException(PaymentTypeException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ApplicationHeadChargesException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(InvoiceException.class)
	public final ResponseEntity<Response<?>> handleInvoiceException(InvoiceException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ApplicationHeadChargesException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(MisExcelDataException.class)
	public final ResponseEntity<Response<?>> handleMisExcelDataException(PaymentTypeException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside MisExcelDataException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(NatureOfWorkException.class)
	public final ResponseEntity<Response<?>> handleNatureOfWorkException(PaymentTypeException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside NatureOfWorkException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DistrictException.class)
	public final ResponseEntity<Response<?>> handleDistrictException(DistrictException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside DistrictException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(LoadRequestedException.class)
	public final ResponseEntity<Response<?>> handleLoadRequestedException(LoadRequestedException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside LoadRequestedException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(LandAreaUnitException.class)
	public final ResponseEntity<Response<?>> handleLandAreaUnitException(LandAreaUnitException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside LandAreaUnitException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);// ErpEstimateAmountException
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ErpEstimateException.class)
	public final ResponseEntity<Response<?>> handleErpEstimateException(ErpEstimateException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside LandAreaUnitException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ErpEstimateAmountException.class)
	public final ResponseEntity<Response<?>> handlerpEstimateAmountExceptionn(ErpEstimateAmountException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ErpEstimateAmountException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}
	// ContractorDetailException

	@org.springframework.web.bind.annotation.ExceptionHandler(ContractorDetailException.class)
	public final ResponseEntity<Response<?>> handleContractorDetailException(ContractorDetailException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ContractorDetailException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ContractorForQcException.class)
	public final ResponseEntity<Response<?>> handleContractorForQcException(ContractorForQcException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ContractorForQcException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(MmkyCalculationException.class)
	public final ResponseEntity<Response<?>> handlerMmkyCalculationException(MmkyCalculationException ex,
			WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ContractorForQcException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(VendorException.class)
	public final ResponseEntity<Response<?>> handlerVendorException(VendorException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ContractorForQcException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(com.mpcz.deposit_scheme.backend.api.exception.TransactionAmountException.class)
	public final ResponseEntity<Response<?>> TransactionAmountException(VendorException ex, WebRequest request) {
		logger.error(">>>>>>>>>>>>>>>Inside ContractorForQcException : " + ex.getMessage());
		logger.error(" ", ex);
		return new ResponseEntity<>(ex.getResponse(), HttpStatus.OK);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(com.mpcz.deposit_scheme.backend.api.exception.NatureOfWork14Exception.class)
	public final ResponseEntity<Response<T>> NatureOfWork14Exception(NatureOfWork14Exception ex) {
		Response r = new Response();
		r.setCode("200");
		r.setMessage(ex.getMessage());
		
		return ResponseEntity.ok(r);
	}
}
