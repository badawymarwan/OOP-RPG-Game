public class Bus extends Vehicle {
private int capacity;
public Bus() {
super(); // calling the constructor of the superclass
}
public Bus(String r, String m, int y, double f, int capacity) {
super(r, m, y, f);
this.capacity = capacity;
}
public void setCapacity(int capacity) {
this.capacity = capacity >=0 ? capacity: 0;
}
public int getCapacity() {
return capacity;
}
@Override
public String getStatus() {
if (capacity < 20 )
return "limited";
else if ( capacity>=20 && capacity <= 50)
return "standard";
else
return "premium" ;
}
public void openDoor() {
System.out.println("The doors are opened!");
}
public String toString() {
return super.toString() + "The capacity:" + capacity + "\n";
}
}