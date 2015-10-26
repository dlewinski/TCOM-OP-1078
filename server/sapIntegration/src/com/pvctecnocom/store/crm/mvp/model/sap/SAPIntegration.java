package com.pvctecnocom.store.crm.mvp.model.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.AreaVenta;
import com.pvctecnocom.store.crm.mvp.model.bo.Categoria;
import com.pvctecnocom.store.crm.mvp.model.bo.CentroAlmacen;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.model.bo.Descuento;
import com.pvctecnocom.store.crm.mvp.model.bo.Destinatario;
import com.pvctecnocom.store.crm.mvp.model.bo.ItemPedido;
import com.pvctecnocom.store.crm.mvp.model.bo.Pedido;
import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsulta;
import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsultaDetalle;
import com.pvctecnocom.store.crm.mvp.model.bo.Precio;
import com.pvctecnocom.store.crm.mvp.model.bo.Producto;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class SAPIntegration 
{		
	private JCoDestination destination;
	private String destinationName;
	private String result;	
	private JCoFunction function;
	private JCoTable table;
	private JCoStructure structure;
	
	public SAPIntegration(String destinationName) 
	{
		super();		
		this.destinationName = destinationName;
		this.connectUsingPool();		
	}
	    			   			
	public String connectUsingPool()
	{		
		try
		{
			this.destination = JCoDestinationManager.getDestination(this.destinationName);
			this.destination.ping();
					
			this.result = "" + this.destination.getAttributes();
		}		
		catch (Exception e) 
		{			
			this.result = e.toString();
		}
		
		return this.result;
	}			
	
	public String getCategoriasZRFC(String iProdh, String iDown)
	{
		try
		{
			this.function = this.destination.getRepository().getFunction("ZRFC_CATEGORIAS");
			
			this.function.getImportParameterList().setValue("I_PRODH", iProdh);
			this.function.getImportParameterList().setValue("I_DOWN", iDown);
						
			this.function.execute(destination);
											
			this.result = this.function.getExportParameterList().getValue("E_RETURN").toString();
		}						
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public List<Categoria> getCategorias(String iProdh, String iDown)
	{
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		try
		{
			this.function = this.destination.getRepository().getFunction("ZRFC_CATEGORIAS");
				
			this.function.getImportParameterList().setValue("I_PRODH", iProdh);
			this.function.getImportParameterList().setValue("I_DOWN", iDown);
			
			this.function.execute(destination);
															
			table = (JCoTable)function.getExportParameterList().getValue("E_RETURN");
						
			if(table != null)
			{	
				Categoria categoria;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					categoria = new Categoria();
											
					categoria.setIdCategoria(table.getString("PRODH")); // Identificador
					categoria.setNombreCorto(table.getString("VTEXT")); //Denominaci�n
					categoria.setIdCategoriaSuperior(table.getString("CATSU")); //Categor�a superior
					categoria.setOrden(Integer.valueOf(table.getString("ORDEN"))); //Ubicaci�n dentro de su categor�a
					
					categorias.add(categoria);
				}	
			}																						
		}	
		catch (Exception e) 
		{				
			this.result = e.toString();
		}	
		
		return categorias;
	}
	
	public String getArticulosZRFC(String iProdh, String iDown)
	{
		try
		{
			this.function = this.destination.getRepository().getFunction("ZRFC_ARTICULOS");
			
			this.function.getImportParameterList().setValue("I_PRODH", iProdh);
			this.function.getImportParameterList().setValue("I_DOWN", iDown);
						
			this.function.execute(destination);
											
			this.result = this.function.getExportParameterList().getValue("E_RETURN").toString();
		}						
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;														
	}
	
	public List<Producto> getArticulos(String iProdh, String iDown)
	{
		List<Producto> productos = new ArrayList<Producto>();
		
		try
		{
			this.function = this.destination.getRepository().getFunction("ZRFC_ARTICULOS");
				
			this.function.getImportParameterList().setValue("I_PRODH", iProdh);
			this.function.getImportParameterList().setValue("I_DOWN", iDown);
			
			this.function.execute(destination);
															
			table = (JCoTable)function.getExportParameterList().getValue("E_RETURN");
						
			if(table != null)
			{	
				Producto producto;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					producto = new Producto();
																
					producto.setCodigo(table.getString("MATNR")); // N�mero de material
					producto.setDescripcion(table.getString("MAKTX")); // Texto breve de material
					producto.setIdCategoria(table.getString("PRDHA")); // Categor�a
					producto.setGrupo(table.getString("MATKL")); // Grupo
					producto.setNumeroEuropeo(table.getString("EAN11")); // N�mero europeo
					producto.setPesoBruto(table.getString("BRGEW")); // Peso bruto
					producto.setSector(table.getString("SPART")); // Sector
					producto.setUnidadMedida(table.getString("MEINS")); // Unidad de medida base
					producto.setUnidadPeso(table.getString("GEWEI")); // Unidad de peso
					
					productos.add(producto);
				}	
			}																						
		}	
		catch (Exception e) 
		{				
			this.result = e.toString();
		}	
		
		return productos;
	}
	
	public String getClienteZRFC(String iKunnr, String iPwd)
	{		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CLIENTES");
					
			function.getImportParameterList().setValue("I_KUNNR", iKunnr);
			function.getImportParameterList().setValue("I_PWD", iPwd);
						
			this.function.execute(destination);
						
			this.result = this.function.getExportParameterList().getValue("E_RETURN").toString();
			
			this.result = this.result + this.function.getExportParameterList().getValue("E_DESTIN").toString();	 										
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;	
	}
	
	public String getClienteDatosZRFC(String ivKunnr)
	{		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_CLIENTES_DATOS");
					
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr);			
						
			this.function.execute(destination);
						
			this.result = this.function.getExportParameterList().getValue("ES_CLIENTE").toString();
			
			this.result = this.result + this.function.getExportParameterList().getValue("ET_AREA_VENTA").toString();	 										
			
			this.result = this.result + this.function.getExportParameterList().getValue("ET_DEST_MERC").toString();
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;	
	}
	
	public Cliente getCliente(String iKunnr, String iPwd)
	{		
		Cliente cliente = null;
		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CLIENTES");
					
			function.getImportParameterList().setValue("I_KUNNR", iKunnr);
			function.getImportParameterList().setValue("I_PWD", iPwd);
						
			this.function.execute(destination);
			
			structure = (JCoStructure)function.getExportParameterList().getValue("E_RETURN");				
			
			if(structure != null && structure.getString("NAME1") != null && !structure.getString("NAME1").equals(""))
			{	
				cliente = new Cliente();
								
				cliente.setDireccion(structure.getString("STRAS")); // Calle y n�mero
				cliente.setEmail(structure.getString("SMTP_ADDR")); // Direcci�n de correo electr�nico
				cliente.setLocalidad(structure.getString("ORT01")); // Poblaci�n				
				cliente.setRazonSocial(structure.getString("NAME1")); // Nombre						
				
				table = (JCoTable)function.getExportParameterList().getValue("E_DESTIN");
				
				if(table != null)
				{	
					List<Destinatario> destinatarios = new ArrayList<Destinatario>();
					
					Destinatario destinatario;
					
					for (int i = 0; i < table.getNumRows(); i++)
					{
						table.setRow(i);
												
						destinatario = new Destinatario();
																	
						destinatario.setCliente(table.getString("KUNN2")); // N�mero de cliente del interlocutor
						destinatario.setCalle(table.getString("STRAS")); // Calle y n�mero
						destinatario.setPoblacion(table.getString("ORT01")); // Poblaci�n						
						
						destinatarios.add(destinatario);							
					}	
					
					cliente.setDestinatarios(destinatarios);
				}
			}
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return cliente;	
	}
	
	public Cliente getClienteDatos(String ivKunnr)
	{		
		Cliente cliente = null;
		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_CLIENTES_DATOS");
					
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr);			
						
			this.function.execute(destination);
			
			structure = (JCoStructure)function.getExportParameterList().getValue("ES_CLIENTE");				
			
			if(structure != null && structure.getString("NAME1") != null && !structure.getString("NAME1").equals(""))
			{	
				cliente = new Cliente();
				
				cliente.setRazonSocial(structure.getString("NAME1")); // Nombre
				cliente.setDireccion(structure.getString("STRAS")); // Calle y n�mero
				cliente.setLocalidad(structure.getString("ORT01")); // Poblaci�n
				cliente.setEmail(structure.getString("SMTP_ADDR")); // Direcci�n de correo electr�nico
																																						
				table = (JCoTable)function.getExportParameterList().getValue("ET_AREA_VENTA");
				
				if(table != null)
				{
					List<AreaVenta> areasVenta = new ArrayList<AreaVenta>();
					
					AreaVenta areaVenta;
					
					for (int i = 0; i < table.getNumRows(); i++)
					{
						table.setRow(i);
						
						areaVenta = new AreaVenta();
						
						areaVenta.setOrganizacionVentas(table.getString("VKORG")); // Organizaci�n de ventas						
						areaVenta.setCanalDistribucion(table.getString("VTWEG")); // Canal de distribuci�n
						areaVenta.setSector(table.getString("SPART")); // Sector
						areaVenta.setPrecioEditable(table.getString("PRE_EDIT")); // Precio Editable (X=editable, space=modo visualizaci�n)
						areaVenta.setDescuentoEditable(table.getString("DES_EDIT")); // Descuento Editable (X=editable, space=modo visualizaci�n)
						areaVenta.setCantidadLineasDefault(table.getInt("CAN_LINEA")); // Cantidad de l�neas default						
						areaVenta.setTipoCotizacion(table.getString("KURST")); // Tipo de cotizaci�n
						areaVenta.setTipoCambio(table.getDouble("UKURS")); // Tipo de cambio
						areaVenta.setCantidadDias(table.getInt("CANT_DIAS")); // Cantidad de d�as
																		
						areasVenta.add(areaVenta);
					}	
					
					cliente.setAreasVenta(areasVenta);
				}
				
				table = (JCoTable)function.getExportParameterList().getValue("ET_DEST_MERC");
				
				if(table != null)
				{	
					List<Destinatario> destinatarios = new ArrayList<Destinatario>();
					
					Destinatario destinatario;
					
					for (int i = 0; i < table.getNumRows(); i++)
					{
						table.setRow(i);
												
						destinatario = new Destinatario();
																	
						destinatario.setOrganizacionVentas(table.getString("VKORG")); // Organizaci�n de ventas						
						destinatario.setCanalDistribucion(table.getString("VTWEG")); // Canal de distribuci�n
						destinatario.setSector(table.getString("SPART")); // Sector
						destinatario.setCliente(table.getString("KUNN2")); // N�mero de cliente del interlocutor
						destinatario.setCalle(table.getString("STRAS")); // Calle y n�mero
						destinatario.setPoblacion(table.getString("ORT01")); // Poblaci�n						
						
						destinatarios.add(destinatario);							
					}	
					
					cliente.setDestinatarios(destinatarios);
				}
			}
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return cliente;	
	}
	
	public String getPedidoZRFC(Pedido pedido)
	{		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_PEDIDOS");
					
			structure = (JCoStructure)function.getImportParameterList().getValue("I_CABECERA");
			table = (JCoTable)function.getImportParameterList().getValue("I_DETALLE");
						
			structure.setValue("VKORG", pedido.getOrganizacionVenta());
			structure.setValue("VTWEG", pedido.getCanalDistribucion());
			structure.setValue("SPART", pedido.getSector());
			structure.setValue("KUNNR", pedido.getIdCliente());
			structure.setValue("KUNN2", pedido.getIdDestinatario());
			
			if(pedido.getItemsPedido() != null)
			{
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{	
					table.appendRow();				
					table.setValue("MATNR", itemPedido.getCodigoProducto());
					table.setValue("MENGE", itemPedido.getCantidad());
				}	
			}						
			
			function.getImportParameterList().setValue("I_CABECERA", structure);
			function.getImportParameterList().setValue("I_DETALLE", table);
			
			this.function.execute(destination);
			
			this.result = "E_MESSAGES: " + this.function.getExportParameterList().getValue("E_MESSAGES").toString();
			this.result = this.result + " E_NETWR: " + this.function.getExportParameterList().getValue("E_NETWR").toString();
			this.result = this.result + " E_VBELN: " + this.function.getExportParameterList().getValue("E_VBELN").toString();
			this.result = this.result + " E_WAERK: " + this.function.getExportParameterList().getValue("E_WAERK").toString();									
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public Pedido getPedido(Pedido pedido)
	{		
		Pedido pedidoResult = new Pedido();
		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_PEDIDOS");
					
			structure = (JCoStructure)function.getImportParameterList().getValue("I_CABECERA");
			table = (JCoTable)function.getImportParameterList().getValue("I_DETALLE");
						
			structure.setValue("VKORG", pedido.getOrganizacionVenta());
			structure.setValue("VTWEG", pedido.getCanalDistribucion());
			structure.setValue("SPART", pedido.getSector());
			structure.setValue("KUNNR", pedido.getIdCliente());
			structure.setValue("KUNN2", pedido.getIdDestinatario());
			
			if(pedido.getItemsPedido() != null)
			{
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{	
					table.appendRow();				
					table.setValue("MATNR", itemPedido.getCodigoProducto());
					table.setValue("MENGE", itemPedido.getCantidad());
				}	
			}						
			
			function.getImportParameterList().setValue("I_CABECERA", structure);
			function.getImportParameterList().setValue("I_DETALLE", table);
			
			this.function.execute(destination);
															
			pedidoResult.setDescripcion(this.function.getExportParameterList().getValue("E_MESSAGES").toString());			
			pedidoResult.setNumeroPedido(this.function.getExportParameterList().getValue("E_VBELN").toString());
			pedidoResult.setImporte(this.function.getExportParameterList().getValue("E_NETWR").toString());
			pedidoResult.setMoneda(this.function.getExportParameterList().getValue("E_WAERK").toString());
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return pedidoResult;
	}						
	
	public String getAreasVentaZRFC(String iKunnr , String iSpart)
	{		
		try
		{				
			this.function = this.destination.getRepository().getFunction("ZRFC_AREA_VENTA");
			
			this.function.getImportParameterList().setValue("I_KUNNR", iKunnr);
			this.function.getImportParameterList().setValue("I_SPART", iSpart);					
						
			this.function.execute(destination);
											
			this.result = this.function.getExportParameterList().getValue("E_RETURN").toString();
		}						
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public List<AreaVenta> getAreasVenta(String iKunnr , String iSpart)
	{				
		List<AreaVenta> areasVenta = new ArrayList<AreaVenta>();				
		
		try
		{				
			this.function = this.destination.getRepository().getFunction("ZRFC_AREA_VENTA");
			
			this.function.getImportParameterList().setValue("I_KUNNR", iKunnr);
			this.function.getImportParameterList().setValue("I_SPART", iSpart);					
						
			this.function.execute(destination);														
			
			table = (JCoTable)function.getExportParameterList().getValue("E_RETURN");
			
			if(table != null)
			{
				AreaVenta areaVenta;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					areaVenta = new AreaVenta();
					
					areaVenta.setOrganizacionVentas((table.getString("VKORG"))); // Organizaci�n de ventas
					areaVenta.setOrganizacionVentasDenominacion((table.getString("VKORGT"))); // Denominaci�n
					areaVenta.setCanalDistribucion((table.getString("VTWEG"))); // Canal de distribuci�n
					areaVenta.setCanalDistribucionDenominacion((table.getString("VTWEGT"))); // Denominaci�n
					areaVenta.setSector((table.getString("SPART"))); // Sector
					areaVenta.setSectorDenominacion((table.getString("SPARTT"))); // Denominaci�n
					
					areasVenta.add(areaVenta);
				}
			}
		}						
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return areasVenta;
	}
	
	public String getCentrosAlmacenesZRFC()
	{		
		try
		{				
			this.function = this.destination.getRepository().getFunction("ZRFC_CENTRO_ALMACEN");											
						
			this.function.execute(destination);
											
			this.result = this.function.getExportParameterList().getValue("E_RETURN").toString();
		}						
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public List<CentroAlmacen> getCentrosAlmacenes()
	{		
		List<CentroAlmacen> centrosAlmacenes = new ArrayList<CentroAlmacen>();	
		
		try
		{				
			this.function = this.destination.getRepository().getFunction("ZRFC_CENTRO_ALMACEN");											
						
			this.function.execute(destination);
			
			table = (JCoTable)function.getExportParameterList().getValue("E_RETURN");
			
			if(table != null)
			{
				CentroAlmacen centroAlmacen;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					centroAlmacen = new CentroAlmacen();
					centroAlmacen.setIdCentroAlmacen(i);
					
					centroAlmacen.setOrganizacionVentas((table.getString("VKORG"))); // Organizaci�n de ventas
					centroAlmacen.setOrganizacionVentasDenominacion((table.getString("VKORGT"))); // Denominaci�n
					centroAlmacen.setCentro((table.getString("WERKS"))); // Centro
					centroAlmacen.setNombre((table.getString("WERKST"))); // Nombre
					centroAlmacen.setAlmacen((table.getString("LGORT"))); // Almac�n
					centroAlmacen.setAlmacenDenominacion((table.getString("LGORTT"))); // Denominaci�n de almac�n
					
					centrosAlmacenes.add(centroAlmacen);
				}
			}														
		}						
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return centrosAlmacenes;
	}
	
	public String getPedidoCRM_ZRFC(Pedido pedido)
	{		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_PEDIDOS");
					
			this.structure = (JCoStructure)function.getImportParameterList().getValue("IS_CABECERA"); // Estructura de entrada RFC CRM Pedidos - Cabecera
			this.table = (JCoTable)function.getImportParameterList().getValue("IT_DETALLE"); // Tipo tabla para entrada RFC CRM Pedidos - Detalle			
			
			this.structure.setValue("VKORG", pedido.getOrganizacionVenta()); // Organizaci�n de ventas
			this.structure.setValue("VTWEG", pedido.getCanalDistribucion()); // Canal de distribuci�n
			this.structure.setValue("SPART", pedido.getSector()); // Sector
			this.structure.setValue("KUNNR", pedido.getIdCliente()); // N� de cliente 1
			this.structure.setValue("KUNN2", pedido.getIdClienteInterlocutor()); // N�mero de cliente del interlocutor
			this.structure.setValue("VKGRP", pedido.getGrupoVendedores()); // Grupo de vendedores
			this.structure.setValue("WAERK", pedido.getMoneda()); // Moneda de documento comercial
			this.structure.setValue("KURSK", pedido.getTipoCambio()); // Tp.cambio p.determin.precios			
			this.structure.setValue("EDATU", pedido.getFechaReparto()); // Fecha de reparto
									
			if(pedido.getItemsPedido() != null)
			{
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{	
					this.table.appendRow();				
					
					this.table.setValue("MATNR", itemPedido.getCodigoProducto()); // N�mero de material
					this.table.setValue("MENGE", itemPedido.getCantidad()); // Cantidad prevista en unidad de medida de venta
					this.table.setValue("KWERT_PRE", itemPedido.getValorCondicionPrecio()); // Valor de la condici�n - precio
					this.table.setValue("KWERT_DES", itemPedido.getValorCondicionDescuento()); // Valor de la condici�n - descuento
					this.table.setValue("WERKS", itemPedido.getCentro()); // Centro
					this.table.setValue("LGORT", itemPedido.getAlmacen()); // Almac�n
					this.table.setValue("EDATU", itemPedido.getFechaEntregaPosicion()); // Fecha entrega a nivel posici�n
				}	
			}						
						
			this.function.getImportParameterList().setValue("IS_CABECERA", structure);
			this.function.getImportParameterList().setValue("IT_DETALLE", table);
			this.function.getImportParameterList().setValue("IV_TEXTO", pedido.getObservaciones()); // Texto longitud 1000
			
			this.function.execute(destination);						
			
			this.result =               " EV_VBELN: " + this.function.getExportParameterList().getValue("EV_VBELN").toString(); // Documento de ventas
			this.result = this.result + " EV_WAERK: " + this.function.getExportParameterList().getValue("EV_WAERK").toString(); // Moneda de documento comercial
			this.result = this.result + " EV_NETWR: " + this.function.getExportParameterList().getValue("EV_NETWR").toString(); // Valor neto del pedido en moneda del documento
			this.result = this.result + " ES_RETURN: " + this.function.getExportParameterList().getValue("ES_RETURN").toString(); // Par�metro de retorno
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public Pedido getPedidoCRM(Pedido pedido)
	{
		Pedido pedidoResult = new Pedido();
		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_PEDIDOS");
			
			this.structure = (JCoStructure)function.getImportParameterList().getValue("IS_CABECERA"); // Estructura de entrada RFC CRM Pedidos - Cabecera
			this.table = (JCoTable)function.getImportParameterList().getValue("IT_DETALLE"); // Tipo tabla para entrada RFC CRM Pedidos - Detalle			
			
			this.structure.setValue("VKORG", pedido.getOrganizacionVenta()); // Organizaci�n de ventas
			this.structure.setValue("VTWEG", pedido.getCanalDistribucion()); // Canal de distribuci�n
			this.structure.setValue("SPART", pedido.getSector()); // Sector
			this.structure.setValue("KUNNR", pedido.getIdCliente()); // N� de cliente 1
			this.structure.setValue("KUNN2", pedido.getIdClienteInterlocutor()); // N�mero de cliente del interlocutor
			this.structure.setValue("VKGRP", pedido.getGrupoVendedores()); // Grupo de vendedores
			this.structure.setValue("WAERK", pedido.getMoneda()); // Moneda de documento comercial
			this.structure.setValue("KURSK", pedido.getTipoCambio()); // Tp.cambio p.determin.precios			
			this.structure.setValue("EDATU", pedido.getFechaReparto()); // Fecha de reparto
			
			if(pedido.getItemsPedido() != null)
			{
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{	
					this.table.appendRow();				
					
					this.table.setValue("MATNR", itemPedido.getCodigoProducto()); // N�mero de material
					this.table.setValue("MENGE", itemPedido.getCantidad()); // Cantidad prevista en unidad de medida de venta
					this.table.setValue("KWERT_PRE", itemPedido.getValorCondicionPrecio()); // Valor de la condici�n - precio
					this.table.setValue("KWERT_DES", itemPedido.getValorCondicionDescuento()); // Valor de la condici�n - descuento
					this.table.setValue("WERKS", itemPedido.getCentro()); // Centro
					this.table.setValue("LGORT", itemPedido.getAlmacen()); // Almac�n
					this.table.setValue("EDATU", itemPedido.getFechaEntregaPosicion()); // Fecha entrega a nivel posici�n
				}	
			}
			
			this.function.getImportParameterList().setValue("IS_CABECERA", structure);
			this.function.getImportParameterList().setValue("IT_DETALLE", table);
			this.function.getImportParameterList().setValue("IV_TEXTO", pedido.getObservaciones()); // Texto longitud 1000
			
			this.function.execute(destination);	
			
			pedidoResult.setDocumentoVentas(this.function.getExportParameterList().getValue("EV_VBELN").toString()); // Documento de ventas
			pedidoResult.setMonedaDocumentoComercial(this.function.getExportParameterList().getValue("EV_WAERK").toString()); // Moneda de documento comercial
			pedidoResult.setValorNeto(this.function.getExportParameterList().getValue("EV_NETWR").toString()); // Valor neto del pedido en moneda del documento
			
			this.structure = (JCoStructure)function.getExportParameterList().getValue("ES_RETURN"); // Par�metro de retorno
			
			if(this.structure != null)
			{
				pedidoResult.setMensajeTipo(this.structure.getString("TYPE")); // Tipo mensaje: S Success, E Error, W Warning, I Info A Abort
				pedidoResult.setMensajeClase(this.structure.getString("ID")); // Clase de mensajes
				pedidoResult.setMensajeNumero(this.structure.getString("NUMBER")); // N�mero de mensaje
				pedidoResult.setMensajeTexto(this.structure.getString("MESSAGE")); // Texto de mensaje				
				pedidoResult.setLogAplicacionNumero(this.structure.getString("LOG_NO")); // Log de aplicaci�n: N�mero de log
				pedidoResult.setLogAplicacionNumeroInterno(this.structure.getString("LOG_MSG_NO")); // Log aplicaci�n: N�mero consecutivo interno de mensaje				
				pedidoResult.setVariableMensaje1(this.structure.getString("MESSAGE_V1")); // Variable de mensaje 1
				pedidoResult.setVariableMensaje2(this.structure.getString("MESSAGE_V2")); // Variable de mensaje 2
				pedidoResult.setVariableMensaje3(this.structure.getString("MESSAGE_V3")); // Variable de mensaje 3
				pedidoResult.setVariableMensaje4(this.structure.getString("MESSAGE_V4")); // Variable de mensaje 4				
				pedidoResult.setParametro(this.structure.getString("PARAMETER")); // Par�metro
				pedidoResult.setParametroLinea(this.structure.getString("ROW")); // L�nea en el par�metro
				pedidoResult.setParametroCampo(this.structure.getString("FIELD")); // Campo en par�metro				
				pedidoResult.setSistema(this.structure.getString("SYSTEM")); // Sistema (l�gico) del que procede el mensaje
			}
		}
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return pedidoResult;
	}
	
	public String getDescuentosCRM_ZRFC(String ivKunnr, String ivKorg, String ivTweg, String ivSpart)
	{
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_DESCUENTOS");
					
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr); // Cliente
			function.getImportParameterList().setValue("IV_VKORG", ivKorg);  // Organizaci�n de ventas
			function.getImportParameterList().setValue("IV_VTWEG", ivTweg);  // Canal de distribuci�n
			function.getImportParameterList().setValue("IV_SPART", ivSpart); // Sector
						
			this.function.execute(destination);
			
			this.result = this.function.getExportParameterList().getValue("ET_DES_GRP").toString();
			
			this.result = this.result + this.function.getExportParameterList().getValue("ET_DES_MAT").toString();
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public List<Descuento> getDescuentosCRM(String ivKunnr, String ivKorg, String ivTweg, String ivSpart)
	{
		List<Descuento> descuentos = new ArrayList<Descuento>();
		Descuento descuento;
		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_DESCUENTOS");
					
			this.function.getImportParameterList().setValue("IV_KUNNR", ivKunnr); // Cliente
			this.function.getImportParameterList().setValue("IV_VKORG", ivKorg);  // Organizaci�n de ventas
			this.function.getImportParameterList().setValue("IV_VTWEG", ivTweg);  // Canal de distribuci�n
			this.function.getImportParameterList().setValue("IV_SPART", ivSpart); // Sector
						
			this.function.execute(destination);						
			
			this.table = (JCoTable)function.getExportParameterList().getValue("ET_DES_GRP");
			
			if(this.table != null)
			{						
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					descuento = new Descuento();
																
					descuento.setGrupo(table.getString("MATKL")); // Grupo de art�culos
					descuento.setGrupoDescuento(table.getString("DESC")); // Descuento acumulado concedido					
					descuento.setMaterial("");
					descuento.setMaterialDescuento("");
					
					descuentos.add(descuento);
				}				
			}
			
			this.table = (JCoTable)function.getExportParameterList().getValue("ET_DES_MAT");
			
			if(this.table != null)
			{				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					descuento = new Descuento();
																
					descuento.setGrupo("");
					descuento.setGrupoDescuento("");					
					descuento.setMaterial(table.getString("MATNR")); // N�mero de material
					descuento.setMaterialDescuento(table.getString("DESC")); // Descuento acumulado concedido
					
					descuentos.add(descuento);
				}	
			}
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return descuentos;
	}
	
	public List<HashMap<String, String>> getDescuentosMapCRM(String ivKunnr, String ivKorg, String ivTweg, String ivSpart)
	{
		HashMap<String, String> descuentosGrupo;
		HashMap<String, String> descuentosMaterial;
		
		List<HashMap<String, String>> descuentos = new ArrayList<HashMap<String,String>>();
		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_DESCUENTOS");
					
			this.function.getImportParameterList().setValue("IV_KUNNR", ivKunnr); // Cliente
			this.function.getImportParameterList().setValue("IV_VKORG", ivKorg);  // Organizaci�n de ventas
			this.function.getImportParameterList().setValue("IV_VTWEG", ivTweg);  // Canal de distribuci�n
			this.function.getImportParameterList().setValue("IV_SPART", ivSpart); // Sector
						
			this.function.execute(destination);						
			
			this.table = (JCoTable)function.getExportParameterList().getValue("ET_DES_GRP");
			
			if(this.table != null)
			{		
				descuentosGrupo = new HashMap<String, String>();
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
										
					descuentosGrupo.put(table.getString("MATKL"), table.getString("DESC"));	// Grupo de art�culos - Descuento acumulado concedido																 																			
				}				
				
				descuentos.add(descuentosGrupo);
			}
			
			this.table = (JCoTable)function.getExportParameterList().getValue("ET_DES_MAT");
			
			if(this.table != null)
			{				
				descuentosMaterial = new HashMap<String, String>();
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
										
					descuentosMaterial.put(table.getString("MATNR"), table.getString("DESC"));	// N�mero de material - Descuento acumulado concedido						
				}	
				
				descuentos.add(descuentosMaterial);
			}
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return descuentos;
	}
	
	public String getPreciosCRM_ZRFC(String ivKorg, String ivTweg, String ivSpart, String ivKunnr)
	{
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_PRECIOS");
								
			function.getImportParameterList().setValue("IV_VKORG", ivKorg);  // Organizaci�n de ventas
			function.getImportParameterList().setValue("IV_VTWEG", ivTweg);  // Canal de distribuci�n
			function.getImportParameterList().setValue("IV_SPART", ivSpart); // Sector
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr); // Cliente
						
			this.function.execute(destination);
			
			this.result = this.function.getExportParameterList().getValue("ET_PRECIOS").toString();						
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public List<Precio> getPreciosCRM(String ivKorg, String ivTweg, String ivSpart, String ivKunnr)
	{
		List<Precio> precios = new ArrayList<Precio>();
		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_PRECIOS");
								
			function.getImportParameterList().setValue("IV_VKORG", ivKorg);  // Organizaci�n de ventas
			function.getImportParameterList().setValue("IV_VTWEG", ivTweg);  // Canal de distribuci�n
			function.getImportParameterList().setValue("IV_SPART", ivSpart); // Sector
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr); // Cliente
						
			this.function.execute(destination);
						
			this.table = (JCoTable)function.getExportParameterList().getValue("ET_PRECIOS");
			
			if(this.table != null)
			{
				Precio precio;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					precio = new Precio();
					
					precio.setOrganizacionVenta(table.getString("VKORG")); // Organizaci�n de ventas
					precio.setCanalDistribucion(table.getString("VTWEG")); // Canal de distribuci�n
					precio.setCodigoProducto(table.getString("MATNR")); // N�mero de material 
					precio.setCondicionImporte(table.getString("KBETR")); // Importe/porcentaje de condici�n si no existe escala
					precio.setCondicionUnidad(table.getString("KONWA")); // Unidad de condici�n (Moneda o porcentaje)
					precio.setCondicionUnidadMedida(table.getString("KMEIN")); // Unidad de medida para la condici�n
					precio.setCondicionInicioValidez(table.getString("DATAB")); // Inicio de validez de la condici�n
					precio.setCondicionFinValidez(table.getString("DATBI")); // Fin de validez del registro de condici�n
															
					precios.add(precio);
				}
			}
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return precios;
	}
	
	public HashMap<String, String> getPreciosMapCRM(String ivKorg, String ivTweg, String ivSpart, String ivKunnr)
	{
		HashMap<String, String> precios = new HashMap<String, String>();
		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_PRECIOS");
								
			function.getImportParameterList().setValue("IV_VKORG", ivKorg);  // Organizaci�n de ventas
			function.getImportParameterList().setValue("IV_VTWEG", ivTweg);  // Canal de distribuci�n
			function.getImportParameterList().setValue("IV_SPART", ivSpart); // Sector
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr); // Cliente
						
			this.function.execute(destination);
						
			this.table = (JCoTable)function.getExportParameterList().getValue("ET_PRECIOS");
			
			if(this.table != null)
			{				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					precios.put(table.getString("MATNR"), table.getString("KBETR")); // N�mero de material - Importe/porcentaje de condici�n si no existe escala					
				}
			}
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return precios;
	}
	
	public String getOfertaCRM_ZRFC(Pedido pedido)
	{		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_OFERTAS");
					
			this.structure = (JCoStructure)function.getImportParameterList().getValue("IS_CABECERA"); // Estructura de entrada RFC CRM Pedidos - Cabecera
			this.table = (JCoTable)function.getImportParameterList().getValue("IT_DETALLE"); // Tipo tabla para entrada RFC CRM Pedidos - Detalle			
			
			this.structure.setValue("VKORG", pedido.getOrganizacionVenta()); // Organizaci�n de ventas
			this.structure.setValue("VTWEG", pedido.getCanalDistribucion()); // Canal de distribuci�n
			this.structure.setValue("SPART", pedido.getSector()); // Sector
			this.structure.setValue("KUNNR", pedido.getIdCliente()); // N� de cliente 1
			this.structure.setValue("KUNN2", pedido.getIdClienteInterlocutor()); // N�mero de cliente del interlocutor
			this.structure.setValue("VKGRP", pedido.getGrupoVendedores()); // Grupo de vendedores
			this.structure.setValue("WAERK", pedido.getMoneda()); // Moneda de documento comercial
			this.structure.setValue("KURSK", pedido.getTipoCambio()); // Tp.cambio p.determin.precios			
			this.structure.setValue("EDATU", pedido.getFechaReparto()); // Fecha de reparto
									
			if(pedido.getItemsPedido() != null)
			{
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{	
					this.table.appendRow();				
					
					this.table.setValue("MATNR", itemPedido.getCodigoProducto()); // N�mero de material
					this.table.setValue("MENGE", itemPedido.getCantidad()); // Cantidad prevista en unidad de medida de venta
					this.table.setValue("KWERT_PRE", itemPedido.getValorCondicionPrecio()); // Valor de la condici�n - precio
					this.table.setValue("KWERT_DES", itemPedido.getValorCondicionDescuento()); // Valor de la condici�n - descuento
					this.table.setValue("WERKS", itemPedido.getCentro()); // Centro
					this.table.setValue("LGORT", itemPedido.getAlmacen()); // Almac�n
					this.table.setValue("EDATU", itemPedido.getFechaEntregaPosicion()); // Fecha entrega a nivel posici�n
				}	
			}						
						
			this.function.getImportParameterList().setValue("IS_CABECERA", structure);
			this.function.getImportParameterList().setValue("IT_DETALLE", table);
			this.function.getImportParameterList().setValue("IV_TEXTO", pedido.getObservaciones()); // Texto longitud 1000
			
			this.function.execute(destination);						
			
			this.result =               " EV_VBELN: " + this.function.getExportParameterList().getValue("EV_VBELN").toString(); // Documento de ventas
			this.result = this.result + " EV_WAERK: " + this.function.getExportParameterList().getValue("EV_WAERK").toString(); // Moneda de documento comercial
			this.result = this.result + " EV_NETWR: " + this.function.getExportParameterList().getValue("EV_NETWR").toString(); // Valor neto del pedido en moneda del documento
			this.result = this.result + " ES_RETURN: " + this.function.getExportParameterList().getValue("ES_RETURN").toString(); // Par�metro de retorno
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public Pedido getOfertaCRM(Pedido pedido)
	{
		Pedido pedidoResult = new Pedido();
		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_OFERTAS");
			
			this.structure = (JCoStructure)function.getImportParameterList().getValue("IS_CABECERA"); // Estructura de entrada RFC CRM Pedidos - Cabecera
			this.table = (JCoTable)function.getImportParameterList().getValue("IT_DETALLE"); // Tipo tabla para entrada RFC CRM Pedidos - Detalle			
			
			this.structure.setValue("VKORG", pedido.getOrganizacionVenta()); // Organizaci�n de ventas
			this.structure.setValue("VTWEG", pedido.getCanalDistribucion()); // Canal de distribuci�n
			this.structure.setValue("SPART", pedido.getSector()); // Sector
			this.structure.setValue("KUNNR", pedido.getIdCliente()); // N� de cliente 1
			this.structure.setValue("KUNN2", pedido.getIdClienteInterlocutor()); // N�mero de cliente del interlocutor
			this.structure.setValue("VKGRP", pedido.getGrupoVendedores()); // Grupo de vendedores
			this.structure.setValue("WAERK", pedido.getMoneda()); // Moneda de documento comercial
			this.structure.setValue("KURSK", pedido.getTipoCambio()); // Tp.cambio p.determin.precios			
			this.structure.setValue("EDATU", pedido.getFechaReparto()); // Fecha de reparto
			
			if(pedido.getItemsPedido() != null)
			{
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{	
					this.table.appendRow();				
					
					this.table.setValue("MATNR", itemPedido.getCodigoProducto()); // N�mero de material
					this.table.setValue("MENGE", itemPedido.getCantidad()); // Cantidad prevista en unidad de medida de venta
					this.table.setValue("KWERT_PRE", itemPedido.getValorCondicionPrecio()); // Valor de la condici�n - precio
					this.table.setValue("KWERT_DES", itemPedido.getValorCondicionDescuento()); // Valor de la condici�n - descuento
					this.table.setValue("WERKS", itemPedido.getCentro()); // Centro
					this.table.setValue("LGORT", itemPedido.getAlmacen()); // Almac�n
					this.table.setValue("EDATU", itemPedido.getFechaEntregaPosicion()); // Fecha entrega a nivel posici�n
				}	
			}
			
			this.function.getImportParameterList().setValue("IS_CABECERA", structure);
			this.function.getImportParameterList().setValue("IT_DETALLE", table);
			this.function.getImportParameterList().setValue("IV_TEXTO", pedido.getObservaciones()); // Texto longitud 1000
			
			this.function.execute(destination);	
			
			pedidoResult.setDocumentoVentas(this.function.getExportParameterList().getValue("EV_VBELN").toString()); // Documento de ventas
			pedidoResult.setMonedaDocumentoComercial(this.function.getExportParameterList().getValue("EV_WAERK").toString()); // Moneda de documento comercial
			pedidoResult.setValorNeto(this.function.getExportParameterList().getValue("EV_NETWR").toString()); // Valor neto del pedido en moneda del documento
			
			this.structure = (JCoStructure)function.getExportParameterList().getValue("ES_RETURN"); // Par�metro de retorno
			
			if(this.structure != null)
			{
				pedidoResult.setMensajeTipo(this.structure.getString("TYPE")); // Tipo mensaje: S Success, E Error, W Warning, I Info A Abort
				pedidoResult.setMensajeClase(this.structure.getString("ID")); // Clase de mensajes
				pedidoResult.setMensajeNumero(this.structure.getString("NUMBER")); // N�mero de mensaje
				pedidoResult.setMensajeTexto(this.structure.getString("MESSAGE")); // Texto de mensaje				
				pedidoResult.setLogAplicacionNumero(this.structure.getString("LOG_NO")); // Log de aplicaci�n: N�mero de log
				pedidoResult.setLogAplicacionNumeroInterno(this.structure.getString("LOG_MSG_NO")); // Log aplicaci�n: N�mero consecutivo interno de mensaje				
				pedidoResult.setVariableMensaje1(this.structure.getString("MESSAGE_V1")); // Variable de mensaje 1
				pedidoResult.setVariableMensaje2(this.structure.getString("MESSAGE_V2")); // Variable de mensaje 2
				pedidoResult.setVariableMensaje3(this.structure.getString("MESSAGE_V3")); // Variable de mensaje 3
				pedidoResult.setVariableMensaje4(this.structure.getString("MESSAGE_V4")); // Variable de mensaje 4				
				pedidoResult.setParametro(this.structure.getString("PARAMETER")); // Par�metro
				pedidoResult.setParametroLinea(this.structure.getString("ROW")); // L�nea en el par�metro
				pedidoResult.setParametroCampo(this.structure.getString("FIELD")); // Campo en par�metro				
				pedidoResult.setSistema(this.structure.getString("SYSTEM")); // Sistema (l�gico) del que procede el mensaje
			}
		}
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return pedidoResult;
	}
	
	public String getPedidosCRM_ZRFC(String ivKunnr)
	{		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_CONSULTA_PEDIDOS");
					
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr);						
			
			this.function.execute(destination);
						
			this.result = this.function.getExportParameterList().getValue("ET_CABECERA").toString();								
			
			this.result = this.result + this.function.getExportParameterList().getValue("ET_DETALLE").toString();						
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public List<PedidoConsulta> getPedidosCRM(String ivKunnr)
	{		
		List<PedidoConsulta> pedidos = new ArrayList<PedidoConsulta>();
		List<PedidoConsultaDetalle> pedidosDetalle = new ArrayList<PedidoConsultaDetalle>();
		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_CONSULTA_PEDIDOS");
					
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr);	
						
			this.function.execute(destination);						
			
			table = (JCoTable)function.getExportParameterList().getValue("ET_DETALLE");
			
			if(table != null)
			{
				PedidoConsultaDetalle pedidoConsultaDetalle;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					pedidoConsultaDetalle = new PedidoConsultaDetalle();
																		
					pedidoConsultaDetalle.setCantidadPedido(table.getString("MENGE")); // Cantidad prevista en unidad de medida de venta
					pedidoConsultaDetalle.setDocumentoMoneda(table.getString("WAERK")); // Moneda de documento comercial
					pedidoConsultaDetalle.setDocumentoVenta(table.getString("VBELN")); // Documento de ventas
					pedidoConsultaDetalle.setDocumentoVentaPosicion(table.getString("POSNR")); // Posici�n documento ventas
                    pedidoConsultaDetalle.setNumeroMaterial(table.getString("MATNR")); // N�mero de material                   
                    pedidoConsultaDetalle.setPosicionPedidoEstado(table.getString("LFSTA_TXT")); // Status de entrega
                    pedidoConsultaDetalle.setUnidadMedida(table.getString("VRKME")); // Unidad de medida de venta
                    pedidoConsultaDetalle.setValorNeto(table.getString("NETWR")); // Valor neto de posici�n de pedido en moneda de documento
                    pedidoConsultaDetalle.setValorPrecio(table.getString("KWERT_PRE")); // Valor de la condici�n precio
                    pedidoConsultaDetalle.setValorDescuento(table.getString("KWERT_DES")); // Valor de la condici�n descuento
                    pedidoConsultaDetalle.setCentro(table.getString("WERKS")); // Centro
                    pedidoConsultaDetalle.setAlmacen(table.getString("LGORT")); // Almac�n
                    pedidoConsultaDetalle.setDescripcionMaterial(table.getString("ARKTX")); // Texto breve posici�n de pedido de cliente
                    pedidoConsultaDetalle.setDescuentoAcumuladoConcedido(table.getString("DESC")); // Descuento acumulado concedido
                    pedidoConsultaDetalle.setFechaEntregaPosicion(table.getString("EDATU")); // Fecha entrega a nivel posici�n
                    
					pedidosDetalle.add(pedidoConsultaDetalle);
				}	
			}
			
			table = (JCoTable)function.getExportParameterList().getValue("ET_CABECERA");
			
			if(table != null)
			{	
				PedidoConsulta pedidoConsulta;
				List<PedidoConsultaDetalle> detalles;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					pedidoConsulta = new PedidoConsulta();
																
					pedidoConsulta.setDocumentoFecha(table.getString("AUDAT")); // Fecha de documento (fecha de entrada o salida)
					pedidoConsulta.setDocumentoMoneda(table.getString("WAERK")); // Moneda de documento comercial
					pedidoConsulta.setDocumentoVenta(table.getString("VBELN")); // Documento de ventas					
					pedidoConsulta.setEstadoPedido(table.getString("LFGSK_TXT")); // Status de entrega total de todas las posiciones
					pedidoConsulta.setValorNeto(table.getString("NETWR")); // Valor neto del pedido en moneda del documento					
					pedidoConsulta.setOrganizacionVenta(table.getString("VKORG")); // Organizaci�n de ventas
					pedidoConsulta.setCanalDistribucion(table.getString("VTWEG")); // Canal de distribuci�n
					pedidoConsulta.setSector(table.getString("SPART")); // Sector
					pedidoConsulta.setIdCliente(table.getString("KUNNR")); // N� de cliente 1
					pedidoConsulta.setIdClienteInterlocutor(table.getString("KUNN2")); // N�mero de cliente del interlocutor
					pedidoConsulta.setGrupoVendedores(table.getString("VKGRP")); // Grupo de vendedores
					pedidoConsulta.setTipoCambio(table.getString("KURSK")); // Tp.cambio p.determin.precios
					pedidoConsulta.setFechaReparto(table.getString("EDATU")); // Fecha de reparto
					pedidoConsulta.setClaseDocumentoVentas(table.getString("AUART")); // Clase de documento de ventas
					pedidoConsulta.setEditable(table.getString("MODIF")); // Indicador: 'X' = editable, SPACE = modo de visualizaci�n
					pedidoConsulta.setDomicilioEntrega(table.getString("DEST_MERC")); // Destinatario Mercaderia: Calle y n�mero - Poblaci�n
															
					pedidos.add(pedidoConsulta);
					
					if(pedidosDetalle != null && pedidosDetalle.size() > 0)
					{
						detalles = new ArrayList<PedidoConsultaDetalle>();
						
						for(PedidoConsultaDetalle pedidoConsultaDetalle : pedidosDetalle)
						{
							if(pedidoConsultaDetalle.getDocumentoVenta().equalsIgnoreCase(pedidoConsulta.getDocumentoVenta()))
								detalles.add(pedidoConsultaDetalle);
						}
							
						pedidoConsulta.setDetalles(detalles);
					}
				}	
			}
		}									
		catch (Exception e) 
		{							
			System.out.println(e.toString());
		}																			
		
		return pedidos;	
	}
	
	public String getOfertasCRM_ZRFC(String ivKunnr)
	{		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_CONSULTA_OFERTAS");
					
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr);						
			
			this.function.execute(destination);
						
			this.result = this.function.getExportParameterList().getValue("ET_CABECERA").toString();								
			
			this.result = this.result + this.function.getExportParameterList().getValue("ET_DETALLE").toString();						
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public List<PedidoConsulta> getOfertasCRM(String ivKunnr)
	{		
		List<PedidoConsulta> pedidos = new ArrayList<PedidoConsulta>();
		List<PedidoConsultaDetalle> pedidosDetalle = new ArrayList<PedidoConsultaDetalle>();
		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_CONSULTA_OFERTAS");
					
			function.getImportParameterList().setValue("IV_KUNNR", ivKunnr);	
						
			this.function.execute(destination);						
			
			table = (JCoTable)function.getExportParameterList().getValue("ET_DETALLE");
			
			if(table != null)
			{
				PedidoConsultaDetalle pedidoConsultaDetalle;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					pedidoConsultaDetalle = new PedidoConsultaDetalle();
																
					pedidoConsultaDetalle.setCantidadPedido(table.getString("MENGE")); // Cantidad prevista en unidad de medida de venta
					pedidoConsultaDetalle.setDocumentoMoneda(table.getString("WAERK")); // Moneda de documento comercial
					pedidoConsultaDetalle.setDocumentoVenta(table.getString("VBELN")); // Documento de ventas
					pedidoConsultaDetalle.setDocumentoVentaPosicion(table.getString("POSNR")); // Posici�n documento ventas
					pedidoConsultaDetalle.setNumeroMaterial(table.getString("MATNR")); // N�mero de material                   
					pedidoConsultaDetalle.setPosicionPedidoEstado(table.getString("LFSTA_TXT")); // Status de entrega
					pedidoConsultaDetalle.setUnidadMedida(table.getString("VRKME")); // Unidad de medida de venta
					pedidoConsultaDetalle.setValorNeto(table.getString("NETWR")); // Valor neto de posici�n de pedido en moneda de documento
					pedidoConsultaDetalle.setValorPrecio(table.getString("KWERT_PRE")); // Valor de la condici�n precio
					pedidoConsultaDetalle.setValorDescuento(table.getString("KWERT_DES")); // Valor de la condici�n descuento
					pedidoConsultaDetalle.setCentro(table.getString("WERKS")); // Centro
					pedidoConsultaDetalle.setAlmacen(table.getString("LGORT")); // Almac�n
					pedidoConsultaDetalle.setDescripcionMaterial(table.getString("ARKTX")); // Texto breve posici�n de pedido de cliente
	                pedidoConsultaDetalle.setDescuentoAcumuladoConcedido(table.getString("DESC")); // Descuento acumulado concedido
	                pedidoConsultaDetalle.setFechaEntregaPosicion(table.getString("EDATU")); // Fecha entrega a nivel posici�n
					
					pedidosDetalle.add(pedidoConsultaDetalle);
				}	
			}
			
			table = (JCoTable)function.getExportParameterList().getValue("ET_CABECERA");
			
			if(table != null)
			{	
				PedidoConsulta pedidoConsulta;
				List<PedidoConsultaDetalle> detalles;
				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					pedidoConsulta = new PedidoConsulta();
																
					pedidoConsulta.setDocumentoFecha(table.getString("AUDAT")); // Fecha de documento (fecha de entrada o salida)
					pedidoConsulta.setDocumentoMoneda(table.getString("WAERK")); // Moneda de documento comercial
					pedidoConsulta.setDocumentoVenta(table.getString("VBELN")); // Documento de ventas					
					pedidoConsulta.setEstadoPedido(table.getString("LFGSK_TXT")); // Status de entrega total de todas las posiciones
					pedidoConsulta.setValorNeto(table.getString("NETWR")); // Valor neto del pedido en moneda del documento					
					pedidoConsulta.setOrganizacionVenta(table.getString("VKORG")); // Organizaci�n de ventas
					pedidoConsulta.setCanalDistribucion(table.getString("VTWEG")); // Canal de distribuci�n
					pedidoConsulta.setSector(table.getString("SPART")); // Sector
					pedidoConsulta.setIdCliente(table.getString("KUNNR")); // N� de cliente 1
					pedidoConsulta.setIdClienteInterlocutor(table.getString("KUNN2")); // N�mero de cliente del interlocutor
					pedidoConsulta.setGrupoVendedores(table.getString("VKGRP")); // Grupo de vendedores
					pedidoConsulta.setTipoCambio(table.getString("KURSK")); // Tp.cambio p.determin.precios
					pedidoConsulta.setFechaReparto(table.getString("EDATU")); // Fecha de reparto
					pedidoConsulta.setClaseDocumentoVentas(table.getString("AUART")); // Clase de documento de ventas
					pedidoConsulta.setEditable(table.getString("MODIF")); // Indicador: 'X' = editable, SPACE = modo de visualizaci�n
					pedidoConsulta.setDomicilioEntrega(table.getString("DEST_MERC")); // Destinatario Mercaderia: Calle y n�mero - Poblaci�n
															
					pedidos.add(pedidoConsulta);
					
					if(pedidosDetalle != null && pedidosDetalle.size() > 0)
					{
						detalles = new ArrayList<PedidoConsultaDetalle>();
						
						for(PedidoConsultaDetalle pedidoConsultaDetalle : pedidosDetalle)
						{
							if(pedidoConsultaDetalle.getDocumentoVenta().equalsIgnoreCase(pedidoConsulta.getDocumentoVenta()))
								detalles.add(pedidoConsultaDetalle);
						}
							
						pedidoConsulta.setDetalles(detalles);
					}
				}	
			}
		}									
		catch (Exception e) 
		{							
			System.out.println(e.toString());
		}																			
		
		return pedidos;	
	}
	
	public String getPedidoModificarCRM_ZRFC(Pedido pedido)
	{		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_PEDIDOS_MODIFICAR");
								
			this.table = (JCoTable)function.getImportParameterList().getValue("IT_DETALLE"); // Tipo tabla para entrada RFC CRM Pedidos Modificar - Detalle									
									
			if(pedido.getItemsPedido() != null)
			{				
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{					
					this.table.appendRow();				
					
					this.table.setValue("POSNR", itemPedido.getPosicion()); // Posici�n documento ventas
					this.table.setValue("MATNR", itemPedido.getCodigoProducto()); // N�mero de material
					this.table.setValue("MENGE", itemPedido.getCantidad()); // Cantidad prevista en unidad de medida de venta
					this.table.setValue("KWERT_PRE", itemPedido.getValorCondicionPrecio()); // Valor de la condici�n - precio
					this.table.setValue("KWERT_DES", itemPedido.getValorCondicionDescuento()); // Valor de la condici�n - descuento
					this.table.setValue("WERKS", itemPedido.getCentro()); // Centro
					this.table.setValue("LGORT", itemPedido.getAlmacen()); // Almac�n
					this.table.setValue("EDATU", itemPedido.getFechaEntregaPosicion()); // Fecha entrega a nivel posici�n
					this.table.setValue("LOEKZ", itemPedido.getPosicionBorrada()); // Indicador: Posici�n borrada
				}	
			}						
						
			this.function.getImportParameterList().setValue("IV_VBELN", pedido.getDocumentoVentas()); // Documento de ventas
			this.function.getImportParameterList().setValue("IT_DETALLE", table);			
			
			this.function.execute(destination);						
			
			this.result = "ES_RETURN: " + this.function.getExportParameterList().getValue("ES_RETURN").toString(); // Par�metro de retorno
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public Pedido getPedidoModificarCRM(Pedido pedido)
	{		
		Pedido pedidoResult = new Pedido();
		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_PEDIDOS_MODIFICAR");
								
			this.table = (JCoTable)function.getImportParameterList().getValue("IT_DETALLE"); // Tipo tabla para entrada RFC CRM Pedidos Modificar - Detalle									
									
			if(pedido.getItemsPedido() != null)
			{				
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{					
					this.table.appendRow();				
					
					this.table.setValue("POSNR", itemPedido.getPosicion()); // Posici�n documento ventas
					this.table.setValue("MATNR", itemPedido.getCodigoProducto()); // N�mero de material
					this.table.setValue("MENGE", itemPedido.getCantidad()); // Cantidad prevista en unidad de medida de venta
					this.table.setValue("KWERT_PRE", itemPedido.getValorCondicionPrecio()); // Valor de la condici�n - precio
					this.table.setValue("KWERT_DES", itemPedido.getValorCondicionDescuento()); // Valor de la condici�n - descuento
					this.table.setValue("WERKS", itemPedido.getCentro()); // Centro
					this.table.setValue("LGORT", itemPedido.getAlmacen()); // Almac�n
					this.table.setValue("EDATU", itemPedido.getFechaEntregaPosicion()); // Fecha entrega a nivel posici�n
					this.table.setValue("LOEKZ", itemPedido.getPosicionBorrada()); // Indicador: Posici�n borrada
				}	
			}						
						
			this.function.getImportParameterList().setValue("IV_VBELN", pedido.getDocumentoVentas()); // Documento de ventas
			this.function.getImportParameterList().setValue("IT_DETALLE", table);			
			
			this.function.execute(destination);						
						
			this.structure = (JCoStructure)function.getExportParameterList().getValue("ES_RETURN"); // Par�metro de retorno
			
			if(this.structure != null)
			{
				pedidoResult.setMensajeTipo(this.structure.getString("TYPE")); // Tipo mensaje: S Success, E Error, W Warning, I Info A Abort
				pedidoResult.setMensajeClase(this.structure.getString("ID")); // Clase de mensajes
				pedidoResult.setMensajeNumero(this.structure.getString("NUMBER")); // N�mero de mensaje
				pedidoResult.setMensajeTexto(this.structure.getString("MESSAGE")); // Texto de mensaje				
				pedidoResult.setLogAplicacionNumero(this.structure.getString("LOG_NO")); // Log de aplicaci�n: N�mero de log
				pedidoResult.setLogAplicacionNumeroInterno(this.structure.getString("LOG_MSG_NO")); // Log aplicaci�n: N�mero consecutivo interno de mensaje				
				pedidoResult.setVariableMensaje1(this.structure.getString("MESSAGE_V1")); // Variable de mensaje 1
				pedidoResult.setVariableMensaje2(this.structure.getString("MESSAGE_V2")); // Variable de mensaje 2
				pedidoResult.setVariableMensaje3(this.structure.getString("MESSAGE_V3")); // Variable de mensaje 3
				pedidoResult.setVariableMensaje4(this.structure.getString("MESSAGE_V4")); // Variable de mensaje 4				
				pedidoResult.setParametro(this.structure.getString("PARAMETER")); // Par�metro
				pedidoResult.setParametroLinea(this.structure.getString("ROW")); // L�nea en el par�metro
				pedidoResult.setParametroCampo(this.structure.getString("FIELD")); // Campo en par�metro				
				pedidoResult.setSistema(this.structure.getString("SYSTEM")); // Sistema (l�gico) del que procede el mensaje
			}
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return pedidoResult;
	}
	
	public String getOfertaModificarCRM_ZRFC(Pedido pedido)
	{		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_PEDIDOS_REF_OFERTAS");
					
			this.structure = (JCoStructure)function.getImportParameterList().getValue("IS_CABECERA"); // Estructura de entrada RFC CRM Pedidos - Cabecera
			this.table = (JCoTable)function.getImportParameterList().getValue("IT_DETALLE"); // Tipo tabla para entrada RFC CRM Pedidos - Detalle			
			
			this.structure.setValue("VKORG", pedido.getOrganizacionVenta()); // Organizaci�n de ventas
			this.structure.setValue("VTWEG", pedido.getCanalDistribucion()); // Canal de distribuci�n
			this.structure.setValue("SPART", pedido.getSector()); // Sector
			this.structure.setValue("KUNNR", pedido.getIdCliente()); // N� de cliente 1
			this.structure.setValue("KUNN2", pedido.getIdClienteInterlocutor()); // N�mero de cliente del interlocutor
			this.structure.setValue("VKGRP", pedido.getGrupoVendedores()); // Grupo de vendedores
			this.structure.setValue("WAERK", pedido.getMoneda()); // Moneda de documento comercial
			this.structure.setValue("KURSK", pedido.getTipoCambio()); // Tp.cambio p.determin.precios			
			this.structure.setValue("EDATU", pedido.getFechaReparto()); // Fecha de reparto
									
			if(pedido.getItemsPedido() != null)
			{
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{	
					this.table.appendRow();				
					
					this.table.setValue("MATNR", itemPedido.getCodigoProducto()); // N�mero de material
					this.table.setValue("MENGE", itemPedido.getCantidad()); // Cantidad prevista en unidad de medida de venta
					this.table.setValue("KWERT_PRE", itemPedido.getValorCondicionPrecio()); // Valor de la condici�n - precio
					this.table.setValue("KWERT_DES", itemPedido.getValorCondicionDescuento()); // Valor de la condici�n - descuento
					this.table.setValue("WERKS", itemPedido.getCentro()); // Centro
					this.table.setValue("LGORT", itemPedido.getAlmacen()); // Almac�n
					this.table.setValue("EDATU", itemPedido.getFechaEntregaPosicion()); // Fecha entrega a nivel posici�n
				}	
			}						
						
			this.function.getImportParameterList().setValue("IS_CABECERA", structure);
			this.function.getImportParameterList().setValue("IT_DETALLE", table);
			this.function.getImportParameterList().setValue("IV_TEXTO", pedido.getObservaciones()); // Texto longitud 1000
			this.function.getImportParameterList().setValue("IV_OFERTA", pedido.getDocumentoVentas()); // Documento de ventas Ofertas
			
			this.function.execute(destination);						
			
			this.result =               " EV_VBELN: " + this.function.getExportParameterList().getValue("EV_VBELN").toString(); // Documento de ventas
			this.result = this.result + " EV_WAERK: " + this.function.getExportParameterList().getValue("EV_WAERK").toString(); // Moneda de documento comercial
			this.result = this.result + " EV_NETWR: " + this.function.getExportParameterList().getValue("EV_NETWR").toString(); // Valor neto del pedido en moneda del documento
			this.result = this.result + " ES_RETURN: " + this.function.getExportParameterList().getValue("ES_RETURN").toString(); // Par�metro de retorno
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;
	}
	
	public Pedido getOfertaModificarCRM(Pedido pedido)
	{		
		Pedido pedidoResult = new Pedido();
		
		try
		{	
			this.function = this.destination.getRepository().getFunction("ZRFC_CRM_PEDIDOS_REF_OFERTAS");
					
			this.structure = (JCoStructure)function.getImportParameterList().getValue("IS_CABECERA"); // Estructura de entrada RFC CRM Pedidos - Cabecera
			this.table = (JCoTable)function.getImportParameterList().getValue("IT_DETALLE"); // Tipo tabla para entrada RFC CRM Pedidos - Detalle			
			
			this.structure.setValue("VKORG", pedido.getOrganizacionVenta()); // Organizaci�n de ventas
			this.structure.setValue("VTWEG", pedido.getCanalDistribucion()); // Canal de distribuci�n
			this.structure.setValue("SPART", pedido.getSector()); // Sector
			this.structure.setValue("KUNNR", pedido.getIdCliente()); // N� de cliente 1
			this.structure.setValue("KUNN2", pedido.getIdClienteInterlocutor()); // N�mero de cliente del interlocutor
			this.structure.setValue("VKGRP", pedido.getGrupoVendedores()); // Grupo de vendedores
			this.structure.setValue("WAERK", pedido.getMoneda()); // Moneda de documento comercial
			this.structure.setValue("KURSK", pedido.getTipoCambio()); // Tp.cambio p.determin.precios			
			this.structure.setValue("EDATU", pedido.getFechaReparto()); // Fecha de reparto
									
			if(pedido.getItemsPedido() != null)
			{
				for(ItemPedido itemPedido : pedido.getItemsPedido())
				{	
					this.table.appendRow();				
					
					this.table.setValue("MATNR", itemPedido.getCodigoProducto()); // N�mero de material
					this.table.setValue("MENGE", itemPedido.getCantidad()); // Cantidad prevista en unidad de medida de venta
					this.table.setValue("KWERT_PRE", itemPedido.getValorCondicionPrecio()); // Valor de la condici�n - precio
					this.table.setValue("KWERT_DES", itemPedido.getValorCondicionDescuento()); // Valor de la condici�n - descuento
					this.table.setValue("WERKS", itemPedido.getCentro()); // Centro
					this.table.setValue("LGORT", itemPedido.getAlmacen()); // Almac�n
				}	
			}						
						
			this.function.getImportParameterList().setValue("IS_CABECERA", structure);
			this.function.getImportParameterList().setValue("IT_DETALLE", table);
			this.function.getImportParameterList().setValue("IV_TEXTO", pedido.getObservaciones()); // Texto longitud 1000
			this.function.getImportParameterList().setValue("IV_OFERTA", pedido.getDocumentoVentas()); // Documento de ventas Ofertas
			
			this.function.execute(destination);						
			
			pedidoResult.setDocumentoVentas(this.function.getExportParameterList().getValue("EV_VBELN").toString()); // Documento de ventas
			pedidoResult.setMonedaDocumentoComercial(this.function.getExportParameterList().getValue("EV_WAERK").toString()); // Moneda de documento comercial
			pedidoResult.setValorNeto(this.function.getExportParameterList().getValue("EV_NETWR").toString()); // Valor neto del pedido en moneda del documento
			
			this.structure = (JCoStructure)function.getExportParameterList().getValue("ES_RETURN"); // Par�metro de retorno
			
			if(this.structure != null)
			{
				pedidoResult.setMensajeTipo(this.structure.getString("TYPE")); // Tipo mensaje: S Success, E Error, W Warning, I Info A Abort
				pedidoResult.setMensajeClase(this.structure.getString("ID")); // Clase de mensajes
				pedidoResult.setMensajeNumero(this.structure.getString("NUMBER")); // N�mero de mensaje
				pedidoResult.setMensajeTexto(this.structure.getString("MESSAGE")); // Texto de mensaje				
				pedidoResult.setLogAplicacionNumero(this.structure.getString("LOG_NO")); // Log de aplicaci�n: N�mero de log
				pedidoResult.setLogAplicacionNumeroInterno(this.structure.getString("LOG_MSG_NO")); // Log aplicaci�n: N�mero consecutivo interno de mensaje				
				pedidoResult.setVariableMensaje1(this.structure.getString("MESSAGE_V1")); // Variable de mensaje 1
				pedidoResult.setVariableMensaje2(this.structure.getString("MESSAGE_V2")); // Variable de mensaje 2
				pedidoResult.setVariableMensaje3(this.structure.getString("MESSAGE_V3")); // Variable de mensaje 3
				pedidoResult.setVariableMensaje4(this.structure.getString("MESSAGE_V4")); // Variable de mensaje 4				
				pedidoResult.setParametro(this.structure.getString("PARAMETER")); // Par�metro
				pedidoResult.setParametroLinea(this.structure.getString("ROW")); // L�nea en el par�metro
				pedidoResult.setParametroCampo(this.structure.getString("FIELD")); // Campo en par�metro				
				pedidoResult.setSistema(this.structure.getString("SYSTEM")); // Sistema (l�gico) del que procede el mensaje
			}
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return pedidoResult;
	}
	
	public String getClientesByVendedorZRFC(String ivVkgrp)
	{		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_GRUPO_VENDEDOR_CLI");
					
			function.getImportParameterList().setValue("IV_VKGRP", ivVkgrp);			
						
			this.function.execute(destination);
						
			this.result = this.function.getExportParameterList().getValue("ET_CLIENTES").toString();						
		}									
		catch (Exception e) 
		{				
			this.result = e.toString();
		}																			
		
		return this.result;	
	}
	
	public List<Cliente> getClientesByVendedor(String ivVkgrp)
	{	
		List<Cliente> clientes = new ArrayList<Cliente>();
		Cliente cliente = null;
		
		try
		{	
			function = this.destination.getRepository().getFunction("ZRFC_CRM_GRUPO_VENDEDOR_CLI");
					
			function.getImportParameterList().setValue("IV_VKGRP", ivVkgrp);			
						
			this.function.execute(destination);
			
			table = (JCoTable)function.getExportParameterList().getValue("ET_CLIENTES");

			if(table != null)
			{				
				for (int i = 0; i < table.getNumRows(); i++)
				{
					table.setRow(i);
					
					cliente = new Cliente();	
					
					cliente.setNumeroVendedor(table.getString("VKGRP")); // Grupo de vendedores
					cliente.setNumeroCliente(table.getString("KUNNR")); // N� de cliente					
					cliente.setRazonSocial(table.getString("NAME1")); // Nombre
					cliente.setDireccion(table.getString("STRAS")); // Calle y n�mero
					cliente.setLocalidad(table.getString("ORT01")); // Poblaci�n					
					
					clientes.add(cliente);
				}
			}	
		}									
		catch (Exception e) 
		{				
			e.printStackTrace();
		}																			
		
		return clientes;	
	}
}