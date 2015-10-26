package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sector")
public class Sector implements Serializable
{							
	private static final long serialVersionUID = 9156903571509606173L;
	
	@Id
	private String nombre;	
	
	private String abreviatura;
	
	@Column(name="cantidad_filas_pedido")
	private Integer cantidadFilasPedido;
	
	public Sector() 
	{
		super();
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getAbreviatura() 
	{
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) 
	{
		this.abreviatura = abreviatura;
	}

	public Integer getCantidadFilasPedido() 
	{
		return cantidadFilasPedido;
	}

	public void setCantidadFilasPedido(Integer cantidadFilasPedido) 
	{
		this.cantidadFilasPedido = cantidadFilasPedido;
	}	
}