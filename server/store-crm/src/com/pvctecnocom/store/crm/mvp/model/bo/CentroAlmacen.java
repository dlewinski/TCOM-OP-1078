package com.pvctecnocom.store.crm.mvp.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "centro_almacen")
public class CentroAlmacen extends GenericBO
{										
	@Id
	@Column(name="id_centro_almacen")
	private Integer idCentroAlmacen;
	
	@Column(name="organizacion_venta")
	private String organizacionVentas;
	
	@Column(name="organizacion_venta_deno")
	private String organizacionVentasDenominacion;
	
	private String centro;
	
	private String nombre;
	
	private String almacen;
	
	@Column(name="almacen_deno")
	private String almacenDenominacion;
	
	public CentroAlmacen() 
	{
		super();
	}

	public Integer getIdCentroAlmacen() 
	{
		return idCentroAlmacen;
	}

	public void setIdCentroAlmacen(Integer idCentroAlmacen) 
	{
		this.idCentroAlmacen = idCentroAlmacen;
	}

	public String getOrganizacionVentas() 
	{
		return organizacionVentas;
	}

	public void setOrganizacionVentas(String organizacionVentas) 
	{
		this.organizacionVentas = organizacionVentas;
	}

	public String getOrganizacionVentasDenominacion() 
	{
		return organizacionVentasDenominacion;
	}

	public void setOrganizacionVentasDenominacion(String organizacionVentasDenominacion) 
	{
		this.organizacionVentasDenominacion = organizacionVentasDenominacion;
	}

	public String getCentro() 
	{
		return centro;
	}

	public void setCentro(String centro) 
	{
		this.centro = centro;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getAlmacen() 
	{
		return almacen;
	}

	public void setAlmacen(String almacen) 
	{
		this.almacen = almacen;
	}

	public String getAlmacenDenominacion() 
	{
		return almacenDenominacion;
	}

	public void setAlmacenDenominacion(String almacenDenominacion) 
	{
		this.almacenDenominacion = almacenDenominacion;
	}					
}