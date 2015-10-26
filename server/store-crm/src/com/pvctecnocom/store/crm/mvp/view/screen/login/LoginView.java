package com.pvctecnocom.store.crm.mvp.view.screen.login;

public interface LoginView 
{		
	void loginOK();
	
	void loginKO();
	
	void loginError(String message);
}