package com.pvctecnocom.store.crm.mvp.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria extends GenericBO
{				
	@Id
	@Column(name="id_categoria")
	private String idCategoria;     
	
	@Column(name="nombre_corto")
	private String nombreCorto;	
	
	@Column(name="nombre_largo")
	private String nombreLargo;	
	
	@Column(name="descripcion_gral")
	private String descripcionGeneral;
	
	@Column(name="descripcion_tec")
	private String descripcionTecnica;	
	
	@Column(name="descripcion_com")
	private String descripcionComercializacion;	
	
	@Column(name="id_categoria_sup")
	private String idCategoriaSuperior;
	
	private Integer orden;
	
	@Column(name="imagen_chica_nombre")
	private String nombreImagenChica;

	public Categoria() 
	{
		super();
	}

	public Categoria(String idCategoria) 
	{
		super();
		
		this.idCategoria = idCategoria;
	}        
	
	public String getIdCategoria() 
	{
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) 
	{
		this.idCategoria = idCategoria;
	}
	
	public String getNombreCorto() 
	{
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) 
	{
		this.nombreCorto = nombreCorto;
	}
	
	public String getNombreLargo() 
	{
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) 
	{
		this.nombreLargo = nombreLargo;
	}
	
	public String getDescripcionGeneral() 
	{
		return descripcionGeneral;
	}

	public void setDescripcionGeneral(String descripcionGeneral) 
	{
		this.descripcionGeneral = descripcionGeneral;
	}
	
	public String getDescripcionTecnica() 
	{
		return descripcionTecnica;
	}

	public void setDescripcionTecnica(String descripcionTecnica) 
	{
		this.descripcionTecnica = descripcionTecnica;
	}
	
	public String getDescripcionComercializacion() 
	{
		return descripcionComercializacion;
	}

	public void setDescripcionComercializacion(String descripcionComercializacion) 
	{
		this.descripcionComercializacion = descripcionComercializacion;
	}

	public String getIdCategoriaSuperior() 
	{
		return idCategoriaSuperior;
	}

	public void setIdCategoriaSuperior(String idCategoriaSuperior) 
	{
		this.idCategoriaSuperior = idCategoriaSuperior;
	}

	public Integer getOrden() 
	{
		return orden;
	}

	public void setOrden(Integer orden) 
	{
		this.orden = orden;
	}

	public String getNombreImagenChica() 
	{
		return nombreImagenChica;
	}

	public void setNombreImagenChica(String nombreImagenChica) 
	{
		this.nombreImagenChica = nombreImagenChica;
	}
}