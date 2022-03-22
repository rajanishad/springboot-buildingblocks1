package com.stacksimplify.restservices.exceptions;

import java.util.Date;

//simple custom error detailsbean
public class CustomErrorDetails {
	//fields const
	private Date timeStamp;
	private String message;
	private String errordetails;
	public CustomErrorDetails(Date timeStamp, String message, String errordetails) {
		this.timeStamp = timeStamp;
		this.message = message;
		this.errordetails = errordetails;
	}
	//getters
	public Date getTimeStamp() {
		return timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public String getErrordetails() {
		return errordetails;
	}
	

	
}
