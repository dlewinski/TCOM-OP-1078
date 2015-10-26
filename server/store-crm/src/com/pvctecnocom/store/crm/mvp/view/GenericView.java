package com.pvctecnocom.store.crm.mvp.view;

import com.vaadin.ui.Table;

public interface GenericView
{
	public void addGeneratedColumnActions(Table table);
	
	public void addItem(Object item);
	
	public void deleteItemConfirm(Boolean isConfirmed, Object item);
	
	public void deleteItem(Object item);
	
	public void editItem(Object item);				
}