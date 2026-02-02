package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.IOException;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.bouncycastle.mail.smime.handlers.multipart_signed;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailForm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConsumerApplicationUpdateDto {

    
 

	@ApiModelProperty(notes = "Select valid Application Type", required = true)
	@NotEmpty(message = "Please select natureOfWorkTypeId Type")
	private Long natureOfWorkTypeId;

	@ApiModelProperty(notes = "Select valid Tariff Category", required = true)
	@NotEmpty(message = "Please select Tariff Category")
	private Long tariffCategoryId;

	@ApiModelProperty(notes = "Select valid Supply Voltage", required = true)
	@NotEmpty(message = "Please select Supply Voltage")
	private List<Long> supplyVoltageId;

	@ApiModelProperty(notes = "Select valid Scheme Type", required = true)
	@NotEmpty(message = "Please select Scheme Type")
	private Long schemeTypeId;

//	@ApiModelProperty(notes = "Select valid Task Type", required = true)
//	@NotEmpty(message = "Please select Task Type")
//	private Long taskTypeId;

//	@NotNull
//	@ApiModelProperty(notes = "Select valid Consumer", required = true)
//	private Long consumerId;

	@ApiModelProperty(notes = "consumerName cannot be null", required = true)
	@NotEmpty(message = "Please enter Consumer Name")
	private String consumerName;

	@ApiModelProperty(notes = "guardianName cannot be null", required = true)
	@NotEmpty(message = "Please enter Guardian Name")
	private String guardianName;

	@ApiModelProperty(notes = "address cannot be null", required = true)
	@NotEmpty(message = "Please enter Address")
	private String address;

	@ApiModelProperty(notes = "area cannot be null", required = true)
	@NotEmpty(message = "Please enter area")
	private Double area;

	@ApiModelProperty(notes = "billNo cannot be null", required = true)
//	@NotEmpty(message = "Please enter billNo")
	private String billNo;

	@ApiModelProperty(notes = "dtrCode cannot be null", required = true)
//	@NotEmpty(message = "Please enter dtrCode")
	private Long dtrCode;

	@ApiModelProperty(notes = "poleNo cannot be null", required = true)
	@NotEmpty(message = "Please enter poleNo")
	private String poleNo;

	@ApiModelProperty(notes = "short description of work cannot be null", required = true)
	@NotEmpty(message = "Please enter description of work")
	@NotNull
	private String shortDescriptionOfWork;

	@ApiModelProperty(notes = "aadharNo cannot be null", required = true)
	@NotEmpty(message = "Please enter aadharNo")
	private String aadharNo;

//	@ApiModelProperty(notes = "panNo cannot be null", required = true)
//	@NotEmpty(message = "Please enter panNo")
//	private String panNo;

	public static ConsumerApplicationUpdateDto stringToJson(String consumerApplicationUpdateDto) {

		ConsumerApplicationUpdateDto consumerApplicationDetailJson = new ConsumerApplicationUpdateDto();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			consumerApplicationDetailJson = objectMapper.readValue(consumerApplicationUpdateDto,
					ConsumerApplicationUpdateDto.class);
		} catch (IOException err) {
//			System.out.printf("Error", err.toString());
			err.printStackTrace();
		}

		return consumerApplicationDetailJson;

	}

	@ApiModelProperty(notes = "Select valid Work Type", required = true)
	@NotEmpty(message = "Please select Work Type")
	private Long workTypeId;

	@ApiModelProperty(notes = "Select valid Distribution Center", required = true)
	@NotEmpty(message = "Please select Distribution Center")
	private Long dcId;

	@ApiModelProperty(notes = "Select valid Substation", required = true)
	@NotEmpty(message = "Please select Substation")
	private Long substationId;

	@ApiModelProperty(notes = "Select valid Feeder", required = true)
	@NotEmpty(message = "Please select Feeder")
	private Long feederId;

	@ApiModelProperty(notes = "Select valid Contractor User Id", required = true)
	private Long contractorUserId;

	@ApiModelProperty(notes = "Select valid Contractor Authentication Id", required = true)
	private String contractorAuthenticationId;

	@ApiModelProperty(notes = "Select valid Contractor User Zone", required = true)
	private String contractorUserZone;

	@ApiModelProperty(notes = "Select valid Contractor User Class", required = true)
	private String contractorUserClass;

	@ApiModelProperty(notes = "Select valid Contractor Company Name", required = true)
	private String contractorCompanyName;

	@ApiModelProperty(notes = "Select valid Contractor Mobile", required = true)
	private String contractorMobile;

	@ApiModelProperty(notes = "Select valid Contractor Email", required = true)
	private String contractorEmail;

	@ApiModelProperty(notes = "Select valid Contractor Address", required = true)
	private String contractorAddress;
	
	@ApiModelProperty(notes = "Enter enter valid pin code", required = true)
	@NotEmpty(message = "Pin code cannot be null")
	//@Pattern(regexp = "(^$|[0-9]{6})", message = "pin code length must 6 =and numeric")
	private String pinCode;
	
	@ApiModelProperty(notes = "Select valid District", required = true)
	@NotEmpty(message = "Please select District")
	private Long districtId;
	
	@ApiModelProperty(notes="Enter valid ivrsNo",required = true)
	private String ivrsNo;
	
	@ApiModelProperty(notes="Enter valid workAllocationAddress",required = true)
	private String workAllocationAddress;
	
	@ApiModelProperty(notes="Enter valid worklocationAddresscb",required = true)
	private Boolean workLocationAddresscb = Boolean.FALSE;
	
	
	@ApiModelProperty(notes = "Select valid loadRequestedPerUnit", required = true)
	private String loadRequested;
	
	
	@ApiModelProperty(notes="Enter valid loadUnitId",required = true)
	private Long loadRequestedId;
	
	@ApiModelProperty(notes="Enter valid landAreaUnitId",required = true)
	private Long landAreaUnitId;

	@ApiModelProperty(notes = "Select valid landAreaUnit", required = true)
	private String landAreaUnitName;
	
	@ApiModelProperty(notes = "Select valid Individual Or Group Id", required = true)
	private Long individualOrGroupId  ;
	
	@ApiModelProperty(notes = "Select valid noOfPlot", required = true)
	private String  noOfPlot;
	
	
	@ApiModelProperty(notes = "Select valid khasra", required = true)
	private String khasra;
	
	@ApiModelProperty(notes = "Select valid khatoni", required = true)
	private String khatoni;
	
	@ApiModelProperty(notes = "Select valid GstNumber", required = true)
	private String gstNumber;
	
	@ApiModelProperty(notes = "Select valid Building Type", required = true)
	@NotEmpty(message = "Please select Building Type")
	private String colonyLegalSelectionType;
	

	@ApiModelProperty(notes = "Select valid Colony Type", required = true)
	@NotEmpty(message = "Please select Colony Type")
	private String colonyIllegalSelectionType;
	
	
	@ApiModelProperty(notes = "Select Valid JE Load", required = true)
	private String jeLoad;
	

	@ApiModelProperty(notes = "Select Valid JE Load Unit", required = true)
	private String jeLoadUnitKwYaKva;
    
	@ApiModelProperty(notes = "Selec\t Caste Category", required = true)
    private String castCategory;
	
	@ApiModelProperty(notes = "Select mobile No", required = true)
    private String mobilNo;
	
	@ApiModelProperty(notes = "Select  Samgra Id", required = true)
    private String samgraId;
  
	@ApiModelProperty(notes = "Select valid Samagra List", required = true)
	@NotEmpty(message = "Please select Samagra List")
	private List<SamagraListDto> samagraListDto;
	
	@ApiModelProperty(notes = "Select valid Samagra List", required = true)
	@NotEmpty(message = "Please select Samagra List")
	private List<MMKYParentChildDTO> mmkyParentChildDto;
	
	
	@ApiModelProperty(notes = "Select Consumer Application No", required = true)
	private String consumerApplicationNo;
	
	@ApiModelProperty(notes = "Select Avedak Ka Prakar", required = true)
	private String avedakKaPrakar;
	
	@ApiModelProperty(notes = "Select Avedak Ka Remark", required = true)
	private String avedakRemark;
	
	
	


	public String getAvedakKaPrakar() {
		return avedakKaPrakar;
	}

	public void setAvedakKaPrakar(String avedakKaPrakar) {
		this.avedakKaPrakar = avedakKaPrakar;
	}

	public String getAvedakRemark() {
		return avedakRemark;
	}

	public void setAvedakRemark(String avedakRemark) {
		this.avedakRemark = avedakRemark;
	}

	public List<MMKYParentChildDTO> getMmkyParentChildDto() {
			return mmkyParentChildDto;
		}

		public void setMmkyParentChildDto(List<MMKYParentChildDTO> mmkyParentChildDto) {
			this.mmkyParentChildDto = mmkyParentChildDto;
		}
	
	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public List<SamagraListDto> getSamagraListDto() {
		return samagraListDto;
	}

	public void setSamagraListDto(List<SamagraListDto> samagraListDto) {
		this.samagraListDto = samagraListDto;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public Long getDcId() {
		return dcId;
	}

	public void setDcId(Long dcId) {
		this.dcId = dcId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getKhasra() {
		return khasra;
	}

	public void setKhasra(String khasra) {
		this.khasra = khasra;
	}

	public Long getLandAreaUnitId() {
		return landAreaUnitId;
	}

	public void setLandAreaUnitId(Long landAreaUnitId) {
		this.landAreaUnitId = landAreaUnitId;
	}

	public Long getLoadRequestedId() {
		return loadRequestedId;
	}

	public void setLoadRequestedId(Long loadRequestedId) {
		this.loadRequestedId = loadRequestedId;
	}

	public Long getNatureOfWorkTypeId() {
		return natureOfWorkTypeId;
	}

	public void setNatureOfWorkTypeId(Long natureOfWorkTypeId) {
		this.natureOfWorkTypeId = natureOfWorkTypeId;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Long getSchemeTypeId() {
		return schemeTypeId;
	}

	public void setSchemeTypeId(Long schemeTypeId) {
		this.schemeTypeId = schemeTypeId;
	}

	public String getShortDescriptionOfWork() {
		return shortDescriptionOfWork;
	}

	public void setShortDescriptionOfWork(String shortDescriptionOfWork) {
		this.shortDescriptionOfWork = shortDescriptionOfWork;
	}

	public String getCastCategory() {
		return castCategory;
	}

	public void setCastCategory(String castCategory) {
		this.castCategory = castCategory;
	}

	public String getMobilNo() {
		return mobilNo;
	}

	public void setMobilNo(String mobilNo) {
		this.mobilNo = mobilNo;
	}

	public String getSamgraId() {
		return samgraId;
	}

	public void setSamgraId(String samgraId) {
		this.samgraId = samgraId;
	}

	public String getLoadRequested() {
		return loadRequested;
	}

	public void setLoadRequested(String loadRequested) {
		this.loadRequested = loadRequested;
	}

	public Long getTariffCategoryId() {
		return tariffCategoryId;
	}

	public void setTariffCategoryId(Long tariffCategoryId) {
		this.tariffCategoryId = tariffCategoryId;
	}

	public List<Long> getSupplyVoltageId() {
		return supplyVoltageId;
	}

	public void setSupplyVoltageId(List<Long> supplyVoltageId) {
		this.supplyVoltageId = supplyVoltageId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Long getDtrCode() {
		return dtrCode;
	}

	public void setDtrCode(Long dtrCode) {
		this.dtrCode = dtrCode;
	}

	public String getPoleNo() {
		return poleNo;
	}

	public void setPoleNo(String poleNo) {
		this.poleNo = poleNo;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public Long getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}

	public Long getSubstationId() {
		return substationId;
	}

	public void setSubstationId(Long substationId) {
		this.substationId = substationId;
	}

	public Long getFeederId() {
		return feederId;
	}

	public void setFeederId(Long feederId) {
		this.feederId = feederId;
	}

	public Long getContractorUserId() {
		return contractorUserId;
	}

	public void setContractorUserId(Long contractorUserId) {
		this.contractorUserId = contractorUserId;
	}

	public String getContractorAuthenticationId() {
		return contractorAuthenticationId;
	}

	public void setContractorAuthenticationId(String contractorAuthenticationId) {
		this.contractorAuthenticationId = contractorAuthenticationId;
	}

	public String getContractorUserZone() {
		return contractorUserZone;
	}

	public void setContractorUserZone(String contractorUserZone) {
		this.contractorUserZone = contractorUserZone;
	}

	public String getContractorUserClass() {
		return contractorUserClass;
	}

	public void setContractorUserClass(String contractorUserClass) {
		this.contractorUserClass = contractorUserClass;
	}

	public String getContractorCompanyName() {
		return contractorCompanyName;
	}

	public void setContractorCompanyName(String contractorCompanyName) {
		this.contractorCompanyName = contractorCompanyName;
	}

	public String getContractorMobile() {
		return contractorMobile;
	}

	public void setContractorMobile(String contractorMobile) {
		this.contractorMobile = contractorMobile;
	}

	public String getContractorEmail() {
		return contractorEmail;
	}

	public void setContractorEmail(String contractorEmail) {
		this.contractorEmail = contractorEmail;
	}

	public String getContractorAddress() {
		return contractorAddress;
	}

	public void setContractorAddress(String contractorAddress) {
		this.contractorAddress = contractorAddress;
	}

	public String getIvrsNo() {
		return ivrsNo;
	}

	public void setIvrsNo(String ivrsNo) {
		this.ivrsNo = ivrsNo;
	}

	public String getWorkAllocationAddress() {
		return workAllocationAddress;
	}

	public void setWorkAllocationAddress(String workAllocationAddress) {
		this.workAllocationAddress = workAllocationAddress;
	}

	public Boolean getWorkLocationAddresscb() {
		return workLocationAddresscb;
	}

	public void setWorkLocationAddresscb(Boolean workLocationAddresscb) {
		this.workLocationAddresscb = workLocationAddresscb;
	}

	public String getLandAreaUnitName() {
		return landAreaUnitName;
	}

	public void setLandAreaUnitName(String landAreaUnitName) {
		this.landAreaUnitName = landAreaUnitName;
	}

	public Long getIndividualOrGroupId() {
		return individualOrGroupId;
	}

	public void setIndividualOrGroupId(Long individualOrGroupId) {
		this.individualOrGroupId = individualOrGroupId;
	}

	public String getNoOfPlot() {
		return noOfPlot;
	}

	public void setNoOfPlot(String noOfPlot) {
		this.noOfPlot = noOfPlot;
	}

	public String getKhatoni() {
		return khatoni;
	}

	public void setKhatoni(String khatoni) {
		this.khatoni = khatoni;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getColonyLegalSelectionType() {
		return colonyLegalSelectionType;
	}

	public void setColonyLegalSelectionType(String colonyLegalSelectionType) {
		this.colonyLegalSelectionType = colonyLegalSelectionType;
	}

	public String getColonyIllegalSelectionType() {
		return colonyIllegalSelectionType;
	}

	public void setColonyIllegalSelectionType(String colonyIllegalSelectionType) {
		this.colonyIllegalSelectionType = colonyIllegalSelectionType;
	}

	public String getJeLoad() {
		return jeLoad;
	}

	public void setJeLoad(String jeLoad) {
		this.jeLoad = jeLoad;
	}

	public String getJeLoadUnitKwYaKva() {
		return jeLoadUnitKwYaKva;
	}

	public void setJeLoadUnitKwYaKva(String jeLoadUnitKwYaKva) {
		this.jeLoadUnitKwYaKva = jeLoadUnitKwYaKva;
	}


    
    
    
}
