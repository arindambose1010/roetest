package com.roe.service.application.domain;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceResponse implements Serializable {
	
	private String status;
	private String statusCode;
	private String responseMessage;
	private int searchCount;
	private Object resObject;
	
	
	
	

}
