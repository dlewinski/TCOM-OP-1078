package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

public class AreaVenta implements Serializable
{					
	private static final long serialVersionUID = -5072040447010865668L;
	
	private String organizacionVentas;	
	private String organizacionVentasDenominacion;	
	private String canalDistribucion;	
	private String canalDistribucionDenominacion;	
	private String sector;	
	private String sectorDenominacion;	
	private String precioEditable;	
	private String descuentoEditable;
	private Integer cantidadLineasDefault;
	private String tipoCotizacion;	
	private Double tipoCambio;
	private Integer cantidadDias;		

	public AreaVenta() 
	{
		super();
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

	public String getCanalDistribucion() 
	{
		return canalDistribucion;
	}

	public void setCanalDistribucion(String canalDistribucion) 
	{
		this.canalDistribucion = canalDistribucion;
	}

	public String getCanalDistribucionDenominacion() 
	{
		return canalDistribucionDenominacion;
	}

	public void setCanalDistribucionDenominacion(String canalDistribucionDenominacion) 
	{
		this.canalDistribucionDenominacion = canalDistribucionDenominacion;
	}

	public String getSector() 
	{
		return sector;
	}

	public void setSector(String sector) 
	{
		this.sector = sector;
	}

	public String getSectorDenominacion() 
	{
		return sectorDenominacion;
	}

	public void setSectorDenominacion(String sectorDenominacion) 
	{
		this.sectorDenominacion = sectorDenominacion;
	}				
	
	public String getPrecioEditable() 
	{
		return precioEditable;
	}

	public void setPrecioEditable(String precioEditable) 
	{
		this.precioEditable = precioEditable;
	}

	public String getDescuentoEditable() 
	{
		return descuentoEditable;
	}

	public void setDescuentoEditable(String descuentoEditable) 
	{
		this.descuentoEditable = descuentoEditable;
	}

	public Integer getCantidadLineasDefault() 
	{
		return cantidadLineasDefault;
	}

	public void setCantidadLineasDefault(Integer cantidadLineasDefault) 
	{
		this.cantidadLineasDefault = cantidadLineasDefault;
	}
	
	public String getTipoCotizacion() 
	{
		return tipoCotizacion;
	}

	public void setTipoCotizacion(String tipoCotizacion) 
	{
		this.tipoCotizacion = tipoCotizacion;
	}

	public Double getTipoCambio() 
	{
		return tipoCambio;
	}

	public void setTipoCambio(Double tipoCambio) 
	{
		this.tipoCambio = tipoCambio;
	}
	
	public Integer getCantidadDias() 
	{
		return cantidadDias;
	}

	public void setCantidadDias(Integer cantidadDias) 
	{
		this.cantidadDias = cantidadDias;
	}
}