package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

public class PedidoConsultaDetalle implements Serializable
{
	private static final long serialVersionUID = -196259373215655106L;
	
	private String documentoVenta;
	
	private String documentoVentaPosicion;
	
	private String numeroMaterial;
	
	private String descripcionMaterial;		

	private String posicionPedido;
	
	private String cantidadPedido;
	
	private String unidadMedida;
	
	private String valorNeto;
	
	private String documentoMoneda;
	
	private String posicionPedidoEstado;
	
	private String valorPrecio;
	
	private String valorDescuento;
	
	private String centro;
	
	private String almacen;
	
	private String descuentoAcumuladoConcedido;	
	
	private String fechaEntregaPosicion;	

	public String getDocumentoVenta() 
	{
		return documentoVenta;
	}

	public void setDocumentoVenta(String documentoVenta) 
	{
		this.documentoVenta = documentoVenta;
	}

	public String getDocumentoVentaPosicion() 
	{
		return documentoVentaPosicion;
	}

	public void setDocumentoVentaPosicion(String documentoVentaPosicion) 
	{
		this.documentoVentaPosicion = documentoVentaPosicion;
	}

	public String getNumeroMaterial() 
	{
		return numeroMaterial;
	}

	public void setNumeroMaterial(String numeroMaterial) 
	{
		this.numeroMaterial = numeroMaterial;
	}
	
	public String getDescripcionMaterial() 
	{
		return descripcionMaterial;
	}

	public void setDescripcionMaterial(String descripcionMaterial) 
	{
		this.descripcionMaterial = descripcionMaterial;
	}

	public String getPosicionPedido() 
	{
		return posicionPedido;
	}

	public void setPosicionPedido(String posicionPedido) 
	{
		this.posicionPedido = posicionPedido;
	}

	public String getCantidadPedido() 
	{
		return cantidadPedido;
	}

	public void setCantidadPedido(String cantidadPedido) 
	{
		this.cantidadPedido = cantidadPedido;
	}

	public String getUnidadMedida() 
	{
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) 
	{
		this.unidadMedida = unidadMedida;
	}

	public String getValorNeto() 
	{
		return valorNeto;
	}

	public void setValorNeto(String valorNeto) 
	{
		this.valorNeto = valorNeto;
	}

	public String getDocumentoMoneda() 
	{
		return documentoMoneda;
	}

	public void setDocumentoMoneda(String documentoMoneda) 
	{
		this.documentoMoneda = documentoMoneda;
	}

	public String getPosicionPedidoEstado() 
	{
		return posicionPedidoEstado;
	}

	public void setPosicionPedidoEstado(String posicionPedidoEstado) 
	{
		this.posicionPedidoEstado = posicionPedidoEstado;
	}

	public String getValorPrecio() 
	{
		return valorPrecio;
	}

	public void setValorPrecio(String valorPrecio) 
	{
		this.valorPrecio = valorPrecio;
	}

	public String getValorDescuento() 
	{
		return valorDescuento;
	}

	public void setValorDescuento(String valorDescuento) 
	{
		this.valorDescuento = valorDescuento;
	}

	public String getCentro() 
	{
		return centro;
	}

	public void setCentro(String centro) 
	{
		this.centro = centro;
	}

	public String getAlmacen() 
	{
		return almacen;
	}

	public void setAlmacen(String almacen) 
	{		
		this.almacen = almacen;
	}
	
	public String getDescuentoAcumuladoConcedido() 
	{
		return descuentoAcumuladoConcedido;
	}

	public void setDescuentoAcumuladoConcedido(String descuentoAcumuladoConcedido) 
	{
		this.descuentoAcumuladoConcedido = descuentoAcumuladoConcedido;
	}
	
	public String getFechaEntregaPosicion() 
	{
		return fechaEntregaPosicion;
	}

	public void setFechaEntregaPosicion(String fechaEntregaPosicion) 
	{
		this.fechaEntregaPosicion = fechaEntregaPosicion;
	}
}