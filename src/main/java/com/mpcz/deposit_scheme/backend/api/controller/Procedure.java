package com.mpcz.deposit_scheme.backend.api.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

@RestController
@RequestMapping("/procedure")
public class Procedure {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@PersistenceContext
	private EntityManager entityManager; // Injected automatically

//	@Transactional
//	@GetMapping("/sendDataToErpProject")
//	public Integer sendDateToErpProject() {
//		try {
//			Date date = new Date();
//			SimpleDateFormat formatter = new SimpleDateFormat("2025-01-01");
//			String toDate = formatter.format(date);
//			System.out.println("Formatted Date: " + toDate);
//			
//			
//			Calendar calendar = Calendar.getInstance();
//	        calendar.add(Calendar.DAY_OF_MONTH, -1); // Subtract 1 day
//
//	        Date date1 = calendar.getTime();
//	        SimpleDateFormat formatter1 = new SimpleDateFormat("2025-02-01");
//	        String fromDate = formatter1.format(date1);
//	        
//	        
//	        
//			Integer syncWithModification = consumerApplictionDetailRepository.syncWithModification(toDate,fromDate);
//
////			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DATA_TO_ERP");
////			boolean execute = query.execute();
////			if (execute) {
////				return 1;
////			} else {
////				return 0;
////			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	public void callStoredProcedureWithRetry() {
//        int maxRetries = 3;
//        int attempt = 0;
//        boolean success = false;
//
//        while (attempt < maxRetries && !success) {
//            try {
////                StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DATA_TO_ERP");
////                query.execute();
////                success = true; // Mark success if no exception occurs
////               System.out.println("successfull done");
//            	
//            	Integer syncWithModification = consumerApplictionDetailRepository.syncWithModification();
//            	System.err.println("value of depending " +syncWithModification);
//                
//            } catch (PersistenceException e) {
//                if (e.getCause() != null && e.getCause().getMessage().contains("ORA-02049")) {
//                    attempt++;
//                    System.out.println("Lock timeout occurred. Retrying... Attempt " + attempt);
//                    try {
//                        TimeUnit.SECONDS.sleep(2); // Wait before retrying
//                    } catch (InterruptedException ex) {
//                        Thread.currentThread().interrupt();
//                    }
//                } else {
//                    throw e; // If it's not a lock timeout error, throw the exception
//                }
//            }
//        }
//
//        if (!success) {
//            throw new RuntimeException("Failed after " + maxRetries + " attempts due to lock timeout.");
//        }
//    }

	@RequestMapping(value = "/dataToErp", method = RequestMethod.GET)
	@ResponseBody
	public Integer data_to_erp() {

//		Date date = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
//		String fromDate = formatter.format(date);

		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, -1);
		Date newDate = calendar.getTime();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		String toDate = formatter1.format(newDate);

		return executeStoredProc(toDate, toDate);
	}

	private SimpleJdbcCall jdbcCall;

	@Autowired
	public void StoredProcService(DataSource dataSource) {
		// Initialize SimpleJdbcCall with the data source
		this.jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("data_to_erp").declareParameters(
				new SqlParameter("p_start_date", Types.VARCHAR), new SqlParameter("p_end_date", Types.VARCHAR),
				new SqlOutParameter("p_status", Types.INTEGER));
	}

	public Integer executeStoredProc(String param1, String param2) {
		// Prepare the input parameters
		SqlParameterSource inParams = new MapSqlParameterSource().addValue("p_start_date", param1)
				.addValue("p_end_date", param2);

		// Execute the stored procedure and retrieve the output
		Map<String, Object> result = jdbcCall.execute(inParams);

		// Retrieve and return the output parameter value (p_param3)
		return (Integer) result.get("p_status");
	}

//	   ============================================================================
//	@GetMapping("/send/{appNo}")
//	public ResponseEntity<String> callDbProcedure(@PathVariable String appNo) {
//		String result = this.triggerProcedure(appNo);
//
//		return ResponseEntity.ok(result);
//	}
//
//	public String triggerProcedure(String consumerAppNo) {
//		return this.callProducer(consumerAppNo);
//	}
//
//	public String callProducer(String consumerAppNo) {
//		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SEND_TO_ERP_SUPP_SITE_STG");
//
//		// Register parameters
//		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
//		query.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);
//
//		// Set input
//		query.setParameter(1, consumerAppNo);
//
//		// Execute
//		query.execute();
//
//		System.err.println((String) query.getOutputParameterValue(2));
//		// Get OUT param
//		return (String) query.getOutputParameterValue(2);
//	}
	
	@GetMapping("/createInvoiceProcedure")
	public void createInvoiceProcedure() {
		try {
		consumerApplictionDetailRepository.createInvoice();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
