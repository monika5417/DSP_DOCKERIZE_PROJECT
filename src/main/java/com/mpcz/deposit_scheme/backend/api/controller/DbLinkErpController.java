package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.domain.RefundAmount;
import com.mpcz.deposit_scheme.backend.api.domain.XXAP_DSP_INV_HDR_STG;
import com.mpcz.deposit_scheme.backend.api.domain.XX_SUPPIER_STG;
import com.mpcz.deposit_scheme.backend.api.repository.RefundAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.XXAP_DSP_INV_HDR_STGRepository;
import com.mpcz.deposit_scheme.backend.api.repository.XX_SUPPIER_STG_Repository;


@RestController
@RequestMapping("/Db_Link_Erp_Controller")
public class DbLinkErpController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RefundAmountRepository refundAmountRepository;
	
	@Autowired
	private XX_SUPPIER_STG_Repository XX_SUPPIER_STG_Repository;
	
	@Autowired
	private XXAP_DSP_INV_HDR_STGRepository xXAP_DSP_INV_HDR_STGRepository;

	@GetMapping("/saveSupplierData")
	public ResponseEntity<?> saveSupplierData(){
		XX_SUPPIER_STG save = null;
		String consumerApplicationNo = "SV1738238486234";
		RefundAmount consumerApplicationNoIsActiveDB = refundAmountRepository.findByConsumerApplicationNoIsActive(consumerApplicationNo);
		
		XX_SUPPIER_STG SUPPIER_STG = new XX_SUPPIER_STG();
		SUPPIER_STG.setVendorId(consumerApplicationNoIsActiveDB.getId());
		SUPPIER_STG.setVendorName(consumerApplicationNoIsActiveDB.getConsumerName());
		SUPPIER_STG.setTermsName("IMMEDIATE");
		SUPPIER_STG.setInvoiceCurrencyCode("INR");
		SUPPIER_STG.setPaymentCurrencyCode("INR");
		SUPPIER_STG.setCreationDate(new Date());
		try {
		 save = XX_SUPPIER_STG_Repository.save(SUPPIER_STG);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.err.println("aaaaaaaaaaa : " +new Gson().toJson(save));
		return null;
	}
	
	@GetMapping("/saveInvoiceDataERP")
	public ResponseEntity<?> saveInvoiceDataERP(){
//		Map<String, Object> supplierSiteStgDataByVendorId = XX_SUPPIER_STG_Repository.getSupplierSiteStgDataByVendorId(284L);
//		System.err.println("aaaaaa : " +new Gson().toJson(supplierSiteStgDataByVendorId));
		
		List<RefundAmount> supplierSendApplication = refundAmountRepository.getSupplierSendApplication();
		for(RefundAmount refund : supplierSendApplication) {
			if(refund.getId().equals(284L)) {
			XX_SUPPIER_STG supplierDataByVendorId = XX_SUPPIER_STG_Repository.getSupplierDataByVendorId(284L);
			
			Map<String, Object> supplierSiteStgDataByVendorId = XX_SUPPIER_STG_Repository.getSupplierSiteStgDataByVendorId(284L);
			System.err.println("aaaaaa : " +new Gson().toJson(supplierSiteStgDataByVendorId));
			
			
			
			XXAP_DSP_INV_HDR_STG hdrTable = new XXAP_DSP_INV_HDR_STG();
			Long nextId = jdbcTemplate.queryForObject("SELECT XXAP_DSP_INV_HDR_STG_SEQ.NEXTVAL FROM dual", Long.class);

			hdrTable.setInvoiceId(nextId);
			hdrTable.setInvoiceNum(refund.getConsumerApplicationNo());
			hdrTable.setInvoiceTypeLookupCode("STANDARD");
			hdrTable.setVendorId(Long.parseLong(supplierDataByVendorId.getAttribute2()));
			
			Object obj = supplierSiteStgDataByVendorId.get("ATTRIBUTE1");

			if (obj != null) {
			    Long vendorSiteId = null;

			    if (obj instanceof Number) {
			        // Integer, Long, BigDecimal sab handle ho jayega
			        vendorSiteId = ((Number) obj).longValue();
			    } else if (obj instanceof String) {
			        // Agar String me number aa raha ho
			        try {
			            vendorSiteId = Long.parseLong((String) obj);
			        } catch (NumberFormatException e) {
			            e.printStackTrace(); // invalid string
			        }
			    } else {
			        System.out.println("Unknown type: " + obj.getClass().getName());
			    }

			    if (vendorSiteId != null) {
			        hdrTable.setVendorSiteId(vendorSiteId);
			        System.out.println("VendorSiteId set to: " + vendorSiteId);
			    } else {
			        System.out.println("VendorSiteId could not be set, value is null");
			    }
			} else {
			    System.out.println("ATTRIBUTE1 is null");
			}

			
			
			hdrTable.setInvoiceDate(new Date());
			hdrTable.setInvoiceAmount(refund.getRefundAmount().longValue());
			hdrTable.setInvoiceCurrencyCode("INR");
			hdrTable.setSource("Manual_Invoice_Entry");
			hdrTable.setProcessedFlag("Y");
			hdrTable.setCalcTaxDuringImportFlag("Y");
			hdrTable.setAddTaxToInvAmtFlag("Y");
			hdrTable.setOperatingUnit("Corporate.Office");
		    hdrTable.setTermsName("IMMEDIATE");
		    System.err.println("VVVVVVVVVVVVV : " +new Gson().toJson(hdrTable));
		    xXAP_DSP_INV_HDR_STGRepository.save(hdrTable);
			}
		}
		return null;
	}
	
	
}
