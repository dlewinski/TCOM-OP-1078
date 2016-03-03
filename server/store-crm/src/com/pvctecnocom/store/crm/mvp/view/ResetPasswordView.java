package com.pvctecnocom.store.crm.mvp.view;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.presenter.user.UserPresenter;
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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ResetPasswordView extends VerticalLayout 
{		
	final Label title = new Label();
	final Label lblRecuperarPassword = new Label();
	
	private PasswordField fldPassword;		
	private PasswordField fldPasswordConfirm;
	
	final Button btnEnviarInstrucciones = new Button();		
	
    public ResetPasswordView() 
    {    	    	    	
        setSizeFull();        
        
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
        
        lblRecuperarPassword.setValue("Ingese su nueva contraseña");
                                           
        vlFields.addComponent(lblRecuperarPassword);
        
        fldPassword = new PasswordField("Contraseña");
        fldPassword.setNullRepresentation("");
        fldPassword.setMaxLength(10);
        fldPassword.setWidth("200px");
        fldPassword.setIcon(FontAwesome.LOCK);
        fldPassword.setDescription("Contraseña");
        
        fldPasswordConfirm = new PasswordField("Confirmar contraseña");
        fldPasswordConfirm.setNullRepresentation("");
        fldPasswordConfirm.setMaxLength(10);
        fldPasswordConfirm.setWidth("200px");
        fldPasswordConfirm.setIcon(FontAwesome.LOCK);        
        fldPasswordConfirm.setDescription("Confirmar contraseña");
        
        Button btnCambiarPassword = new Button("Cambiar contraseña");
        btnCambiarPassword.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnCambiarPassword.setDescription("Cambiar contraseña");
        btnCambiarPassword.setIcon(FontAwesome.CHECK);        
        
        btnCambiarPassword.addClickListener(new Button.ClickListener() 
        {            						
			private static final long serialVersionUID = 411175704423758017L;

			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {   
				if(fldPassword.getValue() != null && fldPassword.getValue().length() > 7)
				{	
					if(fldPassword.getValue().equals(fldPasswordConfirm.getValue()))
					{	
						Usuario user = new Usuario().getUserSession();
						
						user.setSalt(BCrypt.gensalt(11));
						user.setPassword(BCrypt.hashpw(fldPassword.getValue().concat(user.getSalt()), BCrypt.gensalt(11)));
						user.setResetPassword(false);
						
						UserPresenter userPresenter = new UserPresenter();
						
	            		userPresenter.update(user);	            								
						
						if(user.isStatusOK())
						{
							String message = "La nueva contraseña se registró correctamente";
					    	
							Notification notification = new Notification("Atención", message, Notification.Type.TRAY_NOTIFICATION, true);
				        	notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);			
				   		 	notification.setPosition(Position.MIDDLE_CENTER);
				   		 	notification.show(Page.getCurrent());
				   		 	
				   		 	StoreCRM_UI.getCurrent().updateContent();
						}
						else
						{
							String message = "La contraseña no se pudo cambiar";
					    	
					    	Notification notification = new Notification(message,"", Notification.Type.TRAY_NOTIFICATION, true);
					    	notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
				   		 	notification.setPosition(Position.MIDDLE_CENTER);
				   		 	notification.show(Page.getCurrent());
						}
					}
					else
					{
						String message = "La contraseña y su confirmación no son iguales";
			        	
			        	Notification notification = new Notification("Atención", message, Notification.Type.TRAY_NOTIFICATION, true);
			        	notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
			   		 	notification.setPosition(Position.MIDDLE_CENTER);
			   		 	notification.show(Page.getCurrent()); 
			   		 	
			   		 	fldPassword.focus();
					}
				}
				else
				{
					String message = "La contraseña debe tener al menos 8 caracteres";
		        	
		        	Notification notification = new Notification("Atención", message, Notification.Type.TRAY_NOTIFICATION, true);
		        	notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
		   		 	notification.setPosition(Position.MIDDLE_CENTER);
		   		 	notification.show(Page.getCurrent());
		   		 	
		   		 	fldPassword.focus();
				}
            }
        });
        
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setWidth("100%");
        fields.addStyleName("fields");
        
        fields.addComponent(fldPassword);
        fields.addComponent(fldPasswordConfirm);
        fields.addComponent(btnCambiarPassword);
        fields.setComponentAlignment(btnCambiarPassword, Alignment.BOTTOM_LEFT);
        
        vlFields.addComponent(fields);
        
        Label leyendaPassword = new Label();
        leyendaPassword.setValue("La contraseña debe tener un mínimo de ocho caracteres y un máximo de diez caracteres");
        leyendaPassword.setEnabled(false);
        vlFields.addComponent(leyendaPassword);                             
        
        fldPassword.focus();
                
    	String message = "Dado que su contraseña fue reiniciada por el Administrador, le solicitamos por favor que cambie la misma";
    	
    	Notification notification = new Notification("Atención", message, Notification.Type.TRAY_NOTIFICATION, true);
    	notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
	 	notification.setPosition(Position.MIDDLE_CENTER);
	 	notification.show(Page.getCurrent());        
        
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
}