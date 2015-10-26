package com.pvctecnocom.store.crm.mvp.model.dao.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvctecnocom.store.crm.mvp.model.bo.Producto;
import com.pvctecnocom.store.crm.mvp.model.dao.ProductoDAO;

@Repository
public class ProductoDAOJPAImpl extends GenericDAOJPAImpl<Producto, String> implements ProductoDAO 
{
	@Override
	public List<Producto> findProductosByCategoria(String idCategoria) 
	{		
		List<Producto> productosResult = null;
		String sql = null;
		TypedQuery<Producto> query = null;
		
		try
		{					
			sql = " SELECT p " +
				  "   FROM Producto p " +
				  "  WHERE p.idCategoria = ? " +
				  "  ORDER BY p.codigo ";					     
		
			query = getManager().createQuery(sql, Producto.class);	
			query.setParameter(1, idCategoria);
			
			
			productosResult = query.getResultList();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
				
		return productosResult;
	}

	@Override
	public List<Producto> findProductosByIds(Collection<String> ids) 
	{		
		List<Producto> productosResult = null;
		String sql = null;
		TypedQuery<Producto> query = null;
		
		try
		{					
			sql = " SELECT p " +
				  "   FROM Producto p " +
				  "  WHERE p.idProducto IN :idsList " +
				  "  ORDER BY p.idCategoria, p.codigo ";					     
		
			query = getManager().createQuery(sql, Producto.class);	
			query.setParameter("idsList", ids);
						
			productosResult = query.getResultList();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
				
		return productosResult;
	}

	@Override
	@Transactional
	public void deleteAll() 
	{
		String sql = null;
		Query query = null;
		
		try
		{
			sql = " DELETE FROM Producto ";
			
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
	public void deleteByCategoria(String categoria) 
	{		
		String sql = null;
		Query query = null;
		
		try
		{
			sql = " DELETE " +
				  "   FROM Producto p " +
			      "  WHERE p.idCategoria LIKE ? " ;
			
			query = getManager().createQuery(sql);
			query.setParameter(1, categoria + '%');			
			
			query.executeUpdate();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}	

	@Override
	public List<Producto> findProductosByCodigoDescripcion(String idCodigo) 
	{
		List<Producto> productosResult = null;
		String sql = null;
		TypedQuery<Producto> query = null;
		
		try
		{					
			sql = " SELECT p " +
				  "   FROM Producto p " +
				  "  WHERE p.codigo LIKE ? " +
				  "     OR p.descripcion LIKE ? " +
				  "  ORDER BY p.codigo ";					     
		
			query = getManager().createQuery(sql, Producto.class);	
			query.setParameter(1, '%' + idCodigo + '%');
			query.setParameter(2, '%' + idCodigo + '%');
						
			productosResult = query.getResultList();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
				
		return productosResult;
	}		
}