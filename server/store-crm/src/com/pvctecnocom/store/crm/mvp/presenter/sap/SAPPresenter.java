package com.pvctecnocom.store.crm.mvp.presenter.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.AreaVenta;
import com.pvctecnocom.store.crm.mvp.model.bo.Categoria;
import com.pvctecnocom.store.crm.mvp.model.bo.CentroAlmacen;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.model.bo.Pedido;
import com.pvctecnocom.store.crm.mvp.model.bo.PedidoConsulta;
import com.pvctecnocom.store.crm.mvp.model.bo.Precio;
import com.pvctecnocom.store.crm.mvp.model.bo.Producto;
import com.pvctecnocom.store.crm.mvp.model.sap.SAPIntegration;
import com.pvctecnocom.store.crm.mvp.model.service.CategoriaService;
import com.pvctecnocom.store.crm.mvp.model.service.CentroAlmacenService;
import com.pvctecnocom.store.crm.mvp.model.service.ProductoService;
import com.vaadin.ui.UI;

public class SAPPresenter 
{	
	private CategoriaService categoriaService;
	private ProductoService productoService;
	private CentroAlmacenService centroAlmacenService;
	private SAPIntegration sapIntegration;	
	
	public SAPPresenter() 
    {		
		this.categoriaService = ((StoreCRM_UI)UI.getCurrent()).getCategoriaService(); 
		this.productoService = ((StoreCRM_UI)UI.getCurrent()).getProductoService(); 
		this.centroAlmacenService = ((StoreCRM_UI)UI.getCurrent()).getCentroAlmacenService();  
		this.sapIntegration = ((StoreCRM_UI)UI.getCurrent()).getSapIntegration();		
    } 	
	
	public Integer cargarCategorias(String iProdh, String iDown) 
	{
		Integer cantidadCategorias = 0;
		
		try
		{					
			this.categoriaService.deleteById(iProdh);
			
			List<Categoria> categorias = this.sapIntegration.getCategorias(iProdh, iDown);
			
			if(categorias != null)
			{
				for(Categoria categoria : categorias)
				{					
					this.categoriaService.insertCategoria(categoria);
					cantidadCategorias++;
				}
			}			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return cantidadCategorias;
	}
	
	public Integer cargarProductos(String iProdh, String iDown) 
	{
		Integer cantidadArticulos = 0;
		
		try
		{					
			this.productoService.deleteByCategoria(iProdh);	
			
			List<Producto> productos = this.sapIntegration.getArticulos(iProdh, iDown);
			
			if(productos != null)
			{
				for(Producto producto : productos)
				{
					this.productoService.insertProducto(producto);
					cantidadArticulos++;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return cantidadArticulos;
	}
	
	public Cliente cargarCliente(String ivKunnr)
	{
		Cliente cliente = null;
		
		try
		{								
			cliente = this.sapIntegration.getClienteDatos(ivKunnr);						
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return cliente;
	}
	
	public List<PedidoConsulta> cargarPedidos(String ivKunnr)
	{
		List<PedidoConsulta> pedidosConsulta = null;
		
		try
		{								
			pedidosConsulta = this.sapIntegration.getPedidosCRM(ivKunnr);						
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return pedidosConsulta;
	}
	
	public List<PedidoConsulta> cargarOfertas(String ivKunnr)
	{
		List<PedidoConsulta> pedidosConsulta = null;
		
		try
		{								
			pedidosConsulta = this.sapIntegration.getOfertasCRM(ivKunnr);						
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return pedidosConsulta;
	}		
	
	public List<AreaVenta> cargarAreasVenta(String iKunnr , String iSpart)
	{
		List<AreaVenta> areasVenta = new ArrayList<AreaVenta>();
		
		try
		{								
			areasVenta = this.sapIntegration.getAreasVenta(iKunnr, iSpart);					
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return areasVenta;
	}
	
	public Integer cargarCentrosAlmacenes() 
	{
		Integer cantidadCentroAlmacenes = 0;
		
		try
		{					
			this.centroAlmacenService.deleteAll();	
			
			List<CentroAlmacen> centrosAlmacenes = this.sapIntegration.getCentrosAlmacenes();
			
			if(centrosAlmacenes != null)
			{
				for(CentroAlmacen centroAlmacen : centrosAlmacenes)
				{
					this.centroAlmacenService.insertCentroAlmacen(centroAlmacen);
					cantidadCentroAlmacenes++;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return cantidadCentroAlmacenes;
	}
	
	public Pedido cargarPedido(Pedido pedido)
	{
		Pedido pedidoResult = null;
		
		try
		{								
			pedidoResult = this.sapIntegration.getPedidoCRM(pedido);							
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return pedidoResult;
	}
	
	public Pedido cargarOferta(Pedido pedido)
	{
		Pedido pedidoResult = null;
		
		try
		{								
			pedidoResult = this.sapIntegration.getOfertaCRM(pedido);							
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return pedidoResult;
	}
	
	public List<HashMap<String, String>> cargarDescuentosMap(String ivKunnr, String ivKorg, String ivTweg, String ivSpart)
	{
		List<HashMap<String, String>> descuentosResult = null;
		
		try
		{								
			descuentosResult = this.sapIntegration.getDescuentosMapCRM(ivKunnr, ivKorg, ivTweg, ivSpart);							
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return descuentosResult;
	}
	
	public List<Precio> cargarPrecios(String ivKorg, String ivTweg, String ivSpart, String ivKunnr)
	{
		List<Precio> preciosResult = null;
		
		try
		{								
			preciosResult = this.sapIntegration.getPreciosCRM(ivKorg, ivTweg, ivSpart, ivKunnr);							
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return preciosResult;
	}
	
	public HashMap<String, String> cargarPreciosMap(String ivKorg, String ivTweg, String ivSpart, String ivKunnr)
	{
		HashMap<String, String> preciosResult = null;
		
		try
		{								
			preciosResult = this.sapIntegration.getPreciosMapCRM(ivKorg, ivTweg, ivSpart, ivKunnr);							
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return preciosResult;
	}
	
	public Pedido modificarPedido(Pedido pedido)
	{
		Pedido pedidoResult = null;
		
		try
		{								
			pedidoResult = this.sapIntegration.getPedidoModificarCRM(pedido);							
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return pedidoResult;
	}
	
	public Pedido modificarOferta(Pedido pedido)
	{
		Pedido pedidoResult = null;
		
		try
		{								
			pedidoResult = this.sapIntegration.getOfertaModificarCRM(pedido);								
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return pedidoResult;
	}
	
	public List<Cliente> getClientesByVendedor(String numeroVendedorCRM)
	{
		List<Cliente> clientes = null;
		
		try
		{								
			clientes = this.sapIntegration.getClientesByVendedor(numeroVendedorCRM);								
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}				
		
		return clientes;
	}
}