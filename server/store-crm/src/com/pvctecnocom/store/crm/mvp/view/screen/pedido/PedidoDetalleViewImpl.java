package com.pvctecnocom.store.crm.mvp.view.screen.pedido;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsulta;
import com.pvctecnocom.store.crm.mvp.presenter.pedido.PedidoDetallePresenter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class PedidoDetalleViewImpl implements PedidoDetalleView 
{
	private PedidoDetallePresenter pedidoDetallePresenter;
	private PedidoConsulta pedidoConsulta;
	private String accion = "";
	
	public PedidoDetalleViewImpl(PedidoConsulta pedidoConsulta) 	
    {
		accion = ((StoreCRM_UI)UI.getCurrent()).getAccion();
		
		this.pedidoConsulta = pedidoConsulta;
		
    	this.pedidoDetallePresenter = new PedidoDetallePresenter(this);
    	this.pedidoDetallePresenter.cargarPedidosDetalle(pedidoConsulta.getDetalles());
    }
	
	@Override
	public void pedidoDetalleOK(Component component) 
	{	
		String title = "";
		
		Label labelBreadcrumbs = new Label();
		
		if(this.accion.equalsIgnoreCase("listadoPedidos"))		
			title = "Visualizar pedido: ";		
		else		
			if(this.accion.equalsIgnoreCase("listadoOfertas"))			
				title = "Visualizar oferta: ";		
				
		labelBreadcrumbs.setCaption(title + this.pedidoConsulta.getDocumentoVenta() + " / " + this.pedidoConsulta.getValorNeto() + " " + this.pedidoConsulta.getDocumentoMoneda() + " / " + this.pedidoConsulta.getDocumentoFecha());
		labelBreadcrumbs.setIcon(FontAwesome.TASKS);			
		labelBreadcrumbs.setSizeUndefined();
		
		((StoreCRM_UI)UI.getCurrent()).getPageLayout().getBodyLayout().getContentLayout().showComponentTop(labelBreadcrumbs);
		
		((StoreCRM_UI)UI.getCurrent()).getPageLayout().getBodyLayout().getContentLayout().showComponentBottom(component);
	}	
}