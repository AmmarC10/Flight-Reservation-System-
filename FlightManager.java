import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

class FlightNotFoundException extends Exception{
	public FlightNotFoundException (String m){
		super(m);
	}
}
public class FlightManager
{
  ArrayList<Flight> flights = new ArrayList<Flight>();
  TreeMap<String, Flight> flightMap = new TreeMap<String, Flight>();
  
  String[] cities 	= 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
  final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;
  
  int[] flightTimes = { 3, // Dallas
  											1, // New York
  											7, // London
  											8, // Paris
  											16,// Tokyo
  										};
  
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
  ArrayList<String> flightNumbers = new ArrayList<String>();
  
  String errMsg = null;
  Random random = new Random();



	public FlightManager()
  {
  	// Create some aircraft types with max seat capacities
  	airplanes.add(new Aircraft(84, "Boeing 737"));
  	airplanes.add(new Aircraft(180,"Airbus 320"));
  	airplanes.add(new Aircraft(40, "Dash-8 100"));
  	airplanes.add(new Aircraft(32, "Bombardier 5000"));
  	airplanes.add(new Aircraft(400, 12, "Boeing 747"));
  	
  	// Populate the list of flights with some random test flights

  	
	try {
		FileReader reader = new FileReader("flights.txt");
		Scanner input = new Scanner(reader);
		String info = "";
		String[] flightInfo;
		String airline;
		String flightnum;
		String dest;
		String dep;
		int duration = 0;
		int passengerCap;
		Aircraft aircraft;
		while(input.hasNextLine()){
			info = input.nextLine();
			flightInfo = info.split(" ");
			airline = flightInfo[0].replace("_", " ");
			flightnum = generateFlightNumber(airline);
			dest = flightInfo[1];
			int index = dest.indexOf("_");
			if(index != -1) { dest = dest.replace("_"," "); }
			dep = flightInfo[2];
			passengerCap = Integer.parseInt(flightInfo[3]);
			if(passengerCap <= 32) { aircraft = airplanes.get(3); }
			else if(passengerCap <= 40) { aircraft = airplanes.get(2); }
			else if(passengerCap <= 84) { aircraft = airplanes.get(0); }
			else { aircraft = airplanes.get(1); }
			int city = 0;
			for(int c = 0; c < cities.length; c++){
				if(cities[c].equalsIgnoreCase(dest)) { city = c; }
			}
			duration = flightTimes[city];
			if(duration > 8) { aircraft = airplanes.get(4); }

			Flight current = new Flight(flightnum, airline, dest, dep, duration,aircraft);
			flightMap.put(flightnum, current);

		}


	} catch (FileNotFoundException e) {System.out.println(e.getMessage());}
  }






  private String generateFlightNumber(String airline)
  {
  	String word1, word2;
  	Scanner scanner = new Scanner(airline);
  	word1 = scanner.next();
  	word2 = scanner.next();
  	String letter1 = word1.substring(0, 1);
  	String letter2 = word2.substring(0, 1);
  	letter1.toUpperCase(); letter2.toUpperCase();
  	
  	// Generate random number between 101 and 300
  	boolean duplicate = false;
  	int flight = random.nextInt(200) + 101;
  	String flightNum = letter1 + letter2 + flight;
   	return flightNum;
  }

  public String getErrorMessage()
  {
  	return errMsg;
  }
  
  public void printAllFlights()
  {
  	Set<String> keys = flightMap.keySet();
  	for(String s: keys){
  		System.out.println(flightMap.get(s).toString());
	}

  }
  
  public boolean seatsAvailable(String flightNum, String seatType)
  {
  	try {
		if (!flightMap.containsKey(flightNum)) {
			throw new FlightNotFoundException("");
		}
	}catch(FlightNotFoundException e) {System.out.println("Flight not found"); }
  	Flight current = flightMap.get(flightNum);
   	return current.seatsAvailable(seatType);

  }
  
  public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat) throws FlightNotFoundException, SeatOccupiedException, SeatNotFoundException, FlightFullException, DuplicatePassengerException {

		if (!flightMap.containsKey(flightNum)) {
			throw new FlightNotFoundException("Flight not found");
		}
		Flight current = flightMap.get(flightNum);
		Passenger p = new Passenger(name,passport);
		current.reserveSeat(p,seat);
		return new Reservation(flightNum, current.toString(), name, passport, seat);


  }

  public boolean cancelReservation(Reservation res) throws FlightNotFoundException, SeatNotFoundException, PassengerNotInManifestException {
    if(!flightMap.containsKey(res.getFlightNum()))
    {
    	throw new FlightNotFoundException("Flight not found");
		}
  	Flight flight = flightMap.get(res.getFlightNum());
  	flight.cancelSeat(res.seat);
   	return true;
  }
  
    
  public void sortByDeparture()
  {
	  Collections.sort(flights, new DepartureTimeComparator());
  }
  
  private class DepartureTimeComparator implements Comparator<Flight>
  {
  	public int compare(Flight a, Flight b)
  	{
  	  return a.getDepartureTime().compareTo(b.getDepartureTime());	  
  	}
  }
  
  public void sortByDuration()
  {
	  Collections.sort(flights, new DurationComparator());
  }
  
  private class DurationComparator implements Comparator<Flight>
  {
  	public int compare(Flight a, Flight b)
  	{
  	  return a.getFlightDuration() - b.getFlightDuration();
   	}
  }
  
  public void printAllAircraft()
  {
  	for (Aircraft craft : airplanes)
  		craft.print();
   }
  
  public void sortAircraft()
  {
  	Collections.sort(airplanes);
  }
}
