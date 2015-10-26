package com.pvctecnocom.store.crm.mvp.presenter.password;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.model.service.UserService;
import com.vaadin.ui.UI;

public class PasswordNewPresenter 
{    	
	private UserService usuarioService;
	 
	public PasswordNewPresenter() 
    {                      
        this.usuarioService = ((StoreCRM_UI)UI.getCurrent()).getUserService();          
    }		
	
	public Boolean validarCode(String code)
	{
		Boolean result;
		
		try 
	    {
			Usuario usuario = this.usuarioService.findByCode(code);
			 
			if(usuario != null)							 
				result = true;			 
			else			 
				result = false;			 
	    }	  
		catch (Exception e) 
		{        	
			e.getMessage();
			result = false;
		}
		
		return result;
	}
	
	public Boolean updatePasswordByCode(String code,  String salt, String password)
	{	
		try 
	    {
			int result = this.usuarioService.updatePasswordByCode(code, salt, password);		
			
			if(result == 1)
				return true;
			else
				return false;
	    }	  
		catch (Exception e) 
		{        				
			e.printStackTrace();
			
			return false;
		}
	}	
	
	public void insertUsuario(Usuario usuario)
	{	
		try 
	    {
			this.usuarioService.insert(usuario);											
	    }	  
		catch (Exception e) 
		{        				
			e.printStackTrace();
		}
	}	
}