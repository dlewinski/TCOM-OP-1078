package com.pvctecnocom.store.crm.mvp.presenter.perfil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.dao.DataIntegrityViolationException;

import com.pvctecnocom.store.crm.StoreCRM_UI;
import com.pvctecnocom.store.crm.mvp.model.bo.Perfil;
import com.pvctecnocom.store.crm.mvp.model.service.PerfilService;

public class PerfilPresenter 
{
	private PerfilService perfilService;
	
	public PerfilPresenter()     
    {	
		 this.perfilService = StoreCRM_UI.getCurrent().getPerfilService();
    }														
		
	public List<Perfil> findAll()
	{
		List<Perfil> perfiles = null;
				
		try
		{
			perfiles = perfilService.findAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(perfiles == null)
				perfiles = new ArrayList<Perfil>();
		}
		
		return perfiles;
	}
	
	public Perfil findById(Integer perfilId)
	{				
		Perfil perfil = null;
		
		try
		{
			perfil = perfilService.findById(perfilId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(perfil == null)
				perfil = new Perfil();
		}
		
		return perfil;
	}			
	
	public void insert(Perfil perfil)
	{
		try
		{
			perfilService.insert(perfil);
		}				
		catch (PersistenceException e)
		{
			perfil.setStatusOK(false);
			perfil.setStatusCode("-1");
			perfil.setStatusDescription("El Perfil ya se encuentra definido");
		}
		catch(Exception e)
		{
			e.printStackTrace();						
			
			perfil.setStatusOK(false);
			perfil.setStatusCode("-1");
			perfil.setStatusDescription("Exception: " + e);
		}
		finally
		{		
			if(perfil == null)
			{
				perfil = new Perfil();
				
				perfil.setStatusOK(false);
				perfil.setStatusCode("-1");
				perfil.setStatusDescription("NULL");
			}
		}
	}	
	
	public void update(Perfil perfil)
	{
		try
		{
			perfilService.update(perfil);
		}
		catch (DataIntegrityViolationException e)
    	{
			perfil.setStatusOK(false);
			perfil.setStatusCode("-1");
			perfil.setStatusDescription("El Perfil ya se encuentra definido");
    	}
		catch(Exception e)
		{
			e.printStackTrace();						
			
			perfil.setStatusOK(false);
			perfil.setStatusCode("-1");
			perfil.setStatusDescription("Exception: " + e);
		}
		finally
		{		
			if(perfil == null)
			{
				perfil = new Perfil();
				
				perfil.setStatusOK(false);
				perfil.setStatusCode("-1");
				perfil.setStatusDescription("NULL");
			}
		}
	}
	
	public void delete (Perfil perfil)
	{
		try
		{
			perfilService.delete(perfil);	
		}
		catch (DataIntegrityViolationException e)
    	{			
			perfil.setStatusOK(false);
			perfil.setStatusCode("-1");
			perfil.setStatusDescription("El Perfil no se puede eliminar");
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{			
		}
	}	
}