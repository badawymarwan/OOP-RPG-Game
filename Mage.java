public class Mage extends GameCharacter {

    // these variables are private because theyre exclusive to the Mage character
    private int spellPower;
    private int currentMana;
    private int maxMana;
    // used to track if special ability failed due to insufficient mana
    // used in main to reprompt the player without wasting their turn to inform that theres not enough mana
    private boolean specialAbilityFailed;

    // Constructor
    // name and characterID is unique, so when we create an object of Mage, itll just ask you for the name and characterID
    // hp, attackPower, spellPower, maxMana are fixed values that we do not want to change, hence why they are not parameters
    public Mage(String name, String characterId) {
        super(name, characterId, 80, 8); // hp=80, attackPower=8 (Mages are weak in melee)
        this.spellPower = 25;
        this.maxMana = 100;
        this.currentMana = 100;
        this.specialAbilityFailed = false;
    }

    // getters
    public int getSpellPower(){
        return spellPower;
    }
    public int getCurrentMana(){
        return currentMana; 
    }
    public int getMaxMana(){
        return maxMana;
    }
    public boolean hasSpecialAbilityFailed(){
        return specialAbilityFailed;
    }
    public void resetSpecialAbilityFailed(){ 
        specialAbilityFailed = false;
    }

    // Mages are weak in melee so we return only 60% of base attack power
    // cast to int because setCurrentHp() expects a whole number, not a decimal
    @Override
    public int getAttackPower() {
        return (int) (super.getAttackPower() * 0.6);
    }

    // attack() deals damage to the target and prints a message
    // (GameCharacter) target is a cast, target is typed as Combatant which has no getName()
    // but we know every combatant is actually a GameCharacter so we cast it to access getName()
    @Override
    public void attack(Combatant target) {
        System.out.println(String.format("%s %s attacks %s with a melee hit!", 
        getClassName().toUpperCase(), getName(),                            
        ((GameCharacter) target).getName()));
        target.defend(getAttackPower());
    }

    // useSpecialAbility() is the Fireball
    // costs 30 mana, if the mage has insufficient mana, set the flag and return without attacking
    // the flag tells main to reprompt the player without wasting their turn
    @Override
    public void useSpecialAbility(Combatant target) {
        if (currentMana < 30) {
            System.out.println("Not enough mana to cast Fireball (Need 30, have " + currentMana + ")");
            specialAbilityFailed = true;
            return;
        }
        specialAbilityFailed = false;
        currentMana -= 30;
        System.out.println(String.format("%s %s uses FIREBALL! (Mana: %d/%d)", // : String.format()
                getClassName().toUpperCase(), getName(), currentMana, maxMana)); // : toUpperCase()
        // deal spellPower * 2 damage to the target
        target.defend(spellPower * 2);
    }

    // reset() restores the Mage to its original state before a battle
    // we set currentMana back to maxMana first, then call super.reset() which restores HP to max
    @Override
    public void reset() {
        currentMana = maxMana;
        specialAbilityFailed = false;
        super.reset();
    }

    // getClassName() returns the name of this class as a String
    // used in attack() and useSpecialAbility() to print the class name
    @Override
    public String getClassName() {
        return "Mage";
    }

    // getStatusReport() returns a formatted String showing the current status of the mage
    // : String.format(), toUpperCase(), contains()
    @Override
    public String getStatusReport() {
        String displayName = getName().contains(" ") ? getName() : getName(); 
        // : toUpperCase()
        String classLabel = displayName.toUpperCase() + " (MAGE)";          
        return String.format("%-20s | HP: %3d/%-3d | Mana: %3d/%-3d | SpellPower: %d", 
        classLabel, getCurrentHp(), getMaxHp(), currentMana, maxMana, spellPower);
    }
}