package com.coditas.parking.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingArea {

    private static int parkingCapacity;

    private static List<CustomerParking> parkedVehicleList;

    public static ParkingArea singleToneInstance;

    public static ParkingArea get(int parkingSize) {
        if (singleToneInstance == null) {
            parkingCapacity = parkingSize;
            parkedVehicleList = new ArrayList<>(parkingCapacity);
            singleToneInstance = new ParkingArea();
        }
        return singleToneInstance;
    }

    public static void reset() {
            parkingCapacity = 0;
            parkedVehicleList = null;
            singleToneInstance = null;
    }

    public static int getParkingCapacity() {
        return parkingCapacity;
    }

    public static List<CustomerParking> getParkedVehicleList() {
        return parkedVehicleList;
    }

    public static void setParkedVehicleList(List<CustomerParking> parkedVehicleList) {
        ParkingArea.parkedVehicleList = parkedVehicleList;
    }

}
