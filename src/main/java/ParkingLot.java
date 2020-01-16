import com.coditas.parking.service.ParkingManager;
import com.coditas.parking.service.exception.ParkingLimitOverflowException;
import com.coditas.parking.service.exception.UnrecognisedCommandException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParkingLot {

    public static void main(String args[]) {

        String inputFileName = args[0];
        ParkingManager parkingManager = null;
        //read file into stream, try-with-resources
        try {
            List<String> allLines = Files.readAllLines(Paths.get(inputFileName));
            for (String line : allLines) {
                String[] parameters = line.split(" ");
                String command = parameters[0];
                try {
                        switch (command) {
                            case "create_parking_lot":
                                parkingManager = new ParkingManager(Integer.valueOf(parameters[1]));
                                break;
                            case "park":
                                parkingManager.allocateParking(parameters[1]);
                                break;
                            case "leave":
                                String secondParameter = line.substring(line.indexOf(" ") + 1);
                                parkingManager.deAllocateParking(parameters[1], Integer.valueOf(parameters[2]));
                                break;
                            case "status":
                                parkingManager.parkingStatus();
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
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
