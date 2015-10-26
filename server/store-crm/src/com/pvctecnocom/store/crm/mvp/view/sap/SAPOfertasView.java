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
public final class SAPOfertasView extends Panel implements View 
{   
	public static final String TITLE_ID = "sap-ofertas-title";
	
	private Label titleLabel;	
	  
	private final VerticalLayout root;						
	
	private TextArea textAreaResult;
	
	private TextField txtParam1ConsultaOfertas;
	
	private SAPIntegration sapIntegration;							
		
    public SAPOfertasView() 
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
        
        this.initVlZrfcConsultaOfertas();
        
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

        titleLabel = new Label("SAP - Ofertas - ZRFC_CRM_CONSULTA_OFERTAS");
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
	
	private void initVlZrfcConsultaOfertas()
	{		
    	FormLayout form = new FormLayout();    	    	
    	form.setSpacing(true);    	    
    	
    	txtParam1ConsultaOfertas = new TextField("IV_KUNNR");       
    	txtParam1ConsultaOfertas.setMaxLength(15);
    	txtParam1ConsultaOfertas.setWidth("150px");
    	txtParam1ConsultaOfertas.setValue("20064");
        form.addComponent(txtParam1ConsultaOfertas);
            
		Button btnTest = new Button("Test");
		
		btnTest.addClickListener(new Button.ClickListener() 
        {            									
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {			
				textAreaResult.setValue("Consultando las ofertas en SAP...");				
				String result = sapIntegration.getOfertasCRM_ZRFC(txtParam1ConsultaOfertas.getValue());
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