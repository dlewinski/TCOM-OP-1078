package com.pvctecnocom.store.crm.mvp.presenter.pedido;

import java.util.List;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsultaDetalle;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoDetalleView;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoViewImpl;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;

public class PedidoDetallePresenter 
{
	private PedidoDetalleView view;
	private String accion = "";
	
	public PedidoDetallePresenter(PedidoDetalleView view) 
    {
		accion = ((StoreCRM_UI)UI.getCurrent()).getAccion();
        this.view = view;          
    } 
	
	@SuppressWarnings("serial")
	public void cargarPedidosDetalle(List<PedidoConsultaDetalle> pedidosConsultaDetalle) 
    {		
		VerticalLayout verticalLayout = new VerticalLayout();
    	verticalLayout.setSizeFull();
    	verticalLayout.setSpacing(true);
    	
    	int pageLength = 0;
		
		if(pedidosConsultaDetalle != null && pedidosConsultaDetalle.size() > 0)						
			pageLength = pedidosConsultaDetalle.size();
		
		Table tablePedidosConsulta = new Table();			
		tablePedidosConsulta.setPageLength(pageLength);			
		tablePedidosConsulta.setWidth("100%");
				
		tablePedidosConsulta.addContainerProperty("codigo", String.class, null, "MATERIAL", null, null);
		tablePedidosConsulta.addContainerProperty("descripcion", String.class, null, "DESCRIPCIÓN", null, null);
		tablePedidosConsulta.addContainerProperty("precio", String.class, null, "PRECIO", null, null);		
		tablePedidosConsulta.addContainerProperty("medida", String.class, null, "UM", null, null);
		tablePedidosConsulta.addContainerProperty("descuentoPredefinido", String.class, null, "%DESC P", null, null);
		tablePedidosConsulta.addContainerProperty("descuentoManual", String.class, null, "% DESC M", null, null);
		tablePedidosConsulta.addContainerProperty("descuentoTotal", String.class, null, "% DESC T", null, null);
		tablePedidosConsulta.addContainerProperty("neto", String.class, null, "NETO", null, null);
		tablePedidosConsulta.addContainerProperty("cantidad", String.class, null, "CANTIDAD", null, null);	    
		
		tablePedidosConsulta.setColumnWidth("codigo", 150);
		tablePedidosConsulta.setColumnWidth("precio", 85);		
		tablePedidosConsulta.setColumnWidth("medida", 70);
		tablePedidosConsulta.setColumnWidth("descuentoPredefinido", 85);
		tablePedidosConsulta.setColumnWidth("descuentoManual", 85);
		tablePedidosConsulta.setColumnWidth("descuentoTotal", 85);
		tablePedidosConsulta.setColumnWidth("neto", 85);
		tablePedidosConsulta.setColumnWidth("cantidad", 85);
							
		tablePedidosConsulta.setColumnAlignment("precio", Align.RIGHT);    			
		tablePedidosConsulta.setColumnAlignment("medida", Align.CENTER);
		tablePedidosConsulta.setColumnAlignment("descuentoPredefinido", Align.RIGHT);
		tablePedidosConsulta.setColumnAlignment("descuentoManual", Align.RIGHT);
		tablePedidosConsulta.setColumnAlignment("descuentoTotal", Align.RIGHT);
		tablePedidosConsulta.setColumnAlignment("neto", Align.RIGHT);
		tablePedidosConsulta.setColumnAlignment("cantidad", Align.RIGHT);
		
		tablePedidosConsulta.setSelectable(true);	
		
		if(pedidosConsultaDetalle != null)
    	{				
			String cantidad;
			int i = 0;
			
	    	for(PedidoConsultaDetalle pedidoConsultaDetalle : pedidosConsultaDetalle)
	    	{
	    		cantidad = "0";
	    		
	    		if(pedidoConsultaDetalle.getCantidadPedido() != null)
	    			cantidad = pedidoConsultaDetalle.getCantidadPedido().replaceAll(".000", "");
	    		
	    		tablePedidosConsulta.addItem(new Object[] 
				{ 
	    			pedidoConsultaDetalle.getNumeroMaterial(),
	    			pedidoConsultaDetalle.getDescripcionMaterial(),
	    			pedidoConsultaDetalle.getValorPrecio(),	    		
	    			pedidoConsultaDetalle.getUnidadMedida(),
	    			"",
	    			pedidoConsultaDetalle.getValorDescuento(),
	    			pedidoConsultaDetalle.getDescuentoAcumuladoConcedido(),
	    			pedidoConsultaDetalle.getValorNeto(),
	    			cantidad
				}, i++);
	    	}
    	}	
				
		verticalLayout.addComponent(tablePedidosConsulta);
    	verticalLayout.setComponentAlignment(tablePedidosConsulta, Alignment.TOP_CENTER);
    	
    	Button btnVolver = new Button();
    	btnVolver.setIcon(FontAwesome.LEVEL_UP);
    	
    	if(this.accion.equalsIgnoreCase("listadoPedidos"))		
    	{
    		btnVolver.setCaption("Volver al listado de pedidos");
        	btnVolver.setDescription("Volver al listado de pedidos");
    	}
		else	
		{	
			if(this.accion.equalsIgnoreCase("listadoOfertas"))			
			{
				btnVolver.setCaption("Volver al listado ofertas");
		    	btnVolver.setDescription("Volver al listado de ofertas");
			}    	    	    	
		}
    	
    	btnVolver.addClickListener(new Button.ClickListener() 
        {            									
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {   
				new PedidoViewImpl();
            }
        });
    	
    	verticalLayout.addComponent(btnVolver);
    	verticalLayout.setComponentAlignment(btnVolver, Alignment.TOP_CENTER);
    	
    	this.view.pedidoDetalleOK(verticalLayout);
    }
}