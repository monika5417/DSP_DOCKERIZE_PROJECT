package com.mpcz.deposit_scheme.backend.api.domain;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "APPLICATION_DETAILS_SUMMARY") // Replace with your actual table name
public class ApplicationDetailsSummary {

    @Id
    @Column(name = "APPLICATION_STATUS", nullable = false)
    private Long applicationStatus;

    @Column(name = "APPL_COUNT")
    private Long applCount;

    @Column(name = "DISCOM_ID")
    private Long discomId;

    @Column(name = "REGION_ID")
    private Long regionId;

    @Column(name = "CIRCLE_ID")
    private Long circleId;

    @Column(name = "DIV_ID")
    private Long divId;

    @Column(name = "SUBDIV_ID")
    private Long subdivId;

    @Column(name = "DC_ID")
    private Long dcId;

    // Constructors, Getters, and Setters

  
    public Long getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Long applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Long getApplCount() {
        return applCount;
    }

    public void setApplCount(Long applCount) {
        this.applCount = applCount;
    }

    public Long getDiscomId() {
        return discomId;
    }

    public void setDiscomId(Long discomId) {
        this.discomId = discomId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public Long getDivId() {
        return divId;
    }

    public void setDivId(Long divId) {
        this.divId = divId;
    }

    public Long getSubdivId() {
        return subdivId;
    }

    public void setSubdivId(Long subdivId) {
        this.subdivId = subdivId;
    }

    public Long getDcId() {
        return dcId;
    }

    public void setDcId(Long dcId) {
        this.dcId = dcId;
    }
}
