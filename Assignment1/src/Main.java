//Static variables: Declared after the class directly, can be accessed by all methods
//Local Variables: Declared inside a method, only that method can use it
//Parameters: If a variable is declared in a method and we wanna use it in another method, we make it a parameter

import java.util.Scanner;
public class Main {
   static Scanner scanner = new Scanner(System.in); //static variable, all methods can use it

    //// -------Student Database---------    // arrays should be static, itll be used by plenty of methods
    static int[] studentID = new int[50];
    static String[] studentNames = new String[50];
    static String[] departmentName = new String [50];
    static int studentCount =0; //init value
    static double [] [] studentGrades = new double [50] [5];
    static double [] studentGPA = new double [50];

   //// ---------Main Method---------- //code gets executed from main first
   public static void main(String[] args) { 
     
     int choice=0; //we need a starting value for a loop to run

     //while loop syntax is while(condition){consequence 1;consequence 2; ...}
     while (choice !=6) {
        choice = displayMenu(); //Method called, return value(user input) is set to choice, consequence 1
        handlingMenu(choice); //Method called,correct switch number is chosen  ,consequence 2
     }
    }


    //// --------Menu-----------
    
    /*once a method is executed, anything created inside it is "thrown away", so we cant use int=userChoice here because the other
    methods would not be able to refer to it*/
    static int displayMenu() { 
        int userChoice;
        System.out.println("===== SMART CAMPUS SYSTEM =====");
        System.out.println("1. Register New Student");
        System.out.println("2. Enter Grades");
        System.out.println("3. View Student Report");
        System.out.println("4. Show Statistics");
        System.out.println("5. Search Student");
        System.out.println("6. Exit");
        userChoice = readInt("Enter your choice: ");
        return userChoice;
        /*return is used by tHIS method to return the data to whoever called it so it doesnt get "thrown away",
         in this case, the main method called it, SO THE USER INPUT IS RETURNED TO MAIN METHOD;*/
    }


    //// --------Handling the Menu--------
    
    // CHOICE IS A LOCAL VARIABLE DECLARED IN MAIN, PASSED HERE AS A PARAMETER!!!
    static void handlingMenu(int choice){ // call in main 
     
        switch(choice){ // used enhanced switches here bc (personally) its easier than using loops and if statements
            case 1 -> RegisterStudent(); // instead of System.out.println("Register New Student"); ‼️‼️‼️‼️
            case 2 -> enterGrades();// instead of System.out.println("Enter Grades");
            case 3 -> viewStudentReport();// instead of System.out.println("View Student Report");
            case 4 -> showStatistics();// instead of System.out.println("Show Statistics");
            case 5 -> searchStudent();// instead of System.out.println("Search Student"); 
            case 6 -> System.out.println("Goodbye");
            default -> System.out.println("Invalid choice");  // case 6 btw
        }
    }


    //// ----------Registering the student-----------
    
    static void RegisterStudent(){ //void bc we do not need it to return anything 
        System.out.println("------Register a New Student------");

        // getting valid ID
        int id=0; //local variable, needs to be intialized to start the loop
        boolean validID = false; // set to true when ID passes all checks

        while (validID ==false) { // starting value for loop, stays false until correct ID entered
            id = readInt("Enter Student ID: ");

            if (id<=0){ // if id less than 0 or equal to, display error
                System.out.println("Error, ID must be a positive number");
            }
            else if (idExists(id) == true){ // idExists Method is being called and id is being used as a parameter, idExists method runs
                System.out.println("ID already exists");
            }
            else{
                validID = true;
            }
        }
            // getting valid Name
            String name ="";
            boolean validName=false; //starting value for loop, means if the user types invalid name, it stays false, then loop continues, but if user types true name, loop ends
            while ((validName ==false)) { 
                name = readString("Enter full name: ").trim(); // trim to remove spaces 
                if (isValidName(name) == false){
                    System.out.println("Name must have 2 words and no digits");
                }
                else{
                    validName = true;
                }
            }

            // getting valid department
            String department=""; 
            boolean validDept =false; //starting value for loop
            while (validDept == false){
                department = readString("Enter a department: ");
                if (department.isEmpty()){
                    System.out.println("Enter a valid department name");
                }
                else{
                    department = department.toUpperCase();
                    validDept = true;
                }
            }
            
            // Saving the Data in the arrays
            studentID[studentCount] =id; // save ID at position X
            studentNames[studentCount] =name; // save name at position X
            departmentName[studentCount] =department; // save department at position X
            studentCount++; // increment student count by one, now we have one student 
            System.out.println("Student is now registered");

    }
        //-------Checks if ID already exists (FOR VALIDATION WE NEED NEW METHODS)
        static boolean idExists(int id){ //method to check if ID already exists, used id as a parameter
            for (int i = 0;i<studentCount;i++){
                if (studentID[i] == id){ // does the student ID match in the array? if yes then ID already exists, print an error message
                    return true; // if yes, return true
                }
            }
            return false; //all students are checked, ID is new
        }

        //-----Checks if Name has 2 words and no digits (FOR VALIDATION WE NEED NEW METHODS)
        static boolean isValidName(String name){
            if (name == null || name.isEmpty()){
                return false;
            }
            if (name.matches(".*\\d.*")){ // anything, then a digit, then anything, like Marwan2Badawy then the vlaue returned will be false
                return false;
            } 
             String[] parts = name.split("\\s+"); //split breaks a String into pieces everytime a space is written, MarwanBadawy is split to Marwan Badawy
                return parts.length >= 2;
        }


     //// ----------Entering Grades-----------

        static void enterGrades(){ //void because we do not anything back from the method, we just want it to save grades
            System.out.println("--------Enter your Grades--------");
            
        // Finding the student in the array
            int id = readInt("Please enter your student ID: ");
            
            int index = findStudentIndex(id); //findStudentIndex method called with id as a parameter to find student position, find the position then assign it to index

            if (index ==-1){ // -1 means student not found, this is a value we just give to the program to display our message
                System.out.println("Student ID not found");
                return; // stops method immediately if ID not found
            }

        //Student found, now we ask them for 5 grades
        for(int i=0;i<5;i++){
            double grade = -1; // starting value for the upcoming loop
            boolean validGrade = false; // starting value 
            while (validGrade == false){
                grade = readDouble("Enter grade for course " + (i+1) + " (0-100): "); // we dont wanna start from 0, hence i+1
                if (grade<0 || grade >100){
                    System.out.println("Invalid grade. Must be between 0 and 100");
                }
                else{
                    validGrade = true; // stops the while loop
                }
            }
            studentGrades [index] [i] = grade; // index is the position in the array, i is which course(1,2,3,4,5)
        }      // the output will be like studentGrades[1][2] is students 2's third course grade

            double average = calculateAverage(index);
            studentGPA[index] = calculateGPA(average);

        System.out.println("Grades saved successfully.");
        }

        //Finding the student index by ID
        static int findStudentIndex(int id){ //int id is a parameter
            for(int i =0;i<studentCount;i++){
                if(studentID[i] == id){ //if the id stored in the position is the same as what the user typed
                    return i; // if you found the student id in the index, return it to 'whoever' called, which is the entergrades method
                }
            }
            return -1;
        }



    //// ----------Student Report-----------
    
        static void viewStudentReport(){ // we dont need a return value
            System.out.println("--------Student Report--------");
            
           int id = readInt("Enter your student ID: ");

            int index = findStudentIndex(id); // runs the method to get the index of the 1st/2nd/3rd students

            if (index ==-1){
                System.out.println("Student ID not found ");
                return; // stops method immediately if ID not found
            }

            System.out.println("Name: "+studentNames[index]);
            System.out.println("Department "+ departmentName[index]);

            for (int i = 0; i < 5; i++) { // printing all 5 grades
            System.out.println("Course " + (i + 1) + ": " + studentGrades[index][i]); // using the 2D array
            }
            
            double average = calculateAverage(index);
            double gpa = studentGPA[index];

            System.out.printf("Average %.2f%n",average);
            System.out.printf("GPA: %.1f%n",gpa);
            System.out.println("Status: " + getAcademicStatus(gpa));
            }

            //finding average
            static double calculateAverage(int index){
                double sum=0;
                for (int i=0;i<5;i++){
                    sum = sum + studentGrades[index][i];// index is row, i is column, we are incremenitng sum by that
                }
                return sum/5;
            }

            //GPA calculation
            static double calculateGPA(double average){
                if (average>=90){
                    return 4.0;
                }
                else if (average >= 80) {
                    return 3.5;
                }
                else if (average >= 70){
                    return 3.0;
                }
                else if (average >= 60){
                    return 2.5;
                }
                else{
                    return 1.0;
                }
            }

            // Academic status
            static String getAcademicStatus(double gpa){
                if (gpa>=2.5){
                    return "Student is Passing";
                }
                else{
                    return "Student is Failing";
                }
            }

    //// ----------Show Statistics-----------   //methods should be void, we are just printing not sending anything to a method
    
        static void showStatistics(){
            System.out.println("--------Statistics--------");
        

        if (studentCount == 0){
            System.out.println("No students registered yet.");
            return; //stop the method showstatistics entirely
        }

        System.out.printf("Highest GPA: %.1f%n", highestGPA()); //one dp, place holder for decimal number
        System.out.printf("Lowest GPA: %.1f%n", lowestGPA());
        System.out.printf("Average GPA: %.2f%n", averageGPA());

        departmentCount();
        passFailCount();
        }

        
        
        //highest gpa
        static double highestGPA(){
            double highest = studentGPA[0]; // starting value
            for (int i =1;i<studentCount;i++){
                if (studentGPA[i]>highest){ // go through each index, if the index is higher than the set one, go onto the next
                    highest = studentGPA[i];
                }
            }
            return highest;
        }

        //lowest gpa
        static double lowestGPA(){
            double lowest = studentGPA[0]; // starting value
            for (int i =1;i<studentCount;i++){
                if (studentGPA[i]<lowest){ // go through each index, if the index is lower than the set one, go onto the next
                    lowest = studentGPA[i];
                }
            }
            return lowest;
        }

        // average gpa of all students
        static double averageGPA(){
            double sum =0;
            for(int i =0;i<studentCount;i++){
                sum = sum + studentGPA[i];
            }
            return sum/studentCount;
        }

        // number of students in each department 
        static void departmentCount(){
            System.out.println("Students per Department: ");
            String[] departments = new String[50]; //local array, it stores every individual department name we find
            int[] counts = new int[50]; //local array, stores how many students are in each department
            int depCount=0; // tracks how many unique departments we have

            for(int i =0;i<studentCount;i++){ //loop for student
                String dept = departmentName[i]; // goes through each registered student, gets the students department name, stores it in dept
                boolean found = false; // initial value is false, will be true if we find the department in our local deoartments array

                for (int j=0;j<depCount;j++){ //loop for each department
                    if (departments[j].equals(dept)){ // this checks if current student's department matches one we already have in our list
                        counts[j]++; //if it matches add 1 to the department count
                        found = true; // no need to add again
                    }
                }

                if (found == false){ //if found is false, the department is new
                    departments[depCount]=dept; // so we add the new department to our list
                    counts[depCount]=1;//it has only one student
                    depCount++; // now we have one new departmet
                }
            }

            for (int i=0;i<depCount;i++){
                System.out.println(departments[i] + ": " + counts[i] + " student(s)"); // prints each department and its count
            }
        }

        static void passFailCount(){
            int passing =0;//init value
            int failing =0;//init value

            for(int i=0;i<studentCount;i++){
                if (studentGPA[i] >=2.5){
                    passing++;
                }
                else {
                    failing++;
                }
            }
        System.out.println("Passing: " + passing + " student(s)");
        System.out.println("Failing: " + failing + " student(s)");
        }


        
    //// ----------Search for Student----------- 
        static void searchStudent(){
            System.out.println("--------Search Student--------");
            System.out.println("1. Search by ID");
            System.out.println("2. Search by Name");
           int choice = readInt("Enter your choice: ");

            if (choice ==1){
                searchByID(); //calling method
            }
            else if(choice ==2){
                searchByName(); //calling method
            }
            else {
                System.out.println("Invalid Choice");
            }
        }

        static void searchByID(){
            int id = readInt("Enter student ID: ");

            int index = findStudentIndex(id); // reusing the method to find student ID

            if (index ==-1){
                System.out.println("Student not found");
            }
            else{
                System.out.println("Student Found:");
                System.out.println("Name: "+ studentNames[index]); // index is the position of the student in the array
                System.out.println("Department: "+ departmentName[index]);
                System.out.printf("GPA: %.1f%n" , studentGPA[index]); // 1 dp
                System.out.println("Status: "+ getAcademicStatus(studentGPA[index])); //method, with studentgpa as a parameter
                 
            }
        }

        static void searchByName(){
            String nameSearch = readString("Enter name to search: ").toLowerCase();// lowercase makes the name search less case sensitive
            boolean found =false;

            for(int i =0;i<studentCount;i++){
                if (studentNames[i].toLowerCase().contains(nameSearch)) { // contains anything from what the user typed for the search
                System.out.println("Student Found:");
                System.out.println("Name: "+ studentNames[i]); // instead of getting position from findstudentindex, we go through all students ourselves and check each one
                System.out.println("Department: "+ departmentName[i]);
                System.out.printf("GPA: %.1f%n" , studentGPA[i]); // 1 dp
                System.out.println("Status: "+ getAcademicStatus(studentGPA[i])); //method, with studentgpa as a parameter
                found = true;
                }
            }

            if (found == false){
                System.out.println("No students found matching " + nameSearch);
            }
        }


        //// -------- Input Utilities --------
        /// scanner is ONLY used here, all other methods call these instead of using scanner directly
        /// These are methods used to make our code look cleaner, and more convinient 
        /// the prompt parameter is basically sysout-ing what you want the user to do instead of writing 2 separate lines of code
            
        // reads an integer from the user
        static int readInt(String prompt) {
        System.out.println(prompt); 
        int value = scanner.nextInt();
        scanner.nextLine(); // clears leftover newline after nextInt
        return value;
        }
 
         // reads a double from the user
        static double readDouble(String prompt) {
        System.out.println(prompt);
        double value = scanner.nextDouble();
        scanner.nextLine(); // clears leftover newline after nextDouble
        return value;
        }
 
        // reads a string from the user
        static String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
        }
}
