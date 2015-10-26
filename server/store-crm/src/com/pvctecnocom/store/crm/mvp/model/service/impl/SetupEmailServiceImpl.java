package com.pvctecnocom.store.crm.mvp.model.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pvctecnocom.store.crm.mvp.model.bo.SetupEmail;
import com.pvctecnocom.store.crm.mvp.model.dao.SetupEmailDAO;
import com.pvctecnocom.store.crm.mvp.model.service.SetupEmailService;

@Service(value="setupEmailService")
public class SetupEmailServiceImpl implements SetupEmailService 
{
	private SetupEmailDAO setupEmailDAO = null;
	
	public SetupEmailDAO getSetupEmailDAO() 
	{
		return this.setupEmailDAO;
	}
	
	@Resource
	public void setSetupEmailDAO(SetupEmailDAO setupEmailDAO) 
	{
		this.setupEmailDAO = setupEmailDAO;
	}	
	
	@Override
	public List<SetupEmail> findAll() 
	{		
		return this.setupEmailDAO.findAll();
	}
	
	@Override
	public void insertSetupEmail(SetupEmail setupEmail) 
	{		
		this.setupEmailDAO.insert(setupEmail);		
	}
	
	@Override
	public void updateSetupEmail(SetupEmail setupEmail) 
	{	
		this.setupEmailDAO.update(setupEmail);
	}

	@Override
	public void deleteSetupEmail(SetupEmail setupEmail) 
	{
		this.setupEmailDAO.delete(setupEmail);	
	}
	
	@Override
	public SetupEmail findById(String id) 
	{		
		return setupEmailDAO.findById(id);
	}		
}