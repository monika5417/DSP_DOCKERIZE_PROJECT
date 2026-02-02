package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;

@Repository
public interface PoseMachinePostDataRepository extends JpaRepository<PoseMachinePostData, Long> {

	
	public PoseMachinePostData findByApplicationNumber(String applicationNumber);
	
	@Query(value = "select * from pose_machine_d where application_numbwer=:applicationNumber", nativeQuery = true)
	public List<PoseMachinePostData> findByApplicationNumber1(String applicationNumber);

	@Query(value = "select * from pose_machine_d where application_numbwer=:consumerApplicationNo", nativeQuery = true)
	PoseMachinePostData getDemandDataFromPoseMachine(String consumerApplicationNo);

	@Query(value = "select * from pose_machine_d where application_numbwer=:consumerApplicationNo", nativeQuery = true)
	List<PoseMachinePostData> getDemandDataFromPoseMachineData(String consumerApplicationNo);

	@Query(value = "select * from pose_machine_d where application_numbwer = :consumerApplicationNo and payment_type='Revised_Demand_fees'", nativeQuery = true)
	List<PoseMachinePostData> getRevisePaymentFromPoseMachineData(String consumerApplicationNo);
	
//	@Query(value="select POSE_MACHINE_D_SEQ.nextVal From Dual", nativeQuery = true)
//	Map<String,Double> getNextRefSequence();
	
//	@Query(value="select nextVal('DSP_REF_SEQ')", nativeQuery = true)
//	Long getNextRefSequence();
	
//	 @Query(value = "SELECT POSE_MACHINE_D_SEQ.nextVal FROM DUAL", nativeQuery = true)
//	    Long getNextValFromSequence();
	
	@Query(value="select * from pose_machine_d", nativeQuery = true)
	List<PoseMachinePostData> getAll();

	
	@Query(value = "SELECT deposite_schema.DSP_REF_NO_SEQ.nextval FROM dual", nativeQuery = true)
	public Long getNextApplicationRefNo();
	
	@Query(value="select * from pose_machine_d where application_numbwer=:applicationNumber and payment_type=:paymentType",nativeQuery = true)
	public PoseMachinePostData findDataByApplicationNumber(String applicationNumber, String paymentType);

	@Query(value = "select * from pose_machine_d where application_numbwer = :consumerApplicationNo and payment_type='Registration_Fees'", nativeQuery = true)
	public List<PoseMachinePostData> getRegistrationPaymentDetail(String consumerApplicationNo);

}
