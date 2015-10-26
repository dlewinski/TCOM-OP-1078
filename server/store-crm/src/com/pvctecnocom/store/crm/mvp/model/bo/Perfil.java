package com.pvctecnocom.store.crm.mvp.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "perfil")
public class Perfil extends GenericBO
{			
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="perfil_id")
	private Integer id;
		
	@Size(max=250)
	private String nombre;		

	public Perfil()
	{
		super();
	}
	
	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}
			
	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Perfil other = (Perfil) obj;
		
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		
		return true;
	}
}