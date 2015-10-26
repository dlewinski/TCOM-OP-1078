package com.pvctecnocom.store.crm.mvp.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.SetupEmail;
import com.pvctecnocom.store.crm.mvp.presenter.email.SetupEmailPresenter;
import com.pvctecnocom.store.crm.mvp.presenter.password.PasswordPresenter;
import com.pvctecnocom.store.crm.util.Email;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class GetBackPasswordView extends VerticalLayout 
{	
	private PasswordPresenter passwordPresenter;
	
	final Label title = new Label();
	final Label lblRecuperarPassword = new Label();
	final TextField txtEmail = new TextField();
	final Button btnEnviarInstrucciones = new Button();
			
    public GetBackPasswordView() 
    {
        setSizeFull();

        passwordPresenter = new PasswordPresenter();
        
        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);   
    }

    private Component buildLoginForm() 
    {
        final VerticalLayout loginPanel = new VerticalLayout();
        
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        
        Responsive.makeResponsive(loginPanel);
        
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());                                   
              
        return loginPanel;
    }

    private Component buildFields() 
    {
    	VerticalLayout vlFields = new VerticalLayout();
    	vlFields.setSpacing(true);
    	vlFields.setWidth("100%");
    	
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setWidth("100%");
        fields.addStyleName("fields");        
        
        lblRecuperarPassword.setValue("Ingese su dirección de e-mail y le enviaremos las instrucciones para recuperar su contraseña");
                               
        txtEmail.setCaption("e-mail");        
        txtEmail.setMaxLength(200);
        txtEmail.setWidth("378px");
        txtEmail.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        txtEmail.setIcon(FontAwesome.ENVELOPE);
        
        btnEnviarInstrucciones.setCaption("Enviar instrucciones");       
        btnEnviarInstrucciones.setIcon(FontAwesome.PAPER_PLANE);
        btnEnviarInstrucciones.addStyleName(ValoTheme.BUTTON_PRIMARY);
        
        btnEnviarInstrucciones.addClickListener(new Button.ClickListener() 
        {            						
			private static final long serialVersionUID = -2372993179541126181L;

			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {   
				Boolean isValidEmail = passwordPresenter.validarEmail(txtEmail.getValue());
				
				if(isValidEmail)
					enviarEmail();
				else
				{
					String message = "El e-mail ingresado no corresponde a un usuario registrado";
			    	
			    	Notification notification = new Notification("Atención", message, Notification.Type.TRAY_NOTIFICATION);
			    	notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
	    			notification.setPosition(Position.MIDDLE_CENTER);
			    	notification.show(Page.getCurrent());			    				    	
				}
            }
        });
        
        vlFields.addComponents(lblRecuperarPassword);
        
        fields.addComponents(txtEmail);
        fields.addComponent(btnEnviarInstrucciones);
        fields.setComponentAlignment(txtEmail, Alignment.BOTTOM_LEFT);
        fields.setComponentAlignment(btnEnviarInstrucciones, Alignment.BOTTOM_RIGHT);                
        
        vlFields.addComponent(fields);
        
        txtEmail.focus();
        
        return vlFields;
    }

    private Component buildLabels() 
    {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("labels");
        header.setWidth("100%");

        title.setValue("PVC Tecnocom - Pedidos online");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_BOLD);
        title.addStyleName(ValoTheme.LABEL_COLORED);
        header.addComponent(title);
        
        return header;
    }               
    
    private void enviarEmail()
	{
		SetupEmailPresenter setupEmailPresenter = new SetupEmailPresenter();
		
		SetupEmail setupEmail = setupEmailPresenter.cargarSetupEmail("password");
		
		if(setupEmail != null)
		{
			List<String> mensajes = new ArrayList<String>();
			
			String code = UUID.randomUUID().toString();
			
			String mensaje = setupEmail.getUrl() + "?code=" + code;
			
			mensajes.add(mensaje);
			
			Email email = new Email(setupEmail);
						
			String resultEnvio = email.sendEmail(this.txtEmail.getValue(), mensajes);						
							    										
			this.passwordPresenter.updateCode(this.txtEmail.getValue(), code);
			
			StoreCRM_UI.getCurrent().updateContentRecuperarContrasenia(resultEnvio);
		}
		else
		{
			String message = "El email no se pudo enviar";
	    	
	    	Notification notification = new Notification("Atención", message, Notification.Type.TRAY_NOTIFICATION);
	    	notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
			notification.setPosition(Position.MIDDLE_CENTER);
	    	notification.show(Page.getCurrent());
		}
	}
}