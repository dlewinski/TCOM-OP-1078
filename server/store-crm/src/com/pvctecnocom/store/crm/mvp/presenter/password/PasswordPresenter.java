package com.pvctecnocom.store.crm.mvp.presenter.password;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.model.service.UserService;
import com.vaadin.ui.UI;

public class PasswordPresenter 
{    	
	private UserService usuarioService;
	 
	public PasswordPresenter() 
    {                         
        this.usuarioService = ((StoreCRM_UI)UI.getCurrent()).getUserService();          
    }
	
	public Boolean validarEmail(String email) 
	{             
		Boolean result = false;
		
		try 
	    {
			Usuario usuario = this.usuarioService.findByEmail(email);
			 
			if(usuario != null)			
				result = true;						 
	    }	  
		catch (Exception e) 
		{        	
			e.printStackTrace();;  
		}
		
		return result;
	}
	
	public void updateCode(String email, String code)
	{
		try 
	    {
			this.usuarioService.updateCodeByEmail(email, code);			 						
	    }	  
		catch (Exception e) 
		{        	
			e.getMessage();  
		}
	}
}