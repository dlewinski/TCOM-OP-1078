package com.pvctecnocom.store.crm.mvp.presenter.pedido;

import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsulta;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoDetalleViewImpl;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class ColumnDetalleClickListener implements ClickListener
{					
	private PedidoConsulta pedidoConsulta;
	
	public PedidoConsulta getPedidoConsulta() 
	{
		return pedidoConsulta;
	}

	public void setPedidoConsulta(PedidoConsulta pedidoConsulta) 
	{
		this.pedidoConsulta = pedidoConsulta;
	}

	@Override
	public void buttonClick(ClickEvent event) 
	{		
		new PedidoDetalleViewImpl(this.pedidoConsulta);
	}					
}