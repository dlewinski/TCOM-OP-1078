package com.pvctecnocom.store.crm.mvp.view.screen.pedido;

import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.PedidoNuevoViewEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsulta;
import com.pvctecnocom.store.crm.mvp.presenter.pedido.PedidoNuevoPresenter;
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
public class PedidoNuevoViewImpl implements PedidoNuevoView 
{
	private SAPPresenter sapPresenter;
	private PedidoNuevoPresenter pedidoPresenter;
	private String title = "";	
	private PedidoConsulta pedidoConsulta;		
	private ComboBox cboClientes;
	private Boolean isFirstTime = true;
	
	public PedidoNuevoViewImpl(PedidoConsulta pedidoConsulta) 	
    {   			
		StoreCrmEventBus.register(this);
		
		this.sapPresenter = new SAPPresenter();
		
		this.pedidoConsulta = pedidoConsulta;
		
		String accion = ((StoreCRM_UI)UI.getCurrent()).getAccion();
		
		if(accion != null)
		{			
			switch (accion) 
			{
            	case "nuevoPedido": title = "Nuevo pedido";            		 
                break;
                
            	case "nuevaOferta": title = "Nueva oferta";
                break;
                
            	case "modificarPedido": title = "Modificar pedido " + this.pedidoConsulta.getDocumentoVenta();
                break;          
                
            	case "modificarOferta": title = "Crear pedido desde la oferta " + this.pedidoConsulta.getDocumentoVenta();
                break;
                
            	case "verDetallePedido": title = "Visualizar pedido " + this.pedidoConsulta.getDocumentoVenta();
                break;
			}
		}
		
    	this.pedidoPresenter = new PedidoNuevoPresenter(this);
    	this.pedidoPresenter.nuevoPedido();
    }
	
	public PedidoConsulta getPedidoConsulta() 
	{
		return pedidoConsulta;
	}	
	
	@Override
	public void pedidoNuevoOK() 
	{			
		List<Cliente> clientes = ((StoreCRM_UI)UI.getCurrent()).getClientes();
		BeanItemContainer<Cliente> bicClientes = new BeanItemContainer<Cliente>(Cliente.class, clientes); 
		
		Cliente cliente = ((StoreCRM_UI)UI.getCurrent()).getCliente();								
		
		cboClientes = new ComboBox("Cliente - " + title, bicClientes);		
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
	    		Cliente clienteActual = null;
	    		Cliente clienteSeleccionado = null;
	    		
	    		if(isFirstTime)	    		
	    		{	
	    			isFirstTime = false;
	    			
	    			
	    			if(cboClientes.getValue() != null)
	    			{	
	    				Cliente clienteSelected = (Cliente)cboClientes.getValue();
	    				
	    				Cliente clienteSAP = sapPresenter.cargarCliente(clienteSelected.getNumeroCliente());
	    				clienteSAP.setNumeroCliente(clienteSelected.getNumeroCliente());
	    				
	    				((StoreCRM_UI)UI.getCurrent()).setCliente(clienteSAP);	
	    			}
	    			
	    			StoreCrmEventBus.post(new PedidoNuevoViewEvent());
	    		}	
	    		else
	    		{	
	    			clienteActual = ((StoreCRM_UI)UI.getCurrent()).getCliente();
	    			
	    			if(clienteActual != null && clienteActual.getNumeroCliente() != null)
	    			{	    						    			
		    			if(cboClientes.getValue() != null)
		    				clienteSeleccionado = (Cliente)cboClientes.getValue();
		    				
		    			if(!clienteActual.equals(clienteSeleccionado))
		    			{	
			    			ConfirmDialog.show(
			    				UI.getCurrent(), 
			    				"Por favor, confirme", 
			    				"Está seguro de cambiar de Cliente? Los datos ingresados para el pedido actual serán blanqueados", 
			    				"Aceptar", 
			    				"Cancelar",
			    				
			    				new ConfirmDialog.Listener() 
			    		    	{
			    		    		public void onClose(ConfirmDialog dialog) 
			    		    		{
			    		    			if (dialog.isConfirmed())     			    				
			    		    				cambiarCliente(); // Confirmed to continue    	     
			    		    			else
			    		    				noCambiarCliente();
			    		    		}
			    		    	}
			    			);
		    			}	
	    			}
	    			else
	    				cambiarCliente();
	    		}	    		
	    	}
	    });
		
		if(cliente != null && cliente.getNumeroCliente() != null && !cliente.getNumeroCliente().equals("") && clientes != null && clientes.size() > 0)
			cboClientes.setValue(cliente);
		
		if(clientes != null && clientes.size() == 1)		
			cboClientes.setValue(clientes.get(0));			
		
		String accion = ((StoreCRM_UI)UI.getCurrent()).getAccion();
		
		if(accion != null && (accion.equalsIgnoreCase("modificarPedido") || accion.equalsIgnoreCase("modificarOferta") || accion.equalsIgnoreCase("verDetallePedido")))		
			cboClientes.setEnabled(false);		
		else
			cboClientes.setEnabled(true);
		
		((StoreCRM_UI)UI.getCurrent()).getPageLayout().getBodyLayout().getContentLayout().showComponentTop(cboClientes);								
	}
	
	private void cambiarCliente()
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
		
		StoreCrmEventBus.post(new PedidoNuevoViewEvent());
	}
	
	private void noCambiarCliente()
	{
		Cliente clienteActual = ((StoreCRM_UI)UI.getCurrent()).getCliente();
		
		cboClientes.setValue(clienteActual);
	}
}