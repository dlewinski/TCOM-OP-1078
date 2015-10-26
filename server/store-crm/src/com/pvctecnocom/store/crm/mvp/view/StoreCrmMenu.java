package com.pvctecnocom.store.crm.mvp.view;

import com.google.common.eventbus.Subscribe;
import com.pvctecnocom.store.crm.component.ProfilePreferencesWindow;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.PostViewChangeEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.ProfileUpdatedEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.UserLoggedOutEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@SuppressWarnings({ "serial"})
public final class StoreCrmMenu extends CustomComponent 
{
    public static final String ID = "storeCRM-menu";    
    private static final String STYLE_VISIBLE = "valo-menu-visible";    
    private MenuItem settingsItem;    
    
    public StoreCrmMenu() 
    {    	
        addStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();

        // There's only one OrdersMenu per UI so this doesn't need to be
        // unregistered from the UI-scoped OrdersEventBus.
        StoreCrmEventBus.register(this);

        setCompositionRoot(buildContent());
    }

    private Component buildContent() 
    {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());
     
        return menuContent;
    }

    private Component buildTitle() 
    {
        Label logo = new Label("<strong>PVC Tecnocom - Pedidos online</strong>", ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);        
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
     
        return logoWrapper;
    }        
    
    private Component buildUserMenu() 
    {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        
        final Usuario user = new Usuario().getUserSession();
        
        settingsItem = settings.addItem("", new ThemeResource("img/profile-pic-300px.jpg"), null);
        
        updateUserName(null);
        
        settingsItem.addItem("Cambiar Contraseña", new Command() 
        {
            @Override
            public void menuSelected(final MenuItem selectedItem) 
            {
                ProfilePreferencesWindow.open(user, false);
            }
        });                
        
        settingsItem.addSeparator();
        
        settingsItem.addItem("Cerrar Sesión", new Command() 
        {
            @Override
            public void menuSelected(final MenuItem selectedItem) 
            {
            	StoreCrmEventBus.post(new UserLoggedOutEvent());
            }
        });
        
        return settings;
    }

    private Component buildToggleButton() 
    {
        Button valoMenuToggleButton = new Button("Menú", new ClickListener() 
        {
            @Override
            public void buttonClick(final ClickEvent event) 
            {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE))                
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);                
                else                 
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);                
            }
        });
        
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() 
    {
    	CssLayout menuItemsLayout;
    	Label separador;
    	Component menuItemComponent;
    	StoreCrmViewType view;
    	
        menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);

        separador = new Label("Administración", ContentMode.HTML);
        separador.setPrimaryStyleName("valo-menu-subtitle");
        separador.addStyleName("h4");
        separador.setSizeUndefined();        

        menuItemsLayout.addComponent(separador);
        
        view = StoreCrmViewType.USER;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);

        separador = new Label("SAP", ContentMode.HTML);
        separador.setPrimaryStyleName("valo-menu-subtitle");
        separador.addStyleName("h4");
        separador.setSizeUndefined();        

        menuItemsLayout.addComponent(separador);
        
        view = StoreCrmViewType.SAP_CONNECT;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        separador = new Label("Actualización de caches", ContentMode.HTML);
        separador.setPrimaryStyleName("valo-menu-subtitle");
        separador.addStyleName("h4");
        separador.setSizeUndefined();        

        menuItemsLayout.addComponent(separador);
        
        view = StoreCrmViewType.SAP_ARTICULOS;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        view = StoreCrmViewType.SAP_CATEGORIAS;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        view = StoreCrmViewType.SAP_CENTRO_ALMACEN;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        separador = new Label("Prueba de RFCs", ContentMode.HTML);
        separador.setPrimaryStyleName("valo-menu-subtitle");
        separador.addStyleName("h4");
        separador.setSizeUndefined();        

        menuItemsLayout.addComponent(separador);
        
        view = StoreCrmViewType.SAP_CLIENTE;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        view = StoreCrmViewType.SAP_CLIENTES;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        view = StoreCrmViewType.SAP_DESCUENTOS;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        view = StoreCrmViewType.SAP_OFERTAS;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        view = StoreCrmViewType.SAP_PEDIDOS;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);
        
        view = StoreCrmViewType.SAP_PRECIOS;        
        menuItemComponent = new ValoMenuItemButton(view);
        menuItemsLayout.addComponent(menuItemComponent);                

        return menuItemsLayout;
    }
    
    
    @Override
    public void attach() 
    {
        super.attach();        
    }

    @Subscribe
    public void postViewChange(final PostViewChangeEvent event) 
    {
        // After a successful view change the menu can be hidden in mobile view.
        getCompositionRoot().removeStyleName(STYLE_VISIBLE);
    }      

    @Subscribe
    public void updateUserName(final ProfileUpdatedEvent event) 
    {
        Usuario user = new Usuario().getUserSession();
        String dataUser = "";
        
        if(user.getNombre() != null && user.getApellido() != null)
        	dataUser += user.getNombre() + " " + user.getApellido();
        else
        	dataUser += user.getUsername();        	        
        
        settingsItem.setText(dataUser);
    }
               
    public final class ValoMenuItemButton extends Button 
    {
        private static final String STYLE_SELECTED = "selected";

        private final StoreCrmViewType view;

        public ValoMenuItemButton(final StoreCrmViewType view) 
        {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getCaption());

            StoreCrmEventBus.register(this);

            addClickListener(new ClickListener() 
            {
                @Override
                public void buttonClick(final ClickEvent event) 
                {
                    UI.getCurrent().getNavigator().navigateTo(view.getViewName());
                }
            });
        }

        @Subscribe
        public void postViewChange(final PostViewChangeEvent event) 
        {
            removeStyleName(STYLE_SELECTED);

            if (event.getView() == view)
                addStyleName(STYLE_SELECTED);            
        }
    }
}