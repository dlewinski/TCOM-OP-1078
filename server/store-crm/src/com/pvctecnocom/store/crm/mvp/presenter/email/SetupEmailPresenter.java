package com.pvctecnocom.store.crm.mvp.presenter.email;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.SetupEmail;
import com.pvctecnocom.store.crm.mvp.model.service.SetupEmailService;
import com.vaadin.ui.UI;

public class SetupEmailPresenter 
{	
	private SetupEmailService setupEmailService;  	
	
	public SetupEmailPresenter() 
    {                                          	    
        this.setupEmailService = ((StoreCRM_UI)UI.getCurrent()).getSetupEmailService();                   
    } 
		
	public SetupEmail cargarSetupEmail(String id) 
    {				       	
		return setupEmailService.findById(id);
    }		
}