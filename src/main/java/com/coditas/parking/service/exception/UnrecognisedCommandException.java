package com.coditas.parking.service.exception;

public class UnrecognisedCommandException extends  Exception {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "'%s' is not a valid command";

    private String argument;

    public UnrecognisedCommandException(){}

    public UnrecognisedCommandException(String param){
        this.argument = param;
    }

    @Override
    public String getMessage() {
        return String.format(MESSAGE,argument);
    }
}
