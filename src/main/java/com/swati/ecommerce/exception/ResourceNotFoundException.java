package com.swati.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{

    String resourceName;
    String fieldName;
    String fieldValue;


    public ResourceNotFoundException(String resourceName, String fieldName, String userId) {
        super(String.format("%s not found with %s :%s ",resourceName, fieldName, userId));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = userId;
    }



}
