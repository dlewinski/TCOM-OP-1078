package com.pvctecnocom.store.crm.mvp.view.sap;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.CloseOpenWindowsEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.sap.SAPIntegration;
import com.pvctecnocom.store.crm.mvp.presenter.sap.SAPPresenter;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class SAPArticulosView extends Panel implements View 
{   
	public static final String TITLE_ID = "sap-articulos-title";
	
	private Label titleLabel;	
	  
	private final VerticalLayout root;						
	
	private TextArea textAreaResult;
	
	private TextField txtParam1Articulos;
	private TextField txtParam2Articulos;
	
	private SAPIntegration sapIntegration;	
	
	private SAPPresenter sapPresenter;		
	
	private String categoriaBase;		
		
    public SAPArticulosView() 
    {       	     	
    	this.sapIntegration = ((StoreCRM_UI)UI.getCurrent()).getSapIntegration(); 	
    	this.sapPresenter = new SAPPresenter();		
    	this.categoriaBase = ((StoreCRM_UI)UI.getCurrent()).getCategoriaBase();
		
    	addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        StoreCrmEventBus.register(this);
        
        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);
        
        root.addComponent(buildHeader());                                
        
        this.initVlZrfcArticulos();
        
        // All the open sub-windows should be closed whenever the root layout gets clicked.
        root.addLayoutClickListener(new LayoutClickListener() 
        {
            @Override
            public void layoutClick(final LayoutClickEvent event) 
            {
            	StoreCrmEventBus.post(new CloseOpenWindowsEvent());
            }
        });                
    }
    
    private Component buildHeader() 
    {
        HorizontalLayout header = new HorizontalLayout();        
        header.setSpacing(true);

        titleLabel = new Label("SAP - Artículos - ZRFC_ARTICULOS");
        titleLabel.setId(TITLE_ID);        
        titleLabel.addStyleName(ValoTheme.LABEL_H2);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);                        
        
        return header;
    }
        
	@Override
	public void enter(ViewChangeEvent event) 
	{		
	}
	
	private void initVlZrfcArticulos()
	{				
    	FormLayout form = new FormLayout();    	    	
    	form.setSpacing(true);    	    
    	
    	txtParam1Articulos = new TextField("I_PRODH");       
    	txtParam1Articulos.setMaxLength(15);
    	txtParam1Articulos.setWidth("150px");
    	txtParam1Articulos.setValue(this.categoriaBase);
        form.addComponent(txtParam1Articulos);
    
        txtParam2Articulos = new TextField("I_DOWN");       
        txtParam2Articulos.setMaxLength(15);
        txtParam2Articulos.setWidth("150px");
        txtParam2Articulos.setValue("X");
        form.addComponent(txtParam2Articulos);
        
		Button btnTest = new Button("Test");
		
		btnTest.addClickListener(new Button.ClickListener() 
        {            														
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {   						
				textAreaResult.setValue("Consultando los artículos en SAP...");
				String result = sapIntegration.getArticulosZRFC(txtParam1Articulos.getValue(), txtParam2Articulos.getValue());
				textAreaResult.setValue(result);				
            }
        });
		
		form.addComponent(btnTest);
		
		Button btnUpdate = new Button("Update");
		
		btnUpdate.addClickListener(new Button.ClickListener() 
        {            															
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {
				textAreaResult.setValue("Actualizando los artículos en el catálogo...");
				Integer cantidadArticulos = sapPresenter.cargarProductos(txtParam1Articulos.getValue(), txtParam2Articulos.getValue());
				textAreaResult.setValue("Se actualizaron " + cantidadArticulos + " artículos en el catálogo");
            }
        });
		
		form.addComponent(btnUpdate);
		
		this.textAreaResult = new TextArea("Resultado");				
		this.textAreaResult.setSizeFull();
		this.textAreaResult.setRows(15);
		
		form.addComponent(textAreaResult);
		
		this.root.addComponent(form);
		this.root.setExpandRatio(form, 1);
	}
}