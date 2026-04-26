import java.util.Scanner;

public class Main {

    static GameCharacter[] characters = new GameCharacter[50];
    static int characterCount = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== RPG CHARACTER BATTLE SYSTEM =====");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readIntSafe();

            switch (choice) {
                case 1:
                    createCharacter();
                    break;
                case 2:
                    viewAllCharacters();
                    break;
                case 3:
                    viewCharacterDetails();
                    break;
                case 4:
                    simulateBattle();
                    break;
                case 5:
                    System.out.println("Goodbye, adventurer!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-5.");
            }
        }
        scanner.close();
    }

    static void printMainMenu() {
        System.out.println("\n========================================");
        System.out.println("1) Create Character");
        System.out.println("2) View All Characters");
        System.out.println("3) View Character Details");
        System.out.println("4) Simulate Battle");
        System.out.println("5) Exit");
        System.out.print("Choose an option: ");
    }

    static void createCharacter() {
        if (characterCount >= characters.length) {
            System.out.println("Character roster is full!");
            return;
        }

        // Validate name - must not be blank and not longer than 20 characters
        String name = "";
        while (true) {
            System.out.print("Enter character name: ");
            name = scanner.nextLine();
            // String method: trim()
            name = name.trim(); // String method: trim()
            // String method: isEmpty()
            if (name.isEmpty()) { // String method: isEmpty()
                System.out.println("Name cannot be blank. Try again.");
                continue;
            }
            // String method: length()
            if (name.length() > 20) { // String method: length()
                System.out.println("Name cannot be longer than 20 characters. Try again.");
                continue;
            }
            break;
        }

        // Validate class choice
        String classChoice = "";
        while (true) {
            System.out.print("Choose class (Warrior / Mage / Rogue): ");
            classChoice = scanner.nextLine().trim(); // String method: trim()
            // String method: toLowerCase() for case-insensitive comparison
            String lower = classChoice.toLowerCase(); // String method: toLowerCase()
            if (lower.equals("warrior") || lower.equals("mage") || lower.equals("rogue")) {
                break;
            }
            System.out.println("Invalid class. Please enter Warrior, Mage, or Rogue.");
        }

        // Generate character ID:  "CHAR-" + name.substring(0,3).toUpperCase() + "-" + System.currentTimeMillis() % 1000
        // String method: substring()
        String prefix = name.length() >= 3 ? name.substring(0, 3) : name; // String method: substring()
        // String method: toUpperCase()
        String charId = "CHAR-" + prefix.toUpperCase() + "-" + (System.currentTimeMillis() % 1000); // String method: toUpperCase()

        GameCharacter newChar;
        // String method: toLowerCase() for switch comparison
        switch (classChoice.toLowerCase()) { // String method: toLowerCase()
            case "warrior":
                newChar = new Warrior(name, charId);
                break;
            case "mage":
                newChar = new Mage(name, charId);
                break;
            case "rogue":
                newChar = new Rogue(name, charId);
                break;
            default:
                System.out.println("Unexpected error creating character.");
                return;
        }

        characters[characterCount++] = newChar;
        System.out.println("Character created successfully!");
        System.out.println("ID: " + charId);
    }

    static void viewAllCharacters() {
        if (characterCount == 0) {
            System.out.println("No characters created yet.");
            return;
        }
        System.out.println("\n===== ALL CHARACTERS =====");
        // Polymorphic call - no instanceof, no casting
        for (int i = 0; i < characterCount; i++) {
            System.out.println("[" + i + "] " + characters[i].getStatusReport());
        }
    }

    static void viewCharacterDetails() {
        if (characterCount == 0) {
            System.out.println("No characters created yet.");
            return;
        }
        System.out.print("Enter character index (0-" + (characterCount - 1) + "): ");
        int index = readIntSafe();
        if (index < 0 || index >= characterCount) {
            System.out.println("Invalid index.");
            return;
        }
        GameCharacter c = characters[index];
        System.out.println("\n===== CHARACTER DETAILS =====");
        // Polymorphic call to getStatusReport()
        System.out.println(c.getStatusReport());
        System.out.println("ID: " + c.getCharacterId());
    }

    static void simulateBattle() {
        if (characterCount < 2) {
            System.out.println("Need at least 2 characters to battle.");
            return;
        }

        System.out.print("Select first character index (0-" + (characterCount - 1) + "): ");
        int idx1 = readIntSafe();
        System.out.print("Select second character index (0-" + (characterCount - 1) + "): ");
        int idx2 = readIntSafe();

        if (idx1 < 0 || idx1 >= characterCount || idx2 < 0 || idx2 >= characterCount) {
            System.out.println("Invalid character indices.");
            return;
        }
        if (idx1 == idx2) {
            System.out.println("Cannot battle yourself! Pick two different characters.");
            return;
        }

        GameCharacter player = characters[idx1];
        GameCharacter enemy  = characters[idx2];

        // Reset both characters before battle
        player.reset();
        enemy.reset();

        System.out.println("\nBattle Starting: " + player.getName() + " vs " + enemy.getName());

        int round = 1;
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- Round " + round + " ---");

            // --- Player's turn ---
            playerTurn(player, enemy);

            if (!enemy.isAlive()) break;

            // --- Enemy's turn (random attack or special ability) ---
            System.out.println("\nEnemy's turn...");
            enemyTurn(enemy, player);

            // --- Print status after full round ---
            System.out.println("\nStatus:");
            System.out.println(player.getStatusReport()); // polymorphic
            System.out.println(enemy.getStatusReport());  // polymorphic

            round++;
        }

        // Announce winner
        GameCharacter winner = player.isAlive() ? player : enemy;
        System.out.println("\n===== WINNER =====");
        System.out.println(winner.getName() + " (" + winner.getClassName().toUpperCase() + ") is victorious!");
        System.out.println("==================");
    }

    static void playerTurn(GameCharacter player, GameCharacter enemy) {
        boolean turnComplete = false;
        while (!turnComplete) {
            System.out.println("\n" + player.getName() + "'s turn:");
            System.out.println("1) Attack");
            System.out.println("2) Special Ability");
            System.out.print("Choose action: ");
            int action = readIntSafe();

            if (action == 1) {
                player.attack(enemy);
                turnComplete = true;
            } else if (action == 2) {
                player.useSpecialAbility(enemy);

                // Check if Mage failed due to insufficient mana - re-prompt without wasting turn
                if (player instanceof Mage) {
                    Mage mage = (Mage) player;
                    if (mage.hasSpecialAbilityFailed()) {
                        mage.resetSpecialAbilityFailed();
                        System.out.println("Choose again:");
                        // Loop continues - turn not wasted
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

    static void enemyTurn(GameCharacter enemy, GameCharacter player) {
        // Enemy randomly chooses attack or special ability
        if (Math.random() < 0.5) {
            enemy.attack(player);
        } else {
            enemy.useSpecialAbility(player);
            // If enemy Mage can't cast, fall back to normal attack
            if (enemy instanceof Mage) {
                Mage mage = (Mage) enemy;
                if (mage.hasSpecialAbilityFailed()) {
                    mage.resetSpecialAbilityFailed();
                    enemy.attack(player);
                }
            }
        }
    }

    // Safe integer reader - handles non-integer input gracefully
    static int readIntSafe() {
        while (true) {
            String line = scanner.nextLine().trim(); // String method: trim()
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
