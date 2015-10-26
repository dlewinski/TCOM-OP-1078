package com.pvctecnocom.store.crm.mvp.model.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvctecnocom.store.crm.mvp.model.bo.Perfil;
import com.pvctecnocom.store.crm.mvp.model.dao.PerfilDAO;
import com.pvctecnocom.store.crm.mvp.model.service.PerfilService;

@Service(value="perfilService")
public class PerfilServiceImpl implements PerfilService
{
	private PerfilDAO perfilDAO = null;
	
	public PerfilDAO getPerfilDAO() 
	{
		return this.perfilDAO;
	}
	
	@Resource
	@Autowired
	public void setPerfilDAO(PerfilDAO perfilDAO) 
	{
		this.perfilDAO = perfilDAO;
	}						

	@Override
	public List<Perfil> findAll() 
	{
		return perfilDAO.findAll();
	}
	
	@Override
	public Perfil findById(Integer perfilId) 
	{
		return perfilDAO.findById(perfilId);
	}			
	
	@Override
	public void insert(Perfil perfil) 
	{		
		perfilDAO.insert(perfil);
	}

	@Override
	public void update(Perfil perfil) 
	{
		perfilDAO.update(perfil);
	}
	
	@Override
	public void delete(Perfil perfil) 
	{
		perfilDAO.delete(perfil);
	}
}