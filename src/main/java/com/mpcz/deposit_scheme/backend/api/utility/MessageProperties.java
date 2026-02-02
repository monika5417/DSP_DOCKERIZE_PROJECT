package com.mpcz.deposit_scheme.backend.api.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Component
public class MessageProperties {

	@Value("${message.otp.sms}")
	private String otpMessage;

	@Value("${message.default.pwd.sms}")
	private String defaultPasswordMessage;

	@Value("${message.forgotPassword}")
	private String forgotPasswordMessage;

	@Value("${message.bill}")
	private String billMessage;

	@Value("${message.bill.templateid}")
	private String billTemplateId;

	@Value("${message.default.forgotPassword.sms.templateId}")
	private String forgotPasswordTemplateId;

	@Value("${message.default.pwd.sms.templateId}")
	private String passwordTemplateId;

	@Value("${message.otp.sms.templateid}")
	private String otpTemplateId;

	@Value("${message.payment.success.sms}")
	private String paymentSuccessMessage;

	@Value("${message.payment.success.sms.templateid}")
	private String paymentSuccessMessageTemplateId;

	@Value("${message.payment.cancel.sms}")
	private String paymentCancelMessage;

	@Value("${message.payment.cancel.sms.templateid}")
	private String paymentCancelMessageTemplateId;

	@Value("${message.asd}")
	private String asdMessage;

	@Value("${message.asd.templateid}")
	private String asdMessageTemplateId;
	
	@Value("${message.sendtoconsumerbyMMKY.templateId}")
	private String tamplateIdsendsmsConsumerMsgfromMMKY;
	
	@Value("${message.sendtoconsumerbyMMKYsms}")
	private String sendsmsConsumerMsgfromMMKYsms;

	// tds194Q Messages

	@Value("${message.tds194qApproved}")
	private String tds194qApproved;

	@Value("${message.tds194qApproved.templateid}")
	private String tds194qApprovedTemplateid;

	@Value("${message.tds194qReject}")
	private String tds194qReject;

	@Value("${message.tds194qReject.templateid}")
	private String tds194qRejectTemplateid;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public String getOtpMessage() {
		return otpMessage;
	}

	public void setOtpMessage(String otpMessage) {
		this.otpMessage = otpMessage;
	}

	/**
	 * @return the defaultPasswordMessage
	 */
	public String getDefaultPasswordMessage() {
		return defaultPasswordMessage;
	}

	/**
	 * @param defaultPasswordMessage the defaultPasswordMessage to set
	 */
	public void setDefaultPasswordMessage(String defaultPasswordMessage) {
		this.defaultPasswordMessage = defaultPasswordMessage;
	}

	/**
	 * @return the forgotPasswordMessage
	 */
	public String getForgotPasswordMessage() {
		return forgotPasswordMessage;
	}

	/**
	 * @param forgotPasswordMessage the forgotPasswordMessage to set
	 */
	public void setForgotPasswordMessage(String forgotPasswordMessage) {
		this.forgotPasswordMessage = forgotPasswordMessage;
	}

	/**
	 * @return the billMessage
	 */
	public String getBillMessage() {
		return billMessage;
	}

	/**
	 * @param billMessage the billMessage to set
	 */
	public void setBillMessage(String billMessage) {
		this.billMessage = billMessage;
	}

	public String getBillTemplateId() {
		return billTemplateId;
	}

	public void setBillTemplateId(String billTemplateId) {
		this.billTemplateId = billTemplateId;
	}

	public String getForgotPasswordTemplateId() {
		return forgotPasswordTemplateId;
	}

	public void setForgotPasswordTemplateId(String forgotPasswordTemplateId) {
		this.forgotPasswordTemplateId = forgotPasswordTemplateId;
	}

	public String getPasswordTemplateId() {
		return passwordTemplateId;
	}

	public void setPasswordTemplateId(String passwordTemplateId) {
		this.passwordTemplateId = passwordTemplateId;
	}

	public String getOtpTemplateId() {
		return otpTemplateId;
	}

	public void setOtpTemplateId(String otpTemplateId) {
		this.otpTemplateId = otpTemplateId;
	}

	public String getPaymentSuccessMessage() {
		return paymentSuccessMessage;
	}

	public void setPaymentSuccessMessage(String paymentSuccessMessage) {
		this.paymentSuccessMessage = paymentSuccessMessage;
	}

	public String getPaymentSuccessMessageTemplateId() {
		return paymentSuccessMessageTemplateId;
	}

	public void setPaymentSuccessMessageTemplateId(String paymentSuccessMessageTemplateId) {
		this.paymentSuccessMessageTemplateId = paymentSuccessMessageTemplateId;
	}

	public String getPaymentCancelMessage() {
		return paymentCancelMessage;
	}

	public void setPaymentCancelMessage(String paymentCancelMessage) {
		this.paymentCancelMessage = paymentCancelMessage;
	}

	public String getPaymentCancelMessageTemplateId() {
		return paymentCancelMessageTemplateId;
	}

	public void setPaymentCancelMessageTemplateId(String paymentCancelMessageTemplateId) {
		this.paymentCancelMessageTemplateId = paymentCancelMessageTemplateId;
	}

	public String getAsdMessage() {
		return asdMessage;
	}

	public void setAsdMessage(String asdMessage) {
		this.asdMessage = asdMessage;
	}

	public String getAsdMessageTemplateId() {
		return asdMessageTemplateId;
	}

	public void setAsdMessageTemplateId(String asdMessageTemplateId) {
		this.asdMessageTemplateId = asdMessageTemplateId;
	}

	public String getTds194qApproved() {
		return tds194qApproved;
	}

	public void setTds194qApproved(String tds194qApproved) {
		this.tds194qApproved = tds194qApproved;
	}

	public String getTds194qApprovedTemplateid() {
		return tds194qApprovedTemplateid;
	}

	public void setTds194qApprovedTemplateid(String tds194qApprovedTemplateid) {
		this.tds194qApprovedTemplateid = tds194qApprovedTemplateid;
	}

	public String getTds194qReject() {
		return tds194qReject;
	}

	public void setTds194qReject(String tds194qReject) {
		this.tds194qReject = tds194qReject;
	}

	public String getTds194qRejectTemplateid() {
		return tds194qRejectTemplateid;
	}

	public void setTds194qRejectTemplateid(String tds194qRejectTemplateid) {
		this.tds194qRejectTemplateid = tds194qRejectTemplateid;
	}
	
	
//	charitra code start here
	@Value("${message.vendor}")
	private String sendMsgVendor;
	
	@Value("${message.consumer}")
	private String sendMsgConsumer;
	
	@Value("${message.vendor.templateId}")
	private String vendorTemplateId;
	
	@Value("${message.consumer.templateId}")
	private String consumerTemplateId;
	

	public String getSendMsgVendor() {
		return sendMsgVendor;
	}

	public void setSendMsgVendor(String sendMsgVendor) {
		this.sendMsgVendor = sendMsgVendor;
	}

	public String getSendMsgConsumer() {
		return sendMsgConsumer;
	}

	public void setSendMsgConsumer(String sendMsgConsumer) {
		this.sendMsgConsumer = sendMsgConsumer;
	}

	public String getVendorTemplateId() {
		return vendorTemplateId;
	}

	public void setVendorTemplateId(String vendorTemplateId) {
		this.vendorTemplateId = vendorTemplateId;
	}

	public String getConsumerTemplateId() {
		return consumerTemplateId;
	}

	public void setConsumerTemplateId(String consumerTemplateId) {
		this.consumerTemplateId = consumerTemplateId;
	}

	
	
	@Value("${message.consumer.accepetByDc}")
	private String messageConsumerAccepetByDC;
	
	@Value("${message.consumer.accepetByDc.templateId}")
	private String messageConsumerAccepetByDcTemplatedId;
	
	@Value("${message.serveyor.accepetByDc}")
	private String messageServeyouAccepetByDc;

	@Value("${message.serveyor.accepetByDc.templateId}")
	private String messageServeyorAccepetByDcTemplatedId;

	public String getMessageConsumerAccepetByDC() {
		return messageConsumerAccepetByDC;
	}

	public void setMessageConsumerAccepetByDC(String messageConsumerAccepetByDC) {
		this.messageConsumerAccepetByDC = messageConsumerAccepetByDC;
	}

	public String getMessageConsumerAccepetByDcTemplatedId() {
		return messageConsumerAccepetByDcTemplatedId;
	}

	public void setMessageConsumerAccepetByDcTemplatedId(String messageConsumerAccepetByDcTemplatedId) {
		this.messageConsumerAccepetByDcTemplatedId = messageConsumerAccepetByDcTemplatedId;
	}

	public String getMessageServeyouAccepetByDc() {
		return messageServeyouAccepetByDc;
	}

	public void setMessageServeyouAccepetByDc(String messageServeyouAccepetByDc) {
		this.messageServeyouAccepetByDc = messageServeyouAccepetByDc;
	}

	public String getMessageServeyorAccepetByDcTemplatedId() {
		return messageServeyorAccepetByDcTemplatedId;
	}

	public void setMessageServeyorAccepetByDcTemplatedId(String messageServeyorAccepetByDcTemplatedId) {
		this.messageServeyorAccepetByDcTemplatedId = messageServeyorAccepetByDcTemplatedId;
	}

	public String getTamplateIdsendsmsConsumerMsgfromMMKY() {
		return tamplateIdsendsmsConsumerMsgfromMMKY;
	}

	public void setTamplateIdsendsmsConsumerMsgfromMMKY(String tamplateIdsendsmsConsumerMsgfromMMKY) {
		this.tamplateIdsendsmsConsumerMsgfromMMKY = tamplateIdsendsmsConsumerMsgfromMMKY;
	}

	public String getSendsmsConsumerMsgfromMMKYsms() {
		return sendsmsConsumerMsgfromMMKYsms;
	}

	public void setSendsmsConsumerMsgfromMMKYsms(String sendsmsConsumerMsgfromMMKYsms) {
		this.sendsmsConsumerMsgfromMMKYsms = sendsmsConsumerMsgfromMMKYsms;
	}
	
	
	@Value("${message.consumer.mkmyPaymentUnderThirtyDay}")
	private String mkmyPaymentUnderThirty;
	
	@Value("${message.consumer.mkmyPayment.templateId}")
	private String mkmyPaymenttemplateId;


	public String getMkmyPaymentUnderThirty() {
		return mkmyPaymentUnderThirty;
	}

	public void setMkmyPaymentUnderThirty(String mkmyPaymentUnderThirty) {
		this.mkmyPaymentUnderThirty = mkmyPaymentUnderThirty;
	}

	public String getMkmyPaymenttemplateId() {
		return mkmyPaymenttemplateId;
	}

	public void setMkmyPaymenttemplateId(String mkmyPaymenttemplateId) {
		this.mkmyPaymenttemplateId = mkmyPaymenttemplateId;
	}
	
	
	
//	charitr code end here
	
	
	//Monika code start
	
	@Value("${message.consumer.mkmyPaymentRemainFiveDay}")
	private String mkmyPaymentRemainFiveDay;
	
	@Value("${message.consumer.mkmyPaymentRemain.templateId}")
	private String mkmyPaymentRemainTempId;


	public String getMkmyPaymentRemainFiveDay() {
		return mkmyPaymentRemainFiveDay;
	}

	public void setMkmyPaymentRemainFiveDay(String mkmyPaymentRemainFiveDay) {
		this.mkmyPaymentRemainFiveDay = mkmyPaymentRemainFiveDay;
	}

	public String getMkmyPaymentRemainTempId() {
		return mkmyPaymentRemainTempId;
	}

	public void setMkmyPaymentRemainTempId(String mkmyPaymentRemainTempId) {
		this.mkmyPaymentRemainTempId = mkmyPaymentRemainTempId;
	}
	
	// Monika code end here
	
	
	
//	charitra  resampling send msg code 03-11-2025
//	message.discom.user.resampling
//	message.discom.user.resampling.templateId
	
	@Value("${message.discom.user.resampling}")
	private String sendMsgReSamplingForAe;
	
	@Value("${message.discom.user.resampling.templateId}")
	private String sendMsgReSamplingForAeTamplateId;

	public String getSendMsgReSamplingForAe() {
		return sendMsgReSamplingForAe;
	}

	public void setSendMsgReSamplingForAe(String sendMsgReSamplingForAe) {
		this.sendMsgReSamplingForAe = sendMsgReSamplingForAe;
	}

	public String getSendMsgReSamplingForAeTamplateId() {
		return sendMsgReSamplingForAeTamplateId;
	}

	public void setSendMsgReSamplingForAeTamplateId(String sendMsgReSamplingForAeTamplateId) {
		this.sendMsgReSamplingForAeTamplateId = sendMsgReSamplingForAeTamplateId;
	}

	
}
