package com.coditas.parking.service.exception;

public class ParkingLimitOverflowException extends  Exception {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Sorry, parking lot is full";

    public ParkingLimitOverflowException(){}

    @Override
    public String getMessage() {
        return this.MESSAGE;
    }
}
