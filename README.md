## Parking Lot Java Application
	Parking lot is an java application which helps in parking a vehicle. Calcutate the total parking charges on hourly basis. Currently the 
application takes file as an input containing parking commands. Application creates number of parking as per user input. When a car comes for parking, this application check for availble parking. In case parking is not availble system will prompt user "parking lot is full". When vehicle leaves the parking, based on number of parking hours, application calculates parking charges.


## Entities
ParkingManagerService is main entity which performs all operations like :
- create car parking area.
- check parking availability and allocate parking for vehicle.
- validate vehicle details.
- View parking status.
- calculate parking charges.

## Future Enhancments
- This application can be extended for multilevel parking.
- Persisting the parking information to database will be additional advantage.
- We can obtain reports like, collection of money, parking history days wise, month wise year wise. 


## System requirements
JDK 1.7 or higher.
Maven.

## Installation Process
To install all dependencies, compile and run tests:
$ bin/setup

To run the code so it accepts input from a file:
$ bin/parking_lot file_inputs.txt



