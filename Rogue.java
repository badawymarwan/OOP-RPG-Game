public class Rogue extends GameCharacter {

// these variables are private because theyre exclusive to the Rogue character
private int critChance; // 0-100
private boolean stealthed;

// Constructor
// name and characterID is unique, so when create an object of Rogue, itll just ask you for the name and characterID
// critChance, stealthed, hp and attackPower are fixed values that we do not want to change, hence why they are not parameters
public Rogue(String name, String characterId) {
    super(name, characterId, 75, 20); // hp=75, attackPower=20 because it is a high risk high reward character
    this.critChance = 30;
    this.stealthed = false;
}

// getters
public int getCritChance() { return critChance; }
public boolean isStealthed() { return stealthed; }

// getAttackPower() uses Math.random() to check if the attack is a critical hit
// Math.random() returns a decimal between 0 and 1, we scale it to 0 to 100
// if the random value falls within critChance,which is below 30, return double attack power
@Override
public int getAttackPower() {
    double roll = Math.random() * 100;
    if (roll < critChance) {
        System.out.println("CRITICAL HIT!");
        return super.getAttackPower() * 2;
    }
    return super.getAttackPower();
}

// attack() deals damage to the target and prints a message
// if the Rogue is stealthed, deal 3x normal damage and exit stealth
// (GameCharacter) target is a cast target is typed as Combatant which has no getName()
// but we know every Combatant is actually a GameCharacter so we cast it to access getName()
@Override
public void attack(Combatant target) {
    if (stealthed == true) {
        // stealth attack deals 3x normal damage regardless of crit roll
        // then exit stealth
        System.out.println(String.format("%s %s is stealthed, CRITICAL HIT!", 
        getClassName().toUpperCase(), getName()));                                   
        target.defend(super.getAttackPower() * 3);
        stealthed = false;
    } else {
        System.out.println(String.format("%s %s attacks %s!",         
        getClassName().toUpperCase(), getName(),               
        ((GameCharacter) target).getName()));
        target.defend(getAttackPower());
    }
}

// useSpecialAbility() is Shadowstep
// sets stealthed to true, next attack() call will automatically deal 3x damage
@Override
public void useSpecialAbility(Combatant target) {
    stealthed = true;
    System.out.println(String.format("%s %s is stealthed! (Next attack is a mega critical!)", 
            getClassName().toUpperCase(), getName()));                                                         
}

// reset() restores the Rogue to its original state before a battle
// we set stealthed back to false first, then call super.reset() which restores HP to max
@Override
public void reset() {
    stealthed = false;
    super.reset();
}

// getClassName() returns the name of this class as a String
// used in attack() and useSpecialAbility() to print the class name
@Override
public String getClassName() {
    return "Rogue";
}

// getStatusReport() returns a formatted String showing the Rogue's current status
@Override
public String getStatusReport() {
   
    String stealthStatus = stealthed ? "STEALTHED" : "visible".toLowerCase(); // ternary op
    
    String classLabel = getName().toUpperCase() + " (ROGUE)";                
    return String.format("%-20s | HP: %3d/%-3d | Crit: %2d%% | Status: %s",  
            classLabel, getCurrentHp(), getMaxHp(), critChance, stealthStatus);
    }
}