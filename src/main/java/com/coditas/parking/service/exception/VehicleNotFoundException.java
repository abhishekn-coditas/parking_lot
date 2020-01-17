package com.coditas.parking.service.exception;

public class VehicleNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Registration number %s not found";
    private String argument;

    public VehicleNotFoundException(){}

    public VehicleNotFoundException(String param) {
        this.argument = param;
    }

    @Override
    public String getMessage() {
        return String.format(MESSAGE,argument);
    }
}
