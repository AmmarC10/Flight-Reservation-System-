
public class Passenger
{
	private String name;
	private String passport;
	private String seat;
	private String seatType;
	
	public Passenger(String name, String passport, String seat)
	{
		this.name = name;
		this.passport = passport;
		this.seat = seat;

	}

	public Passenger(){
		this.name = "";
		this.passport = "";
		this.seat = "";

	}

	
	public Passenger(String name, String passport)
	{
		this.name = name;
		this.passport = passport;
	}
	
	public boolean equals(Object other)
	{
		Passenger otherP = (Passenger) other;
		return name.equals(otherP.name) && passport.equals(otherP.passport);
	}
	
	public String getSeatType()
	{
		return seatType;
	}

	public void setSeatType(String seatType)
	{
		this.seatType = seatType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getSeat()
	{
		return seat;
	}

	public void setSeat(String seat)
	{
		this.seat = seat;
	}
	
	public String toString()
	{
		return name + " " + passport + " " + seat;
	}
}
