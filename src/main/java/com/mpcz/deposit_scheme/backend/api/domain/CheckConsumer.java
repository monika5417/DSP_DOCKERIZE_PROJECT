package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CHECK_CONSUMER")
public class CheckConsumer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "CHECK_CONSUMER_SEQ")
	@SequenceGenerator(name = "CHECK_CONSUMER_SEQ",sequenceName = "CHECK_CONSUMER_SEQ",allocationSize =1 )
	@Column(name ="ID")
	private int id;
	
	
	@Column(name ="CONSUMER_NUMBER")
	private String consumerNumber;
	
	@Column(name ="CHECK_CONSUMER")
	private String  checkConsumer;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getConsumerNumber() {
		return consumerNumber;
	}

	public void setConsumerNumber(String consumerNumber) {
		this.consumerNumber = consumerNumber;
	}

	public String getCheckConsumer() {
		return checkConsumer;
	}

	public void setCheckConsumer(String checkConsumer) {
		this.checkConsumer = checkConsumer;
	}

	
	
}
