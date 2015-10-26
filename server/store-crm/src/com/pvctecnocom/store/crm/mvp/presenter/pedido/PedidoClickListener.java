package com.pvctecnocom.store.crm.mvp.presenter.pedido;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.AreaVenta;
import com.pvctecnocom.store.crm.mvp.model.bo.CentroAlmacen;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.model.bo.Destinatario;
import com.pvctecnocom.store.crm.mvp.model.bo.ItemPedido;
import com.pvctecnocom.store.crm.mvp.model.bo.Pedido;
import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsulta;
import com.pvctecnocom.store.crm.mvp.model.bo.Producto;
import com.pvctecnocom.store.crm.mvp.presenter.sap.SAPPresenter;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoNuevoViewImpl;
import com.vaadin.data.Item;
import com.vaadin.server.Page;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import com.zybnet.autocomplete.server.AutocompleteField;

@SuppressWarnings("serial")
public class PedidoClickListener implements ClickListener 
{
	private ComboBox cbAreaVenta;
	private ComboBox cbCentro;	
	private ComboBox cbAlmacen;
	private DateField dfFechaEntrega;			
	private TextField txtTipoCambio;	
	private TextArea txtObservaciones;	
	private ComboBox cbDestinatario;		
	private Table tableProductos;				
	private SAPPresenter sapPresenter;		
	private String documentoVentas;
	private ArrayList<String> posicionBorradas;			

	public void setCbAreaVenta(ComboBox cbAreaVenta) 
	{
		this.cbAreaVenta = cbAreaVenta;
	}

	public void setCbCentro(ComboBox cbCentro) 
	{
		this.cbCentro = cbCentro;
	}
	
	public void setCbAlmacen(ComboBox cbAlmacen) 
	{
		this.cbAlmacen = cbAlmacen;
	}

	public void setDfFechaEntrega(DateField dfFechaEntrega) 
	{
		this.dfFechaEntrega = dfFechaEntrega;
	}
	
	public void setTxtTipoCambio(TextField txtTipoCambio) 
	{
		this.txtTipoCambio = txtTipoCambio;
	}

	public void setTxtObservaciones(TextArea txtObservaciones) 
	{
		this.txtObservaciones = txtObservaciones;
	}
	
	public void setCbDestinatario(ComboBox cbDestinatario) 
	{
		this.cbDestinatario = cbDestinatario;
	}
		
	public void setTableProductos(Table tableProductos) 
	{
		this.tableProductos = tableProductos;		
	}				
	
	public String getDocumentoVentas() 
	{
		return documentoVentas;
	}

	public void setDocumentoVentas(String documentoVentas) 
	{
		this.documentoVentas = documentoVentas;
	}

	public void setPosicionBorradas(ArrayList<String> posicionBorradas) 
	{
		this.posicionBorradas = posicionBorradas;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override	
	public void buttonClick(ClickEvent event) 
	{				
		Notification notification;
		String message;
		
		if(this.tableProductos != null && this.tableProductos.size() > 0)
		{			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			
			this.sapPresenter = new SAPPresenter();
			
			Pedido pedido = new Pedido();
									
			pedido.setMoneda("USD");
			pedido.setDocumentoVentas(this.documentoVentas);
			
			try
			{
				Cliente cliente = ((StoreCRM_UI)UI.getCurrent()).getCliente();
				
				if(cliente != null)					
					pedido.setIdCliente(cliente.getNumeroCliente());			
				
				String nroVendedorCRM = ((StoreCRM_UI)UI.getCurrent()).getNumeroVendedorCRM();
				
				if(nroVendedorCRM != null)
					pedido.setGrupoVendedores(nroVendedorCRM);												
				
				AreaVenta areaVenta = (AreaVenta)cbAreaVenta.getValue();
				
				if(areaVenta != null)
				{
					pedido.setOrganizacionVenta(areaVenta.getOrganizacionVentas());					
					pedido.setCanalDistribucion(areaVenta.getCanalDistribucion());			
					pedido.setSector(areaVenta.getSector());
				}
				
				String tipoCambio = txtTipoCambio.getValue();
				
				if(tipoCambio != null)				
					pedido.setTipoCambio(tipoCambio.replace(",", "."));				
				
				String observaciones = txtObservaciones.getValue();
				
				if(observaciones != null)
					pedido.setObservaciones(observaciones);
				
				Date fechaEntrega = dfFechaEntrega.getValue();
				
				if(fechaEntrega != null)									
					pedido.setFechaReparto(simpleDateFormat.format(fechaEntrega));				
																
				Destinatario destinatario = (Destinatario)cbDestinatario.getValue();
				
				if(destinatario != null)
					pedido.setIdClienteInterlocutor(destinatario.getCliente());
				else
				{
					message = "Debe seleccionar el Destinatario de mercadería";
					notification = new Notification(message);
					notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);	
					notification.setDelayMsec(-1);
					notification.show(Page.getCurrent());
					cbDestinatario.focus();
					
					return;
				}
				
				List<ItemPedido> itemsPedido = new ArrayList<ItemPedido>();
											
				Iterator<?> it = this.tableProductos.getItemIds().iterator();
				
				Integer index;
				Item item;
				
				ItemPedido itemPedido;
				AutocompleteField<Producto> acCodigo;
				TextField txtPrecio;
				TextField txtDescuentoManual;
				DateField dfFechaEntregaPosicion;
				
				CentroAlmacen centro = (CentroAlmacen)cbCentro.getValue();
				CentroAlmacen almacen = (CentroAlmacen)cbAlmacen.getValue();
				
				String material = null;
				Integer cantidad;
				Double precio = 0.0;
				
				while (it.hasNext()) 
				{				
					index = (Integer) it.next();
					item = this.tableProductos.getItem(index);
					
					itemPedido = new ItemPedido();
					
					acCodigo = (AutocompleteField)item.getItemProperty("codigo").getValue();
					
					if(acCodigo != null)
					{	
						itemPedido.setCodigoProducto(acCodigo.getText());
						material = itemPedido.getCodigoProducto();												
					}	
					
					if(material == null || material.equals(""))
						continue;
					
					TextField txtCantidad = (TextField)item.getItemProperty("cantidad").getValue();					
					
					try
					{
						cantidad = Integer.valueOf(txtCantidad.getValue());
					}
					catch(NumberFormatException e)
					{
						cantidad = 0;						
					}	
					
					if(cantidad > 0)
					{
						itemPedido.setCantidad(String.valueOf(cantidad));
					}	
					else
					{
						notification = new Notification("La cantidad del material " + material + " debe ser mayor a 0");
						notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);	
						notification.setDelayMsec(-1);
						notification.show(Page.getCurrent());
						
						txtCantidad.focus();
						
						return;
					}
					
					acCodigo = (AutocompleteField)item.getItemProperty("codigo").getValue();																								
					itemPedido.setCodigoProducto(acCodigo.getText());
																	
					txtPrecio = (TextField)item.getItemProperty("precio").getValue();
					itemPedido.setValorCondicionPrecio(txtPrecio.getValue().replace(",", "."));
					
					try
					{
						if(itemPedido.getValorCondicionPrecio() != null)
							precio = Double.valueOf(itemPedido.getValorCondicionPrecio());
					}
					catch(NumberFormatException e)
					{
						precio = 0.0;						
					}
					
					if(precio <= 0)
					{
						notification = new Notification("El precio del material " + material + " debe ser mayor a 0");
						notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);	
						notification.setDelayMsec(-1);
						notification.show(Page.getCurrent());
						
						txtPrecio.focus();
						
						return;
					}
					
					txtDescuentoManual = (TextField)item.getItemProperty("descuentoManual").getValue();
			    	itemPedido.setValorCondicionDescuento(txtDescuentoManual.getValue().replace(",", "."));
			    	
			    	dfFechaEntregaPosicion = (DateField)item.getItemProperty("fechaEntrega").getValue();

			    	if(dfFechaEntregaPosicion != null)
			    		itemPedido.setFechaEntregaPosicion(simpleDateFormat.format(dfFechaEntregaPosicion.getValue()));				    					    	
			    						
			    	if(centro != null)
			    		itemPedido.setCentro(centro.getCentro());
			    	
			    	if(almacen != null)				    		
			    		itemPedido.setAlmacen(almacen.getAlmacen());
			    	
			    	itemPedido.setPosicion((String)item.getItemProperty("posicion").getValue());
			    	itemPedido.setPosicionBorrada("");
			    	
					itemsPedido.add(itemPedido);
				
				}	
					
				ItemPedido itemBorrado;
				
				for(String posicionBorrada : posicionBorradas)
				{
					itemBorrado = new ItemPedido();
					itemBorrado.setPosicionBorrada(posicionBorrada);
					
					itemsPedido.add(itemBorrado);
				}
				
				pedido.setItemsPedido(itemsPedido);
							
				Pedido pedidoResult = null; 
				
				String accion = ((StoreCRM_UI)UI.getCurrent()).getAccion();
				
				if(accion != null)
				{			
					switch (accion) 
					{
		            	case "nuevoPedido": pedidoResult = sapPresenter.cargarPedido(pedido);            		 
		                break;
		                
		            	case "nuevaOferta": pedidoResult = sapPresenter.cargarOferta(pedido);
		                break;
		                
		            	case "modificarPedido": pedidoResult = sapPresenter.modificarPedido(pedido);
		                break;
		                
		            	case "modificarOferta": pedidoResult = sapPresenter.modificarOferta(pedido);
		                break;
					}
				}								
				else
				{
					return;
				}
								
				if(pedidoResult != null)
				{
					System.out.println("DocumentoVentas: " + pedidoResult.getDocumentoVentas());	
					System.out.println("MonedaDocumentoComercial: " + pedidoResult.getMonedaDocumentoComercial());
					System.out.println("ValorNeto: " + pedidoResult.getValorNeto());				
					System.out.println("MensajeTipo: " + pedidoResult.getMensajeTipo());
					System.out.println("MensajeClase: " + pedidoResult.getMensajeClase());
					System.out.println("MensajeNumero: " + pedidoResult.getMensajeNumero());
					System.out.println("MensajeTexto: " + pedidoResult.getMensajeTexto());				
					System.out.println("LogAplicacionNumero: " + pedidoResult.getLogAplicacionNumero());
					System.out.println("LogAplicacionNumeroInterno: " + pedidoResult.getLogAplicacionNumeroInterno());				
					System.out.println("VariableMensaje1: " + pedidoResult.getVariableMensaje1());
					System.out.println("VariableMensaje2: " + pedidoResult.getVariableMensaje2());
					System.out.println("VariableMensaje3: " + pedidoResult.getVariableMensaje3());
					System.out.println("VariableMensaje4: " + pedidoResult.getVariableMensaje4());				
					System.out.println("Parametro: " + pedidoResult.getParametro());
					System.out.println("ParametroLinea: " + pedidoResult.getParametroLinea());
					System.out.println("ParametroCampo: " + pedidoResult.getParametroCampo());
					System.out.println("Sistema: " + pedidoResult.getSistema());
															
					message = pedidoResult.getMensajeTexto();
					String resultado = pedidoResult.getMensajeTipo();
							
					notification = new Notification(message);
					notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);	
					notification.setDelayMsec(-1);
					
					if(message == null || message.equals(""))							
						notification.setCaption("El pedido no se pudo realizar");											
					else
					{
						if(resultado.equalsIgnoreCase("S"))	
						{	
							((StoreCRM_UI)UI.getCurrent()).setAccion("nuevoPedido");
							new PedidoNuevoViewImpl(new PedidoConsulta());	
							notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);	
							((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().refreshDatosSesion(0, 0D, true);
						}	
					}
					
			    	notification.show(Page.getCurrent());
				}	
			}
			catch (Exception e) 
			{			
				e.printStackTrace();
			}
		}	
	}		
}