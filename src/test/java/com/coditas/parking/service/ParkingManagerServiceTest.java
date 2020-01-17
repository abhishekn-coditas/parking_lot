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

    @Mock
    ParkingArea parkingArea;

    List<CustomerParking> parkedVehicleList;

    @Before
    public void init() {
        parkedVehicleList = new ArrayList<>(1);
        CustomerParking parking = new CustomerParking();
        parking.setParkingId(1);
        parkedVehicleList.add(parking);
        parkingArea.setParkedVehicleList(parkedVehicleList);
    }

    @Test
    public void testCreateParkingArea() {
        parkingManagerService.createParkingArea(2);
        Assert.assertEquals("Parking not created",parkingArea.getParkingCapacity(),2);
    }

    @Test
    public void testAllocateParking() throws ParkingLimitOverflowException {
        parkingManagerService.allocateParking("park KA-01-HH-1234");
    }

    @Test
    public void testDeAllocateParking()throws ParkingLimitOverflowException, VehicleNotFoundException {
        testAllocateParking();
        parkingManagerService.deAllocateParking("park KA-01-HH-1234",4);
    }

    @Test
    public void testIsParkingAvailable() throws ParkingLimitOverflowException {
        Assert.assertEquals("Parking is full",parkingManagerService.isParkingAvailable(),Boolean.TRUE);
    }

    @Test
    public void testGetNextAvailableParkingSlot() throws ParkingLimitOverflowException {
        Assert.assertEquals("Incorrect slot",Integer.valueOf(1), Integer.valueOf(parkingManagerService.getNextAvailableParkingSlot()));
    }
}
