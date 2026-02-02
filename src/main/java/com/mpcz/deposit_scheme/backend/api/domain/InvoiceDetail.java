package com.mpcz.deposit_scheme.backend.api.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVOICE_DETAIL")
@Data
@NoArgsConstructor
public class InvoiceDetail extends Auditable<Long>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "INVOICE_DETAIL_SEQ", sequenceName = "INVOICE_DETAIL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVOICE_DETAIL_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "CHARGES_ID")
	private Integer chargesId;
	
	@Column(name = "INVOICE_ID")
	private String invoiceId;
	
	
	
	

}
