package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

public class Precio implements Serializable
{				
	private static final long serialVersionUID = 1095151922272476285L;

	private String codigoProducto;
		
	private String organizacionVenta;
			
	private String canalDistribucion;
		
	private String condicionImporte;
		
	private String condicionUnidad;
		
	private String condicionUnidadMedida;
		
	private String condicionInicioValidez;
		
	private String condicionFinValidez;		
		
	public Precio() 
	{
		super();
	}

	public String getOrganizacionVenta() 
	{
		return organizacionVenta;
	}

	public void setOrganizacionVenta(String organizacionVenta) 
	{
		this.organizacionVenta = organizacionVenta;
	}

	public String getCanalDistribucion() 
	{
		return canalDistribucion;
	}

	public void setCanalDistribucion(String canalDistribucion) 
	{
		this.canalDistribucion = canalDistribucion;
	}

	public String getCodigoProducto() 
	{
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) 
	{
		this.codigoProducto = codigoProducto;
	}

	public String getCondicionImporte() 
	{
		return condicionImporte;
	}

	public void setCondicionImporte(String condicionImporte) 
	{
		this.condicionImporte = condicionImporte;
	}

	public String getCondicionUnidad() 
	{
		return condicionUnidad;
	}

	public void setCondicionUnidad(String condicionUnidad) 
	{
		this.condicionUnidad = condicionUnidad;
	}

	public String getCondicionUnidadMedida() 
	{
		return condicionUnidadMedida;
	}

	public void setCondicionUnidadMedida(String condicionUnidadMedida) 
	{
		this.condicionUnidadMedida = condicionUnidadMedida;
	}

	public String getCondicionInicioValidez() 
	{
		return condicionInicioValidez;
	}

	public void setCondicionInicioValidez(String condicionInicioValidez) 
	{
		this.condicionInicioValidez = condicionInicioValidez;
	}

	public String getCondicionFinValidez() 
	{
		return condicionFinValidez;
	}

	public void setCondicionFinValidez(String condicionFinValidez) 
	{
		this.condicionFinValidez = condicionFinValidez;
	}	
}