package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateDomain;

@Repository
public interface EstimateAmountRepository extends JpaRepository<ErpEstimateAmountData, Long> {

	@Query(value="SELECT * FROM ERP_BUDGET_WORKFLOW_AMOUNT WHERE ERP_BUDGET_WORKFLOW_NUMBER =:estimateNumber",nativeQuery = true)
	Optional<ErpEstimateAmountData> findById(String estimateNumber);

	@Query(value="SELECT ESTIMATE_SANCTION_NO FROM ERP_BUDGET_WORKFLOW_AMOUNT WHERE ERP_NO=?1 AND ROWNUM<2",nativeQuery = true)
	String getWorkflowNumber(String erpWorkFlowNumber);

	ErpEstimateAmountData findByErpNo(String erpWorkFlowNumber);
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="delete from erp_budget_workflow_amount where erp_no=:erpWorkFlowNumber",nativeQuery = true)
	void deleteByErpNo(String erpWorkFlowNumber);
	
	
	@Query(value="SELECT *  FROM ERP_BUDGET_WORKFLOW_AMOUNT WHERE ERP_BUDGET_WORKFLOW_NUMBER =:estimateNumber and consumer_application_no=:consumerApplicationNo",nativeQuery = true)
	Optional<ErpEstimateAmountData> findByIdAndConsumerApplicationNo(String estimateNumber,String consumerApplicationNo);


	

	
}
