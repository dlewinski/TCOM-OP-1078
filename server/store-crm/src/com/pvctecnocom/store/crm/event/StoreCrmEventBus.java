package com.pvctecnocom.store.crm.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.pvctecnocom.store.crm.StoreCRM_UI;

/**
 * A simple wrapper for Guava event bus. Defines static convenience methods for relevant actions.
 */
public class StoreCrmEventBus implements SubscriberExceptionHandler 
{
    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) 
    {
    	if(StoreCRM_UI.getStoreCrmEventbus() != null)
    		StoreCRM_UI.getStoreCrmEventbus().eventBus.post(event);
    }

    public static void register(final Object object) 
    {
    	if(StoreCRM_UI.getStoreCrmEventbus() != null)
    		StoreCRM_UI.getStoreCrmEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) 
    {
    	if(StoreCRM_UI.getStoreCrmEventbus() != null)
    		StoreCRM_UI.getStoreCrmEventbus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception, final SubscriberExceptionContext context) 
    {
        exception.printStackTrace();
    }
}