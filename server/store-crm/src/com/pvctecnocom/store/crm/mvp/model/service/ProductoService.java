package com.pvctecnocom.store.crm.mvp.model.service;

import java.util.Collection;
import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.Producto;

public interface ProductoService 
{	
	public List<Producto> findAll();
	
	public List<Producto> findProductosByCodigoDescripcion(String codigo);
	
	public List<Producto> findProductosByCategoria(String idCategoria);
	
	public List<Producto> findProductosByIds(Collection<String> ids);
	
	public Producto findById(String id);				
	
	public void insertProducto(Producto producto);
	
	public void updateProducto(Producto producto);
	
	public void deleteProducto(Producto producto);	 
	
	public void deleteAll();
	
	public void deleteByCategoria(String categoria);
}