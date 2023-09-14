package com.wendy.backendassignment.exception;



public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(){
        super();
    }
    public RecordNotFoundException(String message){
        super(message);
    }
}


//invalidpasswordexception is beter om het via frontend hook-form aan te maken