package com.pvctecnocom.store.crm.mvp.model.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pvctecnocom.store.crm.mvp.model.bo.Producto;
import com.pvctecnocom.store.crm.mvp.model.dao.ProductoDAO;
import com.pvctecnocom.store.crm.mvp.model.service.ProductoService;

@Service(value="productoService")
public class ProductoServiceImpl implements ProductoService 
{
	private ProductoDAO productoDAO = null;
	
	public ProductoDAO getProductoDAO() 
	{
		return this.productoDAO;
	}
	
	@Resource
	public void setProductoDAO(ProductoDAO productoDAO) 
	{
		this.productoDAO = productoDAO;
	}	
	
	@Override
	public List<Producto> findAll() 
	{		
		return productoDAO.findAll();
	}
	
	@Override
	public void insertProducto(Producto producto) 
	{		
		productoDAO.insert(producto);		
	}
	
	@Override
	public void updateProducto(Producto producto) 
	{	
		productoDAO.update(producto);
	}

	@Override
	public void deleteProducto(Producto producto) 
	{
		productoDAO.delete(producto);	
	}
	
	@Override
	public Producto findById(String id) 
	{		
		return productoDAO.findById(id);
	}
	
	@Override
	public List<Producto> findProductosByCodigoDescripcion(String codigo)
	{		
		return productoDAO.findProductosByCodigoDescripcion(codigo);
	}

	@Override
	public List<Producto> findProductosByCategoria(String idCategoria) 
	{		
		return productoDAO.findProductosByCategoria(idCategoria);
	}

	@Override
	public List<Producto> findProductosByIds(Collection<String> ids) 
	{		
		return productoDAO.findProductosByIds(ids);
	}

	@Override
	public void deleteAll() 
	{
		productoDAO.deleteAll();
	}			
	
	@Override
	public void deleteByCategoria(String categoria) 
	{	
		productoDAO.deleteByCategoria(categoria);
	}	
}