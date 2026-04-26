import java.util.Scanner;

// Main class drives the entire system, it is the entry point of the program
public class Main {

// array stores all created characters, typed as GameCharacter to allow polymorphism
// this means we can store Warriors, Mages and Rogues in the same array without type checking
static GameCharacter[] characters = new GameCharacter[50];
static int characterCount = 0; // tracks how many characters have been created so far
static Scanner scanner = new Scanner(System.in); // reads input from the user, declared here so every method can use it

public static void main(String[] args) {

    System.out.println("===== RPG CHARACTER BATTLE SYSTEM =====");

    // keep the menu running until the user chooses to exit
    boolean running = true; // boolean true instead of while(true) because there would be no way of stopping the loop, but declaring this variable can allow us to set it to false when user presses exit
    while (running) {
         // we want the menu to keep showing after every action, without it the program would run once and exit
        printMainMenu();// calling the method to just print the menu that users see
        int choice = readIntSafe(); // readIntSafe() prevents the program from crashing on bad input, it allows us to reprompt the user

    // used enhanced switches for convinience 
       switch (choice) {
    case 1 -> createCharacter();
    case 2 -> viewAllCharacters();
    case 3 -> viewCharacterDetails();
    case 4 -> simulateBattle();
    case 5 -> {
        System.out.println("Goodbye!");
        running = false;
    }
    default -> System.out.println("Invalid option. Please choose 1-5.");
    }

    }
    scanner.close(); // just a good practice 
}

// prints the main menu options to the console
static void printMainMenu() {
    System.out.println("\n========================================");
    System.out.println("1) Create Character");
    System.out.println("2) View All Characters");
    System.out.println("3) View Character Details");
    System.out.println("4) Simulate Battle");
    System.out.println("5) Exit");
    System.out.print("Choose an option: ");
}

// createCharacter() prompts the user for a name and class, validates input, generates an ID and stores the character in the array
static void createCharacter() {

    // check if the array is full before creating a new character
    if (characterCount >= characters.length) {
        System.out.println("Character roster is full.");
        return; // stops the method, ensures nothing is executed below this line 
    }

    // validating name, must not be blank and not longer than 20 characters
    // we use a while loop to keep re-prompting the user until they enter a valid name
    String name = "";
    while (true) {
        System.out.print("Enter character name: ");
        name = scanner.nextLine();
        name = name.trim(); 

        // isEmpty() checks if the name is blank after trimming
        if (name.isEmpty()) { 
            System.out.println("Name cannot be blank. Try again.");
            continue; // continue skips the rest of the loop and reprompts the user
        }

        // length() checks if the name exceeds 20 characters
        if (name.length() > 20) { 
            System.out.println("Name cannot be longer than 20 characters. Try again.");
            continue;
        }
        break; // break exits the while loop once the name is valid
    }

    // validate class choice, must be Warrior, Mage or Rogue
    // we use toLowerCase() so the user can type in any case: "warrior", "WARRIOR", "Warrior"
    String classChoice = "";
    while (true) {
        System.out.print("Choose class (Warrior / Mage / Rogue): ");
        classChoice = scanner.nextLine().trim(); // unnecessary spaces 
        String lower = classChoice.toLowerCase(); 

        if (lower.equals("warrior") || lower.equals("mage") || lower.equals("rogue")) {
            break;
        }
        System.out.println("Invalid class. Please enter Warrior, Mage, or Rogue.");
    }

    // generate character ID as "CHAR-" + first 3 letters of name in uppercase + "-" + last 3 digits of current time
    // if name is "Ali" and time ends in 532, the ID is "CHAR-ALI-532"
    // ternary operator used to handle names shorter than 3 characters
    String prefix = name.length() >= 3 ? name.substring(0, 3) : name; 
    String charId = "CHAR-" + prefix.toUpperCase() + "-" + (System.currentTimeMillis() % 1000); 

    // create the correct subclass based on the users class choice
    // polymorphism: all three subclasses are stored as GameCharacter in the array
    // Java automatically calls the correct methods based on the actual object type
    GameCharacter newChar;
    switch (classChoice.toLowerCase()) {
    case "warrior" -> newChar = new Warrior(name, charId);
    case "mage"    -> newChar = new Mage(name, charId);
    case "rogue"   -> newChar = new Rogue(name, charId);
    default -> {
        System.out.println("Unexpected error creating character.");
        return;
        }
    }

    // store the new character in the array and increment the count
    characters[characterCount++] = newChar;
    System.out.println("Character created successfully!");
    System.out.println("ID: " + charId);
}

// viewAllCharacters() prints the status of every character in the array
// getStatusReport() is called polymorphically, no instanceof or casting needed
static void viewAllCharacters() {
    if (characterCount == 0) {
        System.out.println("No characters created yet.");
        return;
    }

    System.out.println("\n===== ALL CHARACTERS =====");

    // polymorphism: getStatusReport() is called on each character without knowing their specific type
    // Java automatically calls the correct getStatusReport() based on the actual object type
    // charactercount is looped instead of characters.length is always 50, we want to print the characters that exist
    for (int i = 0; i < characterCount; i++) {
        System.out.println("[" + i + "] " + characters[i].getStatusReport());
    }
}

// viewCharacterDetails() prints the full details of a single character by index
static void viewCharacterDetails() {
    if (characterCount == 0) {
        System.out.println("No characters created yet.");
        return;
    }

    // when you initially run the code and only have 1 character it will show 0 to 0
    // so the user will have to choose 0, as more characters are added the range will increase
    System.out.print("Enter character index (0-" + (characterCount - 1) + "): ");
    int index = readIntSafe();

    // validate the index to make sure it is within the range of created characters
    if (index < 0 || index >= characterCount) {
        System.out.println("Invalid index.");
        return;
    }

    GameCharacter c = characters[index]; // using c allows us to polymorphically call getStatusReport()
    System.out.println("\n===== CHARACTER DETAILS =====");
    System.out.println(c.getStatusReport()); 
    System.out.println("ID: " + c.getCharacterID());
}

// simulateBattle() prompts the user to select two characters and runs a turn based battle loop
static void simulateBattle() {

    // need at least 2 characters to battle
    if (characterCount < 2) {
        System.out.println("Need at least 2 characters to battle.");
        return;
    }

    System.out.print("Select first character index (0-" + (characterCount - 1) + "): ");
    int idx1 = readIntSafe();
    System.out.print("Select second character index (0-" + (characterCount - 1) + "): ");
    int idx2 = readIntSafe();

    // validate both indices to make sure they are within range, idx is short ofr index1 and index2 of the array 
    if (idx1 < 0 || idx1 >= characterCount || idx2 < 0 || idx2 >= characterCount) {
        System.out.println("Invalid character indices.");
        return;
    }

    // prevent the user from selecting the same character twice
    if (idx1 == idx2) {
        System.out.println("Cannot battle yourself! Pick two different characters.");
        return;
    }

    GameCharacter player = characters[idx1];
    GameCharacter enemy  = characters[idx2];

    // reset both characters to full HP and original state before the battle starts
    // this makes sure neither character carries stats from a previous battle
    player.reset();
    enemy.reset();

    System.out.println("\nBattle Starting: " + player.getName() + " vs " + enemy.getName());

    int round = 1;

    // loop continues until one character dies, isAlive() returns false when currentHp reaches 0
    while (player.isAlive() && enemy.isAlive()) {
        System.out.println("\n--- Round " + round + " ---");

        // player chooses their action each round
        playerTurn(player, enemy);

        // check if enemy died from players attack before enemy takes their turn
        if (!enemy.isAlive()) break;

        // enemy randomly attacks or uses special ability
        System.out.println("\nEnemy's turn...");
        enemyTurn(enemy, player);

        // print both characters status after each full round using polymorphic calls
        System.out.println("\nStatus:");
        System.out.println(player.getStatusReport()); // polymorphic call
        System.out.println(enemy.getStatusReport());  // polymorphic call

        round++;
    }

    // announce the winner, whichever character is still alive wins
    GameCharacter winner = player.isAlive() ? player : enemy;
    System.out.println("\n===== WINNER =====");
    System.out.println(winner.getName() + " (" + winner.getClassName().toUpperCase() + ") is victorious!");
    System.out.println("==================");
}

// playerTurn() prompts the player to attack or use a special ability
// if the special ability fails, for example if mage has no mana, the turn is not wasted and the player is re-prompted
static void playerTurn(GameCharacter player, GameCharacter enemy) {
    boolean turnComplete = false;

    // loop continues until the player completes their turn
    while (!turnComplete) {
        System.out.println("\n" + player.getName() + "'s turn:");
        System.out.println("1) Attack");
        System.out.println("2) Special Ability");
        System.out.print("Choose action: ");
        int action = readIntSafe();

        if (action == 1) {
            player.attack(enemy); // polymorphic call, correct attack() called automatically
            turnComplete = true;
        } else if (action == 2) {
            player.useSpecialAbility(enemy); // polymorphic call, correct useSpecialAbility() called automatically

            // instanceof is used here specifically because we need to check the Mages mana flag
            // this is the only place instanceof appears, it is not used in the battle loop itself
            // instanceof is generally used to check if an object is an instance of a specific class
            if (player instanceof Mage) {
                Mage mage = (Mage) player;
                if (mage.hasSpecialAbilityFailed()) {
                    mage.resetSpecialAbilityFailed();
                    System.out.println("Choose again:");
                    // loop continues, turn is not wasted
                } else {
                    turnComplete = true;
                }
            } else {
                turnComplete = true;
            }
        } else {
            System.out.println("Invalid action. Please choose 1 or 2.");
        }
    }
}

// enemyTurn() randomly chooses between attacking and using a special ability
// Math.random() returns a decimal between 0 and 1, if its less than 0.5 the enemy attacks, the enemy is ai, we 
// if the enemy is a Mage and has no mana, it falls back to a normal attack
static void enemyTurn(GameCharacter enemy, GameCharacter player) {
    if (Math.random() < 0.5) { 
        enemy.attack(player); // polymorphic call
    } else {
        enemy.useSpecialAbility(player); // polymorphic call

        // if enemy Mage has no mana, fall back to normal attack
        if (enemy instanceof Mage) {
            Mage mage = (Mage) enemy;
            if (mage.hasSpecialAbilityFailed()) {
                mage.resetSpecialAbilityFailed();
                enemy.attack(player);
            }
        }
    }
}

// readIntSafe() reads an integer from the user without crashing on bad input
// if the user enters something that is not a number, it reprompts them
static int readIntSafe() { // method that returns an int value
    while (true) { // loop to keep it running until input is found
        String line = scanner.nextLine().trim(); // Store whatever the user typed in variable line, trim it to avoid any unnecessary spaces

        // check if the input is a valid number by checking each character
        // .isempty= true when empty, !..isempty = true when not empty 
        // make sure the user did not press enter with nothing typed (empty) and ensure every character is a digit from 0-9, so digits pass, but letters dont
        if (!line.isEmpty() && line.matches("[0-9]+")) { 
            return Integer.parseInt(line); //if both conditions match, the input is converted from text to int, if true, while loop is exited
        } else {
            System.out.print("Please enter a valid number: "); //if failed, print this message and loop back
        }
    }
}

}