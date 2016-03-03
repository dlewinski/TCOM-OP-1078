package com.pvctecnocom.store.crm.mvp.view;

import com.pvctecnocom.store.crm.mvp.view.sap.SAPArticulosView;
import com.pvctecnocom.store.crm.mvp.view.sap.SAPCategoriasView;
import com.pvctecnocom.store.crm.mvp.view.sap.SAPCentroAlmacenView;
import com.pvctecnocom.store.crm.mvp.view.sap.SAPClientesView;
import com.pvctecnocom.store.crm.mvp.view.sap.SAPConnectView;
import com.pvctecnocom.store.crm.mvp.view.sap.SAPDescuentosView;
import com.pvctecnocom.store.crm.mvp.view.sap.SAPOfertasView;
import com.pvctecnocom.store.crm.mvp.view.sap.SAPPedidosView;
import com.pvctecnocom.store.crm.mvp.view.sap.SAPPreciosView;
import com.pvctecnocom.store.crm.mvp.view.user.UserView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum StoreCrmViewType
{	    
    USER("user", UserView.class, FontAwesome.USER, false, "Usuarios"),
    SAP_CONNECT("sap-connect", SAPConnectView.class, FontAwesome.DASHBOARD, false, "Monitoreo de conexión a SAP"),
    SAP_ARTICULOS("sap-articulos", SAPArticulosView.class, FontAwesome.DATABASE, false, "Artículos"),
    SAP_CATEGORIAS("sap-categorias", SAPCategoriasView.class, FontAwesome.DATABASE, false, "Categorías"),
    SAP_CENTRO_ALMACEN("sap-centro-almacen", SAPCentroAlmacenView.class, FontAwesome.DATABASE, false, "Centros y Almacenes"),    
    SAP_CLIENTES("sap-clientes", SAPClientesView.class, FontAwesome.EXCHANGE, false, "Clientes"),
    SAP_DESCUENTOS("sap-descuentos", SAPDescuentosView.class, FontAwesome.EXCHANGE, false, "Descuentos"),
    SAP_OFERTAS("sap-ofertas", SAPOfertasView.class, FontAwesome.EXCHANGE, false, "Ofertas"),
    SAP_PEDIDOS("sap-pedidos", SAPPedidosView.class, FontAwesome.EXCHANGE, false, "Pedidos"),
    SAP_PRECIOS("sap-precios", SAPPreciosView.class, FontAwesome.EXCHANGE, false, "Precios");
	
    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;
    private final String caption;    
    private boolean isQuery;

	private StoreCrmViewType(final String viewName, final Class<? extends View> viewClass, final Resource icon, final boolean stateful, final String caption) 
    {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
        this.caption = caption;
    }

    public boolean isStateful() 
    {
        return stateful;
    }

    public String getViewName() 
    {
        return viewName;
    }

    public Class<? extends View> getViewClass() 
    {
        return viewClass;
    }

    public Resource getIcon() 
    {
        return icon;
    }
    
    public String getCaption() 
    {
		return caption;
	}
    
    public static StoreCrmViewType getByViewName(final String viewName) 
    {
    	StoreCrmViewType result = null;
    	
        for (StoreCrmViewType viewType : values()) 
        {
            if (viewType.getViewName().equals(viewName)) 
            {
                result = viewType;
                break;
            }
        }
        
        return result;
    }

	public boolean isQuery() 
	{
		return isQuery;
	}

	public void setQuery(boolean isQuery) 
	{
		this.isQuery = isQuery;
	}
}