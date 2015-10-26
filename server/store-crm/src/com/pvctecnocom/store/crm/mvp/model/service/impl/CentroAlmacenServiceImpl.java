package com.pvctecnocom.store.crm.mvp.model.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pvctecnocom.store.crm.mvp.model.bo.CentroAlmacen;
import com.pvctecnocom.store.crm.mvp.model.dao.CentroAlmacenDAO;
import com.pvctecnocom.store.crm.mvp.model.service.CentroAlmacenService;

@Service(value="centroAlmacenService")
public class CentroAlmacenServiceImpl implements CentroAlmacenService 
{
	private CentroAlmacenDAO centroAlmacenDAO = null;
	
	public CentroAlmacenDAO getCentroAlmacenDAO() 
	{
		return this.centroAlmacenDAO;
	}
	
	@Resource
	public void setCentroAlmacenDAO(CentroAlmacenDAO centroAlmacenDAO) 
	{
		this.centroAlmacenDAO = centroAlmacenDAO;
	}			
	
	@Override
	public List<CentroAlmacen> findAll() 
	{		
		return this.centroAlmacenDAO.findAll();
	}
	
	@Override
	public void insertCentroAlmacen(CentroAlmacen centroAlmacen) 
	{		
		this.centroAlmacenDAO.insert(centroAlmacen);		
	}
		
	@Override
	public void deleteAll() 
	{
		this.centroAlmacenDAO.deleteAll();
	}

	@Override
	public List<CentroAlmacen> findCentrosAlmacenByOrganizacionVentas(String organizacionVentas) 
	{		
		return this.centroAlmacenDAO.findCentrosAlmacenByOrganizacionVentas(organizacionVentas);
	}

	@Override
	public List<CentroAlmacen> findCentrosAlmacenByCentro(String centro) 
	{	
		return this.centroAlmacenDAO.findCentrosAlmacenByCentro(centro);
	}			
}