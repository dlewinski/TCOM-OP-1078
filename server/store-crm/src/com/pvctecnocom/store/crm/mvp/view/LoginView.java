package com.pvctecnocom.store.crm.mvp.view;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.UserLoginRequestedEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout 
{
	final TextField username = new TextField();
	final PasswordField password = new PasswordField();
	final Button signin = new Button();
	final Label title = new Label();
	final Button btnOlvidoContrasenia = new Button();
	
    public LoginView() 
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
        
        HorizontalLayout hlOlvidoContrasenia = (HorizontalLayout)buildOlvidoContrasenia();                
        
        loginPanel.addComponent(hlOlvidoContrasenia);
        loginPanel.setComponentAlignment(hlOlvidoContrasenia, Alignment.BOTTOM_RIGHT);
              
        return loginPanel;
    }

    private Component buildFields() 
    {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");
        
        username.setCaption("Usuario");
		password.setCaption("Contraseña");
		signin.setCaption("Iniciar Sesión");
		
		username.setDescription("Ingrese Usuario");
		password.setDescription("Ingrese Contraseña");
		signin.setDescription("Iniciar Sesión");
        
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
       
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
       
        signin.setIcon(FontAwesome.UNLOCK);
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
      
        username.focus();

        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        signin.addClickListener(new ClickListener() 
        {
            @Override
            public void buttonClick(final ClickEvent event) 
            {        
            	StoreCrmEventBus.post(new UserLoginRequestedEvent(username.getValue(), password.getValue()));            	
            }
        });
        
        signin.setClickShortcut(KeyCode.ENTER);
        
        return fields;
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
    
    private Component buildOlvidoContrasenia() 
    {
    	HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");
        
        btnOlvidoContrasenia.setCaption("Olvidó su contraseña?");
        btnOlvidoContrasenia.setDescription("Olvidó su contraseña?");
        btnOlvidoContrasenia.setIcon(FontAwesome.EXTERNAL_LINK_SQUARE);
        btnOlvidoContrasenia.addStyleName(ValoTheme.BUTTON_LINK);
        
        btnOlvidoContrasenia.addClickListener(new ClickListener() 
        {
            @Override
            public void buttonClick(final ClickEvent event) 
            {        
            	((StoreCRM_UI)UI.getCurrent()).setContent(new GetBackPasswordView());          	
            }
        });
        
        fields.addComponents(btnOlvidoContrasenia);
        fields.setComponentAlignment(btnOlvidoContrasenia, Alignment.BOTTOM_RIGHT);
        
    	return fields;
    }
}