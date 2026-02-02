package com.mpcz.deposit_scheme.backend.api.dto;
public class MMKYParentChildDTO {

    private String parentApplicationNumber;
    private String childApplicationNumber;
    private String load;
    private String cutPoint;
    private String elevenKvDistance;
    private String dtrCapacity;
    private String consumerName;
    private String guardianName;
    
    
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getParentApplicationNumber() {
		return parentApplicationNumber;
	}
	public void setParentApplicationNumber(String parentApplicationNumber) {
		this.parentApplicationNumber = parentApplicationNumber;
	}
	public String getChildApplicationNumber() {
		return childApplicationNumber;
	}
	public void setChildApplicationNumber(String childApplicationNumber) {
		this.childApplicationNumber = childApplicationNumber;
	}
	public String getLoad() {
		return load;
	}
	public void setLoad(String load) {
		this.load = load;
	}
	public String getCutPoint() {
		return cutPoint;
	}
	public void setCutPoint(String cutPoint) {
		this.cutPoint = cutPoint;
	}
	public String getElevenKvDistance() {
		return elevenKvDistance;
	}
	public void setElevenKvDistance(String elevenKvDistance) {
		this.elevenKvDistance = elevenKvDistance;
	}
	public String getDtrCapacity() {
		return dtrCapacity;
	}
	public void setDtrCapacity(String dtrCapacity) {
		this.dtrCapacity = dtrCapacity;
	}
    
    

}
