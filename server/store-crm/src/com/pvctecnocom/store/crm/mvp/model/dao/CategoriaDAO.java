package com.pvctecnocom.store.crm.mvp.model.dao;

import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.Categoria;

public interface CategoriaDAO extends GenericDAO<Categoria,String> 
{	
	public List<Categoria> findCategoriasByCategoriaSuperior(String id);
		
	public void deleteAll();
	
	public void deleteById(String id);
}