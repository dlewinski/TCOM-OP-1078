package com.pvctecnocom.store.crm.mvp.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class Utility 
{
	public static String calcularNeto(String precio, String descuento, Integer cantidad)
	{
		if(precio != null)
			precio = precio.replace(",", ".");
		
		if(descuento != null)
			descuento = descuento.replace(",", ".");
			
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator('.');
		
		DecimalFormat decimalFormat = new DecimalFormat("##.##"); 
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		
		Double precioDouble;
		Double descuentoDouble;
		Double netoDouble;	
		
		String neto = "0";
		
		try
		{
			precioDouble = Double.valueOf(precio);
			descuentoDouble = Double.valueOf(descuento);
			
			netoDouble = (precioDouble) - ((precioDouble * descuentoDouble) / 100);
			
			netoDouble =  netoDouble * cantidad;
			
			neto = decimalFormat.format(netoDouble);
		}
		catch(Exception e)
		{
			e.getMessage();
			netoDouble = 0D;
		}				
		
		return neto;
	}
	
	@SuppressWarnings("unchecked")
	public static void refreshTablaMateriales(Table tableProductos)
	{
		DecimalFormat decimalFormat = new DecimalFormat("##.##"); 	
		
		if(tableProductos != null)
		{																		
			Iterator<?> it = tableProductos.getItemIds().iterator();
			
			Integer itemId;
			Item item;
			
			TextField txtCantidad;
			String txtCantidadValue;
			Integer cantidadTotal = 0;
			Integer cantidad = 0;
			
			String neto;
			Double netoParcial;
			Double netoTotal = 0D;
            
			TextField txtDescuentoManual;
			String txtDescuentoManualValue;
			String descuento;
			Double descuentoManual = 0D;
			Double descuentoTotal = 0D;
			Double descuentoPredefinido = 0D;
			String descuentoPredefinidoValue;
			
			TextField txtPrecio;
			String precio;
						
			while (it.hasNext()) 
			{				
				itemId = (Integer) it.next();
				item = tableProductos.getItem(itemId);

				//PRECIO
				txtPrecio = (TextField)item.getItemProperty("precio").getValue();
				precio = txtPrecio.getValue();
				
				//DESCUENTO
                txtDescuentoManual = (TextField)(item.getItemProperty("descuentoManual").getValue());	
				txtDescuentoManualValue = txtDescuentoManual.getValue();
				txtDescuentoManualValue = txtDescuentoManualValue.replace(",", ".");
				
				try
				{
					descuentoManual = Double.valueOf(txtDescuentoManualValue);
				}
				catch(NumberFormatException e)
				{
					descuentoManual = 0D;					
				}
														
				descuentoPredefinidoValue = (String)(item.getItemProperty("descuentoPredefinido").getValue());
				descuentoPredefinidoValue = descuentoPredefinidoValue.replace(",", ".");
				
				try
				{
					descuentoPredefinido = Double.valueOf(descuentoPredefinidoValue);
				}
				catch(NumberFormatException e)
				{
					descuentoPredefinido = 0D;					
				}
				
				descuentoTotal = 1 - (1 - (descuentoPredefinido/100)) * (1 - (descuentoManual/100));
				
				descuentoTotal = descuentoTotal * 100;
				
				if(descuentoTotal > 0)
					item.getItemProperty("descuentoTotal").setValue(decimalFormat.format(descuentoTotal));
				else
					item.getItemProperty("descuentoTotal").setValue("0.00");
				
				//CANTIDAD
				txtCantidad = (TextField)(item.getItemProperty("cantidad").getValue());				
				txtCantidadValue = txtCantidad.getValue();
														
				try
				{
					cantidad = Integer.valueOf(txtCantidadValue);
				}
				catch(NumberFormatException e)
				{
					cantidad = 0;						
				}																		
								
				if(cantidad > 0)
				{						
					cantidadTotal = cantidadTotal + cantidad;
				}			
				
				//NETO
				descuento = (String)item.getItemProperty("descuentoTotal").getValue();
				
				neto = Utility.calcularNeto(precio, descuento, cantidad);
				
				item.getItemProperty("neto").setValue(neto);
				
				neto = (String)(item.getItemProperty("neto").getValue());				
				neto = neto.replace(",", ".");
				
				try
				{
					netoParcial = Double.valueOf(neto);
				}
				catch(NumberFormatException e)
				{
					netoParcial = 0D;						
				}
				
				netoTotal = netoTotal + netoParcial;				
			}
			
			((StoreCRM_UI)UI.getCurrent()).getPageLayout().getHeaderLayout().refreshDatosSesion(cantidadTotal, netoTotal, true);	
		}	
	}
}