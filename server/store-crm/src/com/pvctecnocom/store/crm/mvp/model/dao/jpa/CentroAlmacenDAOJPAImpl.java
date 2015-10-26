package com.pvctecnocom.store.crm.mvp.model.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvctecnocom.store.crm.mvp.model.bo.CentroAlmacen;
import com.pvctecnocom.store.crm.mvp.model.dao.CentroAlmacenDAO;

@Repository
public class CentroAlmacenDAOJPAImpl extends GenericDAOJPAImpl<CentroAlmacen, Integer> implements CentroAlmacenDAO 
{	
	@Override
	@Transactional
	public void deleteAll() 
	{
		String sql = null;
		Query query = null;
		
		try
		{
			sql = " DELETE FROM CentroAlmacen ";
			
			query = getManager().createQuery(sql);
			
			query.executeUpdate();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CentroAlmacen> findCentrosAlmacenByOrganizacionVentas(String organizacionVentas) 
	{		
		List<CentroAlmacen> centrosAlmacenResult = new ArrayList<CentroAlmacen>();
		String sql = null;
		Query query = null;
		
		try
		{					
			sql = " SELECT DISTINCT ca.centro, ca.nombre " +
				  "   FROM centro_almacen ca " +
				  "  WHERE ca.organizacion_venta = ? " +
				  "  ORDER BY ca.centro ";					     
		
			query = getManager().createNativeQuery(sql);	
			query.setParameter(1, organizacionVentas);									
			
			List<Object[]> resultList = (List<Object[]>)query.getResultList();
			
			if(resultList != null)
			{	
				CentroAlmacen centroAlmacen;
				
				for (int i = 0; i < resultList.size(); i++)
				{
					centroAlmacen = new CentroAlmacen();
					
					centroAlmacen.setCentro((String)resultList.get(i)[0]);
					centroAlmacen.setNombre((String)resultList.get(i)[0] + " - " + (String)resultList.get(i)[1]);
					
					centrosAlmacenResult.add(centroAlmacen);
				}
			}	
		}
		catch(Exception e)
		{
			e.getMessage();
		}
				
		return centrosAlmacenResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentroAlmacen> findCentrosAlmacenByCentro(String centro) 
	{
		List<CentroAlmacen> centrosAlmacenResult = new ArrayList<CentroAlmacen>();
		String sql = null;
		Query query = null;
		
		try
		{					
			sql = " SELECT DISTINCT ca.almacen, ca.almacen_deno " +
				  "   FROM centro_almacen ca " +
				  "  WHERE ca.centro = ? " +
				  "  ORDER BY ca.almacen ";					     
		
			query = getManager().createNativeQuery(sql);	
			query.setParameter(1, centro);
			
			List<Object[]> resultList = (List<Object[]>)query.getResultList();
			
			if(resultList != null)
			{	
				CentroAlmacen centroAlmacen;
				
				for (int i = 0; i < resultList.size(); i++)
				{
					centroAlmacen = new CentroAlmacen();
					
					centroAlmacen.setAlmacen((String)resultList.get(i)[0]);
					centroAlmacen.setAlmacenDenominacion((String)resultList.get(i)[0] + " - " + (String)resultList.get(i)[1]);
					
					centrosAlmacenResult.add(centroAlmacen);
				}
			}	
		}
		catch(Exception e)
		{
			e.getMessage();
		}
				
		return centrosAlmacenResult;
	}		
}