package com.pvctecnocom.store.crm;

import java.io.File;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.Subscribe;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.BrowserResizeEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.CloseOpenWindowsEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.UserLoggedOutEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.UserLoginRequestedEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.bo.Cliente;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.model.config.SpringContextHelper;
import com.pvctecnocom.store.crm.mvp.model.sap.SAPIntegration;
import com.pvctecnocom.store.crm.mvp.model.service.CategoriaService;
import com.pvctecnocom.store.crm.mvp.model.service.CentroAlmacenService;
import com.pvctecnocom.store.crm.mvp.model.service.PerfilService;
import com.pvctecnocom.store.crm.mvp.model.service.ProductoService;
import com.pvctecnocom.store.crm.mvp.model.service.SetupEmailService;
import com.pvctecnocom.store.crm.mvp.model.service.UserService;
import com.pvctecnocom.store.crm.mvp.presenter.login.LoginPresenter;
import com.pvctecnocom.store.crm.mvp.view.LoginView;
import com.pvctecnocom.store.crm.mvp.view.MainView;
import com.pvctecnocom.store.crm.mvp.view.NewPasswordView;
import com.pvctecnocom.store.crm.mvp.view.layout.PageLayout;
import com.pvctecnocom.store.crm.mvp.view.screen.login.LoginViewImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("store_crm")
@Title("PVC Tecnocom - Pedidos online")
public class StoreCRM_UI extends UI 
{		
	public final String sapDestination = "PVC_TECNOCOM_CRM";
	
	private PageLayout pageLayout; 
	
	private CategoriaService categoriaService;
	private ProductoService productoService;
	private CentroAlmacenService centroAlmacenService;
	private UserService userService;
	private PerfilService perfilService;
	private SetupEmailService setupEmailService;
	
	private SAPIntegration sapIntegration;
	
	private String categoriaBase = "0MA";
	
	private Cliente cliente;		
	private String numeroVendedorCRM;
	private String nombreVendedorCRM;		

	private String accion = "listadoPedidos";		
	
	private final StoreCrmEventBus storeCrmEventbus = new StoreCrmEventBus();
	
	List<Cliente> clientes;	
	
	public static String basePath;
	public static String basePathResources;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = true, 
	                                        ui = StoreCRM_UI.class,
	                                 widgetset = "com.pvctecnocom.store.crm.widgetset.Store_crmWidgetset")
	public static class Servlet extends VaadinServlet 
	{
		private static class StoreSystemMessagesProvider implements SystemMessagesProvider
		{
			private static final long serialVersionUID = -771048276626668042L;

			@Override
			public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo)
			{
				CustomizedSystemMessages messages = new CustomizedSystemMessages();
				
				messages.setSessionExpiredCaption("Su sesión expiró");
				messages.setSessionExpiredMessage("Haga click aquí para iniciar sesión nuevamente");
				messages.setSessionExpiredNotificationEnabled(true);
				messages.setSessionExpiredURL(null);
				messages.setCommunicationErrorCaption("Error de comunicación con el servidor");
				messages.setCommunicationErrorMessage("");	              				
				
				return messages;
			}
		}
		
		@Override
		public void servletInitialized() throws ServletException
		{
			super.servletInitialized();

			getService().addSessionInitListener(new SessionInitListener()
			{
				private static final long serialVersionUID = -851648627273665961L;

				@Override
				public void sessionInit(SessionInitEvent event) throws ServiceException
				{
					event.getService().setSystemMessagesProvider(new StoreSystemMessagesProvider());
				}

			});

			getService().addSessionDestroyListener(new SessionDestroyListener()
			{
				private static final long serialVersionUID = 7071674972660697925L;

				@Override
				public void sessionDestroy(SessionDestroyEvent event)
				{
					if(StoreCRM_UI.getCurrent() != null)
						StoreCrmEventBus.post(new UserLoggedOutEvent());					
				}
			});
		}
	}

	@Override
	protected void init(VaadinRequest request) 
	{				
		this.setLocale(new Locale("es"));
		Locale.setDefault(new Locale("es"));
		
		Responsive.makeResponsive(this);
		
		this.setStyleName(ValoTheme.UI_WITH_MENU);
		
		StoreCRM_UI.basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		StoreCRM_UI.basePathResources = basePath + File.separator + "WEB-INF" + File.separator + "resources";
		
		this.initSAP();
		this.initSession();
		this.initSpring();	
		
		StoreCrmEventBus.register(this);
		
		String ip = request.getRemoteAddr();
			
		if(ip != null && (ip.equals("127.0.0.1") || ip.startsWith("10.")))
		{
			String clienteSAP = request.getParameter("clienteSAP");
			String vendedorCRM = request.getParameter("vendedorCRM");
			
			if(clienteSAP != null && vendedorCRM != null)
				initVendedorView(clienteSAP, vendedorCRM, null);
			else
				updateContent();
		}	
		else		
		{
			String code = request.getParameter("code");
			
			if(code != null && !code.equals(""))
				updateContentCode(code);
			else
				updateContent();
		}	
						
		// Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event but on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() 
        {
            @Override
            public void browserWindowResized(final BrowserWindowResizeEvent event) 
            {
            	StoreCrmEventBus.post(new BrowserResizeEvent());
            }
        });
	}					
	
	private void initVendedorView(String clienteSAP, String vendedorCrmNumero, String vendedorCrmNombre)
	{		
		((StoreCRM_UI)UI.getCurrent()).setNumeroVendedorCRM(vendedorCrmNumero);
		((StoreCRM_UI)UI.getCurrent()).setNombreVendedorCRM(vendedorCrmNombre);
		
		this.getPage().setTitle("PVC Tecnocom - eCommerceCRM");
		
		this.pageLayout = new PageLayout();
        this.setContent(pageLayout);                        
        
        Label labelBreadcrumbs = new Label("CRM - eCommerce");				
		labelBreadcrumbs.setSizeUndefined();
		
    	((StoreCRM_UI)UI.getCurrent()).getPageLayout().getBodyLayout().getContentLayout().showComponentTop(labelBreadcrumbs);    	            	
    	
        new LoginViewImpl(clienteSAP, vendedorCrmNumero);        	        	     
	}
	
	private void initSAP()
	{				
		this.sapIntegration = new SAPIntegration(this.sapDestination);
	}
	
	private void initSession()
	{		
		Cliente cliente = new Cliente();
				
		this.setCliente(cliente);
		
		this.setNumeroVendedorCRM("");	
		this.setNombreVendedorCRM("");
	}
	
	private void initSpring()
	{
		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		
		this.categoriaService = (CategoriaService) helper.getBean("categoriaService");		
		this.productoService = (ProductoService) helper.getBean("productoService");
		this.centroAlmacenService = (CentroAlmacenService) helper.getBean("centroAlmacenService");
		this.userService = (UserService) helper.getBean("userService");
		this.perfilService = (PerfilService) helper.getBean("perfilService");
		this.setupEmailService = (SetupEmailService) helper.getBean("setupEmailService");
	}
	
	public static StoreCRM_UI getCurrent() 
    {
        return (StoreCRM_UI) UI.getCurrent();
    }
	
	public SAPIntegration getSapIntegration() 
	{
		return sapIntegration;
	}
	
	public String getSapDestination() 
	{
		return sapDestination;
	}
	
	public PageLayout getPageLayout() 
    {
        return pageLayout;
    }	
	
	public String getCategoriaBase() 
	{
		return categoriaBase;
	}
	
	public CategoriaService getCategoriaService() 
	{
		return this.categoriaService;
	}
	
	public ProductoService getProductoService() 
	{
		return this.productoService;
	}
	
	public CentroAlmacenService getCentroAlmacenService() 
	{
		return this.centroAlmacenService;
	}		
	
	public UserService getUserService() 
	{
		return userService;
	}
	
	public PerfilService getPerfilService() 
	{
		return perfilService;
	}
	
	public Cliente getCliente() 
	{
		return cliente;
	}

	public void setCliente(Cliente cliente) 
	{
		this.cliente = cliente;
	}
	
	public String getNumeroVendedorCRM() 
	{
		return numeroVendedorCRM;
	}

	public void setNumeroVendedorCRM(String numeroVendedorCRM) 
	{
		this.numeroVendedorCRM = numeroVendedorCRM;
	}
	
	public String getNombreVendedorCRM() 
	{
		return nombreVendedorCRM;
	}

	public void setNombreVendedorCRM(String nombreVendedorCRM) 
	{
		this.nombreVendedorCRM = nombreVendedorCRM;
	}
	
	public String getAccion() 
	{
		return accion;
	}

	public void setAccion(String accion) 
	{
		this.accion = accion;
	}
	
	public void updateContent() 
    {
        Usuario user = new Usuario().getUserSession();
        
        if (user != null)
        {   
        	if(user.getPerfil() != null)
        	{	
        		Integer perfilId = user.getPerfil().getId();
        		
	        	if(perfilId == 1) //Admin
	        	{	
	        		setContent(new MainView());
	        		removeStyleName("loginview");
	            
	        		getNavigator().navigateTo(getNavigator().getState());
	        	}
	        	else
	        	{	
	        		String numeroVendedorSAP = user.getNumeroVendedorSAP();
	        		
	        		if(numeroVendedorSAP == null || numeroVendedorSAP.equals(""))	        			
	        		{
	        			String mensaje = "";
	        			
	        			if(perfilId == 2)
	        				mensaje = "representante";
	        			else if(perfilId == 3)
	        				mensaje = "vendedor";
	        			
	        			Notification notification = new Notification("Atención", "Usuario sin número de " + mensaje + " asignado, por favor consulte al Administrador", Type.TRAY_NOTIFICATION);			
	        			notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
	        			notification.setPosition(Position.MIDDLE_CENTER);
	        			notification.show(Page.getCurrent());
	        		}
	        		else
	        		{
	        			String vendedorCrmNombre = null;
	        			
	        			if(user.getApellido() != null && !user.getApellido().equals(""))
	        				vendedorCrmNombre = user.getApellido();
	        			
	        			if(user.getNombre() != null && !user.getNombre().equals(""))
	        			{
	        				if(vendedorCrmNombre == null)
	        					vendedorCrmNombre = user.getNombre();
	        				else
	        					vendedorCrmNombre = vendedorCrmNombre + ", " + user.getNombre();
	        			}
	        				
	        			initVendedorView(null, numeroVendedorSAP, vendedorCrmNombre);
	        		}	
	        	}	
        	}
        	else
        	{
        		Notification notification = new Notification("Atención", "Usuario sin perfil asignado, por favor consulte al Administrador", Type.TRAY_NOTIFICATION);			
    			notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
    			notification.setPosition(Position.MIDDLE_CENTER);
    			notification.show(Page.getCurrent());
        	}
        } 
        else 
        {
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }
	
	public static StoreCrmEventBus getStoreCrmEventbus() 
	{
		return ((StoreCRM_UI) getCurrent()).storeCrmEventbus;
	}
	
	@Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) 
    {  		
		LoginPresenter loginPresenter = new LoginPresenter(); 
		
		Usuario user = loginPresenter.login(event.getUserName(), event.getPassword());
		
		if(user.isStatusOK())
		{			
        	VaadinSession.getCurrent().setAttribute(Usuario.class.getName(), user);
        	
			updateContent();
		}
		else
		{
			Notification notification = new Notification("Atención", user.getStatusDescription(), Type.TRAY_NOTIFICATION);			
			notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);			
			notification.setPosition(Position.MIDDLE_CENTER);
			notification.show(Page.getCurrent());			
		}	
    }
	
	@Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) 
    {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't invalidate the current HttpSession.
		this.cliente = null;
		this.clientes = null;
		this.nombreVendedorCRM = null;
		this.numeroVendedorCRM = null;
		
        VaadinSession.getCurrent().close();
        
        Page.getCurrent().reload();        
    }
	
	@Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) 
    {
        for (Window window : getWindows())         
            window.close();        
    }
	
	public List<Cliente> getClientes() 
	{
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) 
	{
		this.clientes = clientes;
	}
	
	public SetupEmailService getSetupEmailService() 
	{
		return this.setupEmailService;
	}
	
	public void updateContentRecuperarContrasenia(String mensaje)
	{
		 setContent(new LoginView());
         addStyleName("loginview");
         
         Notification notification = new Notification("Atención", mensaje, Notification.Type.TRAY_NOTIFICATION, true);
         notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);			
		 notification.setPosition(Position.MIDDLE_CENTER);
	     notification.show(Page.getCurrent());  
	}
	
	private void updateContentCode(String code) 
    {
		setContent(new NewPasswordView(code));
        addStyleName("loginview");
    }
}