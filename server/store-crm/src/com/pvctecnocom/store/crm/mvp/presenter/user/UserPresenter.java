package com.pvctecnocom.store.crm.mvp.presenter.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.model.service.UserService;

public class UserPresenter 
{
	private UserService userService;
	
	public UserPresenter()     
    {	
		 this.userService = StoreCRM_UI.getCurrent().getUserService();
    }														
		
	public List<Usuario> findAll()
	{
		List<Usuario> users = null;
				
		try
		{
			users = userService.findAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(users == null)
				users = new ArrayList<Usuario>();
		}
		
		return users;
	}
	
	public Usuario findById(Integer userId)
	{				
		Usuario user = null;
		
		try
		{
			user = userService.findById(userId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(user == null)
				user = new Usuario();
		}
		
		return user;
	}	
	
	public Usuario findByUsername(Usuario user)
	{				
		try
		{
			user = userService.findByUsername(user);
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
		
		return user;
	}
	
	public void insert(Usuario user)
	{
		try
		{
			userService.insert(user);
		}				
		catch (PersistenceException e)
		{
			user.setStatusOK(false);
			user.setStatusCode("-1");
			user.setStatusDescription("El Usuario ya se encuentra definido");
			
			String constraintName = ((ConstraintViolationException)e.getCause()).getConstraintName();
			
			if(constraintName != null && constraintName.equalsIgnoreCase("email_UNIQUE"))
				user.setStatusDescription("El email ingresado ya se encuentra asignado a otro usuario");
		}
		catch(Exception e)
		{
			e.printStackTrace();						
			
			user.setStatusOK(false);
			user.setStatusCode("-1");
			user.setStatusDescription("Exception: " + e);
		}
		finally
		{		
			if(user == null)
			{
				user = new Usuario();
				
				user.setStatusOK(false);
				user.setStatusCode("-1");
				user.setStatusDescription("NULL");
			}
		}
	}	
	
	public void update(Usuario user)
	{
		try
		{
			userService.update(user);
		}		
		catch (DataIntegrityViolationException e)
    	{			
			user.setStatusOK(false);
			user.setStatusCode("-1");						
			user.setStatusDescription("El Usuario ya se encuentra definido");
			
			String constraintName = ((ConstraintViolationException)e.getCause()).getConstraintName();
			
			if(constraintName != null && constraintName.equalsIgnoreCase("email_UNIQUE"))
				user.setStatusDescription("El email ingresado ya se encuentra asignado a otro usuario");
    	}
		catch(Exception e)
		{
			e.printStackTrace();						
			
			user.setStatusOK(false);
			user.setStatusCode("-1");
			user.setStatusDescription("Exception: " + e);
		}
		finally
		{		
			if(user == null)
			{
				user = new Usuario();
				
				user.setStatusOK(false);
				user.setStatusCode("-1");
				user.setStatusDescription("NULL");
			}
		}
	}
	
	public void delete (Usuario user)
	{
		try
		{
			userService.delete(user);	
		}
		catch (DataIntegrityViolationException e)
    	{			
			user.setStatusOK(false);
			user.setStatusCode("-1");
			user.setStatusDescription("El Usuario no se puede eliminar");
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{			
		}
	}	
}