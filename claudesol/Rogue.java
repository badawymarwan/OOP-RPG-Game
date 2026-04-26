public class Rogue extends GameCharacter {

    private int critChance; // 0-100
    private boolean stealthed;

    public Rogue(String name, String characterId) {
        super(name, characterId, 75, 20);
        this.critChance = 30;
        this.stealthed = false;
    }

    public int getCritChance() { return critChance; }
    public boolean isStealthed() { return stealthed; }

    // Override getAttackPower() - uses Math.random() for critical hits
    @Override
    public int getAttackPower() {
        double roll = Math.random() * 100;
        if (roll < critChance) {
            System.out.println("CRITICAL HIT!");
            return super.getAttackPower() * 2;
        }
        return super.getAttackPower();
    }

    // attack() implementation - handles stealth mega critical
    @Override
    public void attack(Combatant target) {
        if (stealthed) {
            // Stealth attack: 3x normal damage, then exit stealth
            System.out.println(String.format("%s %s strikes from the shadows! MEGA CRITICAL!",
                    getClassName().toUpperCase(), getName())); // String method: toUpperCase()
            target.defend(super.getAttackPower() * 3);
            stealthed = false;
        } else {
            // String method: toUpperCase()
            System.out.println(String.format("%s %s attacks %s!",
                    getClassName().toUpperCase(), getName(), // String method: toUpperCase()
                    ((GameCharacter) target).getName()));
            target.defend(getAttackPower());
        }
    }

    // Special ability: Shadowstep - enter stealth
    @Override
    public void useSpecialAbility(Combatant target) {
        stealthed = true;
        System.out.println(String.format("%s %s disappears into the shadows! (Next attack is a mega critical!)",
                getClassName().toUpperCase(), getName()));
    }

    // Reset: exit stealth, then call super.reset()
    @Override
    public void reset() {
        stealthed = false;
        super.reset();
    }

    @Override
    public String getClassName() {
        return "Rogue";
    }

    // getStatusReport() - String method: String.format(), toUpperCase(), toLowerCase()
    @Override
    public String getStatusReport() {
        // String method: toLowerCase() for stealth status
        String stealthStatus = stealthed ? "STEALTHED" : "visible".toLowerCase(); // String method: toLowerCase()
        // String method: toUpperCase()
        String classLabel = getName().toUpperCase() + " (ROGUE)"; // String method: toUpperCase()
        return String.format("%-20s | HP: %3d/%-3d | Crit: %2d%% | Status: %s",
                classLabel, getCurrentHp(), getMaxHp(), critChance, stealthStatus);
    }
}
