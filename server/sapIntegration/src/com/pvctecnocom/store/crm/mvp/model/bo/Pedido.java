package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable
{	
	private static final long serialVersionUID = 3423825192201012191L;
	
	private String organizacionVenta;
    
    private String canalDistribucion;
    
    private String sector;
    
    private String idCliente;
    
    private String idClienteInterlocutor;
    
    private String idDestinatario;
    
    private List<ItemPedido> itemsPedido;
    
    private String numeroPedido;
    
    private String moneda;
    
    private String importe;
    
    private String descripcion;    
    
    private String grupoVendedores;
    
    private String tipoCambio;
    
    private String fechaReparto;
    
    private String observaciones;
    
    private String documentoVentas; 
    
    private String valorNeto; 
    
    private String monedaDocumentoComercial;
    
    private String mensajeTipo;
    
    private String mensajeClase;
    
    private String mensajeNumero;
    
    private String mensajeTexto;
    
    private String logAplicacionNumero;
    
    private String logAplicacionNumeroInterno;
    
    private String variableMensaje1;
    
    private String variableMensaje2;
    
    private String variableMensaje3;
    
    private String variableMensaje4;
    
    private String parametro;
    
    private String parametroLinea;
    
    private String parametroCampo;
    
    private String sistema;

	public Pedido() 
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

	public String getIdDestinatario() 
	{
		return idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) 
	{
		this.idDestinatario = idDestinatario;
	}

	public List<ItemPedido> getItemsPedido() 
	{
		return itemsPedido;
	}

	public void setItemsPedido(List<ItemPedido> itemsPedido) 
	{
		this.itemsPedido = itemsPedido;
	}

	public String getNumeroPedido() 
	{
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) 
	{
		this.numeroPedido = numeroPedido;
	}

	public String getMoneda() 
	{
		return moneda;
	}

	public void setMoneda(String moneda) 
	{
		this.moneda = moneda;
	}

	public String getImporte() 
	{
		return importe;
	}

	public void setImporte(String importe) 
	{
		this.importe = importe;
	}

	public String getDescripcion() 
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
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
	
	public String getObservaciones() 
	{
		return observaciones;
	}

	public void setObservaciones(String observaciones) 
	{
		this.observaciones = observaciones;
	}

	public String getDocumentoVentas() 
	{
		return documentoVentas;
	}

	public void setDocumentoVentas(String documentoVentas) 
	{
		this.documentoVentas = documentoVentas;
	}

	public String getValorNeto() 
	{
		return valorNeto;
	}

	public void setValorNeto(String valorNeto) 
	{
		this.valorNeto = valorNeto;
	}

	public String getMonedaDocumentoComercial() 
	{
		return monedaDocumentoComercial;
	}

	public void setMonedaDocumentoComercial(String monedaDocumentoComercial) 
	{
		this.monedaDocumentoComercial = monedaDocumentoComercial;
	}

	public String getMensajeTipo() 
	{
		return mensajeTipo;
	}

	public void setMensajeTipo(String mensajeTipo) 
	{
		this.mensajeTipo = mensajeTipo;
	}

	public String getMensajeClase() 
	{
		return mensajeClase;
	}

	public void setMensajeClase(String mensajeClase) 
	{
		this.mensajeClase = mensajeClase;
	}

	public String getMensajeNumero() 
	{
		return mensajeNumero;
	}

	public void setMensajeNumero(String mensajeNumero)
	{
		this.mensajeNumero = mensajeNumero;
	}

	public String getMensajeTexto() 
	{
		return mensajeTexto;
	}

	public void setMensajeTexto(String mensajeTexto) 
	{
		this.mensajeTexto = mensajeTexto;
	}

	public String getLogAplicacionNumero() 
	{
		return logAplicacionNumero;
	}

	public void setLogAplicacionNumero(String logAplicacionNumero) 
	{
		this.logAplicacionNumero = logAplicacionNumero;
	}

	public String getLogAplicacionNumeroInterno() 
	{
		return logAplicacionNumeroInterno;
	}

	public void setLogAplicacionNumeroInterno(String logAplicacionNumeroInterno) 
	{
		this.logAplicacionNumeroInterno = logAplicacionNumeroInterno;
	}

	public String getVariableMensaje1() 
	{
		return variableMensaje1;
	}

	public void setVariableMensaje1(String variableMensaje1) 
	{
		this.variableMensaje1 = variableMensaje1;
	}

	public String getVariableMensaje2() 
	{
		return variableMensaje2;
	}

	public void setVariableMensaje2(String variableMensaje2) 
	{
		this.variableMensaje2 = variableMensaje2;
	}

	public String getVariableMensaje3() 
	{
		return variableMensaje3;
	}

	public void setVariableMensaje3(String variableMensaje3) 
	{
		this.variableMensaje3 = variableMensaje3;
	}

	public String getVariableMensaje4() 
	{
		return variableMensaje4;
	}

	public void setVariableMensaje4(String variableMensaje4) 
	{
		this.variableMensaje4 = variableMensaje4;
	}

	public String getParametro() 
	{
		return parametro;
	}

	public void setParametro(String parametro) 
	{
		this.parametro = parametro;
	}

	public String getParametroLinea() 
	{
		return parametroLinea;
	}

	public void setParametroLinea(String parametroLinea) 
	{
		this.parametroLinea = parametroLinea;
	}

	public String getParametroCampo() 
	{
		return parametroCampo;
	}

	public void setParametroCampo(String parametroCampo) 
	{
		this.parametroCampo = parametroCampo;
	}

	public String getSistema() 
	{
		return sistema;
	}

	public void setSistema(String sistema) 
	{
		this.sistema = sistema;
	}
}