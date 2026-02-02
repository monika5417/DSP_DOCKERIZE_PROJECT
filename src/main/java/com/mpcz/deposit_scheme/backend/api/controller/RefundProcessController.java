//package com.mpcz.deposit_scheme.backend.api.controller;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
//import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
//import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
//import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
//import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
//import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
//import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;
//import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
//import com.mpcz.deposit_scheme.backend.api.domain.RefundProcessData;
//import com.mpcz.deposit_scheme.backend.api.domain.Upload;
//import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
//import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
//import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
//import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
//import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.ManualPaymentRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
//import com.mpcz.deposit_scheme.backend.api.repository.RefundProcessDataRepository;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateAmountService;
//import com.mpcz.deposit_scheme.backend.api.services.UploadService;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/user/refund_process")
//public class RefundProcessController {
//	@Autowired
//	   private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;
//	   @Autowired
//	   private ErpEstimateAmountService erpEstimateAmountService;
//	   @Autowired
//	   private RefundProcessDataRepository refundProcessDataRepository;
//	   @Autowired
//	   private UploadService uploadService;
//	   @Autowired
//	   private ApplicationDocumentRepository applicationDocumentRepository;
//	   @Autowired
//	   private ErpRevRepository erpRevRepository;
//	   @Autowired
//	   private ApplicationStatusRepository applicationStatusRepository;
//	   @Autowired
//	   private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;
//	   @Autowired
//	   private ManualPaymentRepository manualPaymentRepository;
//	   @Autowired
//	   private PoseMachinePostDataRepository poseMachinePostDataRepository;
//
//	   @PostMapping({"/saveRefundProcessData"})
//	   public ResponseEntity<Response> saveRefundProcessData(@RequestPart("refundForm") String refundFormData,
//			   @RequestPart("docRefundLetter") Optional<MultipartFile> docRefundLetterFile,
//			   @RequestPart("docCheckOrPassBook") Optional<MultipartFile> docCheckOrPassBookFile,
//			   @RequestPart("docConsumerRefundLetterFile") Optional<MultipartFile> docConsumerRefundLetterFile) throws ErpEstimateAmountException, ConsumerApplicationDetailException, DocumentTypeException {
//	      Response response = new Response();
//	      Upload checkOrPassBookUpload = null;
//	      Upload refundLetterUpload = null;
//	      Upload consumerRefundLetter=null;
//	      BigDecimal jeReturnAmount = null;
//	      PoseMachinePostData demandDataFromPoseMachine = null;
//	      ManualPayment demandDataFromManualPayment = null;
//	      BillDeskPaymentResponse demandDataFromBilldesk = null;
//	      BillDeskPaymentResponse reviseDemandDataFromBilldesk = null;
//	      
//     	 ErpRev findByConsAppNo =null;
//             BigDecimal remCgst=null;
//             BigDecimal remSgst=null;
//             BigDecimal payAmt=null;
//	      
//	      RefundProcessData refundData = stringToJson(refundFormData);
//	      ConsumerApplicationDetail findByConsumerApplicationNumber = this.consumerApplictionDetailRepository.findByConsumerApplicationNumber(refundData.getConsumerApplicationNo());
//	      if (findByConsumerApplicationNumber == null) {
//	         response.setMessage("Data not found for given application");
//	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//	      } else {
//	         RefundProcessData findByConsumerApplicationNo = refundProcessDataRepository.findByConsumerApplicationNo(refundData.getConsumerApplicationNo());
//	         if (findByConsumerApplicationNo != null) {
//	            response.setMessage("Refund already initiated for this application -- " + findByConsumerApplicationNo.getRefundType());
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//	         } else {
//	            refundLetterUpload = checkAndUploadDocument(docRefundLetterFile, "REFUND_LETTER", response);
//	            if (refundLetterUpload == null) {
//	               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//	            } 
//	               checkOrPassBookUpload = checkAndUploadDocument(docCheckOrPassBookFile, "CHECK_OR_PASSBOOK", response);
//	               if (checkOrPassBookUpload == null) {
//	                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//	               } 
//	               
//	                consumerRefundLetter = checkAndUploadDocument(docConsumerRefundLetterFile,"CONSUMER_REFUND_LETTER",response);
//	                if (consumerRefundLetter == null) {
//		                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//		               }
//	               if (findByConsumerApplicationNumber.getApplicationStatus().getApplicationStatusId() > 12L) {
////	                  demandDataFromBilldesk = billPaymentResponseeeeeeeRepository.getDemandDataFromBilldesk(refundData.getConsumerApplicationNo());
////	                  if (demandDataFromBilldesk == null) {
////	                     demandDataFromPoseMachine = poseMachinePostDataRepository.getDemandDataFromPoseMachine(refundData.getConsumerApplicationNo());
////	                     if (demandDataFromPoseMachine == null) {
////	                        demandDataFromManualPayment = manualPaymentRepository.getDemandDataFromManualPayment(refundData.getConsumerApplicationNo());
////	                        if (demandDataFromManualPayment == null) {
////	                           response.setCode("406");
////	                           response.setMessage("No Demand Payment found for given application no");
////	                           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
////	                        }
////	                     }
////	                  }
//	                  
//	                  BigDecimal demandAmount = getDemandAmount(refundData.getConsumerApplicationNo());
//	          	    if (demandAmount == null) {
//	          	        response.setCode("406");
//	          	        response.setMessage("No Demand Payment found for the given application number");
//	          	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//	          	    }
//
////	                  if (demandDataFromBilldesk != null || demandDataFromPoseMachine != null || demandDataFromManualPayment != null) {
//	          	    if(demandAmount!=null) {
//	                     ErpEstimateAmountData findByEstimateNumber = this.erpEstimateAmountService.findByEstimateNumber(findByConsumerApplicationNumber.getErpWorkFlowNumber());
//	                     BigDecimal totalBalanceSupervisionAmount = findByEstimateNumber.getTotalBalanceSupervisionAmount();
//	                     BigDecimal totalBalanceDepositAmount = findByEstimateNumber.getTotalBalanceDepositAmount();
//	                     jeReturnAmount = findByEstimateNumber.getJeReturnAmount();
//	                     BigDecimal cgst = (BigDecimal)Optional.ofNullable(findByEstimateNumber.getCgst()).orElse(BigDecimal.ZERO);
//	                     BigDecimal sgst = (BigDecimal)Optional.ofNullable(findByEstimateNumber.getSgst()).orElse(BigDecimal.ZERO);
//	                     findByConsAppNo = erpRevRepository.findByConsAppNo(refundData.getConsumerApplicationNo());
//	                     
//	                     
//	                     
////	                     value 1 mtlb demand note refund krna hai
//	                     if (refundData.getValue() == 1L) {
//	                        if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1L)) {
//	                           refundData.setRefundableAmnt(totalBalanceSupervisionAmount.subtract(cgst).subtract(sgst));
//	                        } else {
//	                           refundData.setRefundableAmnt(totalBalanceDepositAmount.subtract(cgst).subtract(sgst));
//	                        }
//	                        
//	                        BigDecimal billDeskDemandAmountCheck = demandAmount.subtract(cgst).subtract(sgst);
//	                        
//	                        System.out.println(" billDeskDemandAmountCheck : " +billDeskDemandAmountCheck);
//	                        System.out.println("refundable amount : " +refundData.getRefundableAmnt());
//	                        
//	                        if (billDeskDemandAmountCheck.compareTo(refundData.getRefundableAmnt()) != 0) {
//	                	        response.setCode(HttpCode.NOT_ACCEPTABLE);
//	                	        response.setMessage("Difference in billdesk Received amount and refund created amount ." );
//	                	        return ResponseEntity.status(HttpStatus.OK).body(response);
//	                	    }
//	                        
//	                     } else if (refundData.getValue() == 2L) {  //value 2 mtlb return data refund krna hai
//	                    	 if (findByConsAppNo.getPayAmt().compareTo(BigDecimal.ZERO) >= 0) {
//	                    		 refundData.setRefundableAmnt(jeReturnAmount.add(findByConsAppNo.getRemReturnAmt()));
//	                    	 }else {
//	                    	
//	                    	 refundData.setRefundableAmnt(jeReturnAmount);
//	                    	 }
//	                    	 
//	                     } else if (refundData.getValue() == 3L) {   //value mtlb  revise demand ka negative amount refund krna hai
//	                     
//	                    	 
////	                    	 findByConsAppNo = erpRevRepository.findByConsAppNo(refundData.getConsumerApplicationNo());
//	                           if (findByConsAppNo.getPayAmt().compareTo(BigDecimal.ZERO) >= 0) {
//	                              response.setCode("406");
//	                              response.setMessage("Your amount is not in negative so we can not refund it");
//	                              return ResponseEntity.ok(response);
//	                           }
//
//	                           remCgst = (BigDecimal)Optional.ofNullable(findByConsAppNo.getRemCgst()).orElse(BigDecimal.ZERO);
//	                           remSgst = (BigDecimal)Optional.ofNullable(findByConsAppNo.getRemSgst()).orElse(BigDecimal.ZERO);
//	                           payAmt = findByConsAppNo.getPayAmt();
//	                           if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1L)) {
//	                              refundData.setRefundableAmnt(payAmt.subtract(remCgst).subtract(remSgst).abs());
//	                           } else {
//	                              refundData.setRefundableAmnt(payAmt.subtract(remCgst).subtract(remSgst).abs());
//	                           }
//	                        } else if (refundData.getValue() == 4L) {  //value 4 mtlb demand + revise demand ka amount refund krna hai
//	                           demandDataFromBilldesk = billPaymentResponseeeeeeeRepository.getDemandDataFromBilldesk(refundData.getConsumerApplicationNo());
//	                           if (demandDataFromBilldesk == null) {
//	                              response.setCode("406");
//	                              response.setMessage("You have not paid demand fees");
//	                              return ResponseEntity.ok(response);
//	                           }
//
//	                           reviseDemandDataFromBilldesk = billPaymentResponseeeeeeeRepository.getReviseDemandDataFromBilldesk(refundData.getConsumerApplicationNo());
//	                           if (reviseDemandDataFromBilldesk == null) {
//	                              response.setCode("406");
//	                              response.setMessage("You have not paid revise demand fees");
//	                              return ResponseEntity.ok(response);
//	                           }
//
//	                           if (demandDataFromBilldesk != null && reviseDemandDataFromBilldesk != null) {
//	                              findByConsAppNo = erpRevRepository.findByConsAppNo(refundData.getConsumerApplicationNo());
//	                              remCgst = Optional.ofNullable(findByConsAppNo.getRemCgst()).orElse(BigDecimal.ZERO);
//	                              remSgst = Optional.ofNullable(findByConsAppNo.getRemSgst()).orElse(BigDecimal.ZERO);
//	                              payAmt = findByConsAppNo.getPayAmt();
//	                              BigDecimal ReviseDemandReturnAmnt = payAmt.subtract(remCgst).subtract(remSgst);
//	                              BigDecimal demandReturnSupervisionAmnt;
//	                              if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1L)) {
//	                                 demandReturnSupervisionAmnt = totalBalanceSupervisionAmount.subtract(cgst).subtract(sgst);
//	                                 refundData.setRefundableAmnt(demandReturnSupervisionAmnt.add(ReviseDemandReturnAmnt));
//	                                 
//	                              } else {
//	                                 demandReturnSupervisionAmnt = totalBalanceDepositAmount.subtract(cgst).subtract(sgst);
//	                                 refundData.setRefundableAmnt(demandReturnSupervisionAmnt.add(ReviseDemandReturnAmnt));
//	                              }
//	                              
//	                              BigDecimal reviseDemandAmount = new BigDecimal(reviseDemandDataFromBilldesk.getAmount());
//	                      	    BigDecimal demandReviseDemandTotalAmnt = demandAmount.add(reviseDemandAmount);
//
//	                      	    BigDecimal reviseDemandRefundableAmnt = demandReviseDemandTotalAmnt.subtract(cgst)
//	                      	            .subtract(sgst).subtract(remCgst).subtract(remSgst);
//	                      	    
//	                      	  System.out.println(" reviseDemandRefundableAmnt : " +reviseDemandRefundableAmnt);
//		                        System.out.println("refundable amount : " +refundData.getRefundableAmnt());
//	                      	  if (reviseDemandRefundableAmnt.compareTo(refundData.getRefundableAmnt()) != 0) {
//	                  	        
//	                  	        response.setCode(HttpCode.NOT_ACCEPTABLE);
//	                  	        response.setMessage("Difference in billdesk sending amount and refund amount");
//	                  	        return ResponseEntity.status(HttpStatus.OK).body(response);
//	                  	    }
//	                              
//	                           }
//	                        }
//	                     
//	                  }
//
//	                  refundData.setRefundType(refundData.getRefundType());
//	                  RefundProcessData save = (RefundProcessData)this.refundProcessDataRepository.save(refundData);
//	                  if (save != null) {
//	                	  
//	                	  if(refundData.getValue()==3L) {
//	                		  findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository.findById(ApplicationStatusEnum.REFUND_APPLICATION_MOVED_TO_MD.getId()).get());
//	                	  }else {
////	                     findByConsumerApplicationNumber.setApplicationStatus(applicationStatusRepository.findById(ApplicationStatusEnum.REFUND_PROPOSAL_BY_DGM.getId()).get());
//	                	  }
//	                     consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
//	                     ApplicationDocument applicationDocument = new ApplicationDocument();
//	                     applicationDocument.setDocRefundLetterFile(refundLetterUpload);
//	                     applicationDocument.setDocCheckOrPassBookFile(checkOrPassBookUpload);
//	                     applicationDocument.setDocConsumerRefundLetterFile(consumerRefundLetter);
//	                     ApplicationDocument existingDocument = applicationDocumentRepository.findByConsumerApplicationDetailId(findByConsumerApplicationNumber.getConsumerApplicationId());
//	                     if (existingDocument != null) {
//	                        existingDocument.setDocRefundLetterFile(applicationDocument.getDocRefundLetterFile());
//	                        existingDocument.setDocCheckOrPassBookFile(applicationDocument.getDocCheckOrPassBookFile());
//	                        existingDocument.setDocConsumerRefundLetterFile(consumerRefundLetter);
//	                        this.applicationDocumentRepository.save(existingDocument);
//	                     } else {
//	                        applicationDocument.setConsumerApplicationDetail(findByConsumerApplicationNumber);
//	                        this.applicationDocumentRepository.save(applicationDocument);
//	                     }
//	                  }
//
//	                  response.setCode("200");
//	                  response.setMessage("Refund data saved successfully");
//	                  response.setList(Arrays.asList(save));
//	                  return ResponseEntity.ok(response);
//	               } else {
//	                  response.setCode("406");
//	                  System.out.println("You are not eligible for refund amount as you have not paid any demand fees and your application status > 12");
//	                  response.setMessage("You are not eligible for refund amount as you have not paid any demand fees and your application status > 12");
//	                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//	               }
//	            }
//	         
//	      }
//	   }
//
//	   public static RefundProcessData stringToJson(String refundFormData) {
//	      RefundProcessData data = new RefundProcessData();
//
//	      try {
//	         ObjectMapper objectMapper = new ObjectMapper();
//	         data = (RefundProcessData)objectMapper.readValue(refundFormData, RefundProcessData.class);
//	      } catch (IOException var3) {
//	         var3.printStackTrace();
//	      }
//
//	      return data;
//	   }
//
//	   private Upload checkAndUploadDocument(Optional<MultipartFile> fileOptional, String fileType, Response response) throws ConsumerApplicationDetailException, DocumentTypeException {
//	      if (fileOptional.isPresent()) {
//	         Upload upload = uploadService.uploadFile((MultipartFile)fileOptional.get(), fileType);
//	         if (upload == null) {
//	            response.setCode("100");
//	            response.setMessage("Document " + fileType + " not uploaded.");
//	            throw new ConsumerApplicationDetailException(response);
//	         } else {
//	            return upload;
//	         }
//	      } else {
//	         response.setMessage("Document " + fileType + " Not Found");
//	         return null;
//	      }
//	   }
//
//	   
//	   private BigDecimal getDemandAmount(String consumerApplicationNo) {
//		    BillDeskPaymentResponse demandDataFromBilldesk = billPaymentResponseeeeeeeRepository
//		            .getDemandDataFromBilldesk(consumerApplicationNo);
//		    if (demandDataFromBilldesk != null) {
//		        return new BigDecimal(demandDataFromBilldesk.getAmount());
//		    }
//
//		    PoseMachinePostData demandDataFromPoseMachine = poseMachinePostDataRepository
//		            .getDemandDataFromPoseMachine(consumerApplicationNo);
//		    if (demandDataFromPoseMachine != null) {
//		        return demandDataFromPoseMachine.getTxnAmount();
//		    }
//
//		    ManualPayment demandDataFromManualPayment = manualPaymentRepository
//		            .getDemandDataFromManualPayment(consumerApplicationNo);
//		    if (demandDataFromManualPayment != null) {
//		        return new BigDecimal(demandDataFromManualPayment.getAmount());
//		    }
//
//		    return null;
//		}
//	   
//	   
//	   @PutMapping({"/refundApplicationByGM"})
//	   public ResponseEntity<?> refundApplicationByGM(@RequestParam String consumerApplicationNumber, @RequestParam String refundApplicationDate, @RequestParam String refundRemark, @RequestParam String refundApplicationGMName, @RequestParam Boolean gmRefundAccepted) {
//	      Response response = new Response();
//	      ConsumerApplicationDetail consumerApplicationDetail = this.consumerApplictionDetailRepository.findByConsumerApplicationNumber(consumerApplicationNumber);
//	      if (consumerApplicationDetail == null) {
//	         response.setCode("100");
//	         response.setMessage("Data not found for given application");
//	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//	      } else {
//	         if (gmRefundAccepted) {
//	            consumerApplicationDetail.setRefundAppGmDate(refundApplicationDate);
//	            consumerApplicationDetail.setRefundAppGMName(refundApplicationGMName);
//	            consumerApplicationDetail.setRefundGmRemark(refundRemark);
//	            consumerApplicationDetail.setGmRefundAcceptedOrRejected("true");
//	            consumerApplicationDetail.setApplicationStatus((ApplicationStatus)this.applicationStatusRepository.findById(ApplicationStatusEnum.REFUND_APPLICATION_MOVED_TO_MD.getId()).get());
//	         } else {
//	            consumerApplicationDetail.setRefundAppGmDate(refundApplicationDate);
//	            consumerApplicationDetail.setRefundAppGMName(refundApplicationGMName);
//	            consumerApplicationDetail.setRefundGmRemark(refundRemark);
//	            consumerApplicationDetail.setGmRefundAcceptedOrRejected("false");
////	            consumerApplicationDetail.setApplicationStatus((ApplicationStatus)this.applicationStatusRepository.findById(ApplicationStatusEnum.REFUND_PROPOSAL_BY_DGM.getId()).get());
//	         }
//
//	         ConsumerApplicationDetail updatedConsumerApplicationDetail = (ConsumerApplicationDetail)this.consumerApplictionDetailRepository.save(consumerApplicationDetail);
//	         response.setList(Arrays.asList(updatedConsumerApplicationDetail));
//	         response.setCode("200");
//	         response.setMessage("All Record Retrived Successfully");
//	         return ResponseEntity.ok(response);
//	      }
//	   }
//
//	   @GetMapping({"/getRefundProcessDataByApplicationNo/{consumerApplicationNo}"})
//	   public ResponseEntity<?> getRefundProcessDataByApplicationNo(@PathVariable String consumerApplicationNo) {
//	      Response response = new Response();
//	      RefundProcessData findByConsumerApplicationNo = this.refundProcessDataRepository.findByConsumerApplicationNo(consumerApplicationNo);
//	      if (findByConsumerApplicationNo == null) {
//	         response.setCode("100");
//	         response.setMessage("Data not found for given application");
//	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//	      } else {
//	         response.setCode("200");
//	         response.setMessage("Refund data saved successfully");
//	         response.setList(Arrays.asList(findByConsumerApplicationNo));
//	         return ResponseEntity.ok(response);
//	      }
//	   }
//	   
//	   
//	   @GetMapping("/processFinanceMdResponse/{consumerApplicationNo}/{amountRefundedOrNot}")
//	   public ResponseEntity<?> processFinanceMdResponse(@PathVariable String consumerApplicationNo, @PathVariable String amountRefundedOrNot){
//		   Response response = new Response();
//		   ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository.findByConsumerApplicationNumber(consumerApplicationNo);
//		   if (consumerApplicationDetail == null) {
//		         response.setCode("100");
//		         response.setMessage("Data not found for given application");
//		         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//		      }else {
//		    	  if(amountRefundedOrNot.equals("yes")) {
//		    		  consumerApplicationDetail.setApplicationStatus(applicationStatusRepository.findById(ApplicationStatusEnum.AMOUNT_REFUNDED_TO_APPLICANT_SUCCESSFULLY.getId()).get());
//		    		  ConsumerApplicationDetail save = consumerApplictionDetailRepository.save(consumerApplicationDetail);
//		    		  response.setCode("200");
//				         response.setMessage("Amount Refunded To Applicant Successfully ");
//				         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//		    	  }
//		      }
//		   
//		   return null;
//		   
//	   }
//	   
//}
