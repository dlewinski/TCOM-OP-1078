package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto implements Serializable 
{							
	private static final long serialVersionUID = -984293355171478762L;		
	
	@Id
	private String codigo;
				
	private String descripcion;
	
	@Column(name="id_categoria")
	private String idCategoria;		
	
	@Column(name="unidad_medida")
	private String unidadMedida;
	
	@Column(name="unidad_peso")
	private String unidadPeso;
	
	private String grupo;
	
	@Column(name="peso_bruto")
	private String pesoBruto;
	
	private String sector;
	
	@Column(name="numero_europeo")
	private String numeroEuropeo;			

	public Producto() 
	{
		super();
	}
	
	public String getCodigo() 
	{
		return codigo;
	}

	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

	public String getDescripcion() 
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	public String getIdCategoria() 
	{
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) 
	{
		this.idCategoria = idCategoria;
	}

	public String getUnidadMedida() 
	{
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) 
	{
		this.unidadMedida = unidadMedida;
	}

	public String getUnidadPeso() 
	{
		return unidadPeso;
	}

	public void setUnidadPeso(String unidadPeso) 
	{
		this.unidadPeso = unidadPeso;
	}

	public String getGrupo() 
	{
		return grupo;
	}

	public void setGrupo(String grupo) 
	{
		this.grupo = grupo;
	}

	public String getPesoBruto() 
	{
		return pesoBruto;
	}

	public void setPesoBruto(String pesoBruto) 
	{
		this.pesoBruto = pesoBruto;
	}

	public String getSector() 
	{
		return sector;
	}

	public void setSector(String sector) 
	{
		this.sector = sector;
	}

	public String getNumeroEuropeo() 
	{
		return numeroEuropeo;
	}

	public void setNumeroEuropeo(String numeroEuropeo) 
	{
		this.numeroEuropeo = numeroEuropeo;
	}		     				
}