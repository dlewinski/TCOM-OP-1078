package com.pvctecnocom.store.crm.mvp.view.user;

import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.google.common.eventbus.Subscribe;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.CloseOpenWindowsEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.UserChangedEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.presenter.user.UserPresenter;
import com.pvctecnocom.store.crm.mvp.view.GenericView;
import com.pvctecnocom.store.crm.mvp.view.StoreCrmViewType;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class UserView extends Panel implements View, GenericView 
{   
	public static final String TITLE_ID = "user-title";
	
	private Label titleLabel;	
	private Button addUser;
	private UserPresenter userPresenter;  
	private final VerticalLayout root;
	private final Table table;		
	private final boolean isQuery;
	private TextField filter;
	
    public UserView() 
    {   
    	isQuery = StoreCrmViewType.getByViewName(StoreCrmViewType.USER.getViewName()).isQuery();
    	userPresenter = new UserPresenter(); 
    	
    	addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        StoreCrmEventBus.register(this);
        
        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);
        
        root.addComponent(buildHeader());
        
        table = buildTable();        
        
        root.addComponent(table);
        root.setExpandRatio(table, 1);
        
        // All the open sub-windows should be closed whenever the root layout gets clicked.
        root.addLayoutClickListener(new LayoutClickListener() 
        {
            @Override
            public void layoutClick(final LayoutClickEvent event) 
            {
            	StoreCrmEventBus.post(new CloseOpenWindowsEvent());
            }
        });
        
        filter.focus();
    }
    
    private Component buildHeader() 
    {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setSpacing(true);

        titleLabel = new Label("Usuarios");
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H2);
        
        header.addComponent(titleLabel); 
        header.setComponentAlignment(titleLabel, Alignment.TOP_LEFT);

        addUser = buildAddUser();
        
        HorizontalLayout tools = new HorizontalLayout(buildFilter(), addUser);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        
        header.addComponent(tools);
        header.setComponentAlignment(tools, Alignment.TOP_RIGHT);
        
        addUser.setVisible(!isQuery);
        
        return header;
    }
    
    private Table buildTable() 
    {
        final Table table = new Table() 
        {
            @Override
            protected String formatPropertyValue(final Object rowId, final Object colId, final Property<?> property) 
            {
            	return super.formatPropertyValue(rowId, colId, property);                                                
            }
        };
        
        List<Usuario> users = userPresenter.findAll();
		
        BeanItemContainer<Usuario> bicUsers = new BeanItemContainer<Usuario>(Usuario.class, users); 	
        
        table.setSizeFull();
        table.addStyleName(ValoTheme.TABLE_BORDERLESS);
        table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);       
                       
        table.setContainerDataSource(bicUsers);
                
        table.addGeneratedColumn("perfil", new Table.ColumnGenerator()
		{
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId)
			{
				Label lblResult = new Label();
				final Usuario usuarioSelected = (Usuario) itemId;
				
				if(usuarioSelected != null && usuarioSelected.getPerfil() != null)
				{													
					String nombrePerfil = usuarioSelected.getPerfil().getNombre();

					if(nombrePerfil != null)				
						lblResult.setValue(nombrePerfil);									
				}
				
				return lblResult;
			}
		});
        
        addGeneratedColumnActions(table);
        
        table.setVisibleColumns(
        		"username", 
        		"apellido",
        		"nombre",
        		"perfil",
        		"actions"
        		);
        
        table.setColumnHeaders( 
        		"Username", 
          		"Apellido",
          		"Nombre",
          		"Perfil",
          		"Acciones"
          		);
                
        table.setColumnWidth("actions", 80);
        
        table.setColumnAlignment("actions", Align.CENTER);
        
        return table;
    }

	@Override
	public void enter(ViewChangeEvent event) 
	{		
	}
	
	private Button buildAddUser() 
	{
        final Button addUser = new Button("Nuevo");
        addUser.setDescription("Nuevo Usuario");

        addUser.addClickListener(new ClickListener() 
        {
            @Override
            public void buttonClick(final ClickEvent event) 
            {                     	
            	addItem(new Usuario());
            }
        });
                
        return addUser;
    }
	
	private Component buildFilter() 
	{
        filter = new TextField();
        
        filter.addTextChangeListener(new TextChangeListener() 
        {
            @Override
            public void textChange(final TextChangeEvent event) 
            {
                Filterable data = (Filterable) table.getContainerDataSource();
                data.removeAllContainerFilters();
                data.addContainerFilter(new Filter() 
                {
                    @Override
                    public boolean passesFilter(final Object itemId, final Item item) 
                    {
                        if (event.getText() == null || event.getText().equals(""))                         
                            return true;                        

                        return filterByProperty("username", item, event.getText())
                            || filterByProperty("nombre", item, event.getText())
                            || filterByProperty("apellido", item, event.getText())
                            || filterByProperty("perfil", item, event.getText());
                    }

                    @Override
                    public boolean appliesToProperty(final Object propertyId) 
                    {
                        if (propertyId.equals("username")
                         || propertyId.equals("nombre")
                         || propertyId.equals("apellido")
                         || propertyId.equals("perfil"))   
                            return true;                        
                        
                        return false;
                    }
                });
            }
        });

        filter.setInputPrompt("Buscar");
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        
        filter.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) 
        {
            @Override
            public void handleAction(final Object sender, final Object target) 
            {
                filter.setValue("");
                ((Filterable) table.getContainerDataSource()).removeAllContainerFilters();
            }
        });
        
        return filter;
    }
	
	private boolean filterByProperty(final String prop, final Item item, final String text) 
	{

        if (item == null || item.getItemProperty(prop) == null || item.getItemProperty(prop).getValue() == null)         
            return false;        
        
        String val = item.getItemProperty(prop).getValue().toString().trim().toLowerCase();
        
        if (val.contains(text.toLowerCase().trim()))         
            return true;        
        
        return false;
    }				
	
	@Subscribe
	public void userChanged(UserChangedEvent event)
	{						
		table.removeAllItems();
		table.addItems(userPresenter.findAll());
	}
	
	@Override
	public void addGeneratedColumnActions(Table table)
	{
		table.addGeneratedColumn("actions", new Table.ColumnGenerator() 
		{			
			@Override
			public Object generateCell(Table source, final Object item, Object column) 
			{				
				HorizontalLayout hlActions = new HorizontalLayout();
				hlActions.setSizeFull();
				hlActions.setSpacing(true);
				Button btnEdit = new Button("");
				btnEdit.setIcon(FontAwesome.PENCIL_SQUARE_O);
				btnEdit.setStyleName(ValoTheme.BUTTON_LINK);
				btnEdit.setDescription("Editar");
				
				btnEdit.addClickListener(new ClickListener() 
		        {
		            @Override
		            public void buttonClick(final ClickEvent event) 
		            {         	 
		            	editItem(item);
		            }
		        });
				
				Button btnDelete = new Button("");
				btnDelete.setIcon(FontAwesome.TRASH_O);
				btnDelete.setStyleName(ValoTheme.BUTTON_LINK);
				btnDelete.setDescription("Eliminar");
				
				btnDelete.addClickListener(new ClickListener() 
		        {					
		            @Override
		            public void buttonClick(final ClickEvent event) 
		            {         		     		            	
		            	deleteItem(item);
		            }
		        });
				
				hlActions.addComponent(btnEdit);
				hlActions.addComponent(btnDelete);
				btnDelete.setVisible(!isQuery);
				
				return hlActions;
			}
		});		
	}
	
	@Override
	public void deleteItem(final Object item)
	{
		ConfirmDialog.show(
				UI.getCurrent(), 
				"Por favor, confirme", 
				"Está seguro de eliminar el Usuario?", 
				"Aceptar", 
				"Cancelar",
				
				new ConfirmDialog.Listener() 
		    	{
		    		public void onClose(ConfirmDialog dialog) 
		    		{
		    			if (dialog.isConfirmed())     			    				
		    				deleteItemConfirm(dialog.isConfirmed(), item); // Confirmed to continue    	        
		    			else     			
		    				deleteItemConfirm(dialog.isConfirmed(), item); // User did not confirm    	        
		    		}
		    	}
			);
	}
	
	@Override
	public void deleteItemConfirm(Boolean isConfirmed, Object item)
	{
		if(isConfirmed)
		{				
			Usuario user = (Usuario)item;
			
			userPresenter.delete(user);
			
			if(user.isStatusOK())
	        {	
	        	Notification success = new Notification("Usuario eliminado correctamente");
	        	success.setDelayMsec(2000);
	        	success.setStyleName("bar success small");
	        	success.setPosition(Position.BOTTOM_RIGHT);
	        	success.show(Page.getCurrent());
	
	        	StoreCrmEventBus.post(new UserChangedEvent());        	        	
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

	@Override
	public void addItem(Object item) 
	{							
		UserWindow.open((Usuario)item, 0, "new", isQuery);
	}
	
	@Override
	public void editItem(Object item) 
	{	
		Usuario user = userPresenter.findById(((Usuario)item).getId());
		
    	UserWindow.open(user, 0, "edit", isQuery);
	}	
}