package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;
import java.util.List;

public class PedidoConsulta implements Serializable
{	
	private static final long serialVersionUID = 6378078151321943875L;

	private String documentoVenta;
	
	private String documentoFecha;
	
	private String domicilioEntrega;
	
	private String valorNeto;
	
	private String documentoMoneda;
	
	private String estadoPedido;
	
	private String organizacionVenta;
	
	private String canalDistribucion;
	
	private String sector;
	
	private String idCliente;
	 
	private String idClienteInterlocutor;
	
	private String grupoVendedores;
	
	private String tipoCambio;
	
	private String fechaReparto;
	
	private String claseDocumentoVentas;
	
	private String editable;
	
	private List<PedidoConsultaDetalle> detalles;

	public String getDocumentoVenta() 
	{
		return documentoVenta;
	}

	public void setDocumentoVenta(String documentoVenta) 
	{
		this.documentoVenta = documentoVenta;
	}

	public String getDocumentoFecha() 
	{
		return documentoFecha;
	}

	public void setDocumentoFecha(String documentoFecha) 
	{
		this.documentoFecha = documentoFecha;
	}

	public String getDomicilioEntrega() 
	{
		return domicilioEntrega;
	}

	public void setDomicilioEntrega(String domicilioEntrega) 
	{
		this.domicilioEntrega = domicilioEntrega;
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

	public String getEstadoPedido() 
	{
		return estadoPedido;
	}

	public void setEstadoPedido(String estadoPedido) 
	{
		this.estadoPedido = estadoPedido;
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

	public String getSector() 
	{
		return sector;
	}

	public void setSector(String sector) 
	{
		this.sector = sector;
	}

	public String getIdCliente() 
	{
		return idCliente;
	}

	public void setIdCliente(String idCliente) 
	{
		this.idCliente = idCliente;
	}

	public String getIdClienteInterlocutor() 
	{
		return idClienteInterlocutor;
	}

	public void setIdClienteInterlocutor(String idClienteInterlocutor) 
	{
		this.idClienteInterlocutor = idClienteInterlocutor;
	}

	public String getGrupoVendedores() 
	{
		return grupoVendedores;
	}

	public void setGrupoVendedores(String grupoVendedores) 
	{
		this.grupoVendedores = grupoVendedores;
	}

	public String getTipoCambio() 
	{
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) 
	{
		this.tipoCambio = tipoCambio;
	}

	public String getFechaReparto() 
	{
		return fechaReparto;
	}

	public void setFechaReparto(String fechaReparto) 
	{
		this.fechaReparto = fechaReparto;
	}
	
	public String getClaseDocumentoVentas() 
	{
		return claseDocumentoVentas;
	}

	public void setClaseDocumentoVentas(String claseDocumentoVentas) 
	{
		this.claseDocumentoVentas = claseDocumentoVentas;
	}

	public String getEditable() 
	{
		return editable;
	}

	public void setEditable(String editable) 
	{
		this.editable = editable;
	}

	public List<PedidoConsultaDetalle> getDetalles() 
	{
		return detalles;
	}

	public void setDetalles(List<PedidoConsultaDetalle> detalles) 
	{
		this.detalles = detalles;
	}
}