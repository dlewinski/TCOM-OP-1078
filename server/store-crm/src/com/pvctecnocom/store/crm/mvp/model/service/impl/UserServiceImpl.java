package com.pvctecnocom.store.crm.mvp.model.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.model.dao.UserDAO;
import com.pvctecnocom.store.crm.mvp.model.service.UserService;

@Service(value="userService")
public class UserServiceImpl implements UserService
{
	private UserDAO userDAO = null;
	
	public UserDAO getUserDAO() 
	{
		return this.userDAO;
	}
	
	@Resource
	@Autowired
	public void setUserDAO(UserDAO userDAO) 
	{
		this.userDAO = userDAO;
	}						

	@Override
	public List<Usuario> findAll() 
	{
		return userDAO.findAll();
	}
	
	@Override
	public Usuario findById(Integer userId) 
	{
		return userDAO.findById(userId);
	}
	
	@Override
	public Usuario findByUsername(Usuario user) 
	{
		return userDAO.findByUsername(user);
	}	
	
	@Override
	public void insert(Usuario user) 
	{		
		userDAO.insert(user);
	}

	@Override
	public void update(Usuario user) 
	{
		userDAO.update(user);
	}
	
	@Override
	public void delete(Usuario user) 
	{
		userDAO.delete(user);
	}
	
	@Override
	public Usuario findByCode(String code) 
	{		
		return userDAO.findByCode(code);
	}
	
	@Override
	public int updatePasswordByCode(String code, String salt, String password) 
	{
		return userDAO.updatePasswordByCode(code, salt, password);
	}	
	
	@Override
	public Usuario findByEmail(String email) 
	{		
		return userDAO.findByEmail(email);
	}

	@Override
	public void updateCodeByEmail(String email, String code) 
	{	
		userDAO.updateCodeByEmail(email, code);
	}
}