package com.pvctecnocom.store.crm.mvp.model.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.pvctecnocom.store.crm.mvp.model.bo.Perfil;
import com.pvctecnocom.store.crm.mvp.model.dao.PerfilDAO;

@Repository
public class PerfilDAOJPAImpl extends GenericDAOJPAImpl<Perfil, Integer> implements PerfilDAO 
{			
	@SuppressWarnings("unchecked")
	@Override
	public List<Perfil> findAll()
	{
		List<Perfil> perfiles = null;
		
		try
		{		
			String sql = "    SELECT p " +
						 "      FROM Perfil p  " +						 
						 "  ORDER BY p.nombre " ; 
			
			Query query =  getManager().createQuery(sql, Perfil.class);
		
			perfiles = query.getResultList();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		
		return perfiles;
	}
}