package com.example.bambergBeverageBox.exceptions;

public class RequiredParamMissingException extends Exception{
    public RequiredParamMissingException(String exception){
        super(exception);
    }
}
