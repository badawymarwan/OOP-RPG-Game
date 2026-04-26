public abstract class Vehicle {
private String registrationNo;
private String model;
private int year;
private double rentalFees;
public Vehicle() {
}
public Vehicle(String r, String m, int y, double f) {
this.registrationNo = r;
this.model = m;
this.year = y;
this.rentalFees=f;
}
public String getRegistrationNo() {
return registrationNo;
}
public void setRegistrationNo(String registrationNo) {
this.registrationNo = registrationNo;
}
public String getModel() {
return model;
}
public void setModel(String model) {
this.model = model;
}
public int getYear() {
return year;
}
public void setYear(int year) {
this.year = year;
}
public double getRentalFees() {
return rentalFees;
}
public void setRentalFees(double rentalFees) {
this.rentalFees = rentalFees;
}
public double calculateRentalFee (int d) {
return d * rentalFees;
}
public abstract String getStatus();
public String toString() {
return "The registration No:" + registrationNo + "\n" +
"The model:" + model + "\n" +
"The year:" + year + "\n" +
"The rental fees:" + rentalFees+ "\n";
}

}
