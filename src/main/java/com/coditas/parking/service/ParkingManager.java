package com.coditas.parking.service;

import com.coditas.parking.model.MyParking;
import com.coditas.parking.model.Vehicle;
import com.coditas.parking.service.exception.ParkingLimitOverflowException;

import java.util.*;

public class ParkingManager {

        private static int totalCapacity;

        private List<MyParking> parkedVehicleList;

        public ParkingManager() {}

     public ParkingManager(int allocateCapacity) {
        totalCapacity = allocateCapacity;
        parkedVehicleList = new ArrayList<>(totalCapacity);
        int count;
        for(count = 1; count<= totalCapacity; count++) {
            MyParking parking = new MyParking();
            parking.setParkingId(count);
            parkedVehicleList.add(parking);
        }
        System.out.println("Created parking lot with "+totalCapacity +" slots");
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public boolean isParkingAvailable() {
        return parkedVehicleList.stream().anyMatch(myParking -> (null == myParking.getVehicle()));
    }

    public Integer getNextAvailableParkingSlot() {
        Integer parkingId=null;
        Optional<MyParking> availableParking = parkedVehicleList.stream().filter(myParking ->(null==myParking.getVehicle())).findFirst();
        if(availableParking.isPresent())
            parkingId = availableParking.get().getParkingId();
        return parkingId;
    }

    public void allocateParking(String vehicleNumber) throws ParkingLimitOverflowException {
        if(isParkingAvailable()) {
            Vehicle newVehicle = new Vehicle();
            newVehicle.setRegistrationNumber(vehicleNumber);
            MyParking availableParking = parkedVehicleList.get(getNextAvailableParkingSlot()-1);
            availableParking.setVehicle(newVehicle);
            parkedVehicleList.set(availableParking.getParkingId()-1, availableParking);
            System.out.println("Allocated slot number: " + availableParking.getParkingId());
        }
        else {
            throw new ParkingLimitOverflowException();
        }
    }

    public void deAllocateParking(String vehicleNumber,int parkingHrs) {
        Optional<MyParking> leavingVehicleParking = null;
        leavingVehicleParking  = parkedVehicleList.stream().filter(myParking->
                (vehicleNumber.equalsIgnoreCase(null!=myParking.getVehicle() ? myParking.getVehicle().getRegistrationNumber():""))).findFirst();
        if(leavingVehicleParking.isPresent()) {
            int parkingCharge;
            if(parkingHrs<=2) {
                parkingCharge = 10;
            }
            else {
                parkingCharge = 10 + 10 *(parkingHrs-2);
            }
            MyParking parking = leavingVehicleParking.get();
            parking.setVehicle(null);
            parkedVehicleList.set(parking.getParkingId()-1,parking);
            System.out.println(vehicleNumber +" with Slot Number "+parking.getParkingId()+" is free with Charge "+parkingCharge);
        }
        else {
            System.out.println("Registration number "+vehicleNumber+" not found");
        }
    }

    public void parkingStatus() {
        System.out.println("Slot No.\t Registration No.");
        parkedVehicleList.stream().forEach(parking->{
            if(null!=parking.getVehicle()) {
                System.out.println(parking.getParkingId() + "\t\t" + parking.getVehicle().getRegistrationNumber());
            }
        });
    }
}
