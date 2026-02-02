package com.mpcz.deposit_scheme.backend.api.dto;
public class SamagraResponseDTO {

	public int Status;

	public String ErrCode;

	public String Msg;

	public String data;

	public String RequestIP;

	
	public SamagraResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getErrCode() {
		return ErrCode;
	}

	public void setErrCode(String errCode) {
		ErrCode = errCode;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getRequestIP() {
		return RequestIP;
	}

	public void setRequestIP(String requestIP) {
		RequestIP = requestIP;
	}
	
}