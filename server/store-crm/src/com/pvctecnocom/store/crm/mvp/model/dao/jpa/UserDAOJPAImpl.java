package com.pvctecnocom.store.crm.mvp.model.dao.jpa;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.model.dao.UserDAO;

@Repository
public class UserDAOJPAImpl extends GenericDAOJPAImpl<Usuario, Integer> implements UserDAO 
{		
	@Override
	@Transactional(readOnly=true)
	public Usuario findByUsername(Usuario user) 
	{		
		Usuario userResult = null;
		
		try
		{		
			String sql = " SELECT u " +
					     "   FROM Usuario u " +
					     "  WHERE u.username = ? " + 
					     "    AND u.habilitado = 'S' ";
			
			TypedQuery<Usuario> query = getManager().createQuery(sql, Usuario.class);
			query.setParameter(1, user.getUsername());
			
			userResult = query.getSingleResult();
		}
		catch(Exception e)
		{
			e.getMessage();			
		}
		
		return userResult;
	}		
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findAll()
	{
		List<Usuario> users = null;
		
		try
		{		
			String sql = "    SELECT u " +
						 "      FROM Usuario u  " +						 
						 "  ORDER BY u.apellido, u.nombre, u.username " ; 
			
			Query query =  getManager().createQuery(sql, Usuario.class);
		
			users = query.getResultList();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		
		return users;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario findByCode(String code) 
	{
		Usuario usuarioResult = null;
		
		try
		{		
			String sql = " SELECT u " +
					     "   FROM Usuario u " +
					     "  WHERE u.code = ? ";					     
			
			TypedQuery<Usuario> query = getManager().createQuery(sql, Usuario.class);
			query.setParameter(1, code);			
			
			usuarioResult = query.getSingleResult();						
		}
		catch(Exception e)
		{
			e.getMessage();			
		}
		
		return usuarioResult;
	}
	
	@Override
	@Transactional
	public int updatePasswordByCode(String code, String salt, String password) 
	{
		int result = 0;
		
		try
		{		
			String sql = " UPDATE Usuario u " +
					     "    SET u.clave = ? , " +
					     "        u.salt = ? , " +
					     "        u.code = ? " +
					     "  WHERE u.code = ? ";					     
			
			Query query = getManager().createNativeQuery(sql);
			query.setParameter(1, password);	
			query.setParameter(2, salt);	
			query.setParameter(3, null);
			query.setParameter(4, code);
			
			result = query.executeUpdate();						
		}
		catch(Exception e)
		{
			e.getMessage();			
		}
		
		return result;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario findByEmail(String email) 
	{		
		Usuario usuarioResult = null;
		
		try
		{		
			String sql = " SELECT u " +
					     "   FROM Usuario u " +
					     "  WHERE u.email = ? ";					     
			
			TypedQuery<Usuario> query = getManager().createQuery(sql, Usuario.class);
			query.setParameter(1, email);			
			
			usuarioResult = query.getSingleResult();						
		}
		catch(Exception e)
		{
			e.getMessage();			
		}
		
		return usuarioResult;
	}

	@Override
	@Transactional
	public void updateCodeByEmail(String email, String code) 
	{		
		try
		{		
			String sql = " UPDATE Usuario u " +
					     "    SET u.code = ? " +
					     "  WHERE u.email = ? ";					     
			
			Query query = getManager().createNativeQuery(sql);
			query.setParameter(1, code);			
			query.setParameter(2, email);
			
			query.executeUpdate();						
		}
		catch(Exception e)
		{
			e.getMessage();			
		}
	}
}