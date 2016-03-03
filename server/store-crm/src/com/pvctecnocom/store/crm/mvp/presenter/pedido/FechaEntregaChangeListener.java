package com.pvctecnocom.store.crm.mvp.presenter.pedido;

import java.util.Date;

import com.pvctecnocom.store.crm.mvp.util.Utility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class FechaEntregaChangeListener implements ValueChangeListener
{		
	private Table tableProductos;				        		

	public void setTableProductos(Table tableProductos) 
	{
		this.tableProductos = tableProductos;
	}				
		
	@Override
	public void valueChange(ValueChangeEvent event) 
	{						
		Date fechaEntrega = null;
		
		if(event.getProperty() != null && event.getProperty().getValue() != null)
			fechaEntrega = (Date)event.getProperty().getValue();
		
		Utility.refreshTablaMaterialesFechaEntrega(tableProductos, fechaEntrega);						
	}
}