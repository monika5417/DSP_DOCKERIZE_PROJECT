//package com.mpcz.deposit_scheme.backend.api.domain;
//
//import java.math.BigDecimal;
//import java.sql.Date;
//import java.sql.Timestamp;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Entity
//@Table(name = "TAX")
//@Data
//@NoArgsConstructor
//public class Tax extends Auditable<Long> {
//
//	/**
//	 * 
//	 */
//
//	private static final long serialVersionUID = 1L;
//	
//	@SequenceGenerator(name = "TAX_SEQ", sequenceName = "TAX_SEQ", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAX_SEQ")
//	@Column(name = "ID", columnDefinition = "serial", updatable = false)
//	private Long id;
//	
//	@Column(name = "TAX_NAME")
//	private String taxName;
//	
//	@Column(name = "YEAR_FROM")
//	private Date yearFrom;
//	
//	@Column(name = "YEAR_TO")
//	private Date yearTo;
//	
//	@Column(name = "VALUE")
//	private BigDecimal value;
//	
//	@Column(name = "IS_ENABLE")
//	private String isEnamble;
//	
//	@Column(name="TAX_SLAB")
//	private Long taxSlab;
//
//	
//
//
//}
