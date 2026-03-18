package com.ministore.ministoreapi.exception;

    //this class will provide the error message instead of the system generated message
    public class NotFoundException extends RuntimeException{
        public NotFoundException(String message){
            super(message);
    }
}
