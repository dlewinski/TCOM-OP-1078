package com.pvctecnocom.store.crm.mvp.model.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.vaadin.server.VaadinSession;

@MappedSuperclass
public class GenericBO 
{
	@Transient
	private Boolean statusOK;			

	@Transient
	private String statusCode;
	
	@Transient
	private String statusDescription;
	
	@Transient
	private Usuario userSession;
	
	@Column(name="entered_user_id")
	private Integer enteredUserID;

	@Column(name="entered_date_time")
	private Date enteredDateTime;

	public GenericBO()
	{
		statusOK = true;
		statusCode = "0";
		statusDescription = "";		
		
		if(VaadinSession.getCurrent() != null)
			userSession = (Usuario)VaadinSession.getCurrent().getAttribute(Usuario.class.getName());				
	}		
	
	public Boolean isStatusOK() 
	{
		return statusOK;
	}

	public void setStatusOK(Boolean statusOK) 
	{
		this.statusOK = statusOK;
	}
	
	public String getStatusCode() 
	{
		return statusCode;
	}

	public void setStatusCode(String statusCode) 
	{
		this.statusCode = statusCode;
	}

	public String getStatusDescription() 
	{
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) 
	{
		this.statusDescription = statusDescription;
	}	
	
	public Usuario getUserSession() 
	{
		return userSession;
	}

	public void setUserSession(Usuario userSession) 
	{
		this.userSession = userSession;
	}

	public Integer getEnteredUserID() 
	{
		return enteredUserID;
	}

	public void setEnteredUserID(Integer enteredUserID) 
	{
		this.enteredUserID = enteredUserID;
	}

	public Date getEnteredDateTime() 
	{
		return enteredDateTime;
	}

	public void setEnteredDateTime(Date enteredDateTime) 
	{
		this.enteredDateTime = enteredDateTime;
	}		
}