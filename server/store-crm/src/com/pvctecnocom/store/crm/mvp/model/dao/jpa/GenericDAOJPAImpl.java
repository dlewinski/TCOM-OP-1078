package com.pvctecnocom.store.crm.mvp.model.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.model.dao.GenericDAO;
import com.vaadin.server.VaadinSession;


@Repository
public abstract class GenericDAOJPAImpl<T, Id extends Serializable>  implements GenericDAO<T, Id> 
{
	private Class<T> persistenceClass;
	
	@PersistenceContext
	private EntityManager manager;

	
	public EntityManager getManager() 
	{
		return manager;
	}

	public void setManager(EntityManager manager) 
	{
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	public GenericDAOJPAImpl() 
	{
		this.persistenceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T findById(Id id) 
	{
		return getManager().find(persistenceClass, id);
	}

	
	@Transactional(readOnly = true)
	public List<T> findAll() 
	{		
		List<T> result = null;

		TypedQuery<T> query = manager.createQuery("select o from " + persistenceClass.getSimpleName() + " o", persistenceClass);

		result = query.getResultList();
		
		return result;
	}	

	@Transactional
	public void insert(T objet) 
	{
		setCommonUpdateData(objet, null);
		getManager().persist(objet);
	}
	
	@Transactional
	public void update(T objet) 
	{
		setCommonUpdateData(objet, null);
		getManager().merge(objet);
	}		
	
	@Transactional
	public void delete(T objet) 
	{
		getManager().remove(getManager().merge(objet));
	}
	
	private void setCommonUpdateData(T objet, Usuario user) 
	{
		try 
		{
			if(user == null)			
				user = (Usuario)VaadinSession.getCurrent().getAttribute(Usuario.class.getName());			
			
			Method setUpdateUser =  objet.getClass().getMethod("setEnteredUserID", Integer.class);
			setUpdateUser.invoke(objet, user.getId());
			
			Method setUpdateDate =  objet.getClass().getMethod("setEnteredDateTime", Date.class);
			setUpdateDate.invoke(objet, new Date());						
		} 
		catch (NoSuchMethodException e) 
		{			
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{			
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{			
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) 
		{			
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{			
			e.printStackTrace();
		}
	}
}