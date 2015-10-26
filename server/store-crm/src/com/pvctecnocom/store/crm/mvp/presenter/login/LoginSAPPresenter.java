package com.pvctecnocom.store.crm.mvp.presenter.login;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.presenter.sap.SAPPresenter;
import com.pvctecnocom.store.crm.mvp.view.screen.login.LoginView;
import com.vaadin.ui.UI;

public class LoginSAPPresenter 
{
    private LoginView view;        
    private SAPPresenter sapPresenter;     
    
    public LoginSAPPresenter(LoginView view) 
    {
        this.view = view;                            
        this.sapPresenter = new SAPPresenter();
    }    	       
       
    public void login(String numeroClienteSAP) 
    {               
    	if(numeroClienteSAP == null)
    		view.loginOK();
    	else
    	{	
	        try 
	        {        	
	        	Cliente clienteSAP = this.sapPresenter.cargarCliente(numeroClienteSAP);   
	        	
	        	if (clienteSAP != null) //EXISTE EN SAP, SE PERMITE INICIAR SESIÓN
	        	{        		
	        		clienteSAP.setNumeroCliente(numeroClienteSAP);                		                		
	        			        			        		
	        		((StoreCRM_UI)UI.getCurrent()).setCliente(clienteSAP);
	        		
	        		view.loginOK();
	        	}
	        	else          		
	        		view.loginKO(); //NO EXISTE EN SAP, NO SE PERMITE INICIAR SESIÓN    		
	        } 
	        catch (Exception e) 
	        {        	
	        	view.loginError(e.getMessage());  
	        }       
	    }
    }	
}