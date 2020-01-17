package com.coditas.parking.service;

import com.coditas.parking.model.CustomerParking;
import com.coditas.parking.model.ParkingArea;
import com.coditas.parking.service.exception.ParkingLimitOverflowException;
import com.coditas.parking.service.exception.VehicleNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(value = MockitoJUnitRunner.class)
public class ParkingManagerServiceTest {

    @InjectMocks
    ParkingManagerService parkingManagerService;


    List<CustomerParking> parkedVehicleList;

    @Test
    public void testCreateParkingArea() {
        parkingManagerService.createParkingArea(2);
        Assert.assertEquals("Parking not created",2,ParkingArea.getParkingCapacity());
        Assert.assertFalse("Parking area must be available",ParkingArea.getParkedVehicleList().isEmpty());
        ParkingArea.reset();
    }

    @Test
    public void testAllocateParking() throws ParkingLimitOverflowException {
        parkingManagerService.createParkingArea(2);
        parkingManagerService.allocateParking("KA-01-HH-7777");
        Assert.assertEquals("Vehicle parking failure.","KA-01-HH-7777",ParkingArea.getParkedVehicleList().get(0).getVehicle().getRegistrationNumber());
        ParkingArea.reset();
    }

    @Test
    public void testDeAllocateParking()throws ParkingLimitOverflowException, VehicleNotFoundException {
        parkingManagerService.createParkingArea(2);
        parkingManagerService.allocateParking("KA-01-HH-2345");
        parkingManagerService.deAllocateParking("KA-01-HH-2345",4);
        Assert.assertNull("Parking must be empty.",ParkingArea.getParkedVehicleList().get(0).getVehicle());
        ParkingArea.reset();
    }

    @Test
    public void testNextAvailableParkingSlot() {
        parkingManagerService.createParkingArea(2);
        Assert.assertEquals("Next available parking id : 1",Integer.valueOf(1),parkingManagerService.getNextAvailableParkingSlot());
    }

    @Test
    public void testIsParkingAvailable() {
        parkingManagerService.createParkingArea(2);
        Assert.assertEquals("Parking is full",parkingManagerService.isParkingAvailable(),Boolean.TRUE);
        ParkingArea.reset();
    }

    @Test(expected = ParkingLimitOverflowException.class)
    public void testParkingIsFull() throws ParkingLimitOverflowException {
        parkingManagerService.createParkingArea(2);
        parkingManagerService.allocateParking("KA-01-HH-3456");
        parkingManagerService.allocateParking("KA-01-HH-4567");
        parkingManagerService.allocateParking("KA-01-HH-5678");
    }

    @Test(expected = VehicleNotFoundException.class)
    public void testUnknownVehicle() throws VehicleNotFoundException {
        parkingManagerService.createParkingArea(2);
        parkingManagerService.deAllocateParking("KA-01-HH-2345",4);
        ParkingArea.reset();
    }
}
