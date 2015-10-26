package com.pvctecnocom.store.crm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.SetupEmail;

public class Email 
{		
	private SetupEmail setupEmail;
	
	public Email(SetupEmail setupEmail)
	{
		this.setupEmail = setupEmail;
	}
	
	public String sendEmail(String paraEmail, List<String> mensajes)
	{
		String mensaje = "";										
		
		try
		{			
			final String username = this.setupEmail.getUsername();
			final String password = this.setupEmail.getPassword();
	 
			Properties props = new Properties();			
			
			props.put("mail.smtp.host", this.setupEmail.getSmtpHost());
			props.put("mail.smtp.port", this.setupEmail.getSmtpPort());
			props.put("mail.smtp.auth", this.setupEmail.getSmtpAuth());
			
			if(setupEmail.getMethod().equalsIgnoreCase("SSL"))
			{					
				props.put("mail.smtp.socketFactory.port", this.setupEmail.getSmtpPort());
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");								
			}
			else
			{					
				props.put("mail.smtp.starttls.enable", this.setupEmail.getSmtpStarttls());								
			}								
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() 
															{
																protected PasswordAuthentication getPasswordAuthentication() 
																{
																	return new PasswordAuthentication(username, password);
																}
															});
			
					
												
			String plantillaHtml = this.readFileAsString(StoreCRM_UI.basePathResources + File.separator + "templates" + File.separator + setupEmail.getTemplate());
			
			String textoToReplace;
			
			for(int i=0; i<mensajes.size(); i++)
			{
				textoToReplace = "#TEXTO_" + i + "#";
				
				plantillaHtml = plantillaHtml.replaceFirst(textoToReplace, mensajes.get(i));
			}
							
			MimeMessage message;
			
			int size = 1;	
			
			for(int i=0; i<size; i++)
			{
				String plantillaHtmlFinal =  plantillaHtml;								
								
				if(paraEmail != null && !paraEmail.equals(""))
				{	
					message = new MimeMessage(session);				
					
					message.setFrom(new InternetAddress(this.setupEmail.getUsername(), this.setupEmail.getFrom()));					
					
					message.setSubject(this.setupEmail.getSubject(), "ISO-8859-1");
					
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(paraEmail));	
					
					if(this.setupEmail.getCc() != null && !this.setupEmail.getCc().equalsIgnoreCase(""))
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(this.setupEmail.getCc()));
					
					if(this.setupEmail.getBcc() != null && !this.setupEmail.getBcc().equalsIgnoreCase(""))
						message.addRecipient(Message.RecipientType.BCC, new InternetAddress(this.setupEmail.getBcc()));
					
					plantillaHtmlFinal = plantillaHtmlFinal.replaceFirst("#TEXTO#", "");
					
					BodyPart texto = new MimeBodyPart();					
					texto.setContent(plantillaHtmlFinal, "text/html");
					
					MimeMultipart multiParte = new MimeMultipart();
					multiParte.addBodyPart(texto);
									
					message.setContent(multiParte);						
					
					Transport.send(message);
				}
			}																																						
			
			mensaje = "El email ha sido enviado a la casilla de correo indicada";						
		}
		catch(Exception e)
		{
			e.printStackTrace();
			mensaje = "Ha ocurrido un inconveniente al intentar enviar el email";						
		}
		
		return mensaje;
	}		
	
	private String readFileAsString(String filePath) throws java.io.IOException
	{
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		
		while((numRead=reader.read(buf)) != -1)
		{
			String readData = String.valueOf(buf, 0, numRead);
	        fileData.append(readData);
	        buf = new char[1024];
        }
		
        reader.close();
        return fileData.toString();
    }	
}