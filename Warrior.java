public class Warrior extends GameCharacter { // inherit all attributes and methods from gamecharacter

// these variables are private because theyre exclusive to the Warrior Character
private int armor; 
private int rage;

// Constructor
// name and characterID is unique, so when create an object of warrior, itll just ask you for the name and characterID
public Warrior (String name, String characterID){ 
// used super to set values earlier, rather than using this.name=name etc
// the Hp and attachpower are fixed values that we do not want to change, hence why they are not parameters 
    super(name, characterID,100, 15); 
    this.armor =10;
    this.rage=0;
}

// getters
public int getArmor(){
    return armor;
}

public int getRage(){
    return rage;
}

// we are asking java to run the getattack power we made in the parent class, not the one we overrode
@Override
public int getAttackPower(){
    return super.getAttackPower() + rage/10; // each rage point adds 0.1 to attack power
}

// asking java to run reduceddamage from parent class 
// we do that because we overrode the method to ensure that warrior takes damage by armor first, then we call super.defend so the parent handles the common part among all characters
@Override
public void defend(int damage){
    int reducedDamage = Math.max(0, damage-armor);  // ensures that reduced damage never becomes negative, if this happens itll heal the opponent
    super.defend(reducedDamage);
}

// combatant is the interface, target is the enemy 
// getClassName() returns "Warrior", toUpperCase() turns it into "WARRIOR"
// getName() returns this character's name 
// (GameCharacter) target is a cast target is typed as Combatant which has no getName()
// but we know every Combatant is actually a GameCharacter so we cast it to access getName()
@Override
    public void attack(Combatant target) { 
        System.out.println(String.format("%s %s attacks %s!", getClassName().toUpperCase(), getName(),
        ((GameCharacter) target).getName()));
        target.defend(getAttackPower()); // get the characters attack value, then defend with the same value, or basically saying take damage equal to my attack value
    }

// useSpecialAbility() is the Berserker Strike
 // deals double attack power, increases rage by 20, costs 25% of current HP
@Override
    public void useSpecialAbility(Combatant target) {
        System.out.println(String.format("%s %s uses BERSERKER STRIKE!",getClassName().toUpperCase(), getName()));
        target.defend(getAttackPower() * 2); // deal double attack power to the target
        rage += 20;// increase rage by 20
        // reduce HP to 75% of current value
        // cast to int because setCurrentHp() expects a whole number, not a decimal
        int newHp = (int) (getCurrentHp() * 0.75);
        setCurrentHp(newHp);
        System.out.println(String.format("%s loses health in berserker rage! HP: %d/%d | Rage: %d",
                getName(), getCurrentHp(), getMaxHp(), rage));
    }

    // reset() restores the Warrior to its original state before a battle
    // we set rage back to 0 first, then call super.reset() [made in gamecharacter, the parent class] which restores HP to max
    public void reset() {
        rage = 0; // order doesnt matter, but i want to give the priority to my fields in my class rather than the parent class
        super.reset(); 
    }

    // getClassName() returns the name of this class as a String
    // used in attack() and useSpecialAbility() to print the class name
    public String getClassName() {
        return "Warrior";
    }

    // getStatusReport() returns a formatted String showing the Warrior's current status

    @Override
    public String getStatusReport() {
        String trimmedName = getName().trim(); 
        return String.format("%-20s | HP: %3d/%-3d | Rage: %-3d | Armor: %d", trimmedName.toUpperCase() + " (WARRIOR)",
            getCurrentHp(), getMaxHp(), rage, armor);
    }
}
