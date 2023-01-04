package com.devsuperior.movieflix.resources.exceptionresource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
    private static final long serialVersionUID=1L;

    public ValidationError (Instant now, int value, String error, String message, String requestURI) {
        super();
    }
    public ValidationError (){
        super();
    }
    private List<FieldMessage> errors=new ArrayList<>();


    public List<FieldMessage> getErrors() {
        return errors;
    }
    public void addError(String fieldName, String message){
        errors.add(new FieldMessage(fieldName,message));
    }
}