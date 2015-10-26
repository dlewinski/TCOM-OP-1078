package com.pvctecnocom.store.crm.mvp.presenter.pedido;

import com.pvctecnocom.store.crm.mvp.util.Utility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class ColumnCantidadChangeListener implements ValueChangeListener
{		
	private Table tableProductos;				

	public void setTableProductos(Table tableProductos) 
	{
		this.tableProductos = tableProductos;
	}				
			
	@Override
	public void valueChange(ValueChangeEvent event) 
	{				
		Utility.refreshTablaMateriales(tableProductos);
	}
}