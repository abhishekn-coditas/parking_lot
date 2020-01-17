import com.coditas.parking.IParkingManagerService;
import com.coditas.parking.service.ParkingManagerService;
import com.coditas.parking.service.exception.ParkingLimitOverflowException;
import com.coditas.parking.service.exception.UnrecognisedCommandException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParkingLot {

    public static void main(String args[]) {
        String inputFileName = args[0];
        IParkingManagerService parkingManager = new ParkingManagerService();
        parkingManager.initParkingApplication(inputFileName);
    }

}
