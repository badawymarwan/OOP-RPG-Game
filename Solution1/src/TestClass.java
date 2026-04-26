import java.util.Scanner;
public class TestClass {
public static void main(String[] args) {
Scanner input = new Scanner(System.in);
System.out.println("Enter the Registration No:");
String no = input.nextLine();
System.out.println("Enter the Model:");
String m = input.nextLine();
System.out.println("Enter the year:");
int y = input.nextInt();
input.nextLine(); // fix 3: clears buffer so choice isn't skipped
System.out.println("Enter the capacity:");
int cap = input.nextInt();
input.nextLine(); // clears buffer

//creates an object of type Bus
Bus b = new Bus(no, m, y, 2.5, cap);

// fix 1: create a Truck object
Truck t = new Truck("0000", "Default", 2015, 3.5, 0);

System.out.println(b.toString());

System.out.println("Are you renting a bus or a truck? (Enter bus/truck):");
String choice = input.nextLine();

if (choice.equals("bus")) {
    System.out.println("How many days would you like to rent the bus:");
    int day = input.nextInt();
    double fees = b.calculateRentalFee(day);
    System.out.println("Fees to pay: " + fees + " BD");
} else if (choice.equals("truck")) {
    System.out.println("How many days would you like to rent the truck:");
    int day = input.nextInt();
    double fees = t.calculateRentalFee(day);
    System.out.println("Fees to pay: " + fees + " BD");
} else {
    System.out.println("Invalid choice!");
}
// fix 2: removed the duplicate fees and day lines that were outside the if block
}
}