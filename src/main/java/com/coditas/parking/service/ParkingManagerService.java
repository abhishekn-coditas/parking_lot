package com.coditas.parking.service;

import com.coditas.parking.IParkingManagerService;
import com.coditas.parking.model.CustomerParking;
import com.coditas.parking.model.ParkingArea;
import com.coditas.parking.model.Vehicle;
import com.coditas.parking.service.exception.ParkingLimitOverflowException;
import com.coditas.parking.service.exception.UnrecognisedCommandException;
import com.coditas.parking.service.exception.VehicleNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ParkingManagerService implements IParkingManagerService {

    ParkingArea parkingArea;

    public ParkingManagerService() {}

     public void createParkingArea(int allocateCapacity) {
         parkingArea = ParkingArea.get(allocateCapacity);
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

    public void deAllocateParking(String vehicleNumber,int parkingHrs) throws VehicleNotFoundException {
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
            throw new VehicleNotFoundException(vehicleNumber);
        }
    }

    public void parkingStatus() {
        System.out.println("Slot No.\t Registration No.");
        parkingArea.getParkedVehicleList().stream().forEach(parking->{
            if(null!=parking.getVehicle()) {
                System.out.println(parking.getParkingId() + "\t\t\t " + parking.getVehicle().getRegistrationNumber());
            }
        });
    }

    public void initParkingApplication(String inputFileName) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(inputFileName));
            for (String line : allLines) {
                String[] parameters = line.split(" ");
                String command = parameters[0];
                try {
                    switch (command) {
                        case "create_parking_lot":
                            createParkingArea(Integer.valueOf(parameters[1]));
                            break;
                        case "park":
                            allocateParking(parameters[1]);
                            break;
                        case "leave":
                            String secondParameter = line.substring(line.indexOf(" ") + 1);
                            deAllocateParking(parameters[1], Integer.valueOf(parameters[2]));
                            break;
                        case "status":
                            parkingStatus();
                            break;
                        default:
                            throw new UnrecognisedCommandException(command);
                    }
                }
                catch(ParkingLimitOverflowException exception) {
                    System.out.println(exception.getMessage());
                }
                catch(UnrecognisedCommandException exception) {
                    System.out.println(exception.getMessage());
                }
                catch(VehicleNotFoundException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
