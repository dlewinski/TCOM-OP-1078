package com.pvctecnocom.store.crm.mvp.presenter.pedido;

import com.pvctecnocom.store.crm.mvp.util.Utility;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class ColumnCantidadChangeListener implements ValueChangeListener
{		
	private Table tableProductos;							
	private TextField txtCantidad;	

	public void setTableProductos(Table tableProductos) 
	{
		this.tableProductos = tableProductos;
	}				
			
	public void setTxtCantidad(TextField txtCantidad) 
	{
		this.txtCantidad = txtCantidad;
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) 
	{				
		Utility.refreshTablaMateriales(tableProductos);
		
		txtCantidad.focus();
	}
}