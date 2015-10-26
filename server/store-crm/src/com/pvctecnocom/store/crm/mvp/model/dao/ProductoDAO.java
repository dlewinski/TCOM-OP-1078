package com.pvctecnocom.store.crm.mvp.model.dao;

import java.util.Collection;
import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.Producto;

public interface ProductoDAO extends GenericDAO<Producto,String> 
{
	public List<Producto> findProductosByCodigoDescripcion(String idCodigo);
	
	public List<Producto> findProductosByCategoria(String idCategoria);
	
	public List<Producto> findProductosByIds(Collection<String> ids);
	
	public void deleteAll();
	
	public void deleteByCategoria(String categoria);
}