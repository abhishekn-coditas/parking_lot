package com.coditas.parking;

import com.coditas.parking.service.exception.ParkingLimitOverflowException;
import com.coditas.parking.service.exception.VehicleNotFoundException;

public interface IParkingManagerService {

    void createParkingArea(int allocateCapacity);

    void allocateParking(String vehicleNumber) throws ParkingLimitOverflowException;

    void deAllocateParking(String vehicleNumber,int parkingHrs) throws VehicleNotFoundException;

    void parkingStatus();

    Integer getNextAvailableParkingSlot();

    boolean isParkingAvailable();

    void initParkingApplication(String inputFileName);
}
