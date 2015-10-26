package com.pvctecnocom.store.crm.mvp.view;

import com.pvctecnocom.store.crm.StoreCrmNavigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class MainView extends HorizontalLayout 
{
	public MainView() 
	{
        setSizeFull();
        addStyleName("mainview");
        
        addComponent(new StoreCrmMenu());

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);

        new StoreCrmNavigator(content);
    }
}