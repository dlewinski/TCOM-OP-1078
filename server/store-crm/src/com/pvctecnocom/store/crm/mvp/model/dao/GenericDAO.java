package com.pvctecnocom.store.crm.mvp.model.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T,Id extends Serializable> 
{	
	T findById (Id id);
	
	List<T> findAll();
	
	void insert(T objet);
	
	void update(T objet);
	
	void delete(T objet);		
}