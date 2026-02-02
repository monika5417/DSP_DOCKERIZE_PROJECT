package com.mpcz.deposit_scheme.backend.api.domain;
import javax.persistence.*;

@Entity
@Table(name = "DEMAND_NOTE_ALLOWED_STATUS")
public class DemandNoteAllowedStatus {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "STATUS_ID", nullable = false)
    private Long statusId;

    @Column(name = "IS_ACTIVE")
    private String isActive;

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
