public class Mage extends GameCharacter {

    private int spellPower;
    private int currentMana;
    private int maxMana;

    // Flag to track if special ability failed (for turn re-prompt logic)
    private boolean specialAbilityFailed = false;

    public Mage(String name, String characterId) {
        super(name, characterId, 80, 8);
        this.spellPower = 25;
        this.maxMana = 100;
        this.currentMana = 100;
    }

    public int getSpellPower() { return spellPower; }
    public int getCurrentMana() { return currentMana; }
    public int getMaxMana() { return maxMana; }
    public boolean hasSpecialAbilityFailed() { return specialAbilityFailed; }
    public void resetSpecialAbilityFailed() { specialAbilityFailed = false; }

    // Override getAttackPower() - Mages are weak in melee
    @Override
    public int getAttackPower() {
        return (int) (super.getAttackPower() * 0.6);
    }

    // attack() implementation using interface type parameter
    @Override
    public void attack(Combatant target) {
        // String method: toUpperCase()
        System.out.println(String.format("%s %s attacks %s with a weak melee hit!",
                getClassName().toUpperCase(), getName(), // String method: toUpperCase()
                ((GameCharacter) target).getName()));
        target.defend(getAttackPower());
    }

    // Special ability: Fireball - costs 30 mana
    @Override
    public void useSpecialAbility(Combatant target) {
        if (currentMana < 30) {
            System.out.println("Not enough mana to cast Fireball! (Need 30, have " + currentMana + ")");
            specialAbilityFailed = true;
            return;
        }
        specialAbilityFailed = false;
        currentMana -= 30;
        System.out.println(String.format("%s %s uses FIREBALL! (Mana: %d/%d)",
                getClassName().toUpperCase(), getName(), currentMana, maxMana));
        target.defend(spellPower * 2);
    }

    // Reset: restore mana to max, then call super.reset()
    @Override
    public void reset() {
        currentMana = maxMana;
        specialAbilityFailed = false;
        super.reset();
    }

    @Override
    public String getClassName() {
        return "Mage";
    }

    // getStatusReport() - String method: String.format(), toUpperCase(), contains()
    @Override
    public String getStatusReport() {
        // String method: contains() - check if name contains special characters
        String displayName = getName().contains(" ") ? getName() : getName(); // String method: contains()
        // String method: toUpperCase()
        String classLabel = (displayName.toUpperCase() + " (MAGE)"); // String method: toUpperCase()
        return String.format("%-20s | HP: %3d/%-3d | Mana: %3d/%-3d | SpellPower: %d",
                classLabel, getCurrentHp(), getMaxHp(), currentMana, maxMana, spellPower);
    }
}
