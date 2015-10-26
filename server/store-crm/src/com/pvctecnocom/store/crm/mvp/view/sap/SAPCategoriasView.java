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
public final class SAPCategoriasView extends Panel implements View 
{   
	public static final String TITLE_ID = "sap-categorias-title";
	
	private Label titleLabel;	
	  
	private final VerticalLayout root;						
	
	private TextArea textAreaResult;
	
	private TextField txtParam1Categorias;
	private TextField txtParam2Categorias;
	
	private SAPIntegration sapIntegration;	
	
	private SAPPresenter sapPresenter;
	
	private String categoriaBase;
	
    public SAPCategoriasView() 
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
        
        this.initVlZrfcCategorias();
        
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

        titleLabel = new Label("SAP - Categorías - ZRFC_CATEGORIAS");
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
	
	private void initVlZrfcCategorias()
	{						       
    	FormLayout form = new FormLayout();    	    	
    	form.setSpacing(true);    	    
    	
    	txtParam1Categorias = new TextField("I_PRODH");       
    	txtParam1Categorias.setMaxLength(15);
    	txtParam1Categorias.setWidth("150px");
    	txtParam1Categorias.setValue(this.categoriaBase);
        form.addComponent(txtParam1Categorias);
    
        txtParam2Categorias = new TextField("I_DOWN");       
        txtParam2Categorias.setMaxLength(15);
        txtParam2Categorias.setWidth("150px");
        txtParam2Categorias.setValue("X");
        form.addComponent(txtParam2Categorias);
        
		Button btnTest = new Button("Test");
		
		btnTest.addClickListener(new Button.ClickListener() 
        {            									
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {			
				textAreaResult.setValue("Consultando las categorías en SAP...");				
				String result = sapIntegration.getCategoriasZRFC(txtParam1Categorias.getValue(), txtParam2Categorias.getValue());
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
				textAreaResult.setValue("Actualizando las categorías en el catálogo...");	
				Integer cantidadCategorias = sapPresenter.cargarCategorias(txtParam1Categorias.getValue(), txtParam2Categorias.getValue());
				textAreaResult.setValue("Se actualizaron " + cantidadCategorias + " categorías en el catálogo");
            }
        });
		
		form.addComponent(btnUpdate);
		
		this.textAreaResult = new TextArea("Resultado");
		this.textAreaResult.setSizeFull();
		this.textAreaResult.setRows(15);
		
		form.addComponent(textAreaResult);
		
		root.addComponent(form);
		root.setExpandRatio(form, 1);
	}	
}