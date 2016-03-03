package com.pvctecnocom.store.crm.mvp.view.screen.menu;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.presenter.menu.MenuItemCommandOferta;
import com.pvctecnocom.store.crm.mvp.presenter.menu.MenuItemCommandOfertaNueva;
import com.pvctecnocom.store.crm.mvp.presenter.menu.MenuItemCommandPedido;
import com.pvctecnocom.store.crm.mvp.presenter.menu.MenuItemCommandPedidoNuevo;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

public class MenuViewImpl implements MenuView
{			      
	private MenuBar menuStore;        	      
    
    public MenuViewImpl() 	
    {    	    	    	    	
    	this.menuStore = new MenuBar();  	     	
				
		MenuItem pedidos = this.menuStore.addItem("PEDIDOS", null);
		pedidos.setIcon(FontAwesome.FOLDER);																				
				
		MenuItem nuevoPedido = pedidos.addItem("Nuevo pedido", null);
		nuevoPedido.setIcon(FontAwesome.PLUS_SQUARE);
		nuevoPedido.setCommand(new MenuItemCommandPedidoNuevo());
		
		MenuItem listadoPedidos = pedidos.addItem("Pedidos realizados", null);
		listadoPedidos.setIcon(FontAwesome.LIST);
		listadoPedidos.setCommand(new MenuItemCommandPedido());
		
		MenuItem ofertas = this.menuStore.addItem("OFERTAS", null);
		ofertas.setIcon(FontAwesome.EXCLAMATION_CIRCLE);
		
		MenuItem nuevaOferta = ofertas.addItem("Nueva oferta", null);
		nuevaOferta.setIcon(FontAwesome.PLUS_SQUARE);
		nuevaOferta.setCommand(new MenuItemCommandOfertaNueva());
		
		MenuItem listadoOfertas = ofertas.addItem("Ofertas realizadas", null);
		listadoOfertas.setIcon(FontAwesome.LIST);
		listadoOfertas.setCommand(new MenuItemCommandOferta());
		
		this.menuOK();
    }                
        
	@Override
	public void menuOK() 
	{
		((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().showMenu(this.menuStore);		
	}	
}