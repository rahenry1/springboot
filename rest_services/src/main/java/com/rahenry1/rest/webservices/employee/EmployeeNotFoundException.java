package com.rahenry1.rest.webservices.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1991358370891772148L;

	public EmployeeNotFoundException(String message) {
		super(message);
	}


}
