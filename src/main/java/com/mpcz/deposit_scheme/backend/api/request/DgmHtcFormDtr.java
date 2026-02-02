package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class DgmHtcFormDtr implements Serializable {
	
	private String consumerApplicationNo;
	private Long dtrId;

}
