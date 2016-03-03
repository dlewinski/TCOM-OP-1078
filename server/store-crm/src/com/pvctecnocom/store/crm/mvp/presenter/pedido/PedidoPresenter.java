package com.pvctecnocom.store.crm.mvp.presenter.pedido;

import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.PedidoViewEvent;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsulta;
import com.pvctecnocom.store.crm.mvp.presenter.sap.SAPPresenter;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoNuevoViewImpl;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoView;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class PedidoPresenter 
{
	private PedidoView view;
	private SAPPresenter sapPresenter;
	private String accion = "";
	
	public PedidoPresenter(PedidoView view) 
    {
		StoreCrmEventBus.register(this);
					
        this.view = view;
        this.view.pedidoOK();                               
    } 
	
	@SuppressWarnings("serial")
	public void cargarPedidos() 
    {	
		accion = ((StoreCRM_UI)UI.getCurrent()).getAccion();
		
		this.sapPresenter = new SAPPresenter(); 
		
		String accion = ((StoreCRM_UI)UI.getCurrent()).getAccion();
		String title = "";
		
		if(accion != null)
		{
			if(accion.equalsIgnoreCase("listadoPedidos"))
				title = "Pedidos realizados";
			else
			{
				if(accion.equalsIgnoreCase("listadoOfertas"))
					title = "Ofertas realizadas";
			}
		}
		
		VerticalLayout verticalLayout = new VerticalLayout();
    	verticalLayout.setSizeFull();
    	verticalLayout.setSpacing(true);    		    	    	
		
		Cliente cliente = ((StoreCRM_UI)UI.getCurrent()).getCliente();
		
		List<PedidoConsulta> pedidosConsulta = null; 
				
		if(this.accion.equalsIgnoreCase("listadoPedidos"))		
			pedidosConsulta = this.sapPresenter.cargarPedidos(cliente.getNumeroCliente());		
		else		
			if(this.accion.equalsIgnoreCase("listadoOfertas"))			
				pedidosConsulta = this.sapPresenter.cargarOfertas(cliente.getNumeroCliente());			
						
		int pageLength = 0;
		
		if(pedidosConsulta != null && pedidosConsulta.size() > 0)						
			pageLength = pedidosConsulta.size();		
				
		Table tablePedidos = new Table();
		
		tablePedidos.addStyleName(ValoTheme.TABLE_BORDERLESS);
		tablePedidos.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
		tablePedidos.addStyleName(ValoTheme.TABLE_COMPACT);
        
		tablePedidos.setCaption(title);
		tablePedidos.setIcon(FontAwesome.LIST);
				
		tablePedidos.setPageLength(pageLength);			
		tablePedidos.setSizeFull();		
				
		tablePedidos.addContainerProperty("fecha", String.class, null, "FECHA", null, null);
		tablePedidos.addContainerProperty("pedido", String.class, null, "PEDIDO", null, null);				
		tablePedidos.addContainerProperty("neto", String.class, null, "NETO", null, null);
		tablePedidos.addContainerProperty("moneda", String.class, null, "MONEDA", null, null);
		tablePedidos.addContainerProperty("estado", String.class, null, "ESTADO", null, null);
		tablePedidos.addContainerProperty("domicilio", String.class, null, "DOMICILIO ENTREGA", null, null);
		tablePedidos.addContainerProperty("detalle", Button.class, null, "VER DETALLE", null, null);
		
		if(this.accion.equalsIgnoreCase("listadoPedidos"))		
			tablePedidos.addContainerProperty("modificar", Button.class, null, "MODIFICAR", null, null);		
		else		
			if(this.accion.equalsIgnoreCase("listadoOfertas"))			
				tablePedidos.addContainerProperty("modificar", Button.class, null, "CREAR PEDIDO", null, null);							
				
		tablePedidos.setSelectable(true);
		
		tablePedidos.setColumnAlignment("fecha",  Align.CENTER);
		tablePedidos.setColumnAlignment("pedido",  Align.CENTER);
		tablePedidos.setColumnAlignment("neto", Align.RIGHT);
		tablePedidos.setColumnAlignment("moneda",  Align.CENTER);
		tablePedidos.setColumnAlignment("estado",  Align.CENTER);
		tablePedidos.setColumnAlignment("detalle", Align.CENTER);
		tablePedidos.setColumnAlignment("modificar", Align.CENTER);
				
		tablePedidos.setColumnWidth("fecha", 85);
		tablePedidos.setColumnWidth("moneda", 85);
		tablePedidos.setColumnWidth("pedido", 110);
		tablePedidos.setColumnWidth("neto", 110);
		tablePedidos.setColumnWidth("estado", 175);
		
		tablePedidos.setColumnWidth("detalle", 115);
		tablePedidos.setColumnWidth("modificar", 115);
		
		tablePedidos.setColumnExpandRatio("domicilio", 1);
		
		Button btnVerDetalle;
		Button btnAccion;
		
		if(pedidosConsulta != null)
    	{	
			ColumnDetalleClickListener columnDetalleClickListener;
			
	    	for(PedidoConsulta pedidoConsulta : pedidosConsulta)
	    	{
	    		btnVerDetalle = new Button();
	    		btnVerDetalle.addStyleName(ValoTheme.BUTTON_LINK);	  
	    		btnVerDetalle.setIcon(FontAwesome.TASKS);		    			    		
	    			    		
	    		btnAccion = new Button();
	    		btnAccion.addStyleName(ValoTheme.BUTTON_LINK);	  
	    		
	    		
	    		if(this.accion.equalsIgnoreCase("listadoPedidos"))
	    		{	
	    			btnVerDetalle.setDescription("Ver detalle del pedido");
	    			
	    			btnAccion.setIcon(FontAwesome.EDIT);	
	    			btnAccion.setDescription("Modificar pedido");
		    		
	    			btnAccion.addClickListener(new Button.ClickListener() 
			        {            									
						@Override
			            public void buttonClick(Button.ClickEvent clickEvent) 
			            {  
							((StoreCRM_UI)UI.getCurrent()).setAccion("modificarPedido");
							((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().refreshDatosSesion(0, 0D, true);	
							new PedidoNuevoViewImpl(pedidoConsulta);							
			            }
			        });
	    		}	
	    		else
	    		{
	    			if(this.accion.equalsIgnoreCase("listadoOfertas"))
	    			{
	    				btnVerDetalle.setDescription("Ver detalle de la oferta");
	    				
	    				btnAccion.setIcon(FontAwesome.PLUS_SQUARE);	
	    				btnAccion.setDescription("Crear pedido");
	    				
	    				btnAccion.addClickListener(new Button.ClickListener() 
				        {            									
							@Override
				            public void buttonClick(Button.ClickEvent clickEvent) 
				            {  
								((StoreCRM_UI)UI.getCurrent()).setAccion("modificarOferta");
								((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().refreshDatosSesion(0, 0D, true);	
								new PedidoNuevoViewImpl(pedidoConsulta);							
				            }
				        });
	    			}
	    		}
	    				    		
	    		if(pedidoConsulta.getEditable() != null && pedidoConsulta.getEditable().equalsIgnoreCase("X"))
	    			btnAccion.setEnabled(true);
	    		else
	    			btnAccion.setEnabled(false);
	    		
	    		columnDetalleClickListener = new ColumnDetalleClickListener();
	    		columnDetalleClickListener.setPedidoConsulta(pedidoConsulta);
	    		
	    		btnVerDetalle.addClickListener(new Button.ClickListener() 
		        {            									
					@Override
		            public void buttonClick(Button.ClickEvent clickEvent) 
		            {  
						((StoreCRM_UI)UI.getCurrent()).setAccion("verDetallePedido");
						((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().refreshDatosSesion(0, 0D, true);	
						new PedidoNuevoViewImpl(pedidoConsulta);							
		            }
		        });
	    		
	    		btnVerDetalle.setImmediate(true);  
	    		
	    		tablePedidos.addItem(new Object[] 
				{ 
	    			pedidoConsulta.getDocumentoFecha(),
	    			pedidoConsulta.getDocumentoVenta(),
	    			pedidoConsulta.getValorNeto(),
	    			pedidoConsulta.getDocumentoMoneda(),
	    			pedidoConsulta.getEstadoPedido(),
	    			pedidoConsulta.getDomicilioEntrega(),
	    			btnVerDetalle,
	    			btnAccion
				}, pedidoConsulta.getDocumentoVenta());
	    	}
    	}	
				
		verticalLayout.addComponent(tablePedidos);
    	verticalLayout.setComponentAlignment(tablePedidos, Alignment.TOP_CENTER);     	    	
    	
    	((StoreCRM_UI)UI.getCurrent()).getPageLayout().getBodyLayout().getContentLayout().showComponentBottom(verticalLayout);
    }
	
	@Subscribe
	public void pedidoViewChanged(PedidoViewEvent event)
	{							
		this.cargarPedidos();
	}
}