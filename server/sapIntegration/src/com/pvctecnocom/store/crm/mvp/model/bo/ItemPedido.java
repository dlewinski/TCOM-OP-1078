package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

public class ItemPedido implements Serializable
{			
	private static final long serialVersionUID = -3204905891942179591L;

	public ItemPedido() 
	{
		super();
	}
	
	private String codigoProducto;
	
	private String cantidad;
	
	private String valorCondicionPrecio;
	
	private String valorCondicionDescuento;
	
	private String centro;
	
	private String almacen;
	
	private String posicion;
	
	private String posicionBorrada;	
	
	private String fechaEntregaPosicion;

	public String getCodigoProducto() 
	{
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) 
	{
		this.codigoProducto = codigoProducto;
	}

	public String getCantidad() 
	{
		return cantidad;
	}

	public void setCantidad(String cantidad) 
	{
		this.cantidad = cantidad;
	}

	public String getValorCondicionPrecio() 
	{
		return valorCondicionPrecio;
	}

	public void setValorCondicionPrecio(String valorCondicionPrecio) 
	{
		this.valorCondicionPrecio = valorCondicionPrecio;
	}

	public String getValorCondicionDescuento() 
	{
		return valorCondicionDescuento;
	}

	public void setValorCondicionDescuento(String valorCondicionDescuento) 
	{
		this.valorCondicionDescuento = valorCondicionDescuento;
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

	public String getPosicion() 
	{
		return posicion;
	}

	public void setPosicion(String posicion) 
	{
		this.posicion = posicion;
	}	
	
	public String getPosicionBorrada() 
	{
		return posicionBorrada;
	}

	public void setPosicionBorrada(String posicionBorrada) 
	{
		this.posicionBorrada = posicionBorrada;
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