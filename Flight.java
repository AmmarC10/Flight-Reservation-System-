import java.beans.PersistenceDelegate;
import java.util.*;

class DuplicatePassengerException extends Exception{

	public DuplicatePassengerException(String m){
		super(m);
	}
}

class FlightFullException extends Exception{

	public FlightFullException(String m){
		super(m);
	}
}

class SeatNotFoundException extends Exception{

	public SeatNotFoundException(String m){
		super(m);
	}
}

class SeatOccupiedException extends Exception {
	public SeatOccupiedException(String m){
		super(m);
	}
}

class PassengerNotInManifestException extends Exception{
	public PassengerNotInManifestException(String m){
		super(m);
	}
}
public class Flight
{


	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	protected TreeMap<String, Passenger> seatMap = new TreeMap<String, Passenger>();;




	protected Random random = new Random();

	public void printSeats() {
		String[][] layout = aircraft.getSeatLayout();
		for (int c = 0; c < layout.length; c++) {
			System.out.println("  ");
			if (c == 2) System.out.println("\n");
			for (int i = 0; i < layout[c].length; i++) {
				System.out.print(layout[c][i] + " ");
			}
		}
	}
	private String errorMessage = "";
	  
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public Flight()
	{
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();

	}
	
	public Flight(String flightNum)
	{
		this.flightNum = flightNum;
	}
	
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();




		}




	public void printPassengerManifest(){
		for(int c = 0; c < manifest.size(); c++){
			System.out.println(manifest.get(c).toString());
		}
	}

	public TreeMap<String, Passenger> getSeatMap() { return seatMap; }
	public Type getFlightType()
	{
		return type;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}

	
	public int getNumPassengers()
	{
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}
	
	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.numEconomySeats);
		p.setSeat("ECO"+ seat);
	}
	
	public String getLastAssignedSeat()
	{
		if (!manifest.isEmpty())
			return manifest.get(manifest.size()-1).getSeat();
		return "";
	}
	
	public boolean seatsAvailable(String seatType)
	{
		if (!seatType.equalsIgnoreCase("ECO")) return false;
		return numPassengers < aircraft.numEconomySeats;
	}
	



	public void reserveSeat(Passenger p, String seat) throws FlightFullException, DuplicatePassengerException, SeatNotFoundException, SeatOccupiedException {
			if(numPassengers >= aircraft.getNumSeats()){ throw new FlightFullException("Flight Full");} // throw exception if flight full
			if(manifest.indexOf(p) >= 0) {throw new DuplicatePassengerException("Duplicate Passenger"); } // throw exception if duplicate passenger
			boolean found = false;
			String[][] layout = aircraft.getSeatLayout();
			for(int c = 0; c < layout.length; c++){
				for(int i = 0; i < layout[c].length; i++){
					if(layout[c][i].equals(seat)) { found = true; } // checks to see if seat exists on the flight
				}
			}
			if(seatMap.containsKey(seat)) {throw new SeatOccupiedException("Seat occupied");} // throws exception if seat occupied
			if(!found) { throw new SeatNotFoundException("Seat not found"); } // throws exception if seat not found

			manifest.add(p); // adds passenger to manifest
			numPassengers++; // increases passenger total
			for(int c = 0; c < layout.length; c++){
				for(int i = 0; i < layout[c].length; i++){
					if(layout[c][i].equals(seat)) { layout[c][i] = "XX"; } // replaces the seat with "XX" to show its occupied in seat layout
				}
			}
			p.setSeat(seat); // sets seat for passenger
			seatMap.put(seat, p); // maps passenger to seat in seatMap

		}



		public void cancelSeat(String seat) throws SeatNotFoundException, PassengerNotInManifestException {
			Set<String> keys = seatMap.keySet();
			Passenger p = new Passenger();
			boolean found = false;
			String seatNum = "";
			for(String s: keys){ // loops through seats in seatMap
				if(s.equals(seat)) {
					found = true;
					p = seatMap.get(seat);
					seatNum = s; // sets seatNum to the appropriate seat of the passenger
				}
			}
			if(!found){throw new SeatNotFoundException("");} // throws SeatNotFoundException if the seat is not in seatMap
			boolean inManifest = false;
			for(int c = 0; c < manifest.size(); c++){
				if(manifest.contains(p)) {inManifest = true; } // checks if passenger is in manifest
			}
			if(!inManifest){ throw new PassengerNotInManifestException("Passenger not in manifest"); } // throws exception if not in manifest
			manifest.remove(p); // removes passenger from manifest
			seatMap.remove(seatNum,p); // removes passenger and seat from seatMap
			for(int c = 0; c < aircraft.getSeatLayout().length; c++){
				for(int i = 0; i < aircraft.getSeatLayout()[c].length; i++){
					if(aircraft.getSeatLayout()[c][i].equals("XX")){aircraft.getSeatLayout()[c][i] = seatNum; } // attempts to change XX to the seatNum but does not work!
				}
			}
	}


	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}
	
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}
}
