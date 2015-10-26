package com.pvctecnocom.store.crm.mvp.model.config;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringContextHelper 
{
	private ApplicationContext context;
	
	public SpringContextHelper(ServletContext servletContext) 
	{        
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }
	
	public Object getBean(final String bean) 
	{
        return context.getBean(bean);
    }
}