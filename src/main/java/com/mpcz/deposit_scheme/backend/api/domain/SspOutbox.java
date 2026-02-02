package com.mpcz.deposit_scheme.backend.api.domain;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "SSP_OUTBOX")
public class SspOutbox {

    public SspOutbox() {
		super();
	}

	@Id
    @SequenceGenerator(name = "SSP_OUTBOX_SEQ", sequenceName = "SSP_OUTBOX_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SSP_OUTBOX_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_NO", nullable = false, length = 50)
    private String applicationNo;

    @Lob
    @Column(name = "PAYLOAD", nullable = false)
    private String payload;

    @Column(name = "STATUS", length = 20)
    private String status; 
    // PENDING / SUCCESS / FAILED

    @Column(name = "RETRY_COUNT")
    private Integer retryCount = 0;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "LAST_TRIED_AT")
    private LocalDateTime lastTriedAt;

    @Column(name = "ERROR_MSG", length = 500)
    private String errorMsg;

    @Column(name = "DSP_STATUS_UPDATED", length = 1)
    private String dspStatusUpdated = "N"; 
    // Y / N

    /* ---------- Lifecycle ---------- */

    public SspOutbox(String consumerApplicationNo, String payload2, String string, int i) {
		// TODO Auto-generated constructor stub
	}

	@PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
        this.retryCount = 0;
    }

    /* ---------- Helper Methods ---------- */

    public void incrementRetry(String errorMsg) {
        this.retryCount++;
        this.lastTriedAt = LocalDateTime.now();
        this.errorMsg = errorMsg;
    }

    public void markSuccess() {
        this.status = "SUCCESS";
        this.lastTriedAt = LocalDateTime.now();
        this.dspStatusUpdated = "Y";
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastTriedAt() {
		return lastTriedAt;
	}

	public void setLastTriedAt(LocalDateTime lastTriedAt) {
		this.lastTriedAt = lastTriedAt;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getDspStatusUpdated() {
		return dspStatusUpdated;
	}

	public void setDspStatusUpdated(String dspStatusUpdated) {
		this.dspStatusUpdated = dspStatusUpdated;
	}
    
    
   
    /* ---------- Getters & Setters ---------- */
    // generate via IDE
}
