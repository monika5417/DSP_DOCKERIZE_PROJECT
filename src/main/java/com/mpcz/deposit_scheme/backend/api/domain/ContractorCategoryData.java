/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Monika Rajpoot
 * @since 11-Sep-2025
 * @description ContractorCategoryData.java class description
 */

@Entity
@Table(name = "CONTRACTOR_CATEGORY_DATA")
public class ContractorCategoryData {
	
	@Id
	@SequenceGenerator(name = "CONTRACTOR_CATEGORY_DATA_SEQ", sequenceName = "CONTRACTOR_CATEGORY_DATA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTOR_CATEGORY_DATA_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
	
	@Column(name="LINE_TYPE")
	private String lineType;
	
	@Column(name="START_AMOUNT")
	private BigDecimal startAmount;
	
	@Column(name="END_AMOUNT")
	private BigDecimal endAmount;
	
	@Column(name="CATEGORY_A1")
	private String categoryA1;
	
	@Column(name="CATEGORY_A2")
	private String categoryA2;
	
	@Column(name="CATEGORY_A3")
	private String categoryA3;
	
	@Column(name="CATEGORY_A4")
	private String categoryA4;
	
	@Column(name="CATEGORY_A5")
	private String categoryA5;
	
	@Column(name="DGM_SELECTED_PREFERENCE")
	private Long dgmSelectedPreference;
	
	@Column(name="SELECTION_DESCRIPTION")
	private String selectionDescription;
	
	
	public Long getDgmSelectedPreference() {
		return dgmSelectedPreference;
	}
	public void setDgmSelectedPreference(Long dgmSelectedPreference) {
		this.dgmSelectedPreference = dgmSelectedPreference;
	}
	public String getSelectionDescription() {
		return selectionDescription;
	}
	public void setSelectionDescription(String selectionDescription) {
		this.selectionDescription = selectionDescription;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	
	public BigDecimal getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}
	public BigDecimal getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}
	public String getCategoryA1() {
		return categoryA1;
	}
	public void setCategoryA1(String categoryA1) {
		this.categoryA1 = categoryA1;
	}
	public String getCategoryA2() {
		return categoryA2;
	}
	public void setCategoryA2(String categoryA2) {
		this.categoryA2 = categoryA2;
	}
	public String getCategoryA3() {
		return categoryA3;
	}
	public void setCategoryA3(String categoryA3) {
		this.categoryA3 = categoryA3;
	}
	public String getCategoryA4() {
		return categoryA4;
	}
	public void setCategoryA4(String categoryA4) {
		this.categoryA4 = categoryA4;
	}
	public String getCategoryA5() {
		return categoryA5;
	}
	public void setCategoryA5(String categoryA5) {
		this.categoryA5 = categoryA5;
	}
	@Override
	public String toString() {
		return "ContractorCategoryData [id=" + id + ", startAmount=" + startAmount + ", endAmount=" + endAmount
				+ ", categoryA1=" + categoryA1 + ", categoryA2=" + categoryA2 + ", categoryA3=" + categoryA3
				+ ", categoryA4=" + categoryA4 + ", categoryA5=" + categoryA5 + ", dgmSelectedPreference="
				+ dgmSelectedPreference + ", selectionDescription=" + selectionDescription + "]";
	}
	
	
	
	

}
