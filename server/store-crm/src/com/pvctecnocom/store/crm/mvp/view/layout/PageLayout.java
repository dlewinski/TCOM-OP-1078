package com.pvctecnocom.store.crm.mvp.view.layout;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PageLayout extends VerticalLayout 
{    	
	private HeaderLayout headerLayout; 
    private BodyLayout bodyLayout;    
        
    public PageLayout() 
    {       	    	
    	this.addStyleName("scrollPageLayout");
    	
    	this.headerLayout = new HeaderLayout();    	     	
        this.headerLayout.setHeight("40px");
        this.headerLayout.setWidth("98%");
        this.addComponent(this.headerLayout);
        this.setComponentAlignment(this.headerLayout, Alignment.TOP_CENTER);
        
        this.bodyLayout = new BodyLayout(); 
        this.bodyLayout.setHeight("100%");
        this.bodyLayout.setWidth("98%");            
        
        this.addComponent(this.bodyLayout);                                            
        this.setComponentAlignment(this.bodyLayout, Alignment.TOP_CENTER);
                
        this.setExpandRatio(this.bodyLayout, 1);
                       
        this.setSizeFull();
       
        this.setSpacing(true);       
    }

    public HeaderLayout getHeaderLayout() 
    {
        return this.headerLayout;
    }

    public BodyLayout getBodyLayout() 
    {
        return this.bodyLayout;
    }                
}