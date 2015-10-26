package com.pvctecnocom.store.crm.mvp.presenter.login;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.presenter.user.UserPresenter;

public class LoginPresenter 
{
	private UserPresenter userPresenter;
	
	public LoginPresenter()     
    {	
		 this.userPresenter = new UserPresenter();
    }
	
	public Usuario login(String username, String password) 
    {		
		Usuario user = null;
		
		try 
        {
			user = new Usuario();
			user.setUsername(username);
			user = userPresenter.findByUsername(user);			
        				
        	if(user != null && BCrypt.checkpw(password.concat(user.getSalt()), user.getPassword()))        	
        		user.setUserSession(user);
        	else
        	{	
        		user = new Usuario();
				
				user.setStatusOK(false);
				user.setStatusCode("-1");
				user.setStatusDescription("Usuario y/o Contraseña incorrectos");
        	}
        }
		catch (Exception e) 
        {
        	e.printStackTrace();        	
        }
		finally
		{
			if(user == null)
			{
				user = new Usuario();
				
				user.setStatusOK(false);
				user.setStatusCode("-1");
				user.setStatusDescription("Usuario y/o Contraseña incorrectos");
			}
		}
		                
        return user;
    }
}