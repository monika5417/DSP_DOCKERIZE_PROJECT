package com.mpcz.deposit_scheme.backend.api.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="MMKYParentChild")
@Table(name = "MMKY_PAR_CHI")
public class MMKYParentChild {

    @Id
    @SequenceGenerator(name = "MMKY_PAR_CHI_SEQ", sequenceName = "MMKY_PAR_CHI_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMKY_PAR_CHI_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
    private Long id;

    @Column(name = "PARENT_APPLICATION_NUMBER")
    private String parentApplicationNumber;

    @Column(name = "CHILD_APPLICATION_NUMBER")
    private String childApplicationNumber;

    @Column(name = "LOAD")
    private String load;

    @Column(name = "CONSUMER_NAME")
    private String consumerName;
    
    @Column(name = "GUARDIAN_NAME")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


    
}
