package com.pvctecnocom.store.crm.mvp.view.layout;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ContentLayout extends VerticalLayout 
{		
	private HorizontalLayout horizontalLayoutTop;
	private HorizontalLayout horizontalLayoutBottom;
	
	public ContentLayout() 
	{		 		
		this.setSpacing(true);
		this.horizontalLayoutTop = new HorizontalLayout();
		this.horizontalLayoutBottom = new HorizontalLayout();
		
		this.horizontalLayoutTop.setWidth("100%");
		this.horizontalLayoutTop.setHeight("60px");
				
		this.horizontalLayoutBottom.setSizeFull();
		
		this.addComponent(this.horizontalLayoutTop);
		this.addComponent(this.horizontalLayoutBottom);
		
		this.setComponentAlignment(this.horizontalLayoutTop, Alignment.MIDDLE_CENTER);
	}	 
	
	public void showComponentTop(Component component)
	{
		this.horizontalLayoutTop.removeAllComponents();		
		
		if(component != null)
		{	
			this.horizontalLayoutTop.addComponent(component);
			this.horizontalLayoutTop.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
		}	
	}
	
	public void showComponentBottom(Component component)
	{
		this.horizontalLayoutBottom.removeAllComponents();
		
		if(component != null)
		{	
			this.horizontalLayoutBottom.addComponent(component);
			this.horizontalLayoutBottom.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
		}	
	}
}