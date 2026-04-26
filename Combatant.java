public interface Combatant {

/*  -- these are abstract methods, as in interfaces all methods are implicitly public and abstract.
    -- you cannot have concrete methods in interfaces, but you can override the methods to make them concrete (abstract need implementation, concrete do not)
    -- ABSTRACT METHODS DO NOT REQUIRE CURLY BRACES 
    -- we are using 3 different interfaces as each interface will represent a responsibility:
combatant is for fighting, describable is for for description, abilities is for special moves
*/

// this is public abstract void attack
// combatant is being used as a parameter here because all characters can be attacked, not only gameCharacters, 
// this allows polymorphism, so the warrior, mage and rogue can behave as their own entity but also combatants
// another example if you want to create a new class of "dragon", its a combatant but not a gamecharacter, if it took agamecharacter parameter you couldnt attack the dragon, anything that takaes a combatant interface can be attacked
 void attack(Combatant target); // any cubclass that has an attack method can attack all classes that implement combatant

 // this is public abstract void defend
 void defend(int damage); 

 // this is public abstract Boolean isAlive
 Boolean isAlive(); 



}

