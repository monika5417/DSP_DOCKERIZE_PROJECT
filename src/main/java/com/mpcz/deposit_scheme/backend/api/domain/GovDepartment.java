package com.mpcz.deposit_scheme.backend.api.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Gov_Department")
public class GovDepartment {

    @Id
    @SequenceGenerator(name = "Gov_Department_SEQ", sequenceName = "Gov_Department_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Gov_Department_SEQ")
    @Column(name = "GOV_DEPARTMENT_ID")
    private Long govDepartmentId;
    
    @Column(name = "GOV_DEPARTMENT_NAME")
    private String govDepartmentName;

    // Default constructor
    public GovDepartment() {
    }

    // Parameterized constructor
    public GovDepartment(String govDepartmentName) {
        this.govDepartmentName = govDepartmentName;
    }

    // Getters and setters
    public Long getGovDepartmentId() {
        return govDepartmentId;
    }

    public void setGovDepartmentId(Long govDepartmentId) {
        this.govDepartmentId = govDepartmentId;
    }

    public String getGovDepartmentName() {
        return govDepartmentName;
    }

    public void setGovDepartmentName(String govDepartmentName) {
        this.govDepartmentName = govDepartmentName;
    }
}
