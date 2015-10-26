package com.pvctecnocom.store.crm.mvp.view.layout;

import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class BodyLayout extends VerticalLayout 
{        	
	private ContentLayout contentLayout;

    public BodyLayout() 
    {       	        	
        this.contentLayout = new ContentLayout();                
        this.addComponent(contentLayout);
    }

    public ContentLayout getContentLayout() 
    {
        return this.contentLayout;
    }
}