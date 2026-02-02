package com.mpcz.deposit_scheme.backend.api.dto;
public class SamagraListDto {
	
	
    private String consumerName;
    private String guardianName;
    private String samagraId;
    private String khasraNo;
    private String loadRequested;
   
	
	// Default constructor
    public SamagraListDto() {
    }

    // Parameterized constructor
    public SamagraListDto(String name, String fatherName, String samagraId, String khasraNo) {
        this.consumerName = name;
        this.guardianName = fatherName;
        this.samagraId = samagraId;
        this.khasraNo = khasraNo;
    }

    // Getter and Setter methods for each field

   

    public String getSamagraId() {
        return samagraId;
    }

    public String getLoadRequested() {
		return loadRequested;
	}

	public void setLoadRequested(String loadRequested) {
		this.loadRequested = loadRequested;
	}

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

	public void setSamagraId(String samagraId) {
        this.samagraId = samagraId;
    }

    public String getKhasraNo() {
        return khasraNo;
    }

    public void setKhasraNo(String khasraNo) {
        this.khasraNo = khasraNo;
    }

    // You can also override toString() method for debugging or logging purposes
    @Override
    public String toString() {
        return "MyDTO{" +
                "name='" + consumerName + '\'' +
                ", fatherName='" + guardianName + '\'' +
                ", samagraId='" + samagraId + '\'' +
                ", khasraNo='" + khasraNo + '\'' +
                '}';
    }
}
