package com.roe.service.application.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.roe.service.application.domain.ServiceResponse;

@ControllerAdvice
public class CommonExceptionAdvice 
{
		@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    @ExceptionHandler({CommonException.class})
	    public ResponseEntity<ServiceResponse> handle(CommonException e) {
			System.out.println("#################################################"+e.getMessage());
		    ServiceResponse response = new ServiceResponse("Fail",String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),"Service is temporarily down, Please be with us",0,e.getMessage());
		    return new ResponseEntity<ServiceResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

}
