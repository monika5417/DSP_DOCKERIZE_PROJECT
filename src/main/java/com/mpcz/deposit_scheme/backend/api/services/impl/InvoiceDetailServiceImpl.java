///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mpcz.deposit_scheme.backend.api.services.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.mpcz.deposit_scheme.backend.api.domain.InvoiceDetail;
//import com.mpcz.deposit_scheme.backend.api.dto.InvoiceDetailDTO;
//import com.mpcz.deposit_scheme.backend.api.enums.ChargesTypeEnum;
//import com.mpcz.deposit_scheme.backend.api.repository.InvoiceDetailRepository;
//import com.mpcz.deposit_scheme.backend.api.services.InvoiceDetailService;
//
///**
// *
// * @author Saurabh kumar gupta
// */
//@Service
//@Transactional
//public class InvoiceDetailServiceImpl implements InvoiceDetailService {
//
////    @Autowired
////    InvoiceDetailDAO invoiceDetailDAO;
//	
//	@Autowired
//	private InvoiceDetailRepository detailRepository;
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public InvoiceDetail save(InvoiceDetail invoice) throws Exception {
//        try {  
//          		return detailRepository.save(invoice);
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    @Override
//    public InvoiceDetail findById(Long ID) throws Exception {
//        try {
//            return detailRepository.findById(ID).get();
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    @Override
//    public List<InvoiceDetail> findAll() throws Exception {
//        try {
//            return detailRepository.findAll();
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public void update(InvoiceDetail invoice) throws Exception {
//        try {
//        	detailRepository.update(invoice);
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    @Override
//    public void delete(InvoiceDetail invoice) throws Exception {
//        try {
//        	detailRepository.delete(invoice);
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    @Override
//    public List<InvoiceDetail> findByProperty(String property, String value) throws Exception {
//        try {
//            return detailRepository.findByProperty(property, value);
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//	@Override
//	public List<InvoiceDetail> findByInvoice(String invoiceId) throws Exception {
//		try {
//		return detailRepository.findByInvoice(invoiceId);
//		 } catch (Exception ex) {
//	            throw ex;
//	        }
//	}
//
//	//saurabh
////	@Override
////	public InvoiceDetailDTO findByInvoiceId(String invoiceId) throws Exception {
////		List<InvoiceDetail> invDtlList = detailRepository.findByInvoice(invoiceId);
////		
////		InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
////		for(InvoiceDetail invDtl:invDtlList)
////		{
////			if(ChargesTypeEnum.CGST.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setcGST(invDtl.getAmount());
////			}
////			if(ChargesTypeEnum.SGST.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setsGST(invDtl.getAmount());
////			}
////			if(ChargesTypeEnum.INITIAL_AMOUNT.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setInitialAmount(invDtl.getAmount());
////			}
////			if(ChargesTypeEnum.ESTIMATE_AMOUNT.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setEstimateAmount(invDtl.getAmount());
////			}
////			if(ChargesTypeEnum.SUPER_VISION.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setSuperVision(invDtl.getAmount());
////			}
////		
////			if(ChargesTypeEnum.TOTAL_AMOUNT.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setTotalAmount(invDtl.getAmount());
////			}
////			
////			if(ChargesTypeEnum.TESTING_CHARGES.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setTestingCharges(invDtl.getAmount());
////			}
////			
////			if(ChargesTypeEnum.METER_COST.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setMeterCost(invDtl.getAmount());
////			}
////			
////			if(ChargesTypeEnum.LOAD_ENHANCEMENT_APPLICATION_FEES.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setLoadEnhancementApplicationAmount(invDtl.getAmount());
////			}
////			
////			if(ChargesTypeEnum.LOAD_ENHANCEMENT_REGISTRATION_FEES.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setLoadEnhancementRegistrationAmount(invDtl.getAmount());
////			}
////				
////			if(ChargesTypeEnum.SUPPLY_AFFORDING_CHARGES.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setSupplyAffordingCharges(invDtl.getAmount());
////			}
////			
////			if(ChargesTypeEnum.SECURITY_DEPOSITE.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setSecurityDeposit(invDtl.getAmount());
////			}
////			
////			if(ChargesTypeEnum.STAMP_CHARGES.getValue().toString().equals(invDtl.getChargesId().toString()))
////			{
////				invoiceDetailDTO.setStampCharges(invDtl.getAmount());
////			}
////			
////		}
////		return invoiceDetailDTO;
////	}
//
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED)
//	public void save(List<InvoiceDetail> invoiceDtlList) throws Exception {
//		for(InvoiceDetail invDtl:invoiceDtlList)
//		{
//			System.out.println("Ins save List "+invDtl.getInvoiceId()+"#"+invDtl.getChargesId()+"#"+invDtl.getAmount());
//			detailRepository.save(invDtl);
//		}
//		
//	}
//
//	@Override
//	public InvoiceDetail findByProperties(String property1, String value1, Integer chargeId) throws Exception {
//		
//		return detailRepository.findByProperties(property1, value1, chargeId);
//	}
//
//	@Override
//	public void updateByChargesId(InvoiceDetail invoice) throws Exception {
//		
//		detailRepository.updateByChargesId(invoice);
//	}
//
//	
//	//////////////////////////////////////////////////////////////////////
//	@Override
//	public InvoiceDetailDTO findByInvoiceId(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public InvoiceDetailDTO findByInvoiceId(String invoiceId) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
