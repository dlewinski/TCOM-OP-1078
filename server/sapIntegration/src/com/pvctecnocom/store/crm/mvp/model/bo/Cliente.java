package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;
import java.util.List;

public class Cliente implements Serializable 
{	
	private static final long serialVersionUID = 1451350360670809345L;
		       
    private String razonSocial;
    
    private String direccion;
    
    private String localidad;
                       
    private String email;                   

    private String numeroCliente;
    
    private String numeroVendedor;        	

	private List<AreaVenta> areasVenta;
    
    private List<Destinatario> destinatarios;
                       
	public Cliente() 
	{
		super();
	}	 	

	public String getNumeroCliente() 
	{
		return numeroCliente;
	}

	public void setNumeroCliente(String numeroCliente) 
	{
		this.numeroCliente = numeroCliente;
	}
	
	public String getNumeroVendedor() 
	{
		return numeroVendedor;
	}

	public void setNumeroVendedor(String numeroVendedor) 
	{
		this.numeroVendedor = numeroVendedor;
	}
	
	public String getRazonSocial() 
	{
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) 
	{
		this.razonSocial = razonSocial;
	}

	public String getDireccion() 
	{
		return direccion;
	}

	public void setDireccion(String direccion) 
	{
		this.direccion = direccion;
	}

	public String getLocalidad() 
	{
		return localidad;
	}

	public void setLocalidad(String localidad) 
	{
		this.localidad = localidad;
	}
			
	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}		

	public List<AreaVenta> getAreasVenta() 
	{
		return areasVenta;
	}

	public void setAreasVenta(List<AreaVenta> areasVenta) 
	{
		this.areasVenta = areasVenta;
	}
	
	public List<Destinatario> getDestinatarios() 
	{
		return destinatarios;
	}

	public void setDestinatarios(List<Destinatario> destinatarios) 
	{
		this.destinatarios = destinatarios;
	}
}