package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

public class Descuento implements Serializable  
{	
	private static final long serialVersionUID = 2077962436512947240L;
		
	private String grupo;	
	private String grupoDescuento;
	private String material;
	private String materialDescuento;
	
	public Descuento() 
	{
		super();
	}		
	
	public String getGrupo() 
	{
		return grupo;
	}

	public void setGrupo(String grupo) 
	{
		this.grupo = grupo;
	}

	public String getGrupoDescuento() 
	{
		return grupoDescuento;
	}

	public void setGrupoDescuento(String grupoDescuento) 
	{
		this.grupoDescuento = grupoDescuento;
	}

	public String getMaterial() 
	{
		return material;
	}

	public void setMaterial(String material) 
	{
		this.material = material;
	}

	public String getMaterialDescuento() 
	{
		return materialDescuento;
	}

	public void setMaterialDescuento(String materialDescuento) 
	{
		this.materialDescuento = materialDescuento;
	}
}