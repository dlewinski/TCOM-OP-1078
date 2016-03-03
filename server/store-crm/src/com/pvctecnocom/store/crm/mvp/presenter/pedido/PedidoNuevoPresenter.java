package com.pvctecnocom.store.crm.mvp.presenter.pedido;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.common.eventbus.Subscribe;
import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.PedidoNuevoViewEvent;
import com.pvctecnocom.store.crm.mvp.model.bo.AreaVenta;
import com.pvctecnocom.store.crm.mvp.model.bo.CentroAlmacen;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.model.bo.Destinatario;
import com.pvctecnocom.store.crm.mvp.model.bo.Producto;
import com.pvctecnocom.store.crm.mvp.model.service.CentroAlmacenService;
import com.pvctecnocom.store.crm.mvp.model.service.ProductoService;
import com.pvctecnocom.store.crm.mvp.presenter.sap.SAPPresenter;
import com.pvctecnocom.store.crm.mvp.util.Utility;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoNuevoViewImpl;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;
import com.zybnet.autocomplete.server.AutocompleteSuggestionPickedListener;

public class PedidoNuevoPresenter 
{
	private PedidoNuevoViewImpl view;	
	private Table tableProductos;	
	private ProductoService productoService;
	private CentroAlmacenService centroAlmacenService;		
	private SAPPresenter sapPresenter;
	private Integer cantidadProductosDefault = 3;		
	private List<HashMap<String, String>> descuentos;
	private HashMap<String, String> precios;	
	private int itemId = 0;
	private Button btnAgregarProducto;
	private int pageLength = 0;	
	private Button btnRealizarPedido;
	private String accion;	
	private ArrayList<String> posicionBorradas;
	private DateField dfFechaEntrega;
	private String itemIdAc = "0";
	
	public PedidoNuevoPresenter(PedidoNuevoViewImpl view) 
    {				
		StoreCrmEventBus.register(this);
		
        this.view = view;  
        this.view.pedidoNuevoOK();                
    } 
			
	@SuppressWarnings({ "serial" })
	public void nuevoPedido() 
    {		
		this.productoService = ((StoreCRM_UI)UI.getCurrent()).getProductoService();     
        this.centroAlmacenService = ((StoreCRM_UI)UI.getCurrent()).getCentroAlmacenService();               
        this.sapPresenter = new SAPPresenter();
        this.accion = ((StoreCRM_UI)UI.getCurrent()).getAccion();
        this.posicionBorradas = new ArrayList<String>();
        
		Cliente cliente = ((StoreCRM_UI)UI.getCurrent()).getCliente();
		
		DecimalFormat dfTipoCambio = new DecimalFormat("##.####"); 										
		
		List<AreaVenta> areasVenta = cliente.getAreasVenta();
		
		BeanItemContainer<AreaVenta> bicAreasVenta = new BeanItemContainer<AreaVenta>(AreaVenta.class, areasVenta);			    					
		
		VerticalLayout vlNuevoPedido = new VerticalLayout();
		vlNuevoPedido.setSizeFull();
		vlNuevoPedido.setSpacing(true);   		
		
    	HorizontalLayout hlTop = new HorizontalLayout();  
    	hlTop.setWidth("100%");
    	hlTop.setSpacing(true);
    	
    	HorizontalLayout hlTopLeft = new HorizontalLayout();      	   	
    	hlTopLeft.setSpacing(true);
    	
    	ComboBox cbAreaVenta = new ComboBox("Area de venta", bicAreasVenta);   
    	cbAreaVenta.setWidth("150px");
    	cbAreaVenta.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
    	cbAreaVenta.setFilteringMode(FilteringMode.CONTAINS);
    	cbAreaVenta.focus();    
    	cbAreaVenta.setIcon(FontAwesome.SITEMAP);    	
    	cbAreaVenta.setInputPrompt("Seleccionar");  
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido"))
    	{	
    		AreaVenta cbAreaVentaSelected = new AreaVenta();
    		cbAreaVentaSelected.setOrganizacionVentas(this.view.getPedidoConsulta().getOrganizacionVenta());
    		cbAreaVentaSelected.setCanalDistribucion(this.view.getPedidoConsulta().getCanalDistribucion());
    		cbAreaVentaSelected.setSector(this.view.getPedidoConsulta().getSector());
    	    	
    		cbAreaVenta.setValue(cbAreaVentaSelected);
    		cbAreaVenta.setEnabled(false);
    	}	
    	
    	ComboBox cbCentro = new ComboBox("Centro");   
    	cbCentro.setWidth("225px");
    	cbCentro.setItemCaptionPropertyId("nombre");
    	cbCentro.setFilteringMode(FilteringMode.CONTAINS);
    	cbCentro.setEnabled(false);    
    	cbCentro.setIcon(FontAwesome.BUILDING);  
    	cbCentro.setInputPrompt("Seleccionar");
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido"))
    	{	
    		if(this.view.getPedidoConsulta().getDetalles() != null && this.view.getPedidoConsulta().getDetalles().size() > 0)
    		{	
    			AreaVenta areaVenta = (AreaVenta)cbAreaVenta.getValue();			
			 	
				if(areaVenta != null)
				{	
					List<CentroAlmacen> centrosAlmacenes = centroAlmacenService.findCentrosAlmacenByOrganizacionVentas(areaVenta.getOrganizacionVentas());	
					
					if(centrosAlmacenes != null && centrosAlmacenes.size() > 0)
					{	
						BeanItemContainer<CentroAlmacen> bicCentro = new BeanItemContainer<CentroAlmacen>(CentroAlmacen.class, centrosAlmacenes);
						
						cbCentro.setContainerDataSource(bicCentro);															
						
						descuentos = sapPresenter.cargarDescuentosMap(cliente.getNumeroCliente(), areaVenta.getOrganizacionVentas(), areaVenta.getCanalDistribucion(), areaVenta.getSector());
						
						precios = sapPresenter.cargarPreciosMap(areaVenta.getOrganizacionVentas(), areaVenta.getCanalDistribucion(), areaVenta.getSector(), cliente.getNumeroCliente());
						
						for(CentroAlmacen centroAlmacen : centrosAlmacenes)
						{
							if(centroAlmacen.getCentro().equalsIgnoreCase(this.view.getPedidoConsulta().getDetalles().get(0).getCentro()))
							{												    			
								cbCentro.setValue(centroAlmacen);								
								break;
							}	
						}	
					}										
				}	    			
    		}	
    	}    	    	  	
    	
    	ComboBox cbAlmacen = new ComboBox("Almacen");     
    	cbAlmacen.setWidth("225px");
    	cbAlmacen.setItemCaptionPropertyId("almacenDenominacion");
    	cbAlmacen.setFilteringMode(FilteringMode.CONTAINS);
    	cbAlmacen.setEnabled(false);  
    	cbAlmacen.setIcon(FontAwesome.ARCHIVE);
    	cbAlmacen.setInputPrompt("Seleccionar");
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido"))
    	{	
    		if(this.view.getPedidoConsulta().getDetalles() != null && this.view.getPedidoConsulta().getDetalles().size() > 0)
    		{
    			CentroAlmacen centroAlmacen = (CentroAlmacen)cbCentro.getValue();
    			
    			if(centroAlmacen != null)
    			{	
	    			List<CentroAlmacen> almacenes = centroAlmacenService.findCentrosAlmacenByCentro(centroAlmacen.getCentro());
	    			
	    			if(almacenes != null && almacenes.size() > 0)
					{	
						BeanItemContainer<CentroAlmacen> bicAlmacen = new BeanItemContainer<CentroAlmacen>(CentroAlmacen.class, almacenes);
						
						cbAlmacen.setContainerDataSource(bicAlmacen);					
						
						for(CentroAlmacen almacen : almacenes)
						{
							if(almacen.getAlmacen().equalsIgnoreCase(this.view.getPedidoConsulta().getDetalles().get(0).getAlmacen()))
							{												    			
								cbAlmacen.setValue(almacen);								
								break;
							}	
						}
					}
    			}	
    		}
    	}	
    	    		    	    	    	    	
    	dfFechaEntrega = new DateField("Fecha preferente entrega");   
    	dfFechaEntrega.setWidth("170px");
    	dfFechaEntrega.setDateFormat("dd-MM-yyyy");
    	dfFechaEntrega.setLocale(new Locale("es", "ES"));
    	dfFechaEntrega.setIcon(FontAwesome.TRUCK);
    	    	
    	Calendar calendar = new GregorianCalendar();
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido"))
    	{
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");     		
    		Date fechaReparto = null;
    		
    		try
    		{
    			fechaReparto = simpleDateFormat.parse(this.view.getPedidoConsulta().getFechaReparto());
    		}
    		catch (Exception e)
    		{
    			fechaReparto = new Date();
    		}
    		
    		calendar.setTime(fechaReparto);
    		
    		if(this.accion.equalsIgnoreCase("verDetallePedido"))
    			dfFechaEntrega.setEnabled(false);
    		
    		dfFechaEntrega.setValue(calendar.getTime());
    	}    	    	    	    	    	
    	    	    	    	
    	TextField txtTipoCambio = new TextField("Tipo de cambio"); 
    	txtTipoCambio.setWidth("105px");
    	txtTipoCambio.setIcon(FontAwesome.DOLLAR);
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido"))
    	{    		
    		Double cotizacion = 0D;
    	
    		if(this.view.getPedidoConsulta().getTipoCambio() != null)
    			cotizacion = Double.valueOf(this.view.getPedidoConsulta().getTipoCambio());    		    		
    		
    		txtTipoCambio.setValue(dfTipoCambio.format(cotizacion));
    		
    		if(this.accion.equalsIgnoreCase("verDetallePedido"))
    			txtTipoCambio.setEnabled(false);
    	}	    	    	    	
    	
    	TextField txtMoneda = new TextField("Moneda"); 
    	txtMoneda.setWidth("75px");
    	txtMoneda.setIcon(FontAwesome.MONEY);
    	txtMoneda.setValue("USD");
    	txtMoneda.setEnabled(false);
    	
    	btnAgregarProducto = new Button("Agregar material");
    	btnAgregarProducto.setEnabled(false);
    	btnAgregarProducto.setIcon(FontAwesome.PLUS_SQUARE_O);
    	
    	btnAgregarProducto.addClickListener(new Button.ClickListener() 
        {            									
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {  		
				AreaVenta areaVenta = (AreaVenta)cbAreaVenta.getValue();
				
				addProduct(1, areaVenta, true);				
				tableProductos.setPageLength(pageLength);
            }
        });    			
    	
    	hlTopLeft.addComponent(cbAreaVenta);
    	hlTopLeft.setComponentAlignment(cbAreaVenta, Alignment.TOP_LEFT);
    	
    	hlTopLeft.addComponent(cbCentro);
    	hlTopLeft.setComponentAlignment(cbCentro, Alignment.TOP_LEFT);
    	
    	hlTopLeft.addComponent(cbAlmacen);
    	hlTopLeft.setComponentAlignment(cbAlmacen, Alignment.TOP_LEFT);
    	
    	hlTopLeft.addComponent(dfFechaEntrega);
    	hlTopLeft.setComponentAlignment(dfFechaEntrega, Alignment.TOP_LEFT);
    	
    	hlTopLeft.addComponent(txtTipoCambio);
    	hlTopLeft.setComponentAlignment(txtTipoCambio, Alignment.TOP_LEFT);
    	
    	hlTopLeft.addComponent(txtMoneda);
    	hlTopLeft.setComponentAlignment(txtMoneda, Alignment.TOP_LEFT);
    	
    	HorizontalLayout hlTopRight = new HorizontalLayout(); 
    	hlTopRight.setWidth("100%");
    	hlTopRight.setSpacing(true);
    	
    	hlTopRight.addComponent(btnAgregarProducto);
    	hlTopRight.setComponentAlignment(btnAgregarProducto, Alignment.BOTTOM_RIGHT);
    	
    	hlTop.addComponent(hlTopLeft);
    	hlTop.setComponentAlignment(hlTopLeft, Alignment.TOP_LEFT);
    	
    	hlTop.addComponent(hlTopRight);
    	hlTop.setComponentAlignment(hlTopRight, Alignment.BOTTOM_RIGHT);
    	    	    	    	
    	vlNuevoPedido.addComponent(hlTop);
    	vlNuevoPedido.setComponentAlignment(hlTop, Alignment.TOP_LEFT);
    	
    	tableProductos = new Table();			
    	tableProductos.setPageLength(pageLength);    	
    	tableProductos.setWidth("100%");
    	tableProductos.setSelectable(true);
    	tableProductos.setCaption("Materiales");
    	tableProductos.setIcon(FontAwesome.LIST_OL);
    	
    	tableProductos.addContainerProperty("codigo", AutocompleteField.class, null, "MATERIAL", null, null);
		tableProductos.addContainerProperty("descripcion", String.class, null, "DESCRIPCIÓN", null, null);
		tableProductos.addContainerProperty("precio", TextField.class, null, "PRECIO", null, null);	    	   	    
	    tableProductos.addContainerProperty("descuentoPredefinido", String.class, null, "%DESC P", null, null);
	    tableProductos.addContainerProperty("descuentoManual", TextField.class, null, "% DESC M", null, null);
	    tableProductos.addContainerProperty("descuentoTotal", String.class, null, "% DESC T", null, null);	    
	    tableProductos.addContainerProperty("neto", String.class, null, "NETO", null, null);
	    tableProductos.addContainerProperty("cantidad", TextField.class, null, "CANTIDAD", null, null);
	    tableProductos.addContainerProperty("medida", String.class, null, "UM", null, null);
	    tableProductos.addContainerProperty("fechaEntrega", DateField.class, null, "FECHA ENTREGA", null, null);
	    tableProductos.addContainerProperty("quitar", Button.class, null, "BORRAR", null, null);	
	    tableProductos.addContainerProperty("posicion", String.class, null, "", null, null);
    	
	    tableProductos.setColumnWidth("codigo", 150);
	    tableProductos.setColumnWidth("precio", 85);	    
	    tableProductos.setColumnWidth("medida", 70);
	    tableProductos.setColumnWidth("descuentoPredefinido", 85);
	    tableProductos.setColumnWidth("descuentoManual", 85);
	    tableProductos.setColumnWidth("descuentoTotal", 85);	    
	    tableProductos.setColumnWidth("neto", 85);
	    tableProductos.setColumnWidth("cantidad", 85);
	    tableProductos.setColumnWidth("fechaEntrega", 130);
	    tableProductos.setColumnWidth("quitar", 90);
	    tableProductos.setColumnWidth("posicion", 0);
	    	    
    	tableProductos.setColumnAlignment("precio", Align.RIGHT);    	    	
    	tableProductos.setColumnAlignment("medida", Align.CENTER);    	
    	tableProductos.setColumnAlignment("descuentoPredefinido", Align.RIGHT);
    	tableProductos.setColumnAlignment("descuentoManual", Align.RIGHT);
    	tableProductos.setColumnAlignment("descuentoTotal", Align.RIGHT);    	
    	tableProductos.setColumnAlignment("neto", Align.RIGHT);
    	tableProductos.setColumnAlignment("cantidad", Align.CENTER);
    	tableProductos.setColumnAlignment("fechaEntrega", Align.CENTER); 
    	tableProductos.setColumnAlignment("quitar", Align.CENTER);
    	    
    	this.tableProductos.setColumnCollapsingAllowed(true);
    	        	
    	this.tableProductos.setColumnCollapsed("posicion", true);
    	    	
    	this.tableProductos.setEnabled(false);    	         	    	
    	
    	vlNuevoPedido.addComponent(tableProductos);
    	vlNuevoPedido.setComponentAlignment(tableProductos, Alignment.MIDDLE_CENTER);    	    	    	    	    	    	    	    	 	    	    	    	  	    	    	    	    	    	    	    	    	        	    	    	    	    	
    	
    	TextArea txtObservaciones = new TextArea(); 
    	txtObservaciones.setCaption("Observaciones");
    	txtObservaciones.setRows(2);    
    	txtObservaciones.setMaxLength(1000);  
    	txtObservaciones.setHeight("70px");
    	txtObservaciones.setWidth("100%");
    	txtObservaciones.setIcon(FontAwesome.INFO_CIRCLE);    	    
    	
    	if(this.accion.equalsIgnoreCase("verDetallePedido"))
    		txtObservaciones.setEnabled(false);
    	
    	ComboBox cbDestinatario = new ComboBox("Destinatario de mercadería");
    	cbDestinatario.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
    	cbDestinatario.setIcon(FontAwesome.MAP_MARKER);
    	cbDestinatario.setFilteringMode(FilteringMode.CONTAINS);
    	cbDestinatario.setWidth("100%");    	  
    	cbDestinatario.setInputPrompt("Seleccionar");
    	
    	if(this.accion.equalsIgnoreCase("verDetallePedido"))
    		cbDestinatario.setEnabled(false);
    	
    	cbAreaVenta.addValueChangeListener(new Property.ValueChangeListener()
    	{			
			public void valueChange(ValueChangeEvent event) 
			{				
				AreaVenta areaVenta = (AreaVenta)cbAreaVenta.getValue();			
								 	
				if(areaVenta != null)
				{						    
					Calendar calendar = new GregorianCalendar();
					
					calendar.setTime(new Date());						
					calendar.add(Calendar.DAY_OF_MONTH, areaVenta.getCantidadDias());
					
					dfFechaEntrega.setValue(calendar.getTime());
					
					txtTipoCambio.setValue(dfTipoCambio.format(areaVenta.getTipoCambio()));										
			    		
					List<CentroAlmacen> centrosAlmacenes = centroAlmacenService.findCentrosAlmacenByOrganizacionVentas(areaVenta.getOrganizacionVentas());	
					
					if(centrosAlmacenes != null && centrosAlmacenes.size() > 0)
					{	
						BeanItemContainer<CentroAlmacen> bicCentro = new BeanItemContainer<CentroAlmacen>(CentroAlmacen.class, centrosAlmacenes);
						
						cbCentro.setContainerDataSource(bicCentro);					
						cbCentro.focus();    	    	
						cbCentro.setEnabled(true);					
						
						descuentos = sapPresenter.cargarDescuentosMap(cliente.getNumeroCliente(), areaVenta.getOrganizacionVentas(), areaVenta.getCanalDistribucion(), areaVenta.getSector());
						
						precios = sapPresenter.cargarPreciosMap(areaVenta.getOrganizacionVentas(), areaVenta.getCanalDistribucion(), areaVenta.getSector(), cliente.getNumeroCliente());						
					}
					else
					{		
						cbCentro.removeAllItems();
				    	cbCentro.setEnabled(false);
					}
							
					List<Destinatario> destinatarios = new ArrayList<Destinatario>();
			    	
			    	if(cliente.getDestinatarios() != null && cliente.getDestinatarios().size() > 0)
			    	{	
			    		for(Destinatario destinatario : cliente.getDestinatarios())
			    		{
			    			if(destinatario.getOrganizacionVentas().equals(areaVenta.getOrganizacionVentas()))
			    			{
			    				if(destinatario.getCanalDistribucion().equals(areaVenta.getCanalDistribucion()))
			    				{
			    					if(destinatario.getSector().equals(areaVenta.getSector()))
			    					{
			    						destinatarios.add(destinatario);
			    					}
			    				}			    				
			    			}			    			
			    		}
			    		
			    		BeanItemContainer<Destinatario> bicDestinatarios = new BeanItemContainer<Destinatario>(Destinatario.class, destinatarios);
			    		
			    		cbDestinatario.setContainerDataSource(bicDestinatarios);	
			    	}	
			    	else
			    	{
			    		cbDestinatario.removeAllItems();
			    		cbDestinatario.setEnabled(false);
			    	}
			    	
					if(areaVenta.getCantidadLineasDefault() != null)	
					{
						tableProductos.removeAllItems();
						pageLength = 0;
						addProduct(areaVenta.getCantidadLineasDefault(), areaVenta, false);
					}	
					else
					{	
						tableProductos.removeAllItems();
						pageLength = 0;
						addProduct(cantidadProductosDefault, areaVenta, false);
					}	
				}
				else
				{
					cbCentro.removeAllItems();
			    	cbCentro.setEnabled(false);
			    	addProduct(0, areaVenta, false);
				}
			}        	
        });    	    	    	    	
    	
    	cbCentro.addValueChangeListener(new Property.ValueChangeListener()
    	{
    		public void valueChange(ValueChangeEvent event) 
			{
    			CentroAlmacen centroAlmacen = (CentroAlmacen)cbCentro.getValue();
    			
    			if(centroAlmacen != null)
    			{	
	    			List<CentroAlmacen> centrosAlmacenes = centroAlmacenService.findCentrosAlmacenByCentro(centroAlmacen.getCentro());
	    			
	    			if(centrosAlmacenes != null && centrosAlmacenes.size() > 0)
					{	
						BeanItemContainer<CentroAlmacen> bicAlmacen = new BeanItemContainer<CentroAlmacen>(CentroAlmacen.class, centrosAlmacenes);
						
						cbAlmacen.setContainerDataSource(bicAlmacen);					
						cbAlmacen.focus();    	    	
						cbAlmacen.setEnabled(true);					
					}
					else
					{		
						cbAlmacen.removeAllItems();
						cbAlmacen.setEnabled(false);
					}
    			}
    			else
    			{
    				cbAlmacen.removeAllItems();
					cbAlmacen.setEnabled(false);
    			}
			}
    	});
    	
    	cbAlmacen.addValueChangeListener(new Property.ValueChangeListener()
    	{			
			public void valueChange(ValueChangeEvent event) 
			{	
				CentroAlmacen centroAlmacen = (CentroAlmacen)cbAlmacen.getValue();
				
				if(centroAlmacen != null)
				{						
					tableProductos.setEnabled(true);										
					btnAgregarProducto.setEnabled(true);
					btnRealizarPedido.setEnabled(true);
				}	
				else
				{	
					tableProductos.setEnabled(false);										
					btnAgregarProducto.setEnabled(false);
					btnRealizarPedido.setEnabled(false);
				}	
			}    	      	
    	}); 
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido"))
    	{
    		if(this.view.getPedidoConsulta().getIdClienteInterlocutor() != null)
    		{
    			AreaVenta areaVenta =  (AreaVenta)cbAreaVenta.getValue();
    					
    			List<Destinatario> destinatarios = new ArrayList<Destinatario>();
		    	
		    	if(cliente.getDestinatarios() != null && cliente.getDestinatarios().size() > 0)
		    	{	
		    		for(Destinatario destinatario : cliente.getDestinatarios())
		    		{
		    			if(destinatario.getOrganizacionVentas().equals(areaVenta.getOrganizacionVentas()))
		    			{
		    				if(destinatario.getCanalDistribucion().equals(areaVenta.getCanalDistribucion()))
		    				{
		    					if(destinatario.getSector().equals(areaVenta.getSector()))
		    					{
		    						destinatarios.add(destinatario);
		    					}
		    				}			    				
		    			}			    			
		    		}
		    		
		    		BeanItemContainer<Destinatario> bicDestinatarios = new BeanItemContainer<Destinatario>(Destinatario.class, destinatarios);
		    		
		    		cbDestinatario.setContainerDataSource(bicDestinatarios);	
		    	}	
		    	
    			Destinatario destinatarioSelected = new Destinatario();
    			destinatarioSelected.setCliente(this.view.getPedidoConsulta().getIdClienteInterlocutor());
    			    			
    			destinatarioSelected.setOrganizacionVentas(areaVenta.getOrganizacionVentas());
    			destinatarioSelected.setCanalDistribucion(areaVenta.getCanalDistribucion());
    			destinatarioSelected.setSector(areaVenta.getSector());
    			
    			cbDestinatario.setValue(destinatarioSelected);
    		}
    	}
    	
    	HorizontalLayout hlObservacionesDestinatario = new HorizontalLayout();
    	hlObservacionesDestinatario.setWidth("100%");
    	hlObservacionesDestinatario.setSpacing(true);
    	
    	VerticalLayout vlDestinatarioRealizarPedido = new VerticalLayout();
    	vlDestinatarioRealizarPedido.setWidth("100%");
    	vlDestinatarioRealizarPedido.setSpacing(true);
    	
    	hlObservacionesDestinatario.addComponent(txtObservaciones);    	
    	hlObservacionesDestinatario.setComponentAlignment(txtObservaciones, Alignment.TOP_LEFT);
    	
    	vlDestinatarioRealizarPedido.addComponent(cbDestinatario);
    	vlDestinatarioRealizarPedido.setComponentAlignment(cbDestinatario, Alignment.TOP_RIGHT);
    	
    	hlObservacionesDestinatario.addComponent(vlDestinatarioRealizarPedido); 
    	hlObservacionesDestinatario.setComponentAlignment(vlDestinatarioRealizarPedido, Alignment.TOP_LEFT);
   	
    	vlNuevoPedido.addComponent(hlObservacionesDestinatario);
    	vlNuevoPedido.setComponentAlignment(hlObservacionesDestinatario, Alignment.BOTTOM_CENTER);
    	
    	PedidoClickListener pedidoClickListener = new PedidoClickListener();
    	pedidoClickListener.setTableProductos(this.tableProductos);
    	pedidoClickListener.setCbAreaVenta(cbAreaVenta);
    	pedidoClickListener.setTxtTipoCambio(txtTipoCambio);
    	pedidoClickListener.setTxtObservaciones(txtObservaciones);
    	pedidoClickListener.setDfFechaEntrega(dfFechaEntrega);
    	pedidoClickListener.setCbCentro(cbCentro);
    	pedidoClickListener.setCbAlmacen(cbAlmacen);
    	pedidoClickListener.setCbDestinatario(cbDestinatario);
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta"))
    		pedidoClickListener.setDocumentoVentas(this.view.getPedidoConsulta().getDocumentoVenta());
    	
    	pedidoClickListener.setPosicionBorradas(posicionBorradas);
    	
    	btnRealizarPedido = new Button();
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido"))
    	{	
    		btnRealizarPedido.setCaption("Grabar Pedido");
    	}	
    	else 
    	{	
    		if(this.accion.equalsIgnoreCase("nuevaOferta"))
    		{
    			btnRealizarPedido.setCaption("Crear Oferta");
    		}
    		else
    		{
    			if(this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("nuevoPedido"))
    			{
    				btnRealizarPedido.setCaption("Crear Pedido");
    			}
    		}
    	}    		    	
    	
    	btnRealizarPedido.setEnabled(false);
    	btnRealizarPedido.addClickListener(pedidoClickListener);
    	btnRealizarPedido.setIcon(FontAwesome.CHECK);
    	btnRealizarPedido.setStyleName(ValoTheme.BUTTON_PRIMARY);
    	
    	vlDestinatarioRealizarPedido.addComponent(btnRealizarPedido);
    	vlDestinatarioRealizarPedido.setComponentAlignment(btnRealizarPedido, Alignment.BOTTOM_RIGHT);
    	
    	if(this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido"))
    	{
    		tableProductos.removeAllItems();
    		
    		if(this.view.getPedidoConsulta().getDetalles() != null && this.view.getPedidoConsulta().getDetalles().size() > 0)    		    			    			
				addProduct(this.view.getPedidoConsulta().getDetalles().size(), (AreaVenta)cbAreaVenta.getValue(), false);    		
    		
    		if(this.accion.equalsIgnoreCase("verDetallePedido"))
    		{    		
    			this.btnAgregarProducto.setEnabled(false);
    			this.btnRealizarPedido.setEnabled(false);
    			this.tableProductos.setEnabled(false);
    		}	
    		else
    		{
    			this.btnAgregarProducto.setEnabled(true);
    			this.btnRealizarPedido.setEnabled(true);
    			this.tableProductos.setEnabled(true);
    		}
    	}
    	    	
    	((StoreCRM_UI)UI.getCurrent()).getPageLayout().getBodyLayout().getContentLayout().showComponentBottom(vlNuevoPedido);
    }
	
	@SuppressWarnings("serial")
	private void addProduct(Integer cantidadProductos, AreaVenta areaVenta, Boolean isAgregarProducto)
	{
		AutocompleteField<Producto> acCodigo;    	
    	TextField txtPrecio;    	    	
    	TextField txtDescuentoManual;		    	
    	TextField txtCantidad;
    	Button btnQuitarProducto;
    	
    	ColumnCantidadChangeListener columnCantidadChangeListener;
    	ColumnDescuentoManualChangeListener columnDescuentoManualChangeListener;
    	ColumnPrecioChangeListener columnPrecioChangeListener;    	
    	FechaEntregaChangeListener fechaEntregaChangeListener = new FechaEntregaChangeListener();    	
    	
    	Boolean isPrecioEditable = false;
    	Boolean isDescuentoEditable = false;
    	
    	if(cantidadProductos > 0 && areaVenta != null)
    	{    		
    		String precioEditable = areaVenta.getPrecioEditable();
    		String descuentoEditable = areaVenta.getDescuentoEditable();
    		
    		if(precioEditable != null && precioEditable.equalsIgnoreCase("X"))
    			isPrecioEditable = true;
    		
    		if(descuentoEditable != null && descuentoEditable.equalsIgnoreCase("X"))
    			isDescuentoEditable = true;
    	}
    	    	
    	String descripcionMaterial = "";
    	String unidadMedida = "";
    	String descuentoPredefinido = "0.00";
    	String descuentoTotal = "";
    	String neto = "";
    	String posicion = "";
    	
    	Integer cantidadTotal = 0;
    	Integer cantidadParcial = 0;
    	Double importeTotal = 0.0D;
    	Double netoParcial = 0.0D;
    	
    	Double calculoDescuentoTotal = 0D;
    	DecimalFormat decimalFormat = new DecimalFormat("##.##");     	
    	Double descuentoPredefinidoNumber = 0D;
    	Double descuentoManual = 0D;
    	
    	String txtDescuentoManualValue;
    	String txtPrecioValue;
    	String netoValue;
    	String cantidadValue;
    	
    	DateField dfFechaEntregaPosicion;
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date fechaRepartoPosicion = null;
    	Calendar calendar = new GregorianCalendar();
    	
		for(int i = 0; i < cantidadProductos; i++)
		{							
	    	cantidadParcial = 0;	    	
	    	netoParcial = 0.0D;
	    	
			acCodigo = new AutocompleteField<Producto>();				
			acCodigo.setImmediate(true);
			acCodigo.setValue("");
			acCodigo.setHeight("25px");	
			acCodigo.setWidth("100%");
			acCodigo.addStyleName("v-textfield");						
			acCodigo.setId(String.valueOf(itemId));
			
			acCodigo.setQueryListener(new AutocompleteQueryListener<Producto>() 
		    		{
		    			@Override
		    			public void handleUserQuery(AutocompleteField<Producto> field, String query) 
		    			{
		    				itemIdAc = field.getId();
		    				
		    				handleSearchQuery(field, query);
		    			}
		    		});
			
			acCodigo.setSuggestionPickedListener(new AutocompleteSuggestionPickedListener<Producto>() 
		    		{	      
		    			@Override
		    			public void onSuggestionPicked(Producto producto) 
		    			{		    			
		    				handleSuggestionSelection(producto);
		    			}
		    		});
	    	
	    	txtPrecio = new TextField();		
	    	txtPrecio.setMaxLength(8);  
	    	txtPrecio.setValue("");
	    	txtPrecio.setHeight("25px");
	    	txtPrecio.setWidth("100%");
	    	txtPrecio.setEnabled(isPrecioEditable);	    	
	    	txtPrecio.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);		    		    	
	    	
	    	columnPrecioChangeListener = new ColumnPrecioChangeListener();
	    	columnPrecioChangeListener.setTableProductos(this.tableProductos);
	    		    		    	
	    	txtDescuentoManual = new TextField();		
	    	txtDescuentoManual.setMaxLength(8);  
	    	txtDescuentoManual.setValue("");
	    	txtDescuentoManual.setHeight("25px");
	    	txtDescuentoManual.setWidth("100%");
	    	txtDescuentoManual.setEnabled(isDescuentoEditable);	    	
	    	txtDescuentoManual.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
	    	
	    	columnPrecioChangeListener.setTxtDescuentoManual(txtDescuentoManual);
	    	
	    	columnDescuentoManualChangeListener = new ColumnDescuentoManualChangeListener();
	    	columnDescuentoManualChangeListener.setTableProductos(this.tableProductos); 	    	
			
	    	txtCantidad = new TextField();		
			txtCantidad.setMaxLength(8);  
			txtCantidad.setValue("");
			txtCantidad.setHeight("25px");
			txtCantidad.setWidth("100%");			
			txtCantidad.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);									
			
			columnDescuentoManualChangeListener.setTxtCantidad(txtCantidad);
			
			columnCantidadChangeListener = new ColumnCantidadChangeListener();    		
    		columnCantidadChangeListener.setTableProductos(this.tableProductos);        		
    		columnCantidadChangeListener.setTxtCantidad(txtCantidad);
						
			btnQuitarProducto = new Button();
	    	btnQuitarProducto.setEnabled(true);
	    	btnQuitarProducto.setIcon(FontAwesome.TRASH_O);	    	
	    	btnQuitarProducto.addStyleName(ValoTheme.BUTTON_LINK);
	    	btnQuitarProducto.setId(String.valueOf(itemId));
	    	
	    	btnQuitarProducto.addClickListener(new Button.ClickListener() 
	        {            									
				@Override
	            public void buttonClick(Button.ClickEvent clickEvent) 
	            {  					
					Integer id = Integer.valueOf(clickEvent.getButton().getId());										
					
					borrarMaterial(id);										
	            }
	        });
	    	
	    	if((this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido")) && !isAgregarProducto)
	    	{
	    		acCodigo.setEnabled(false);	    		
	    		acCodigo.setText(this.view.getPedidoConsulta().getDetalles().get(i).getNumeroMaterial());
	    		
	    		descripcionMaterial = this.view.getPedidoConsulta().getDetalles().get(i).getDescripcionMaterial();
	    			
	    		txtPrecioValue = this.view.getPedidoConsulta().getDetalles().get(i).getValorPrecio();
	    		txtPrecioValue = txtPrecioValue.replace(",", ".");
	    		txtPrecio.setValue(txtPrecioValue);
	    		    		
	    		unidadMedida = this.view.getPedidoConsulta().getDetalles().get(i).getUnidadMedida();
	    		
	    		descuentoPredefinido = this.view.getPedidoConsulta().getDetalles().get(i).getDescuentoAcumuladoConcedido();	    			    		
				descuentoPredefinido = descuentoPredefinido.replace(",", ".");
					    			    			    		
	    		txtDescuentoManualValue = this.view.getPedidoConsulta().getDetalles().get(i).getValorDescuento();
				txtDescuentoManualValue = txtDescuentoManualValue.replace(",", ".");				
				txtDescuentoManual.setValue(txtDescuentoManualValue);
				
	    		try
				{
					descuentoPredefinidoNumber = Double.valueOf(descuentoPredefinido);
				}
				catch(NumberFormatException e)
				{
					descuentoPredefinidoNumber = 0D;					
				}
	    		
	    		try
				{
					descuentoManual = Double.valueOf(txtDescuentoManualValue);
				}
				catch(NumberFormatException e)
				{
					descuentoManual = 0D;					
				}
	    		
	    		calculoDescuentoTotal = 1 - (1 - (descuentoPredefinidoNumber/100)) * (1 - (descuentoManual/100));
				
	    		calculoDescuentoTotal = calculoDescuentoTotal * 100;
				
				if(calculoDescuentoTotal > 0)
					descuentoTotal = decimalFormat.format(calculoDescuentoTotal);
				else
					descuentoTotal = "0.00";	    			    		
	    		
				netoValue = this.view.getPedidoConsulta().getDetalles().get(i).getValorNeto();
				netoValue = netoValue.replace(",", ".");	
	    		neto = netoValue;
	    		
	    		netoParcial = Double.valueOf(neto);	    			    		
	    		
	    		cantidadValue = this.view.getPedidoConsulta().getDetalles().get(i).getCantidadPedido();
	    		txtCantidad.setValue(String.valueOf(Double.valueOf(cantidadValue).intValue()));
	    		
	    		cantidadParcial = Integer.valueOf(txtCantidad.getValue());	    			  	    			    			    		   			    	
	    		
	    		importeTotal = importeTotal + netoParcial;    		
	    		
	    		cantidadTotal = cantidadTotal + cantidadParcial;
	    		
	    		posicion = this.view.getPedidoConsulta().getDetalles().get(i).getDocumentoVentaPosicion();
	    	}
	    		    		    	
	    	txtPrecio.addValueChangeListener(columnPrecioChangeListener);
	    	txtPrecio.setImmediate(true);
	    	
	    	txtDescuentoManual.addValueChangeListener(columnDescuentoManualChangeListener);
	    	txtDescuentoManual.setImmediate(true); 
	    	
	    	txtCantidad.addValueChangeListener(columnCantidadChangeListener);
			txtCantidad.setImmediate(true); 
			
			dfFechaEntregaPosicion = new DateField();   
			dfFechaEntregaPosicion.setHeight("25px");
			dfFechaEntregaPosicion.setWidth("100%");	
		    dfFechaEntregaPosicion.setDateFormat("dd-MM-yyyy");
		    dfFechaEntregaPosicion.setLocale(new Locale("es", "ES"));
		    dfFechaEntregaPosicion.setValue(this.dfFechaEntrega.getValue());		    		    
		    	
		    if((this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido")) && !isAgregarProducto)
	    	{		    	
		    	if(this.view.getPedidoConsulta().getDetalles().get(i).getFechaEntregaPosicion() != null && !this.view.getPedidoConsulta().getDetalles().get(i).getFechaEntregaPosicion().equals(""))
		    	{
		    		try
		    		{
		    			fechaRepartoPosicion = simpleDateFormat.parse(this.view.getPedidoConsulta().getDetalles().get(i).getFechaEntregaPosicion());
		    		}
		    		catch (Exception e)
		    		{
		    			fechaRepartoPosicion = null;
		    		}
		    		
		    		if(fechaRepartoPosicion != null)
		    		{
		    			calendar.setTime(fechaRepartoPosicion);
		    			dfFechaEntregaPosicion.setValue(calendar.getTime());
		    		}
		    	}		    			    	
		    }
		    
	    	this.tableProductos.addItem(new Object[] 
					{ 
	    				acCodigo, 
	    				descripcionMaterial,
						txtPrecio,						
						descuentoPredefinido,
						txtDescuentoManual,
						descuentoTotal,
						neto,
						txtCantidad,
						unidadMedida,
						dfFechaEntregaPosicion,
						btnQuitarProducto,
						posicion
					}, itemId);
	    		    	
	    	itemId++;
	    	pageLength++;
	    	
	    	if((this.accion.equalsIgnoreCase("modificarPedido") || this.accion.equalsIgnoreCase("modificarOferta") || this.accion.equalsIgnoreCase("verDetallePedido")) && !isAgregarProducto)	    	
	    		((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().refreshDatosSesion(cantidadTotal, importeTotal, true);
		}	
				
		fechaEntregaChangeListener.setTableProductos(this.tableProductos);
    	
    	dfFechaEntrega.addValueChangeListener(fechaEntregaChangeListener);
    	
		this.tableProductos.setPageLength(pageLength);		
	}
	
	private void handleSearchQuery(AutocompleteField<Producto> field, String query) 
	{
		try 
		{			
			List<Producto> productos = productoSearch(query);
			
			if(productos != null)
			{	
				for (Producto producto : productos) 
				{
					field.addSuggestion(producto, producto.getCodigo() + " - " + producto.getDescripcion());
				}
			}	
	    } 
		catch (Exception e) 
		{
	      throw new RuntimeException(e);
	    }	    
	}
	
	private List<Producto> productoSearch(String query) 
	{	    		
	    List<Producto> result = productoService.findProductosByCodigoDescripcion(query);
   
	    return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void handleSuggestionSelection(Producto producto) 
	{										
		Integer id = Integer.valueOf(this.itemIdAc);
		
		AutocompleteField<Producto> acCodigo = null;				
		Item itemSelected = null;
		
		TextField txtPrecio;
		String precio;
		String descuento;
		String neto;
		
		String txtCantidadValue;			
		Integer cantidad = 0;
								
		itemSelected = tableProductos.getItem(id);
		
		if(itemSelected != null)
		{	
			acCodigo = (AutocompleteField)itemSelected.getItemProperty("codigo").getValue();
			acCodigo.setText(producto.getCodigo());
			
			itemSelected.getItemProperty("descripcion").setValue(producto.getDescripcion());				
			itemSelected.getItemProperty("medida").setValue(producto.getUnidadMedida());
			itemSelected.getItemProperty("neto").setValue("0.00");						
			
			TextField txtCantidad = (TextField)itemSelected.getItemProperty("cantidad").getValue();
			txtCantidad.setValue("0");
			
			txtPrecio = (TextField)itemSelected.getItemProperty("precio").getValue();			
			
			if(this.precios != null && this.precios.get(producto.getCodigo()) != null)
				txtPrecio.setValue(this.precios.get(producto.getCodigo()));
			else	
				txtPrecio.setValue("0.00");
							    
	    	TextField txtDescuentoManual = (TextField)itemSelected.getItemProperty("descuentoManual").getValue();
	    	txtDescuentoManual.setValue("0.00");	    		    
	    		    	
	    	if(txtPrecio.isEnabled())
	    		txtPrecio.focus();
	    	else
	    		txtDescuentoManual.focus();
	    	
	    	if(this.descuentos != null)
	    	{	
	    		HashMap<String, String> descuentosMaterial = this.descuentos.get(1);
	    		
	    		if(descuentosMaterial != null && descuentosMaterial.get(producto.getCodigo()) != null)
	    		{	
	    			itemSelected.getItemProperty("descuentoPredefinido").setValue(descuentosMaterial.get(producto.getCodigo()));
	    			itemSelected.getItemProperty("descuentoTotal").setValue(descuentosMaterial.get(producto.getGrupo()));
	    		}	
	    		else
	    		{
	    			HashMap<String, String> descuentosGrupo = this.descuentos.get(0);
	    			
	    			if(descuentosGrupo != null && descuentosGrupo.get(producto.getGrupo()) != null)
	    			{	
	    				itemSelected.getItemProperty("descuentoPredefinido").setValue(descuentosGrupo.get(producto.getGrupo()));
	    				itemSelected.getItemProperty("descuentoTotal").setValue(descuentosGrupo.get(producto.getGrupo()));
	    			}	
	    			else
	    			{	
	    				itemSelected.getItemProperty("descuentoPredefinido").setValue("0.00");
	    				itemSelected.getItemProperty("descuentoTotal").setValue("0.00");
	    			}	
	    		}
	    	}
	    	else
	    	{
	    		itemSelected.getItemProperty("descuentoPredefinido").setValue("0.00");
	    	}
	    	
	    	txtPrecio = (TextField)itemSelected.getItemProperty("precio").getValue();
			precio = txtPrecio.getValue();
			
			descuento = (String)itemSelected.getItemProperty("descuentoTotal").getValue();
			
			txtCantidad = (TextField)(itemSelected.getItemProperty("cantidad").getValue());				
			txtCantidadValue = txtCantidad.getValue();
													
			try
			{
				cantidad = Integer.valueOf(txtCantidadValue);
			}
			catch(NumberFormatException e)
			{
				cantidad = 0;						
			}	
			
			neto = Utility.calcularNeto(precio, descuento, cantidad);
			
			itemSelected.getItemProperty("neto").setValue(neto);
		}	
	}
	
	private void borrarMaterial(Integer id)
	{		
		Item item = tableProductos.getItem(id);
		
		String posicion = (String)item.getItemProperty("posicion").getValue();
		
		if(posicion != null && !posicion.equals(""))			
			posicionBorradas.add(posicion);
		
		tableProductos.removeItem(id);
		pageLength--;
		tableProductos.setPageLength(pageLength);				
		
		Utility.refreshTablaMateriales(tableProductos);	
	}
	
	@Subscribe
	public void pedidoNuevoViewChanged(PedidoNuevoViewEvent event)
	{	
		((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().refreshDatosSesion(0, 0D, false);
		
		this.nuevoPedido();
	}
}