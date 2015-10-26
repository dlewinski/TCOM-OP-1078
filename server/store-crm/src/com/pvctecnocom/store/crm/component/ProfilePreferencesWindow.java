package com.pvctecnocom.store.crm.component;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.pvctecnocom.store.crm.event.StoreCrmEvent.CloseOpenWindowsEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.presenter.user.UserPresenter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
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
public class ProfilePreferencesWindow extends Window 
{
    public static final String ID = "profilepreferenceswindow";            
	
	@PropertyId("currentPassword")
	private PasswordField fldPasswordOld;
	
	@PropertyId("newPassword")
	private PasswordField fldPasswordNew;
		
	@PropertyId("confirmPassword")
	private PasswordField fldPasswordNewConfirm;
	
	private UserPresenter userPresenter;
	
	private final BeanFieldGroup<Password> beanFieldGroup;
	
	private Usuario userSession;
	
	private Password password;
	
    private ProfilePreferencesWindow(final Usuario user, final boolean preferencesTabOpen) 
    {
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        userPresenter = new UserPresenter();
        userSession = user;
        password = new Password();
        
        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);
        
        detailsWrapper.addComponent(buildProfileTab());       
        
        beanFieldGroup = new BeanFieldGroup<Password>(Password.class);
        beanFieldGroup.bindMemberFields(this);
        beanFieldGroup.setItemDataSource(password);
        beanFieldGroup.setBuffered(true);
              
        if (preferencesTabOpen)         
            detailsWrapper.setSelectedTab(1);        

        content.addComponent(buildFooter());
        
        fldPasswordOld.focus();
    }   

    private Component buildProfileTab() 
    {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Cambiar contraseña");
        root.setIcon(FontAwesome.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        VerticalLayout pic = new VerticalLayout();
        pic.setSizeUndefined();
        pic.setSpacing(true);
        Image profilePic = new Image(null, new ThemeResource("img/profile-pic-300px.jpg"));
        profilePic.setWidth(100.0f, Unit.PIXELS);
        pic.addComponent(profilePic);                        

        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        fldPasswordOld = new PasswordField("Contraseña actual");
        details.addComponent(fldPasswordOld);
        
        fldPasswordNew = new PasswordField("Nueva contraseña");
        details.addComponent(fldPasswordNew); 
        
        fldPasswordNewConfirm = new PasswordField("Confirmar contraseña");
        details.addComponent(fldPasswordNewConfirm); 
        
        TextField label = new TextField();
        label.setValue("La contraseña debe tener un mínimo de ocho caracteres y un máximo de diez caracteres");
        label.setEnabled(false);
        details.addComponent(label);
        
        fldPasswordOld.setRequired(true);
        fldPasswordNew.setRequired(true);
        fldPasswordNewConfirm.setRequired(true);
        
        fldPasswordOld.setNullRepresentation("");
        fldPasswordNew.setNullRepresentation("");
        fldPasswordNewConfirm.setNullRepresentation("");
        
        fldPasswordOld.setMaxLength(10);
        fldPasswordNew.setMaxLength(10);
        fldPasswordNewConfirm.setMaxLength(10);
        
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
            	try 
                {
	        		beanFieldGroup.commit();
	        		
	            	if(!BCrypt.checkpw(password.getCurrentPassword().concat(userSession.getSalt()), userSession.getPassword()))
	            	{
	            		Notification notification = new Notification("Atención",
	            			"La contraseña actual no coincide con la ingresada",
	            			Type.TRAY_NOTIFICATION);
							notification.setStyleName("failure small");
							notification.setPosition(Position.MIDDLE_CENTER);
							notification.show(Page.getCurrent());   
							
							fldPasswordOld.focus();	            		
	            	}
	            	else if(!password.isEqualsNewPassword())
	            	{
	            		Notification notification = new Notification("Atención",
	            			"La nueva contraseña no coincide con la confirmación ingresada",
	            			Type.TRAY_NOTIFICATION);
							notification.setStyleName("failure small");
							notification.setPosition(Position.MIDDLE_CENTER);
							notification.show(Page.getCurrent());   
							
							fldPasswordNew.focus();	            		
	            	}
	            	else
	            	{
	            		userSession.setSalt(BCrypt.gensalt(11));
	            		userSession.setPassword(BCrypt.hashpw(password.getNewPassword().concat(userSession.getSalt()), BCrypt.gensalt(11)));
	            		userPresenter.update(userSession);
	            		
	            		if(userSession.isStatusOK())
	                    {	
	                    	Notification success = new Notification("Contraseña actualizada correctamente");
	                    	success.setDelayMsec(2000);
	                    	success.setStyleName("bar success small");
	                    	success.setPosition(Position.BOTTOM_RIGHT);
	                    	success.show(Page.getCurrent());	                    	
	                    	
	                    	close();
	                    }	    
	            		else
	            		{
	            			Notification notification = new Notification("Atención", userSession.getStatusDescription(), Type.TRAY_NOTIFICATION);
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
					
					fldPasswordOld.focus();
                }	
            }
        });
                
        footer.addComponent(btnSave);
        footer.setComponentAlignment(btnSave, Alignment.TOP_RIGHT);
        footer.setExpandRatio(btnSave, 1);
        
        Button btnCancel = new Button("Cancelar");
       
        btnCancel.addClickListener(new ClickListener() 
        {
            @Override
            public void buttonClick(ClickEvent event) 
            {    
            	close();
            }
        });
        
        Label separadorSaveCancel = new Label();
        separadorSaveCancel.setWidth("2px");
        
        footer.addComponent(separadorSaveCancel);
        
        footer.addComponent(btnCancel);
        footer.setComponentAlignment(btnCancel, Alignment.TOP_RIGHT);
        
        return footer;
    }

    public static void open(final Usuario user, final boolean preferencesTabActive) 
    {
    	StoreCrmEventBus.post(new CloseOpenWindowsEvent());
        Window w = new ProfilePreferencesWindow(user, preferencesTabActive);
        w.setWidth(90.0f, Unit.PERCENTAGE);
        UI.getCurrent().addWindow(w);        
    }
    
	public void validationVisible(Boolean isVisible)
	{
		fldPasswordOld.setValidationVisible(isVisible);
		fldPasswordNew.setValidationVisible(isVisible);
    	fldPasswordNewConfirm.setValidationVisible(isVisible);
    	
    	if(isVisible)
		{
			fldPasswordOld.setComponentError(new UserError("Contraseña actual requerida"));
			fldPasswordNew.setComponentError(new UserError("Nueva contraseña requerida"));
			fldPasswordNewConfirm.setComponentError(new UserError("Confirmar contraseña requerida"));
		   
			fldPasswordOld.setDescription(null);
		    fldPasswordNew.setDescription(null);
		    fldPasswordNewConfirm.setDescription(null);
		}
    	else
		{
			fldPasswordOld.setComponentError(null);
			fldPasswordNew.setComponentError(null);
			fldPasswordNewConfirm.setComponentError(null);
		    
		    fldPasswordOld.setDescription("Contraseña actual requerida");
		    fldPasswordNew.setDescription("Nueva contraseña requerida");
		    fldPasswordNewConfirm.setDescription("Confirmar contraseña requerida");
		}
	}	
}