package com.pvctecnocom.store.crm.mvp.model.service;

import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.Perfil;

public interface PerfilService 
{			
	public List<Perfil> findAll();
	
	public Perfil findById(Integer perfilId);		
	
	public void insert(Perfil perfil);
	
	public void update(Perfil perfil);
	
	public void delete(Perfil perfil);
}