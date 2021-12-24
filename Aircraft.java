
public class Aircraft implements Comparable<Aircraft>
{
  int numEconomySeats;
  int numFirstClassSeats;
}
  String model;

  private String[][] seatLayout;
  
  public Aircraft(int seats, String model)
  {
  	this.numEconomySeats = seats;
  	this.numFirstClassSeats = 0;
  	this.model = model;
  	this.seatLayout = setSeatLayout(); // sets seatlayout for aircraft using the setSeatLayout method
  }

  public Aircraft(int economy, int firstClass, String model)
  {
  	this.numEconomySeats = economy;
  	this.numFirstClassSeats = firstClass;
  	this.model = model;
  	this.seatLayout = setSeatLayout();
  }

	public String[][] setSeatLayout() {
		int totalSeats = numEconomySeats + numFirstClassSeats; // gets total seats
		if (totalSeats % 4 == 0) { // makes sure seats is divisible by 4
			int row = 4; // each aircraft always has 4 rows
			int seatsinrow = totalSeats / 4; // gets the total seats in a row for an aircaft
			 seatLayout = new String[row][seatsinrow]; // creates appropriate array for the seat layout
			for (int c = 0; c < seatLayout[0].length - 1; c++) {
				for (int i = 0; i < seatsinrow; i++) {
					if (c == 0) {
						seatLayout[c][i] = i+1  + "A"; // for the first row
					}
					if (c == 1) {
						seatLayout[c][i] = i+1  + "B"; // for the second row
					}
					if (c == 2) {
						seatLayout[c][i] = i+1  + "C"; // for the third row
					}
					if (c == 3) {
						seatLayout[c][i] = i+1 + "D"; // for the fourth row
					}
				}
			}

			int firstperrow = numFirstClassSeats / 4;
			for (int c = 0; c < seatLayout.length; c++) { // this part will run if there are first class seats available
				for (int i = 0; i < seatLayout[c].length; i++) {
					if (firstperrow > 0) {
						seatLayout[c][i] = seatLayout[c][i] + "+";
						firstperrow -= 1;
					}

				}
				firstperrow = numFirstClassSeats / 4;
			}

		}
	return seatLayout;
  }


  	public String[][] getSeatLayout() { return seatLayout; }
  
	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}
	
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}

  public int compareTo(Aircraft other)
  {
  	if (this.numEconomySeats == other.numEconomySeats)
  		return this.numFirstClassSeats - other.numFirstClassSeats;
  	
  	return this.numEconomySeats - other.numEconomySeats; 
  }
}
