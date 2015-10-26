package com.pvctecnocom.store.crm.event;

import com.pvctecnocom.store.crm.mvp.view.StoreCrmViewType;

public abstract class StoreCrmEvent 
{
    public static final class UserLoginRequestedEvent 
    {
        private final String userName, password;

        public UserLoginRequestedEvent(final String userName, final String password) 
        {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() 
        {
            return userName;
        }

        public String getPassword() 
        {
            return password;
        }                
    }  
    
    public static class BrowserResizeEvent 
    {
    }        
    
    public static class CloseOpenWindowsEvent 
    {
    }
         
    public static class UserLoggedOutEvent 
    {
    }     
    
    public static class UserChangedEvent     
    {
    	public UserChangedEvent()
    	{    		
    	}
    }    
    
    public static class ProfileUpdatedEvent 
    {
    }        
    
    public static final class PostViewChangeEvent 
    {
        private final StoreCrmViewType view;

        public PostViewChangeEvent(final StoreCrmViewType view) 
        {
            this.view = view;
        }

        public StoreCrmViewType getView() 
        {
            return view;
        }
    }        
    
    public static class PedidoViewEvent 
    {
    }
    
    public static class PedidoNuevoViewEvent 
    {
    }
}	