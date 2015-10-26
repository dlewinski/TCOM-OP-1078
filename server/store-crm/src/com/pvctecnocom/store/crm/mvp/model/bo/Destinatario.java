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
	
	@Override	
	public String toString()			
	{	
		return this.getCalle() + " - " + this.getPoblacion();		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((canalDistribucion == null) ? 0 : canalDistribucion
						.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime
				* result
				+ ((organizacionVentas == null) ? 0 : organizacionVentas
						.hashCode());
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Destinatario other = (Destinatario) obj;
		if (canalDistribucion == null) {
			if (other.canalDistribucion != null)
				return false;
		} else if (!canalDistribucion.equals(other.canalDistribucion))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (organizacionVentas == null) {
			if (other.organizacionVentas != null)
				return false;
		} else if (!organizacionVentas.equals(other.organizacionVentas))
			return false;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		return true;
	}	
}