package com.pvctecnocom.store.crm.util;

import java.text.SimpleDateFormat;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.GenericBO;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class AppUtils 
{	
	public static void buildLastUpdatedInfo(GenericBO gbo, HorizontalLayout footer)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Usuario lastUpdateUser = StoreCRM_UI.getCurrent().getUserService().findById(gbo.getEnteredUserID());
		
		HorizontalLayout hLayout = new HorizontalLayout();
		
		Label lbl1 = new Label("Última actualización " + lastUpdateUser.getUsername() + " - " + sdf.format(gbo.getEnteredDateTime()));
		hLayout.addComponent(lbl1);
		hLayout.setComponentAlignment(lbl1, Alignment.TOP_CENTER);
		
		footer.addComponent(hLayout);
	    footer.setComponentAlignment(hLayout, Alignment.TOP_RIGHT);
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(AbstractField comp)
	{
		return (comp == null || comp.getValue() == null || comp.getValue().toString().length() == 0);
	}
}