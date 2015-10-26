package com.pvctecnocom.store.crm.mvp.presenter.menu;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoViewImpl;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("serial")
public class MenuItemCommandOferta implements Command
{		
	@Override
	public void menuSelected(MenuItem selectedItem) 
	{		
		((StoreCRM_UI)UI.getCurrent()).setAccion("listadoOfertas");
		((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().refreshDatosSesion(0, 0D, false);
		new PedidoViewImpl();	
	}
}