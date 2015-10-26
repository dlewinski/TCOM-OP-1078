package com.pvctecnocom.store.crm.mvp.model.dao;

import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.CentroAlmacen;

public interface CentroAlmacenDAO extends GenericDAO<CentroAlmacen,Integer> 
{	
	public void deleteAll();
	
	public List<CentroAlmacen> findCentrosAlmacenByOrganizacionVentas(String organizacionVentas);
	
	public List<CentroAlmacen> findCentrosAlmacenByCentro(String centro);
}