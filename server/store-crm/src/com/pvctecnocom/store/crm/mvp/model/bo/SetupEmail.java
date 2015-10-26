package com.pvctecnocom.store.crm.mvp.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email")
public class SetupEmail implements Serializable
{
	private static final long serialVersionUID = 5643942070791623710L;
	
	@Id
	@Column(name="id_email")
	private String idEmail;
	
	@Column(name="smtp_host")
	private String smtpHost;
	
	@Column(name="smtp_port")
	private String smtpPort;
	
	@Column(name="smtp_auth")
	private String smtpAuth;
	
	@Column(name="smtp_starttls")
	private String smtpStarttls;
	
	private String username;
	
	private String password;		
	
	private String method;
	
	private String from;
	
	private String subject;
	
	private String bcc;
	
	private String cc;
	
	private String template;
	
	private String url;
	
	public String getIdEmail() 
	{
		return idEmail;
	}

	public void setIdEmail(String idEmail) 
	{
		this.idEmail = idEmail;
	}

	public String getSmtpHost() 
	{
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) 
	{
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() 
	{
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) 
	{
		this.smtpPort = smtpPort;
	}

	public String getSmtpAuth() 
	{
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) 
	{
		this.smtpAuth = smtpAuth;
	}

	public String getSmtpStarttls() 
	{
		return smtpStarttls;
	}

	public void setSmtpStarttls(String smtpStarttls) 
	{
		this.smtpStarttls = smtpStarttls;
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

	public String getMethod() 
	{
		return method;
	}

	public void setMethod(String method) 
	{
		this.method = method;
	}

	public String getFrom() 
	{
		return from;
	}

	public void setFrom(String from) 
	{
		this.from = from;
	}

	public String getSubject() 
	{
		return subject;
	}

	public void setSubject(String subject) 
	{
		this.subject = subject;
	}

	public String getBcc() 
	{
		return bcc;
	}

	public void setBcc(String bcc) 
	{
		this.bcc = bcc;
	}

	public String getCc() 
	{
		return cc;
	}

	public void setCc(String cc) 
	{
		this.cc = cc;
	}

	public String getTemplate() 
	{
		return template;
	}

	public void setTemplate(String template) 
	{
		this.template = template;
	}

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}	
}