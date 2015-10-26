package sap;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.pvctecnocom.store.crm.mvp.model.sap.SAPIntegration;

public class Test 
{
	public static void main(String[] args) 
	{
		DecimalFormat decimalFormat = new DecimalFormat("#.00"); 
		
		try 
		{
			String destinationName = "PVC_TECNOCOM_CRM_DEV";
			
			String iProdh = "0MA0MA";
			String iDown = "X";
			String ivKunnr = "20064";
			String iPwd = "";					
			String ivKorg = "1100";
			String ivTweg = "02";
			String ivSpart = "PE";
			String ivVkgrp = "037"; // 088
			
			List<Categoria> categorias;
			List<Producto> productos;
			Cliente cliente;
			Pedido pedido;
			List<ItemPedido> itemsPedido;
			ItemPedido itemPedido;
			Pedido pedidoResult;
			List<PedidoConsulta> pedidos;										
			List<AreaVenta> areasVenta;
			List<CentroAlmacen> centrosAlmacenes;
			List<Descuento> descuentos;
			List<Precio> precios;
			List<Cliente> clientesVendedor;
			
			SAPIntegration sapIntegration = new SAPIntegration(destinationName);
			
			System.out.println(sapIntegration.connectUsingPool());
			
			System.out.println(sapIntegration.getCategoriasZRFC(iProdh, iDown));
			
			categorias = sapIntegration.getCategorias(iProdh, iDown);
			
			if(categorias != null)
			{
				for(Categoria categoria : categorias)
				{
					System.out.println("IdCategoria: " + categoria.getIdCategoria());
					System.out.println("NombreCorto: " + categoria.getNombreCorto());
					System.out.println("IdCategoriaSuperior: " + categoria.getIdCategoriaSuperior());
					System.out.println("Orden: " + categoria.getOrden());
				}
			}
			
			System.out.println(sapIntegration.getArticulosZRFC(iProdh, iDown));
			
			productos = sapIntegration.getArticulos(iProdh, iDown);
			
			if(productos != null)
			{
				for(Producto producto : productos)
				{					
					System.out.println("Codigo: " + producto.getCodigo());
					System.out.println("Descripcion: " + producto.getDescripcion());
					System.out.println("IdCategoria: " + producto.getIdCategoria());					
					System.out.println("Grupo: " + producto.getGrupo());
					System.out.println("NumeroEuropeo: " + producto.getNumeroEuropeo());
					System.out.println("PesoBruto: " + producto.getPesoBruto());
					System.out.println("Sector: " + producto.getSector());
					System.out.println("UnidadMedida: " + producto.getUnidadMedida());
					System.out.println("UnidadPeso: " + producto.getUnidadPeso());
				}
			}
			
			System.out.println(sapIntegration.getClienteZRFC(ivKunnr, iPwd));
			
			cliente = sapIntegration.getCliente(ivKunnr, iPwd);
			
			if(cliente != null)
			{									
				System.out.println("Direccion: " + cliente.getDireccion());
				System.out.println("Email: " + cliente.getEmail());
				System.out.println("Localidad: " + cliente.getLocalidad());				
				System.out.println("RazonSocial: " + cliente.getRazonSocial());								
				
				if(cliente.getDestinatarios() != null)
				{
					for(Destinatario destinatario : cliente.getDestinatarios())
					{
						System.out.println("Cliente: " + destinatario.getCliente());
						System.out.println("Calle: " + destinatario.getCalle());
						System.out.println("Poblacion: " + destinatario.getPoblacion());
					}
				}
			}
		
			System.out.println(sapIntegration.getClienteDatosZRFC(ivKunnr));
			
			cliente = sapIntegration.getClienteDatos(ivKunnr);
			
			if(cliente != null)
			{	
				System.out.println("RazonSocial: " + cliente.getRazonSocial());	
				System.out.println("Direccion: " + cliente.getDireccion());
				System.out.println("Localidad: " + cliente.getLocalidad());		
				System.out.println("Email: " + cliente.getEmail());																																				
				
				if(cliente.getAreasVenta() != null)
				{
					for(AreaVenta areaVenta : cliente.getAreasVenta())
					{
						System.out.println("OrganizacionVentas: " + areaVenta.getOrganizacionVentas());
						System.out.println("CanalDistribucion: " + areaVenta.getCanalDistribucion());						
						System.out.println("Sector: " + areaVenta.getSector());						
						System.out.println("PrecioEditable: " + areaVenta.getPrecioEditable());
						System.out.println("DescuentoEditable: " + areaVenta.getDescuentoEditable());
						System.out.println("CantidadLineasDefault: " + areaVenta.getCantidadLineasDefault());
						System.out.println("TipoCotizacion: " + areaVenta.getTipoCotizacion());
						System.out.println("TipoCambio: " + decimalFormat.format(areaVenta.getTipoCambio()));
						System.out.println("CantidadDias: " + areaVenta.getCantidadDias());
					}
				}
				
				if(cliente.getDestinatarios() != null)
				{
					for(Destinatario destinatario : cliente.getDestinatarios())
					{
						System.out.println("Cliente: " + destinatario.getCliente());
						System.out.println("Calle: " + destinatario.getCalle());
						System.out.println("Poblacion: " + destinatario.getPoblacion());						
						System.out.println("OrganizacionVentas: " + destinatario.getOrganizacionVentas());
						System.out.println("CanalDistribucion: " + destinatario.getCanalDistribucion());
						System.out.println("Sector: " + destinatario.getSector());
					}
				}
			}
			
			pedido = new Pedido();
			
			pedido.setCanalDistribucion("01");
			pedido.setOrganizacionVenta("2100");
			pedido.setSector("MA");
			pedido.setIdCliente("20064");
			pedido.setIdDestinatario("20064");
			
			itemsPedido = new ArrayList<ItemPedido>();
			
			itemPedido = new ItemPedido();
			itemPedido.setCodigoProducto("TP3P 10+50");
			itemPedido.setCantidad("3");
			
			itemsPedido.add(itemPedido);
			
			pedido.setItemsPedido(itemsPedido);
			
			System.out.println(sapIntegration.getPedidoZRFC(pedido));
			
			pedidoResult = sapIntegration.getPedido(pedido);
			
			if(pedidoResult != null)
			{
				System.out.println("CanalDistribucion: " + pedidoResult.getCanalDistribucion());
				System.out.println("Descripcion: " + pedidoResult.getDescripcion());
				System.out.println("IdCliente: " + pedidoResult.getIdCliente());
				System.out.println("IdDestinatario: " + pedidoResult.getIdDestinatario());
				System.out.println("Importe: " + pedidoResult.getImporte());
				System.out.println("Moneda: " + pedidoResult.getMoneda());
				System.out.println("NumeroPedido: " + pedidoResult.getNumeroPedido());
				System.out.println("OrganizacionVenta: " + pedidoResult.getOrganizacionVenta());
				System.out.println("Sector: " + pedidoResult.getSector());
			}																
				
			System.out.println(sapIntegration.getAreasVentaZRFC(ivKunnr, ivSpart));
			
			areasVenta = sapIntegration.getAreasVenta(ivKunnr, ivSpart);
			
			if(areasVenta != null)
			{
				for(AreaVenta areaVenta : areasVenta)
				{
					System.out.println("OrganizacionVentas: " + areaVenta.getOrganizacionVentas());
					System.out.println("OrganizacionVentasDenominacion: " + areaVenta.getOrganizacionVentasDenominacion());
					System.out.println("CanalDistribucion: " + areaVenta.getCanalDistribucion());
					System.out.println("CanalDistribucionDenominacion: " + areaVenta.getCanalDistribucionDenominacion());
					System.out.println("Sector: " + areaVenta.getSector());
					System.out.println("SectorDenominacion: " + areaVenta.getSectorDenominacion());
				}
			}						
			
			System.out.println(sapIntegration.getCentrosAlmacenesZRFC());	
		
			centrosAlmacenes = sapIntegration.getCentrosAlmacenes();
			
			if(centrosAlmacenes != null)
			{
				for(CentroAlmacen centrosAlmacen : centrosAlmacenes)
				{
					System.out.println("IdCentroAlmacen: " + centrosAlmacen.getIdCentroAlmacen());
					System.out.println("OrganizacionVentas: " + centrosAlmacen.getOrganizacionVentas());
					System.out.println("OrganizacionVentasDenominacion: " + centrosAlmacen.getOrganizacionVentasDenominacion());
					System.out.println("Centro: " + centrosAlmacen.getCentro());
					System.out.println("Nombre: " + centrosAlmacen.getNombre());
					System.out.println("Almacen: " + centrosAlmacen.getAlmacen());
					System.out.println("AlmacenDenominacion: " + centrosAlmacen.getAlmacenDenominacion());
				}
			}			
			
			pedido = new Pedido();
			
			pedido.setOrganizacionVenta("2000");
			pedido.setCanalDistribucion("01");			
			pedido.setSector("MA");			
			pedido.setIdCliente("21");
			pedido.setIdClienteInterlocutor("");
			pedido.setGrupoVendedores("008");
			pedido.setMoneda("USD");
			pedido.setTipoCambio("");
			pedido.setFechaReparto("");
			pedido.setObservaciones("Texto de prueba campo observaciones");
			
			itemsPedido = new ArrayList<ItemPedido>();
			
			itemPedido = new ItemPedido();
			itemPedido.setCodigoProducto("TP3P 10+50");
			itemPedido.setCantidad("3");
			itemPedido.setValorCondicionPrecio("35");
			itemPedido.setValorCondicionDescuento("7.8");
			itemPedido.setCentro("2000");
			itemPedido.setAlmacen("2006");
			
			itemsPedido.add(itemPedido);
			
			pedido.setItemsPedido(itemsPedido);
			
			System.out.println(sapIntegration.getPedidoCRM_ZRFC(pedido));	
			
			pedidoResult = sapIntegration.getPedidoCRM(pedido);
			
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
			}			
			
			pedido = new Pedido();
			
			pedido.setOrganizacionVenta("1200");
			pedido.setCanalDistribucion("02");			
			pedido.setSector("PE");			
			pedido.setIdCliente("25931");
			pedido.setIdClienteInterlocutor("");
			pedido.setGrupoVendedores("082");
			pedido.setMoneda("USD");
			pedido.setTipoCambio("");
			pedido.setFechaReparto("");
			pedido.setObservaciones("Texto de prueba campo observaciones");
			
			itemsPedido = new ArrayList<ItemPedido>();
			
			itemPedido = new ItemPedido();
			itemPedido.setCodigoProducto("ACH004");
			itemPedido.setCantidad("3");
			itemPedido.setValorCondicionPrecio("35");
			itemPedido.setValorCondicionDescuento("7.8");
			itemPedido.setCentro("1100");
			itemPedido.setAlmacen("1106");
			
			itemsPedido.add(itemPedido);
			
			pedido.setItemsPedido(itemsPedido);
			
			System.out.println(sapIntegration.getOfertaCRM_ZRFC(pedido));	
			
			pedidoResult = sapIntegration.getOfertaCRM(pedido);
			
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
			}

			System.out.println(sapIntegration.getDescuentosCRM_ZRFC(ivKunnr, ivKorg, ivTweg, ivSpart));
			
			descuentos = sapIntegration.getDescuentosCRM(ivKunnr, ivKorg, ivTweg, ivSpart);
			
			if(descuentos != null)
			{
				for(Descuento descuento : descuentos)
				{
					System.out.println("Grupo: " + descuento.getGrupo());
					System.out.println("GrupoDescuento: " + descuento.getGrupoDescuento());
					System.out.println("Material: " + descuento.getMaterial());
					System.out.println("MaterialDescuento: " + descuento.getMaterialDescuento());
				}
			}
			
			System.out.println(sapIntegration.getPreciosCRM_ZRFC(ivKorg, ivTweg, ivSpart, ivKunnr));
			
			precios = sapIntegration.getPreciosCRM(ivKorg, ivTweg, ivSpart, ivKunnr);
			
			if(precios != null)
			{
				for(Precio precio : precios)
				{
					System.out.println("CanalDistribucion: " + precio.getCanalDistribucion());
					System.out.println("CodigoProducto: " + precio.getCodigoProducto());
					System.out.println("CondicionFinValidez: " + precio.getCondicionFinValidez());
					System.out.println("CondicionImporte: " + precio.getCondicionImporte());
					System.out.println("CondicionInicioValidez: " + precio.getCondicionInicioValidez());
					System.out.println("CondicionUnidad: " + precio.getCondicionUnidad());
					System.out.println("CondicionUnidadMedida: " + precio.getCondicionUnidadMedida());
					System.out.println("OrganizacionVenta: " + precio.getOrganizacionVenta());
				}
			}
			
			System.out.println(sapIntegration.getPedidosCRM_ZRFC(ivKunnr));	
			
			pedidos = sapIntegration.getPedidosCRM(ivKunnr);
			
			if(pedidos != null)
			{
				for(PedidoConsulta pedidoConsulta : pedidos)
				{
					System.out.println("DocumentoFecha: " + pedidoConsulta.getDocumentoFecha());					
					System.out.println("DocumentoMoneda: " + pedidoConsulta.getDocumentoMoneda());
					System.out.println("DocumentoVenta: " + pedidoConsulta.getDocumentoVenta());
					System.out.println("DomicilioEntrega: " + pedidoConsulta.getDomicilioEntrega());
					System.out.println("EstadoPedido: " + pedidoConsulta.getEstadoPedido());
					System.out.println("ValorNeto: " + pedidoConsulta.getValorNeto());					
					System.out.println("OrganizacionVenta: " + pedidoConsulta.getOrganizacionVenta());
					System.out.println("CanalDistribucion: " + pedidoConsulta.getCanalDistribucion());
					System.out.println("Sector: " + pedidoConsulta.getSector());
					System.out.println("IdCliente: " + pedidoConsulta.getIdCliente());
					System.out.println("IdClienteInterlocutor: " + pedidoConsulta.getIdClienteInterlocutor());
					System.out.println("GrupoVendedores: " + pedidoConsulta.getGrupoVendedores());
					System.out.println("TipoCambio: " + pedidoConsulta.getTipoCambio());
					System.out.println("FechaReparto: " + pedidoConsulta.getFechaReparto());
					System.out.println("ClaseDocumentoVentas: " + pedidoConsulta.getClaseDocumentoVentas());
					System.out.println("Editable: " + pedidoConsulta.getEditable());
					
					if(pedidoConsulta.getDetalles() != null && pedidoConsulta.getDetalles().size() > 0)
					{
						for(PedidoConsultaDetalle pedidoConsultaDetalle : pedidoConsulta.getDetalles())
						{
							System.out.println("CantidadPedido: " + pedidoConsultaDetalle.getCantidadPedido());
							System.out.println("DocumentoMoneda: " + pedidoConsultaDetalle.getDocumentoMoneda());
							System.out.println("DocumentoVenta: " + pedidoConsultaDetalle.getDocumentoVenta());
							System.out.println("DocumentoVentaPosicion: " + pedidoConsultaDetalle.getDocumentoVentaPosicion());
							System.out.println("NumeroMaterial: " + pedidoConsultaDetalle.getNumeroMaterial());
							System.out.println("PosicionPedido: " + pedidoConsultaDetalle.getPosicionPedido());
							System.out.println("PosicionPedidoEstado: " + pedidoConsultaDetalle.getPosicionPedidoEstado());
							System.out.println("UnidadMedida: " + pedidoConsultaDetalle.getUnidadMedida());
							System.out.println("ValorNeto: " + pedidoConsultaDetalle.getValorNeto());	
							System.out.println("ValorPrecio: " + pedidoConsultaDetalle.getValorPrecio());
							System.out.println("ValorDescuento: " + pedidoConsultaDetalle.getValorDescuento());
							System.out.println("Centro: " + pedidoConsultaDetalle.getCentro());
							System.out.println("Almacen: " + pedidoConsultaDetalle.getAlmacen());
							System.out.println("DescripcionMaterial: " + pedidoConsultaDetalle.getDescripcionMaterial());
							System.out.println("DescuentoAcumuladoConcedido: " + pedidoConsultaDetalle.getDescuentoAcumuladoConcedido());
							System.out.println("FechaEntregaPosicion: " + pedidoConsultaDetalle.getFechaEntregaPosicion());
						}
					}
				}
			}
			
			System.out.println(sapIntegration.getOfertasCRM_ZRFC(ivKunnr));
			
			pedidos = sapIntegration.getOfertasCRM(ivKunnr);
			
			if(pedidos != null)
			{
				for(PedidoConsulta pedidoConsulta : pedidos)
				{
					System.out.println("DocumentoFecha: " + pedidoConsulta.getDocumentoFecha());					
					System.out.println("DocumentoMoneda: " + pedidoConsulta.getDocumentoMoneda());
					System.out.println("DocumentoVenta: " + pedidoConsulta.getDocumentoVenta());
					System.out.println("DomicilioEntrega: " + pedidoConsulta.getDomicilioEntrega());
					System.out.println("EstadoPedido: " + pedidoConsulta.getEstadoPedido());
					System.out.println("ValorNeto: " + pedidoConsulta.getValorNeto());					
					System.out.println("OrganizacionVenta: " + pedidoConsulta.getOrganizacionVenta());
					System.out.println("CanalDistribucion: " + pedidoConsulta.getCanalDistribucion());
					System.out.println("Sector: " + pedidoConsulta.getSector());
					System.out.println("IdCliente: " + pedidoConsulta.getIdCliente());
					System.out.println("IdClienteInterlocutor: " + pedidoConsulta.getIdClienteInterlocutor());
					System.out.println("GrupoVendedores: " + pedidoConsulta.getGrupoVendedores());
					System.out.println("TipoCambio: " + pedidoConsulta.getTipoCambio());
					System.out.println("FechaReparto: " + pedidoConsulta.getFechaReparto());
					System.out.println("ClaseDocumentoVentas: " + pedidoConsulta.getClaseDocumentoVentas());
					System.out.println("Editable: " + pedidoConsulta.getEditable());
					
					if(pedidoConsulta.getDetalles() != null && pedidoConsulta.getDetalles().size() > 0)
					{
						for(PedidoConsultaDetalle pedidoConsultaDetalle : pedidoConsulta.getDetalles())
						{
							System.out.println("CantidadPedido: " + pedidoConsultaDetalle.getCantidadPedido());
							System.out.println("DocumentoMoneda: " + pedidoConsultaDetalle.getDocumentoMoneda());
							System.out.println("DocumentoVenta: " + pedidoConsultaDetalle.getDocumentoVenta());
							System.out.println("DocumentoVentaPosicion: " + pedidoConsultaDetalle.getDocumentoVentaPosicion());
							System.out.println("NumeroMaterial: " + pedidoConsultaDetalle.getNumeroMaterial());
							System.out.println("PosicionPedido: " + pedidoConsultaDetalle.getPosicionPedido());
							System.out.println("PosicionPedidoEstado: " + pedidoConsultaDetalle.getPosicionPedidoEstado());
							System.out.println("UnidadMedida: " + pedidoConsultaDetalle.getUnidadMedida());
							System.out.println("ValorNeto: " + pedidoConsultaDetalle.getValorNeto());	
							System.out.println("ValorPrecio: " + pedidoConsultaDetalle.getValorPrecio());
							System.out.println("ValorDescuento: " + pedidoConsultaDetalle.getValorDescuento());
							System.out.println("Centro: " + pedidoConsultaDetalle.getCentro());
							System.out.println("Almacen: " + pedidoConsultaDetalle.getAlmacen());
							System.out.println("DescripcionMaterial: " + pedidoConsultaDetalle.getDescripcionMaterial());
							System.out.println("DescuentoAcumuladoConcedido: " + pedidoConsultaDetalle.getDescuentoAcumuladoConcedido());
							System.out.println("FechaEntregaPosicion: " + pedidoConsultaDetalle.getFechaEntregaPosicion());
						}
					}
				}
			}
						
			pedido = new Pedido();
			
			pedido.setDocumentoVentas("0000000611");		
			pedido.setFechaReparto("");			
			
			itemsPedido = new ArrayList<ItemPedido>();
			
			itemPedido = new ItemPedido();
			itemPedido.setCentro("2000");
			itemPedido.setAlmacen("2005");
			itemPedido.setPosicion("000010");
			itemPedido.setCodigoProducto("TP3P 10+50");			
			itemPedido.setValorCondicionPrecio("28.68");
			itemPedido.setValorCondicionDescuento("3.00");			
			itemPedido.setCantidad("1");
			itemPedido.setFechaEntregaPosicion("");
						
			itemsPedido.add(itemPedido);
			
			itemPedido = new ItemPedido();
			itemPedido.setCentro("2000");
			itemPedido.setAlmacen("2005");
			itemPedido.setPosicion("000020");
			itemPedido.setCodigoProducto("TP3P 10+50");			
			itemPedido.setValorCondicionPrecio("28.68");
			itemPedido.setValorCondicionDescuento("3.00");			
			itemPedido.setCantidad("2");
			itemPedido.setFechaEntregaPosicion("");
						
			itemsPedido.add(itemPedido);
			
			itemPedido = new ItemPedido();
			itemPedido.setCentro("2000");
			itemPedido.setAlmacen("2005");
			itemPedido.setPosicion("000030");
			itemPedido.setCodigoProducto("TP3P 10+50");			
			itemPedido.setValorCondicionPrecio("28.68");
			itemPedido.setValorCondicionDescuento("3.00");			
			itemPedido.setCantidad("3");
			itemPedido.setFechaEntregaPosicion("");
						
			itemsPedido.add(itemPedido);
			
			pedido.setItemsPedido(itemsPedido);
			
			System.out.println(sapIntegration.getPedidoModificarCRM_ZRFC(pedido));
			
			pedidoResult = sapIntegration.getPedidoModificarCRM(pedido);
			
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
			}
		
			pedido = new Pedido();
			
			pedido.setDocumentoVentas("0020000041");											
			pedido.setOrganizacionVenta("1200");
			pedido.setCanalDistribucion("02");			
			pedido.setSector("PE");			
			pedido.setIdCliente("25931");
			pedido.setIdClienteInterlocutor("");
			pedido.setGrupoVendedores("082");
			pedido.setMoneda("USD");
			pedido.setTipoCambio("");
			pedido.setFechaReparto("");
			
			itemsPedido = new ArrayList<ItemPedido>();
			
			itemPedido = new ItemPedido();
			itemPedido.setCentro("1100");
			itemPedido.setAlmacen("1106");
			itemPedido.setPosicion("000010");
			itemPedido.setCodigoProducto("ACH004");			
			itemPedido.setValorCondicionPrecio("11.00");
			itemPedido.setValorCondicionDescuento("2");			
			itemPedido.setCantidad("3");
			itemPedido.setFechaEntregaPosicion("");
			
			System.out.println(sapIntegration.getOfertaModificarCRM_ZRFC(pedido));
			
			pedidoResult = sapIntegration.getOfertaModificarCRM(pedido);
					
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
			}			

			System.out.println(sapIntegration.getClientesByVendedorZRFC(ivVkgrp));
			
			clientesVendedor = sapIntegration.getClientesByVendedor(ivVkgrp);
			
			if(clientesVendedor != null && clientesVendedor.size() > 0)
			{	
				for(Cliente clienteVendedor : clientesVendedor)
				{
					if(clienteVendedor != null)
					{	
						System.out.println("NumeroVendedor: " + clienteVendedor.getNumeroVendedor());
						System.out.println("NumeroCliente: " + clienteVendedor.getNumeroCliente());
						System.out.println("Direccion: " + clienteVendedor.getDireccion());
						System.out.println("RazonSocial: " + clienteVendedor.getRazonSocial());	
						System.out.println("Direccion: " + clienteVendedor.getDireccion());
						System.out.println("Localidad: " + clienteVendedor.getLocalidad());								
					}
				}								
			}						
		}
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
		
		System.out.println("Test SAP end");				
	}	
}