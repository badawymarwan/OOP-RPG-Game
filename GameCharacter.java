// GameCharacter is abstract because we are restricting the players to either warrior, mage or rogue characters, they cannot create their own
// so they cannot create an object of gameCharacter
public abstract class GameCharacter implements Combatant, Describable, Abilities{

// encapsulation used, to prevent any changes happening outside the class, they can only be accessed through getters and setters
// private access modifier is used because we want the data in the fields to be exclusive to this class only, and cannot be changed
private String name;
private final String characterID; // final because we do not want the character ID to be changed once created, no setter allowed
private int maxHp;
private int currentHp;
private int attackPower;

// Constructor
GameCharacter(String name, String characterID, int maxHp, int attackPower){ // currenthp not taken as starting hp must be maxhp
    this.name = name;
    this.characterID = characterID;
    this.maxHp = maxHp;
    this.currentHp = maxHp; // character starts the game iwth full Hp
    this.attackPower = attackPower;
}

// getters and setters are used because fields are private

// Getters 
public String getName(){
    return name;
}

public String getCharacterID(){
    return characterID;
}

public int getMaxHp(){
    return maxHp;
}

public int getAttackPower(){
    return attackPower;
}

public int getCurrentHp(){
    return currentHp;
}

// setter
// this is a clamping setter, restricts values within a certain range, public as itll be accessed by multiple classes
// this makes sure Hp never goes below 0, or exceeds the maximum for each character
public void setCurrentHp(int hp){ 
    if (hp < 0) {
        this.currentHp = 0; // hp cannot be less than zero
    } else if (hp > maxHp) {
        this.currentHp = maxHp; // hp cannot be more than max
    } else {
        this.currentHp = hp;
    }
}

// we have overriden ALL abstract methods to become concrete methods

// isAlive is implemented in gamecharacter rather than the subclasses because every character have Hp and take damage the same way
@Override
public Boolean isAlive(){ // public as interface methods are implicitly public 
    if (currentHp>0){
        return true;
    }
    else{ 
        return false;
    }
}

// defend implemented in gamecharacter as this method applies for all characters
@Override
public void defend(int damage){ // public because interface methods are always public
    setCurrentHp(currentHp-damage); // calling a method from within another method, also known as separation of concerns
    System.out.println(String.format("%s takes %d damage! HP: %d/%d", name,damage,currentHp, maxHp));
}

// restores Hp to max before a battle
public void reset(){ // public to maintain consistency, it will be accessed by multiple classes
    setCurrentHp(maxHp); // calling a method from within another method, also known as separation of concerns 
}

//every subclass has a different class name, 
public abstract String getClassName();

}
