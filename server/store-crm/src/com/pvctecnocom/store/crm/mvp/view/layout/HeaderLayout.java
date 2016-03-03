package com.pvctecnocom.store.crm.mvp.view.layout;

import java.text.DecimalFormat;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.component.ProfilePreferencesWindow;
import com.pvctecnocom.store.crm.event.StoreCrmEvent.UserLoggedOutEvent;
import com.pvctecnocom.store.crm.event.StoreCrmEventBus;
import com.pvctecnocom.store.crm.mvp.model.bo.Usuario;
import com.pvctecnocom.store.crm.mvp.view.screen.pedido.PedidoViewImpl;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class HeaderLayout extends VerticalLayout 
{    				
	private HorizontalLayout horizontalLayout;
	private Button btnDatosSesion;
	
	public HeaderLayout() 
    {    	
		this.horizontalLayout = new HorizontalLayout();														
		this.horizontalLayout.setSizeFull();	
		this.addComponent(this.horizontalLayout);
		this.setComponentAlignment(this.horizontalLayout, Alignment.TOP_CENTER);		
    }		
	
	public void showMenu(Component menuBar)
	{											
		this.btnDatosSesion = new Button("");
		this.btnDatosSesion.setEnabled(false);
		this.btnDatosSesion.setIcon(FontAwesome.REFRESH);		
		
		this.btnDatosSesion.addClickListener(new Button.ClickListener() 
        {            									
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {  
				String message = "Neto Total actualizado";
		    	
		    	Notification notification = new Notification(message);
		    	notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
		    	notification.show(Page.getCurrent()); 
            }
        });
		
		this.refreshDatosSesion(0, 0D, false);					
		
		String numeroVendedorCRM = ((StoreCRM_UI)UI.getCurrent()).getNumeroVendedorCRM();
		String nombreVendedorCRM = ((StoreCRM_UI)UI.getCurrent()).getNombreVendedorCRM();
		
		String vendedor = "";
		
		if(numeroVendedorCRM != null && !numeroVendedorCRM.equals(""))
			vendedor = numeroVendedorCRM;
		
		if(nombreVendedorCRM != null && !nombreVendedorCRM.equals(""))
		{
			if(vendedor.equals(""))
				vendedor = nombreVendedorCRM;
			else
				vendedor = vendedor + " - " + nombreVendedorCRM;
		}	
		
		Button vendedorCRM = new Button();		
		vendedorCRM.setCaption(vendedor);		
		vendedorCRM.setIcon(FontAwesome.DESKTOP);	
		vendedorCRM.setEnabled(false);
		
		Button btnCambiarPassword = new Button();		
		btnCambiarPassword.setIcon(FontAwesome.KEY);
		btnCambiarPassword.setDescription("Cambiar contraseña");	
		btnCambiarPassword.addStyleName(ValoTheme.BUTTON_PRIMARY);
		
		btnCambiarPassword.addClickListener(new Button.ClickListener() 
        {            												
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {  										
				cambiarContrasena();
            }
        });
		
		Button btnCerrarSesion = new Button();		
		btnCerrarSesion.setIcon(FontAwesome.POWER_OFF);
		btnCerrarSesion.setDescription("Cerrar sesión");	
		btnCerrarSesion.addStyleName(ValoTheme.BUTTON_DANGER);
		
		btnCerrarSesion.addClickListener(new Button.ClickListener() 
        {            												
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) 
            {  										
				StoreCrmEventBus.post(new UserLoggedOutEvent());
            }
        });
		
		Link lLogo = new Link(null, new ExternalResource(""));
		lLogo.setIcon(new ClassResource("logo_pvc_tecnocom.png"));		
		lLogo.setTargetBorder(BorderStyle.NONE);
		lLogo.setEnabled(false);
		
		this.horizontalLayout.addComponent(lLogo);
		this.horizontalLayout.setComponentAlignment(lLogo, Alignment.BOTTOM_LEFT);
		this.horizontalLayout.setExpandRatio(lLogo, 2);
		
		this.horizontalLayout.addComponent(vendedorCRM);
		this.horizontalLayout.setComponentAlignment(vendedorCRM, Alignment.BOTTOM_LEFT);				
		
		this.horizontalLayout.addComponent(menuBar);				
		this.horizontalLayout.setComponentAlignment(menuBar, Alignment.BOTTOM_CENTER);
		this.horizontalLayout.setExpandRatio(menuBar, 2);
		
		this.horizontalLayout.addComponent(this.btnDatosSesion);
		this.horizontalLayout.setComponentAlignment(this.btnDatosSesion, Alignment.BOTTOM_CENTER);
		this.horizontalLayout.setExpandRatio(this.btnDatosSesion, 2);
		
		this.horizontalLayout.addComponent(btnCambiarPassword);
		this.horizontalLayout.setComponentAlignment(btnCambiarPassword, Alignment.BOTTOM_CENTER);
		this.horizontalLayout.setExpandRatio(btnCambiarPassword, 1);
		
		this.horizontalLayout.addComponent(btnCerrarSesion);
		this.horizontalLayout.setComponentAlignment(btnCerrarSesion, Alignment.BOTTOM_RIGHT);						
		
		new PedidoViewImpl();
	}	
	
	public void refreshDatosSesion(Integer cantidadProductos, Double netoTotal, Boolean isButtonEnabled)
	{
		DecimalFormat decimalFormat = new DecimalFormat("##.##"); 																
		
		String productosText = "materiales";
		
		if(cantidadProductos == 1)
			productosText = "material";				
		
		String datosSesion = cantidadProductos + " " + productosText + " - Neto Total: $ ";
		
		if(netoTotal == 0)
			datosSesion = datosSesion + "0.00";
		else
			datosSesion = datosSesion + decimalFormat.format(netoTotal);					
		
		this.btnDatosSesion.setEnabled(isButtonEnabled);
		this.btnDatosSesion.setCaption(datosSesion);
	}
	
	private void cambiarContrasena()
	{
		final Usuario user = new Usuario().getUserSession();
		
		ProfilePreferencesWindow.open(user);
	}
}