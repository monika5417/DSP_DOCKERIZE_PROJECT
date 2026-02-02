package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.intellij.lang.annotations.Identifier;

@Entity
@Table(name="MMKY_CALCULATION")
public class MmkyCalculation {
	
	@Id
	@SequenceGenerator(name = "MMKY_CALCULATION_SEQ", sequenceName = "MMKY_CALCULATION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMKY_CALCULATION_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
    private Long id;
	
	@Column(name="P_APP_NUM")
	private String parentApplicationNo;
	
	@Column(name="MMKY_lOAD")
	private double mmkyLoad;
	
	@Column(name="KV_Dis")
	private Long kvDistance;
	
	@Column(name="DTR_CAPACITY")
	private Long dtrCapacity ;
	
	@Column(name="CUT_POINT")
	private String cutPoint ;
	
	
	
	public String getCutPoint() {
		return cutPoint;
	}
	
	public double getMmkyLoad() {
		return mmkyLoad;
	}

	public void setMmkyLoad(double mmkyLoad) {
		this.mmkyLoad = mmkyLoad;
	}

	public void setCutPoint(String cutPoint) {
		this.cutPoint = cutPoint;
	}
	public String getParentApplicationNo() {
		return parentApplicationNo;
	}
	public void setParentApplicationNo(String parentApplicationNo) {
		this.parentApplicationNo = parentApplicationNo;
	}

	
	public Long getKvDistance() {
		return kvDistance;
	}
	public void setKvDistance(Long kvDistance) {
		this.kvDistance = kvDistance;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDtrCapacity() {
		return dtrCapacity;
	}
	public void setDtrCapacity(Long dtrCapacity) {
		this.dtrCapacity = dtrCapacity;
	}


	
}
