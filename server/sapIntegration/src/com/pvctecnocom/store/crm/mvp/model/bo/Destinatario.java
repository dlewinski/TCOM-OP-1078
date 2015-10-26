package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

public class Destinatario implements Serializable  
{	
	private static final long serialVersionUID = 7085048704174578039L;
		
	private String cliente;
	private String calle;
	private String poblacion;	
	private String organizacionVentas;			
	private String canalDistribucion;		
	private String sector;		
	
	public Destinatario() 
	{
		super();
	}
	
	public String getCliente() 
	{
		return cliente;
	}

	public void setCliente(String cliente) 
	{
		this.cliente = cliente;
	}

	public String getCalle() 
	{
		return calle;
	}

	public void setCalle(String calle) 
	{
		this.calle = calle;
	}

	public String getPoblacion() 
	{
		return poblacion;
	}

	public void setPoblacion(String poblacion) 
	{
		this.poblacion = poblacion;
	}
	
	public String getOrganizacionVentas() 
	{
		return organizacionVentas;
	}

	public void setOrganizacionVentas(String organizacionVentas) 
	{
		this.organizacionVentas = organizacionVentas;
	}

	public String getCanalDistribucion() 
	{
		return canalDistribucion;
	}

	public void setCanalDistribucion(String canalDistribucion) 
	{
		this.canalDistribucion = canalDistribucion;
	}

	public String getSector() 
	{
		return sector;
	}

	public void setSector(String sector) 
	{
		this.sector = sector;
	}
}