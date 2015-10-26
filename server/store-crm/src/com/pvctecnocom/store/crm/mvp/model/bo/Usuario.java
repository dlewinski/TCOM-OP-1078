package com.pvctecnocom.store.crm.mvp.model.bo;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.pvctecnocom.store.crm.mvp.util.BooleanToStringConverter;

@Entity
@Table(name = "usuario")
public class Usuario extends GenericBO
{			
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="usuario_id")
	private Integer id;
	
	@NotBlank
	@Size(min=1, max=20)
	@Column(name="nombre_usuario")
	private String username;
	
	@NotNull
	@Column(name="clave")
	private String password;
	
	@NotNull
	@Column(name="salt")
	private String salt;
	
	@Size(max=250)	
	private String nombre;	

	@Size(max=250)	
	private String apellido;		
	
	@Size(max=250)	
	private String telefono;		

	@Size(max=250)	
	@Email
	private String email;
		
	@OneToOne
    @JoinColumn(name="perfil_id", unique=true)
	private Perfil perfil;
	
	@Size(max=250)		
	@Column(name="nro_vendedor_sap")
	private String numeroVendedorSAP;	

	@Transient
	private String passwordConfirm;
	
	@Column(name="habilitado")	
	@Convert(converter=BooleanToStringConverter.class)
	private Boolean habilitado;			
	
	private String code;	

	public Usuario()
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
		
	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getSalt() 
	{
		return salt;
	}
	
	public void setSalt(String salt) 
	{
		this.salt = salt;
	}
	
	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getApellido() 
	{
		return apellido;
	}

	public void setApellido(String apellido) 
	{
		this.apellido = apellido;
	}
	
	public String getTelefono() 
	{
		return telefono;
	}

	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public Perfil getPerfil() 
	{
		return perfil;
	}

	public void setPerfil(Perfil perfil) 
	{
		this.perfil = perfil;
	}
	
	public String getNumeroVendedorSAP() 
	{
		return numeroVendedorSAP;
	}

	public void setNumeroVendedorSAP(String numeroVendedorSAP) 
	{
		this.numeroVendedorSAP = numeroVendedorSAP;
	}
	
	public String getPasswordConfirm() 
	{
		return passwordConfirm;
	}
	
	public void setPasswordConfirm(String passwordConfirm) 
	{
		this.passwordConfirm = passwordConfirm;
	}
	
	public Boolean getHabilitado() 
	{
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) 
	{
		this.habilitado = habilitado;
	}
	
	public String getCode() 
	{
		return code;
	}

	public void setCode(String code) 
	{
		this.code = code;
	}
}