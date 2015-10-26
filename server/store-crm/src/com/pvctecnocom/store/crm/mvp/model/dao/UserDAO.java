package com.pvctecnocom.store.crm.mvp.model.dao;

import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;

public interface UserDAO extends GenericDAO<Usuario, Integer>
{	
	public Usuario findByUsername(Usuario user);	
	
	public Usuario findByCode(String code);
	
	public Usuario findByEmail(String email);
	
	public void updateCodeByEmail(String email, String code);
	
	public int updatePasswordByCode(String code, String salt, String password);
}