package application;

public class Airline {
	
	private String airlineName;
	
	private String airlineNumber;
	
	private String departureAirport;
	
	private String arrivalAirport;

	// Constructor
	public Airline(String airlineName, String airlineNumber, String departureAirport, 
			String arrivalAirport) {
		
		this.airlineName = airlineName;
		this.airlineNumber = airlineNumber;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		
	}
	
	// Getter methods
	public String getAirlineName() {
		return this.airlineName;
	}
	
	public String getAirlineNumber() {
		return this.airlineNumber;
	}
	
	public String getDepartureAirport() {
		return this.departureAirport;
	}
	
	public String getArrivalAirport() {
		return this.arrivalAirport;
	}
	
	@Override
	public String toString() {
		return this.airlineName + "\t" + this.airlineNumber + "\t" + this.departureAirport + "\t" + this.arrivalAirport;
	}
}
