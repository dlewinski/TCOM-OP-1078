package com.pvctecnocom.store.crm.mvp.view.sap;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.CloseOpenWindowsEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.sap.SAPIntegration;
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
public final class SAPPreciosView extends Panel implements View 
{   
	public static final String TITLE_ID = "sap-precios-title";
	
	private Label titleLabel;	
	  
	private final VerticalLayout root;						
	
	private TextArea textAreaResult;
	
	private TextField txtParam1Precios;
	private TextField txtParam2Precios;
	private TextField txtParam3Precios;
	private TextField txtParam4Precios;
	
	private SAPIntegration sapIntegration;	
		
    public SAPPreciosView() 
    {       	     	
    	this.sapIntegration = ((StoreCRM_UI)UI.getCurrent()).getSapIntegration(); 	    	
		
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
        
        this.initVlZrfcPrecios();
        
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

        titleLabel = new Label("SAP - Precios - ZRFC_CRM_PRECIOS");
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
	
	private void initVlZrfcPrecios()
	{		
    	FormLayout form = new FormLayout();    	    	
    	form.setSpacing(true);    	    	    	    	            	        
                       
    	this.txtParam1Precios = new TextField("IV_VKORG");       
    	this.txtParam1Precios.setMaxLength(15);
    	this.txtParam1Precios.setWidth("150px");
    	this.txtParam1Precios.setValue("1100");
        form.addComponent(this.txtParam1Precios);
        
        this.txtParam2Precios = new TextField("IV_VTWEG");       
    	this.txtParam2Precios.setMaxLength(15);
    	this.txtParam2Precios.setWidth("150px");
    	this.txtParam2Precios.setValue("02");
        form.addComponent(this.txtParam2Precios);
        
        this.txtParam3Precios = new TextField("IV_SPART");       
    	this.txtParam3Precios.setMaxLength(15);
    	this.txtParam3Precios.setWidth("150px");
    	this.txtParam3Precios.setValue("PE");
        form.addComponent(this.txtParam3Precios);
        
        this.txtParam4Precios = new TextField("IV_KUNNR");       
    	this.txtParam4Precios.setMaxLength(15);
    	this.txtParam4Precios.setWidth("150px");
    	this.txtParam4Precios.setValue("21");
        form.addComponent(this.txtParam4Precios);
        
		Button btnTest = new Button("Test");
		
		btnTest.addClickListener(new Button.ClickListener() 
        {            									
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {			
				textAreaResult.setValue("Consultando los precios en SAP...");				
				String result = sapIntegration.getPreciosCRM_ZRFC(txtParam1Precios.getValue(), txtParam2Precios.getValue(), txtParam3Precios.getValue(), txtParam4Precios.getValue());
				textAreaResult.setValue(result);									
            }
        });
		
		form.addComponent(btnTest);				
										
		this.textAreaResult = new TextArea("Resultado");
		this.textAreaResult.setSizeFull();
		this.textAreaResult.setRows(15);
		
		form.addComponent(textAreaResult);
		
		this.root.addComponent(form);
		this.root.setExpandRatio(form, 1);
	}
}