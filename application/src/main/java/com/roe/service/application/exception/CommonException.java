package com.roe.service.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CommonException(String message) {		
        super(message);        
    }
}
