package com.pvctecnocom.store.crm.mvp.view.screen.pedido;

import java.util.List;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.PedidoViewEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.presenter.pedido.PedidoPresenter;
import com.pvctecnocom.store.crm.mvp.presenter.sap.SAPPresenter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class PedidoViewImpl implements PedidoView 
{
	private PedidoPresenter pedidoPresenter;	
	private SAPPresenter sapPresenter;
	
	public PedidoViewImpl() 	
    {   							
		StoreCrmEventBus.register(this);
		
		this.sapPresenter = new SAPPresenter();       	      
    	this.pedidoPresenter = new PedidoPresenter(this);
    	this.pedidoPresenter.cargarPedidos();
    }
	
	@Override
	public void pedidoOK() 
	{		
		List<Cliente> clientes = ((StoreCRM_UI)UI.getCurrent()).getClientes();
		BeanItemContainer<Cliente> bicClientes = new BeanItemContainer<Cliente>(Cliente.class, clientes); 
		
		Cliente cliente = ((StoreCRM_UI)UI.getCurrent()).getCliente();								
		
		ComboBox cboClientes = new ComboBox("Cliente", bicClientes);		
		cboClientes.setIcon(FontAwesome.USER);
		cboClientes.setDescription("Número y Razón Social del Cliente");		
		cboClientes.setInputPrompt("Seleccione un Cliente");
		cboClientes.setWidth("400px");
		cboClientes.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
		cboClientes.setFilteringMode(FilteringMode.CONTAINS);		
		
		cboClientes.addValueChangeListener(new ValueChangeListener() 
	    {
	    	@Override
	    	public void valueChange(ValueChangeEvent event) 
	    	{	    			    		    		
    			if(cboClientes.getValue() != null)
    			{	
    				Cliente clienteSelected = (Cliente)cboClientes.getValue();
    				
    				Cliente clienteSAP = sapPresenter.cargarCliente(clienteSelected.getNumeroCliente());
    				clienteSAP.setNumeroCliente(clienteSelected.getNumeroCliente());
    				
    				((StoreCRM_UI)UI.getCurrent()).setCliente(clienteSAP);	
    			}		    		
    			else    			
    				((StoreCRM_UI)UI.getCurrent()).setCliente(new Cliente());	 
    			
    			StoreCrmEventBus.post(new PedidoViewEvent());
	    	}
	    });
		
		if(cliente != null && cliente.getNumeroCliente() != null && !cliente.getNumeroCliente().equals("") && clientes != null && clientes.size() > 0)
			cboClientes.setValue(cliente);
		
		if(clientes != null && clientes.size() == 1)		
			cboClientes.setValue(clientes.get(0));					
		
		((StoreCRM_UI)UI.getCurrent()).getPageLayout().getBodyLayout().getContentLayout().showComponentTop(cboClientes);				
	}
}