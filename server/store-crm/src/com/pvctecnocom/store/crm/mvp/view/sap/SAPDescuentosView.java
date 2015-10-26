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
public final class SAPDescuentosView extends Panel implements View 
{   
	public static final String TITLE_ID = "sap-descuentos-title";
	
	private Label titleLabel;	
	  
	private final VerticalLayout root;						
	
	private TextArea textAreaResult;
	
	private TextField txtParam1Descuentos;
	private TextField txtParam2Descuentos;
	private TextField txtParam3Descuentos;
	private TextField txtParam4Descuentos;
	
	private SAPIntegration sapIntegration;	
		
    public SAPDescuentosView() 
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
        
        this.initVlZrfcDescuentos();
        
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

        titleLabel = new Label("SAP - Descuentos - ZRFC_CRM_DESCUENTOS");
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
	
	private void initVlZrfcDescuentos()
	{		
    	FormLayout form = new FormLayout();    	    	
    	form.setSpacing(true);        	    	    	            	       
                       
    	this.txtParam1Descuentos = new TextField("IV_KUNNR");       
    	this.txtParam1Descuentos.setMaxLength(15);
    	this.txtParam1Descuentos.setWidth("150px");
    	this.txtParam1Descuentos.setValue("27");
        form.addComponent(this.txtParam1Descuentos);
        
        this.txtParam2Descuentos = new TextField("IV_VKORG");       
    	this.txtParam2Descuentos.setMaxLength(15);
    	this.txtParam2Descuentos.setWidth("150px");
    	this.txtParam2Descuentos.setValue("1100");
        form.addComponent(this.txtParam2Descuentos);
        
        this.txtParam3Descuentos = new TextField("IV_VTWEG");       
    	this.txtParam3Descuentos.setMaxLength(15);
    	this.txtParam3Descuentos.setWidth("150px");
    	this.txtParam3Descuentos.setValue("02");
        form.addComponent(this.txtParam3Descuentos);
        
        this.txtParam4Descuentos = new TextField("IV_SPART");       
    	this.txtParam4Descuentos.setMaxLength(15);
    	this.txtParam4Descuentos.setWidth("150px");
    	this.txtParam4Descuentos.setValue("PE");
        form.addComponent(this.txtParam4Descuentos);
        
		Button btnTest = new Button("Test");
		
		btnTest.addClickListener(new Button.ClickListener() 
        {            									
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {			
				textAreaResult.setValue("Consultando los descuentos en SAP...");				
				String result = sapIntegration.getDescuentosCRM_ZRFC(txtParam1Descuentos.getValue(), txtParam2Descuentos.getValue(), txtParam3Descuentos.getValue(), txtParam4Descuentos.getValue());
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