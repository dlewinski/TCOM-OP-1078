package com.pvctecnocom.store.crm.mvp.model.service;

import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.Categoria;

public interface CategoriaService 
{	
	public List<Categoria> findAll();
			
	public Categoria findById(String id);
	
	public List<Categoria> findCategoriasByCategoriaSuperior(String id);
	
	public void insertCategoria(Categoria categoria);
	
	public void updateCategoria(Categoria categoria);
	
	public void deleteCategoria(Categoria categoria);	
	
	public void deleteAll();
	
	public void deleteById(String id);
}