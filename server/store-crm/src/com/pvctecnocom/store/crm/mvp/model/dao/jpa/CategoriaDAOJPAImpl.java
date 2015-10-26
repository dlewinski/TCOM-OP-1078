package com.pvctecnocom.store.crm.mvp.model.dao.jpa;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvctecnocom.store.crm.mvp.model.bo.Categoria;
import com.pvctecnocom.store.crm.mvp.model.dao.CategoriaDAO;

@Repository
public class CategoriaDAOJPAImpl extends GenericDAOJPAImpl<Categoria, String> implements CategoriaDAO 
{
	@Override
	public List<Categoria> findCategoriasByCategoriaSuperior(String id) 
	{
		List<Categoria> categoriasResult = null;
		String sql = null;
		TypedQuery<Categoria> query = null;
		
		try
		{		
			if(id == null)
			{	
				sql = " SELECT c " +
					  "   FROM Categoria c " +
					  "  WHERE c.idCategoriaSuperior IS NULL " +
				      "  ORDER BY c.orden ";					     
				
				query = getManager().createQuery(sql, Categoria.class);											
			}
			else
			{
				sql = " SELECT c " +
					  "   FROM Categoria c " +
					  "  WHERE c.idCategoriaSuperior = ? " +
					  "  ORDER BY c.orden ";					     
			
				query = getManager().createQuery(sql, Categoria.class);	
				query.setParameter(1, id);
			}
			
			categoriasResult = query.getResultList();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
				
		return categoriasResult;
	}

	@Override
	@Transactional
	public void deleteAll() 
	{
		String sql = null;
		Query query = null;
		
		try
		{
			sql = " DELETE FROM Categoria ";
			
			query = getManager().createQuery(sql);
			
			query.executeUpdate();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}

	@Override
	@Transactional
	public void deleteById(String id) 
	{		
		String sql = null;
		Query query = null;
		
		try
		{
			sql = " DELETE " +
				  "   FROM Categoria c " +
			      "  WHERE c.idCategoria LIKE ? " ;
			
			query = getManager().createQuery(sql);
			query.setParameter(1, id + '%');			
			
			query.executeUpdate();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}		
}