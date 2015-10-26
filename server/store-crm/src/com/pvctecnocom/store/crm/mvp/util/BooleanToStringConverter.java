package com.pvctecnocom.store.crm.mvp.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> 
{
    @Override
    public String convertToDatabaseColumn(Boolean value) 
    {        
        return (value != null && value) ? "S" : "N";            
    }    

    @Override
    public Boolean convertToEntityAttribute(String value) 
    {
    	boolean param = false;
    	if(value != null)
    		param = "S".equals(value);
    	
        return param;
    }
} 