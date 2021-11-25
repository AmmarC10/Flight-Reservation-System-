import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args) throws FlightFullException, SeatNotFoundException, DuplicatePassengerException, SeatOccupiedException, FlightNotFoundException, PassengerNotInManifestException {
		FlightManager manager = new FlightManager();

		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations


		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equals("Q") || action.equals("QUIT"))
				return;

			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			else if (action.equalsIgnoreCase("RES")) {

					String flightNum = "";
					String passengerName = "";
					String passport = "";
					String seat = "";
				try {
					if (commandLine.hasNext()) {
						flightNum = commandLine.next();
					}
					if (commandLine.hasNext()) {
						passengerName = commandLine.next();
					}
					if (commandLine.hasNext()) {
						passport = commandLine.next();
					}
					if (commandLine.hasNext()) {
						seat = commandLine.next();

						Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seat);
						if (res != null) {
							myReservations.add(res);
							res.print();
						} else {
							System.out.println(manager.getErrorMessage());
						}
					}
				}catch (FlightNotFoundException e) {System.out.println(flightNum + " is not found");}
				catch (DuplicatePassengerException e) {System.out.println(passengerName + " is a duplicate passenger");}
				catch(SeatNotFoundException e) {System.out.println(seat + " is not found");}
				catch(SeatOccupiedException e) {System.out.println(seat + " is occupied");}
				catch(FlightFullException e){System.out.println(flightNum + " is full"); }

			}
			else if (action.equalsIgnoreCase("CANCEL"))
			{
				Reservation res = null;
				String flightNum = null;
				String passengerName = "";
				String passport = "";


				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
				try {
					int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
					if (index >= 0) {
						manager.cancelReservation(myReservations.get(index));
						myReservations.remove(index);
					} else
						System.out.println("Reservation on Flight " + flightNum + " Not Found");
				}catch(SeatNotFoundException e) {}
				}
			}
			else if (action.equalsIgnoreCase("SEATS")) {
				String flightNum = "";


				if (commandLine.hasNext()) {
					flightNum = commandLine.next();
					if(manager.flightMap.containsKey(flightNum)){
						manager.flightMap.get(flightNum).printSeats();
					}
				}
			}
			else if (action.equalsIgnoreCase("MYRES"))
			{
				for (Reservation myres : myReservations)
					myres.print();
			}
			else if (action.equalsIgnoreCase("SORTBYDEP"))
			{
				manager.sortByDeparture();
			}
			else if (action.equalsIgnoreCase("SORTBYDUR"))
			{
				manager.sortByDuration();
			}
			else if (action.equalsIgnoreCase("CRAFT"))
			{
				manager.printAllAircraft();
			}
			else if (action.equalsIgnoreCase("SORTCRAFT"))
			{
				manager.sortAircraft();
			}
			else if(action.equalsIgnoreCase("PASMAN")){
				String flightNum = "";
				if(commandLine.hasNext()){
					flightNum = commandLine.next();
					if(manager.flightMap.containsKey(flightNum)){
						manager.flightMap.get(flightNum).printPassengerManifest();
					}
				}
			}
			System.out.print("\n>");
		}
	}


}

