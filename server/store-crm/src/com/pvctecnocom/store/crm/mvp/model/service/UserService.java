package com.pvctecnocom.store.crm.mvp.model.service;

import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;

public interface UserService 
{			
	public List<Usuario> findAll();
	
	public Usuario findByCode(String code);
	
	public Usuario findByEmail(String email);
	
	public Usuario findById(Integer userId);
	
	public Usuario findByUsername(Usuario user);		
	
	public void insert(Usuario user);
	
	public void update(Usuario user);
	
	public void updateCodeByEmail(String email, String code);
	
	public int updatePasswordByCode(String code, String salt, String password);	
	
	public void delete(Usuario user);
}