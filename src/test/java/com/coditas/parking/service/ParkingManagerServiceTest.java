package com.coditas.parking.service;

import com.coditas.parking.model.CustomerParking;
import com.coditas.parking.model.ParkingArea;
import com.coditas.parking.service.exception.ParkingLimitOverflowException;
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
    }

    @Test
    public void testCreateParkingArea() {
        parkingManagerService.createParkingArea(2);
        Assert.assertEquals("Parking not created",parkingArea.getParkingCapacity(),2);
    }

}
