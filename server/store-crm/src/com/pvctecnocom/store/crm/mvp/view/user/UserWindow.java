package com.pvctecnocom.store.crm.mvp.view.user;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.vaadin.dialogs.ConfirmDialog;

import com.pvctecnocom.store.crm.event.StoreCrmEvent.CloseOpenWindowsEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.UserChangedEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.bo.Perfil;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.presenter.perfil.PerfilPresenter;
import com.pvctecnocom.store.crm.mvp.presenter.user.UserPresenter;
import com.pvctecnocom.store.crm.mvp.view.GenericWindow;
import com.pvctecnocom.store.crm.util.AppUtils;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class UserWindow extends Window implements GenericWindow
{
	public static final String ID = "userWindow";
	
	@PropertyId("username")
	private TextField fldUsername;
	
	@PropertyId("password")	
	private PasswordField fldPassword;
	
	@PropertyId("passwordConfirm")
	private PasswordField fldPasswordConfirm;
	
	@PropertyId("nombre")	
	private TextField fldFirstname;
	
	@PropertyId("apellido")	
	private TextField fldLastname;
	
	@PropertyId("habilitado")
	private CheckBox fldHabilitado;
	
	@PropertyId("telefono")
	private TextField fldTelefono;

	@PropertyId("email")
	private TextField fldEmail;
	
	private ComboBox fldPerfil;
	
	@PropertyId("numeroVendedorSAP")
	private TextField fldNumeroVendedorSAP;
	
	private final BeanFieldGroup<Usuario> beanFieldGroup;
			
	private UserPresenter userPresenter;	
	
	private PerfilPresenter perfilPresenter;
	
	private Usuario user;
	
	private String action;
	
	private boolean isQuery;
	
	private UserWindow(Usuario user, final int tabActive, final String action, final boolean isQuery) 
    {
		this.isQuery = isQuery;
		
		this.action = action;
		
		this.user = user;		
		
		userPresenter = new UserPresenter();		
		
		perfilPresenter = new PerfilPresenter();
		
		addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);      
        
        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(90.0f, Unit.PERCENTAGE);
        setWidth(90f, Unit.PERCENTAGE);
        
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);
        
        TabSheet tabs = new TabSheet();
        tabs.setSizeFull();
        tabs.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabs.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        tabs.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(tabs);
        content.setExpandRatio(tabs, 1f);
        
        beanFieldGroup = new BeanFieldGroup<Usuario>(Usuario.class);
        beanFieldGroup.bindMemberFields(this);
        beanFieldGroup.setItemDataSource(user);
        beanFieldGroup.setBuffered(true);
        
        tabs.addComponent(buildUserTab());
        
        tabs.setSelectedTab(tabActive);
        
        content.addComponent(buildFooter());
        
        if(action.equalsIgnoreCase("edit"))
        {	
        	fldPasswordConfirm.setValue(fldPassword.getValue());        	
        	fldPassword.setEnabled(false);
        	fldPasswordConfirm.setEnabled(false);        	
        }
        else
        {        	
        	fldPassword.setEnabled(true);
        	fldPasswordConfirm.setEnabled(true);        	
        }   
        
        fldUsername.focus();
    }
	
	private Component buildUserTab() 
    {
		HorizontalLayout root = new HorizontalLayout();
		
		if(action.equalsIgnoreCase("edit"))      
			root.setCaption("Editar Usuario");
		else
			root.setCaption("Nuevo Usuario");
			
        root.setIcon(FontAwesome.LIST);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");
        
        FormLayout frmLayout = new FormLayout();
        frmLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(frmLayout);
        root.setExpandRatio(frmLayout, 1);
        
        Label section = new Label("Usuario");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        frmLayout.addComponent(section);
        
        fldUsername = new TextField("Username");
        fldUsername.setNullRepresentation("");                
        fldUsername.setMaxLength(20);	
        frmLayout.addComponent(fldUsername);
        
        TextField leyendaUsername = new TextField();
        leyendaUsername.setValue("El Username puede tener un máximo de veinte caracteres");
        leyendaUsername.setEnabled(false);
        frmLayout.addComponent(leyendaUsername);

        fldPassword = new PasswordField("Contraseña");
        fldPassword.setNullRepresentation("");
        fldPassword.setMaxLength(10);
        frmLayout.addComponent(fldPassword);
        
        fldPasswordConfirm = new PasswordField("Confirmar contraseña");
        fldPasswordConfirm.setNullRepresentation("");
        fldPasswordConfirm.setMaxLength(10); 
        frmLayout.addComponent(fldPasswordConfirm);
    	
        TextField leyendaPassword = new TextField();
        leyendaPassword.setValue("La contraseña debe tener un mínimo de ocho caracteres y un máximo de diez caracteres");
        leyendaPassword.setEnabled(false);
        frmLayout.addComponent(leyendaPassword);
        
    	fldFirstname = new TextField("Nombre");
    	fldFirstname.setNullRepresentation("");
    	fldFirstname.setMaxLength(250);
        frmLayout.addComponent(fldFirstname);
    	
    	fldLastname = new TextField("Apellido");
    	fldLastname.setNullRepresentation("");
    	fldLastname.setMaxLength(250);
        frmLayout.addComponent(fldLastname);     
        
        fldHabilitado = new CheckBox();        
        HorizontalLayout hlHabilitado = new HorizontalLayout();
        hlHabilitado.setCaption("Acceso habilitado");
        hlHabilitado.addComponent(fldHabilitado);
        frmLayout.addComponent(hlHabilitado);        
        
        fldTelefono = new TextField("Teléfono");
        fldTelefono.setNullRepresentation("");                
        fldTelefono.setMaxLength(100);	
        frmLayout.addComponent(fldTelefono);
        
        fldEmail = new TextField("Email");
        fldEmail.setNullRepresentation("");                
        fldEmail.setMaxLength(250);	
        frmLayout.addComponent(fldEmail);
        
        List<Perfil> perfiles = perfilPresenter.findAll();                                                          
        
        BeanItemContainer<Perfil> bicPerfiles = new BeanItemContainer<Perfil>(Perfil.class, perfiles);	
        
        fldPerfil = new ComboBox("Perfil", bicPerfiles);        
        beanFieldGroup.bind(fldPerfil, "perfil");
        fldPerfil.setItemCaptionPropertyId("nombre"); 
        fldPerfil.setFilteringMode(FilteringMode.CONTAINS);
        fldPerfil.setInputPrompt("Seleccionar Perfil");
        frmLayout.addComponent(fldPerfil);
        
        fldNumeroVendedorSAP = new TextField("Número Vendedor SAP");
        fldNumeroVendedorSAP.setNullRepresentation("");                
        fldNumeroVendedorSAP.setMaxLength(10);	
        frmLayout.addComponent(fldNumeroVendedorSAP);
        
        TextField leyendaNumeroVendedorSAP = new TextField();
        leyendaNumeroVendedorSAP.setValue("Recordar colocar el cero (0) a la izquierda para el Número de Vendedor SAP cuando corresponda");
        leyendaNumeroVendedorSAP.setEnabled(false);
        frmLayout.addComponent(leyendaNumeroVendedorSAP);
        
        fldUsername.setRequired(true);
        fldPassword.setRequired(true);
        fldPasswordConfirm.setRequired(true);    	                                     
        
    	beanFieldGroup.bindMemberFields(this);
        validationVisible(false);                             
        
        return root;
    }
	
	private Component buildFooter() 
    {
		HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(true);                   
        
        Button btnSave = new Button("Guardar");
        btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
        
        btnSave.addClickListener(new ClickListener() 
        {
            @Override
            public void buttonClick(ClickEvent event) 
            {        
            	String notificationMessageSuccess = "";
            	
            	user.setStatusOK(true);
            	user.setStatusCode("0");
            	user.setStatusDescription("");
            	
            	try 
                {
            		beanFieldGroup.commit();
            		
            		if(!user.getPassword().equals(user.getPasswordConfirm()))
 	            	{
 	            		Notification notification = new Notification(
 	            				"Atención",
 	            				"La contraseña no coincide con la confirmación ingresada",
 	            				Type.TRAY_NOTIFICATION);
 	            		
 						notification.setStyleName("failure small");
 						notification.setPosition(Position.MIDDLE_CENTER);
 						notification.show(Page.getCurrent());   
 						
 						fldPassword.focus(); 	            		
 	            	}
            		else
            		{
	            		if(action.equalsIgnoreCase("edit"))
	            		{	
	            			userPresenter.update(user);
	            			notificationMessageSuccess = "Usuario actualizado correctamente";
	            		}	
	            		else
	            		{	
	 	            		user.setSalt(BCrypt.gensalt(11));
	                    	user.setPassword(BCrypt.hashpw(fldPassword.getValue().concat(user.getSalt()), BCrypt.gensalt(11)));
	            			userPresenter.insert(user);
	            			notificationMessageSuccess = "Usuario creado correctamente";	     	            	
	            		}	
	            		
	                    if(user.isStatusOK())
	                    {	
	                    	Notification success = new Notification(notificationMessageSuccess);
	                    	success.setDelayMsec(2000);
	                    	success.setStyleName("bar success small");
	                    	success.setPosition(Position.BOTTOM_RIGHT);
	                    	success.show(Page.getCurrent());
	
	                    	StoreCrmEventBus.post(new UserChangedEvent());
	                    	
	                    	close();
	                    }
	                    else     
	                    {
	                    	Notification notification = new Notification("Atención", user.getStatusDescription(), Type.TRAY_NOTIFICATION);
	    					notification.setStyleName("failure small");
	    					notification.setPosition(Position.MIDDLE_CENTER);
	    					notification.show(Page.getCurrent());   
	                    }
 	            	}
            	}
            	catch (CommitException e) 
                {
            		validationVisible(true);
            		
            		Notification notification = new Notification("Atención", "Por favor, complete todos los campos requeridos *", Type.TRAY_NOTIFICATION);
					notification.setStyleName("failure small");
					notification.setPosition(Position.MIDDLE_CENTER);
					notification.show(Page.getCurrent());   
                }
            }
        });
        
        Button btnCancel = new Button("Cancelar");        
        
        btnCancel.addClickListener(new ClickListener() 
        {
            @Override
            public void buttonClick(ClickEvent event) 
            {                
            	close();
            }
        });
        
        Button btnResetPass = new Button("Reiniciar Contraseña");
        btnResetPass.addStyleName(ValoTheme.BUTTON_PRIMARY);
        
        btnResetPass.addClickListener(new ClickListener() 
        {
            @Override
            public void buttonClick(ClickEvent event) 
            {                
            	ConfirmDialog.show(UI.getCurrent(), 
            			"Atención",
    					"Confirma asignar la contraseña 12345678 al usuario # ?".replaceFirst("#", user.getUsername()), 
    					"Aceptar", 
    					"Cancelar",
    					
    					new ConfirmDialog.Listener() 
            			{    				
							@Override
							public void onClose(ConfirmDialog arg0) 
							{							
								if(arg0.isConfirmed())
								{
									String pass = "12345678";
									user.setSalt(BCrypt.gensalt(11));
									user.setPassword(BCrypt.hashpw(pass.concat(user.getSalt()), BCrypt.gensalt(11)));
									userPresenter.update(user);
		            		
									if(user.isStatusOK())
									{	
										Notification success = new Notification("Contraseña actualizada correctamente");
										success.setDelayMsec(2000);
										success.setStyleName("bar success small");
										success.setPosition(Position.BOTTOM_RIGHT);
										success.show(Page.getCurrent());
			
										StoreCrmEventBus.post(new UserChangedEvent());
			                    	
										close();
									}
									else     
									{
										Notification notification = new Notification("Atención", user.getStatusDescription(), Type.TRAY_NOTIFICATION);
										notification.setStyleName("failure small");
										notification.setPosition(Position.MIDDLE_CENTER);
										notification.show(Page.getCurrent());   
									}
								}
							}
            			});
            		}
        		});
        
        Label separadorSaveCancel = new Label();
        separadorSaveCancel.setWidth("2px");
        
        Label separadorResetSave = new Label();
        separadorResetSave.setWidth("2px");
        
        if(action.equalsIgnoreCase("edit"))
        {
        	AppUtils.buildLastUpdatedInfo(beanFieldGroup.getItemDataSource().getBean(), footer);
        	
	    	footer.addComponents(btnResetPass, separadorResetSave, btnSave, separadorSaveCancel, btnCancel );
	        footer.setComponentAlignment(btnResetPass, Alignment.TOP_RIGHT);
	        footer.setExpandRatio(btnResetPass, 1);
        }
        else
        {        	            
            footer.addComponents(btnSave, separadorSaveCancel, btnCancel);
            footer.setExpandRatio(btnSave, 1);
        }                   
        
        footer.setComponentAlignment(btnSave, Alignment.TOP_RIGHT);         
        footer.setComponentAlignment(btnCancel, Alignment.TOP_RIGHT);                
        
        btnSave.setVisible(!isQuery);
        btnResetPass.setVisible(!isQuery);
        
        return footer;
    }
	
	public static void open(final Usuario user, final int tabActive, final String action, final boolean isQuery) 
    {
		StoreCrmEventBus.post(new CloseOpenWindowsEvent());
        Window window = new UserWindow(user, tabActive, action, isQuery);
        UI.getCurrent().addWindow(window);       
    }	
		
	@Override
	public void validationVisible(Boolean isVisible)
	{
		fldUsername.setValidationVisible(isVisible);
		fldPassword.setValidationVisible(isVisible);
    	fldPasswordConfirm.setValidationVisible(isVisible);
    	
    	if(isVisible)
		{
			fldUsername.setComponentError(new UserError("Username requerido"));
		    fldPassword.setComponentError(new UserError("Contraseña requerida"));
		    fldPasswordConfirm.setComponentError(new UserError("Confirmar Contraseña requerida"));
		    fldUsername.setDescription(null);
		    fldPassword.setDescription(null);
		    fldPasswordConfirm.setDescription(null);
		}
    	else
		{
			fldUsername.setComponentError(null);
		    fldPassword.setComponentError(null);
		    fldPasswordConfirm.setComponentError(null);
		    
		    fldUsername.setDescription("Username requerido");
		    fldPassword.setDescription("Contraseña requerida");
		    fldPasswordConfirm.setDescription("Confirmar Contraseña requerida");
		}
	}	
}