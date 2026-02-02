package com.mpcz.deposit_scheme.backend.api.dto;

public class QcResponseDto {
	
	 private String message;
	    private int status;
	    private Object object;
	    private String error;
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public Object getObject() {
			return object;
		}
		public void setObject(Object object) {
			this.object = object;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
	    
	    

}
