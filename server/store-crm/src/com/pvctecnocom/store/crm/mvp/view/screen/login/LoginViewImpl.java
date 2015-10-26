package com.pvctecnocom.store.crm.mvp.view.screen.login;

import java.util.ArrayList;
import java.util.List;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.presenter.login.LoginSAPPresenter;
import com.pvctecnocom.store.crm.mvp.presenter.sap.SAPPresenter;
import com.pvctecnocom.store.crm.mvp.view.screen.menu.MenuViewImpl;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class LoginViewImpl implements LoginView
{		
	private LoginSAPPresenter loginPresenter;	    	
	private String clienteSAP;
	private String numeroVendedorCRM;
	private SAPPresenter sapPresenter;
	
	public LoginViewImpl() 	
    {          	    	    	
    } 
	
    public LoginViewImpl(String clienteSAP, String numeroVendedorCRM) 	
    {      
    	this.clienteSAP = clienteSAP;
    	this.numeroVendedorCRM = numeroVendedorCRM;
    	
    	this.sapPresenter = new SAPPresenter();
    	this.loginPresenter = new LoginSAPPresenter(this);    	    	
    	this.loginPresenter.login(clienteSAP);    	    	
    }    

    @Override
    public void loginOK()     
    {    	      	
    	List<Cliente> clientes = sapPresenter.getClientesByVendedor(numeroVendedorCRM);    	    	
    	
    	if(clientes == null)    		
    		clientes = new ArrayList<Cliente>();    	    	
    	
    	((StoreCRM_UI)UI.getCurrent()).setClientes(clientes);
    	
    	new MenuViewImpl();
    }
    
    @Override
    public void loginKO() 
    {    	    
    	String message = "Número de cliente SAP " + this.clienteSAP + " no encontrado";
		Notification notification = new Notification(message,"", Notification.Type.ERROR_MESSAGE, true);	    	
    	notification.show(Page.getCurrent());
    }
    
    @Override
    public void loginError(String message) 
    {    
    	Notification notification = new Notification(message,"", Notification.Type.TRAY_NOTIFICATION, true);    	
    	notification.show(Page.getCurrent()); 
    }
}