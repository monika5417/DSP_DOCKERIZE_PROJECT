package com.mpcz.deposit_scheme.backend.api.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "VendorAddMaterial")
@Table(name = "VENDOR_ADD_MATERIAL")
public class VendorAddMaterial extends Auditable<Long> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "VENDOR_ADD_MATERIAL_S", sequenceName = "VENDOR_ADD_MATERIAL_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VENDOR_ADD_MATERIAL_S")
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "CONSUMER_APPLICATION_NUMBER")
    private String consumerApplicationNumber;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "VENDOR_NAME")
    private String vendorName;

    @Column(name = "VENDOR_MATERIAL_SPECIFICATION")
    private String vendorMaterialSpecification;

    @Column(name = "TRANSFORMER_SERIAL_NO")
    private String transformerSerialNo;

    @Column(name = "MATERIAL_INSTALLATION_DATE")
    private String materialInstallationDate;
    
    @Column(name = "RESAMPLING_FLAG")
    private Long resamplingFlag = 0L;
 
    
    
//    0 matlab abhi sapling nai hui hai
//    1 matlab abhi supling ho chuki hai parent ban chuka hai  OR YE HI TESTING KE LIYE JAYEGA
//    2 matlab abhi supling ho chuki hai child  ban chuka hai
    
    
    @Column(name = "POST_FLAG")
    private Long postFlag = 0L ;
 
    @Column(name ="ITEM_SERIAL_NO")
    private String item_serial_no;
    
    @Column(name ="MONTH_YEAR_OF_ITEM_MANUFACTURE")
    private String month_year_of_item_manufacture;
    
    @Column(name ="BILL_NUMBER")
    private String bill_number;
    
    @Column(name ="BILL_DATE")
    private String bill_date;
    
    

    
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

	public Long getResamplingFlag() {
		return resamplingFlag;
	}

	public void setResamplingFlag(Long resamplingFlag) {
		this.resamplingFlag = resamplingFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMaterialInstallationDate() {
		return materialInstallationDate;
	}

	public void setMaterialInstallationDate(String materialInstallationDate) {
		this.materialInstallationDate = materialInstallationDate;
	}

	@Column(name="RE_SAMPLING_DATA")
	private String ReSamplingData;

	public String getReSamplingData() {
		return ReSamplingData;
	}

	public void setReSamplingData(String reSamplingData) {
		ReSamplingData = reSamplingData;
	}

	

	public Long getPostFlag() {
		return postFlag;
	}

	public void setPostFlag(Long postFlag) {
		this.postFlag = postFlag;
	}
	
	@Column(name = "PARANT_APPLICATION_NO", columnDefinition = "PARANT_APPLICATION_NO", updatable = true)
	private  String ParantApplicationNo ;

	@Column(name = "CHILD_APPLICATION_NO", columnDefinition = "childApplicationNo", updatable = true)
	private String childApplicationNo;


//	@Column(name = "TESTING_NOT_DONE", columnDefinition = "PARANT_APPLICATION_NO", updatable = true)
//	private String samplingAndTestingNotDone;
	
	
	@Column(name = "TESTING_AND_SAMPLING", columnDefinition = "PARANT_APPLICATION_NO", updatable = true)
	private String participateForTestingAndSampling = "NOT DONE";
	
	
	@Column(name = "TESTING_SAMPLE_SELECTED", columnDefinition = "PARANT_APPLICATION_NO", updatable = true)
	private String testingsampleSelected;


	@Column(name = "SHUFFLING_FLAG", columnDefinition = "childApplicationNo", updatable = true)
	private Long shufflingFlag =0L;
	
	

	public Long getShufflingFlag() {
		return shufflingFlag;
	}

	public void setShufflingFlag(Long shufflingFlag) {
		this.shufflingFlag = shufflingFlag;
	}

	public String getParantApplicationNo() {
		return ParantApplicationNo;
	}

	public void setParantApplicationNo(String parantApplicationNo) {
		ParantApplicationNo = parantApplicationNo;
	}

	public String getChildApplicationNo() {
		return childApplicationNo;
	}

	public void setChildApplicationNo(String childApplicationNo) {
		this.childApplicationNo = childApplicationNo;
	}

//	public String getSamplingAndTestingNotDone() {
//		return samplingAndTestingNotDone;
//	}
//
//	public void setSamplingAndTestingNotDone(String samplingAndTestingNotDone) {
//		this.samplingAndTestingNotDone = samplingAndTestingNotDone;
//	}

	public String getParticipateForTestingAndSampling() {
		return participateForTestingAndSampling;
	}

	public void setParticipateForTestingAndSampling(String participateForTestingAndSampling) {
		this.participateForTestingAndSampling = participateForTestingAndSampling;
	}

	public String getTestingsampleSelected() {
		return testingsampleSelected;
	}

	public void setTestingsampleSelected(String testingsampleSelected) {
		this.testingsampleSelected = testingsampleSelected;
	}
	


	
	
	
}
