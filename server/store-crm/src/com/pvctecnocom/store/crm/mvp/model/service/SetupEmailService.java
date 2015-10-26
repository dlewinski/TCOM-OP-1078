package com.pvctecnocom.store.crm.mvp.model.service;

import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.SetupEmail;

public interface SetupEmailService 
{	
	public List<SetupEmail> findAll();
	
	public SetupEmail findById(String id);		
	
	public void insertSetupEmail(SetupEmail setupEmail);
	
	public void updateSetupEmail(SetupEmail setupEmail);
	
	public void deleteSetupEmail(SetupEmail setupEmail);	 				
}