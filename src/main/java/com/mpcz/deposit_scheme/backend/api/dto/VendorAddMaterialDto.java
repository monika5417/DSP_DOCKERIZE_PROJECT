package com.mpcz.deposit_scheme.backend.api.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorAddMaterialDto {

    @JsonProperty("CONSUMER_APPLICATION_NUMBER")
    private String consumerApplicationNumber;

    @JsonProperty("USER_ID")
    private String userId;

    @JsonProperty("VENDOR_NAME")
    private String vendorName;

    @JsonProperty("VENDOR_MATERIAL_SPECIFICATION")
    private String vendorMaterialSpecification;

    @JsonProperty("TRANSFORMER_SERIAL_NO")
    private String transformerSerialNo;

    @JsonProperty("MATERIAL_INSTALLATION_DATE")
    private String materialInstallationDate;

    @JsonProperty("RESAMPLING_FLAG")
    private Long resamplingFlag;

    @JsonProperty("PARENT_APP_NO")
    private Long parentApplicationNo;

    @JsonProperty("CHILD_APP_NO")
    private Long chindApplicationNo;

    @JsonProperty("POST_FLAG")
    private Long postFlag;

    @JsonProperty("ITEM_SERIAL_NO")
    private String item_serial_no;

    @JsonProperty("MONTH_YEAR_OF_ITEM_MANUFACTURE")
    private String month_year_of_item_manufacture;

    @JsonProperty("BILL_NUMBER")
    private String bill_number;

    @JsonProperty("BILL_DATE")
    private String bill_date;


	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorMaterialSpecification() {
		return vendorMaterialSpecification;
	}

	public void setVendorMaterialSpecification(String vendorMaterialSpecification) {
		this.vendorMaterialSpecification = vendorMaterialSpecification;
	}

	public String getTransformerSerialNo() {
		return transformerSerialNo;
	}

	public void setTransformerSerialNo(String transformerSerialNo) {
		this.transformerSerialNo = transformerSerialNo;
	}

	public String getMaterialInstallationDate() {
		return materialInstallationDate;
	}

	public void setMaterialInstallationDate(String materialInstallationDate) {
		this.materialInstallationDate = materialInstallationDate;
	}

	public Long getResamplingFlag() {
		return resamplingFlag;
	}

	public void setResamplingFlag(Long resamplingFlag) {
		this.resamplingFlag = resamplingFlag;
	}

	public Long getParentApplicationNo() {
		return parentApplicationNo;
	}

	public void setParentApplicationNo(Long parentApplicationNo) {
		this.parentApplicationNo = parentApplicationNo;
	}

	public Long getChindApplicationNo() {
		return chindApplicationNo;
	}

	public void setChindApplicationNo(Long chindApplicationNo) {
		this.chindApplicationNo = chindApplicationNo;
	}

	public Long getPostFlag() {
		return postFlag;
	}

	public void setPostFlag(Long postFlag) {
		this.postFlag = postFlag;
	}

	public String getItem_serial_no() {
		return item_serial_no;
	}

	public void setItem_serial_no(String item_serial_no) {
		this.item_serial_no = item_serial_no;
	}

	public String getMonth_year_of_item_manufacture() {
		return month_year_of_item_manufacture;
	}

	public void setMonth_year_of_item_manufacture(String month_year_of_item_manufacture) {
		this.month_year_of_item_manufacture = month_year_of_item_manufacture;
	}

	public String getBill_number() {
		return bill_number;
	}

	public void setBill_number(String bill_number) {
		this.bill_number = bill_number;
	}

	public String getBill_date() {
		return bill_date;
	}

	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
	}
    
    
    
    
}
