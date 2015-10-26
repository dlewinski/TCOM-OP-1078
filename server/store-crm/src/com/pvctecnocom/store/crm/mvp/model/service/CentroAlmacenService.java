package com.pvctecnocom.store.crm.mvp.model.service;

import java.util.List;

import com.pvctecnocom.store.crm.mvp.model.bo.CentroAlmacen;
public interface CentroAlmacenService 
{		
	public List<CentroAlmacen> findAll();
	
	public List<CentroAlmacen> findCentrosAlmacenByOrganizacionVentas(String organizacionVentas);
	
	public List<CentroAlmacen> findCentrosAlmacenByCentro(String centro);
	
	public void insertCentroAlmacen(CentroAlmacen centroAlmacen);			
	
	public void deleteAll();
}