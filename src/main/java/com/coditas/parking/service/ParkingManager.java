package com.coditas.parking.service;

import com.coditas.parking.model.CustomerParking;
import com.coditas.parking.model.ParkingArea;
import com.coditas.parking.model.Vehicle;
import com.coditas.parking.service.exception.ParkingLimitOverflowException;

import java.util.*;

public class ParkingManager {

    ParkingArea parkingArea;

    public ParkingManager() {}

     public ParkingManager(int allocateCapacity) {
         parkingArea = ParkingArea.getInstance(allocateCapacity);
        int count;
        for(count = 1; count<= parkingArea.getParkingCapacity(); count++) {
            CustomerParking parking = new CustomerParking();
            parking.setParkingId(count);
            parkingArea.getParkedVehicleList().add(parking);
        }
        System.out.println("Created parking lot with "+parkingArea.getParkingCapacity() +" slots");
    }


    public boolean isParkingAvailable() {
        return parkingArea.getParkedVehicleList().stream().anyMatch(myParking -> (null == myParking.getVehicle()));
    }

    public Integer getNextAvailableParkingSlot() {
        Integer parkingId=null;
        Optional<CustomerParking> availableParking = parkingArea.getParkedVehicleList().stream().filter(myParking ->(null==myParking.getVehicle())).findFirst();
        if(availableParking.isPresent())
            parkingId = availableParking.get().getParkingId();
        return parkingId;
    }

    public void allocateParking(String vehicleNumber) throws ParkingLimitOverflowException {
        if(isParkingAvailable()) {
            Vehicle newVehicle = new Vehicle();
            newVehicle.setRegistrationNumber(vehicleNumber);
            CustomerParking availableParking = parkingArea.getParkedVehicleList().get(getNextAvailableParkingSlot()-1);
            availableParking.setVehicle(newVehicle);
            parkingArea.getParkedVehicleList().set(availableParking.getParkingId()-1, availableParking);
            System.out.println("Allocated slot number: " + availableParking.getParkingId());
        }
        else {
            throw new ParkingLimitOverflowException();
        }
    }

    public void deAllocateParking(String vehicleNumber,int parkingHrs) {
        Optional<CustomerParking> leavingVehicleParking;
        leavingVehicleParking  = parkingArea.getParkedVehicleList().stream().filter(myParking->
                (vehicleNumber.equalsIgnoreCase(null!=myParking.getVehicle() ? myParking.getVehicle().getRegistrationNumber():""))).findFirst();
        if(leavingVehicleParking.isPresent()) {
            int parkingCharge;
            if(parkingHrs<=2) {
                parkingCharge = 10;
            }
            else {
                parkingCharge = 10 + 10 *(parkingHrs-2);
            }
            CustomerParking parking = leavingVehicleParking.get();
            parking.setVehicle(null);
            parkingArea.getParkedVehicleList().set(parking.getParkingId()-1,parking);
            System.out.println(vehicleNumber +" with Slot Number "+parking.getParkingId()+" is free with Charge "+parkingCharge);
        }
        else {
            System.out.println("Registration number "+vehicleNumber+" not found");
        }
    }

    public void parkingStatus() {
        System.out.println("Slot No.\t Registration No.");
        parkingArea.getParkedVehicleList().stream().forEach(parking->{
            if(null!=parking.getVehicle()) {
                System.out.println(parking.getParkingId() + "\t\t" + parking.getVehicle().getRegistrationNumber());
            }
        });
    }
}
