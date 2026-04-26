public class Truck extends Vehicle { // inheriting all attributes and methods from vehicle 

	private double cargoLoad; // adds all inherited attributes as its own unique attribute
	
	public Truck() {
		super(); // calling the constructor of the superclass
	}
	
	// constructor with parameters
	public Truck(String r, String m, int y, double f, double cargoLoad) {
		super(r, m, y, f); // passing the common attributes to the Vehicle constructor
		setCargoLoad(cargoLoad); // use setter for validation
	}

	// setter for cargoLoad with validation
	public void setCargoLoad(double cargoLoad) {
		this.cargoLoad = cargoLoad >= 0 ? cargoLoad : 0; // if negative, set to 0, ternary op used for convinience
	}

	// getter for cargoLoad
	public double getCargoLoad() {
		return cargoLoad;
	}

	// overriding getStatus() from Vehicle
	// determines the status of the truck based on its year
	@Override
	public String getStatus() {
		if (getYear() < 2010)
			return "cancelled"; // too old
		else if (getYear() >= 2010 && getYear() <= 2020)
			return "old"; // midd range
		else
			return "new"; // recent model
	}
	
	// unloads the cargo of the truck
	public void unloadCargo() {
		System.out.println("The cargo is being unloaded!");
	}

	// overriding the toString method from the superclass
	// adds cargoLoad to the Vehicle's toString output
	public String toString() {
		return super.toString() + "The cargo load: " + cargoLoad;
	}
}