package com.pvctecnocom.store.crm.mvp.model.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pvctecnocom.store.crm.mvp.model.bo.Categoria;
import com.pvctecnocom.store.crm.mvp.model.dao.CategoriaDAO;
import com.pvctecnocom.store.crm.mvp.model.service.CategoriaService;

@Service(value="categoriaService")
public class CategoriaServiceImpl implements CategoriaService 
{
	private CategoriaDAO categoriaDAO = null;
	
	public CategoriaDAO getCategoriaDAO() 
	{
		return this.categoriaDAO;
	}
	
	@Resource
	public void setCategoriaDAO(CategoriaDAO categoriaDAO) 
	{
		this.categoriaDAO = categoriaDAO;
	}	
	
	@Override
	public List<Categoria> findAll() 
	{		
		return categoriaDAO.findAll();
	}
	
	@Override
	public void insertCategoria(Categoria categoria) 
	{		
		categoriaDAO.insert(categoria);		
	}
	
	@Override
	public void updateCategoria(Categoria categoria) 
	{	
		categoriaDAO.update(categoria);
	}

	@Override
	public void deleteCategoria(Categoria categoria) 
	{
		categoriaDAO.delete(categoria);	
	}
	
	@Override
	public Categoria findById(String id) 
	{		
		return categoriaDAO.findById(id);
	}

	@Override
	public List<Categoria> findCategoriasByCategoriaSuperior(String id) 
	{		
		return categoriaDAO.findCategoriasByCategoriaSuperior(id);
	}

	@Override
	public void deleteAll() 
	{
		categoriaDAO.deleteAll();		
	}

	@Override
	public void deleteById(String id) 
	{	
		categoriaDAO.deleteById(id);
	}			
}