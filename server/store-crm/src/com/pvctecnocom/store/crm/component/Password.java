package com.pvctecnocom.store.crm.component;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Password 
{
	@NotBlank
	private String currentPassword;
	
	@Size(min=8, max=10)
	private String newPassword;
	
	@Size(min=8, max=10)
	private String confirmPassword;
	
	public Password()
	{		
	}

	public String getCurrentPassword() 
	{
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) 
	{
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() 
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword) 
	{
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() 
	{
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) 
	{
		this.confirmPassword = confirmPassword;
	}
	
	public boolean isEqualsNewPassword()
	{
		return newPassword.equals(confirmPassword);
	}	
}