public abstract class GameCharacter implements Combatant, Describable, Abilities {

    private String name;
    private final String characterId; // immutable - no setter
    private int maxHp;
    private int currentHp;
    private int attackPower;

    public GameCharacter(String name, String characterId, int maxHp, int attackPower) {
        this.name = name;
        this.characterId = characterId;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attackPower = attackPower;
    }

    // Getters
    public String getName() { return name; }
    public String getCharacterId() { return characterId; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
    public int getAttackPower() { return attackPower; }

    // Clamping setter for currentHp - never allows negative HP or HP above max
    public void setCurrentHp(int hp) {
        if (hp < 0) {
            this.currentHp = 0;
        } else if (hp > maxHp) {
            this.currentHp = maxHp;
        } else {
            this.currentHp = hp;
        }
    }

    // Concrete implementation of isAlive()
    @Override
    public boolean isAlive() {
        return currentHp > 0;
    }

    // Concrete implementation of defend()
    @Override
    public void defend(int damage) {
        setCurrentHp(currentHp - damage);
        // String method: String.format()
        System.out.println(String.format("%s takes %d damage! HP: %d/%d",
                name, damage, currentHp, maxHp));
    }

    // Concrete reset() method - restores HP to maximum
    public void reset() {
        setCurrentHp(maxHp);
    }

    // Abstract method - subclasses must implement
    public abstract String getClassName();
}
