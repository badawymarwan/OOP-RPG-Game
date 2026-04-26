public class Warrior extends GameCharacter {

    private int armor;
    private int rage;

    public Warrior(String name, String characterId) {
        super(name, characterId, 100, 15);
        this.armor = 10;
        this.rage = 0;
    }

    public int getArmor() { return armor; }
    public int getRage() { return rage; }

    // Override defend() to reduce incoming damage by armor value
    @Override
    public void defend(int damage) {
        int reducedDamage = Math.max(0, damage - armor);
        super.defend(reducedDamage);
    }

    // Override getAttackPower() - returns base + rage/10
    @Override
    public int getAttackPower() {
        return super.getAttackPower() + rage / 10;
    }

    // attack() implementation using interface type parameter
    @Override
    public void attack(Combatant target) {
        // String method: toUpperCase()
        System.out.println(String.format("%s %s attacks %s!",
                getClassName().toUpperCase(), getName(),
                ((GameCharacter) target).getName()));
        target.defend(getAttackPower());
    }

    // Special ability: Berserker Strike
    @Override
    public void useSpecialAbility(Combatant target) {
        System.out.println(String.format("%s %s uses BERSERKER STRIKE!",
                getClassName().toUpperCase(), getName()));
        target.defend(getAttackPower() * 2);
        rage += 20;
        int newHp = (int) (getCurrentHp() * 0.75);
        setCurrentHp(newHp);
        System.out.println(String.format("%s loses health in berserker rage! HP: %d/%d | Rage: %d",
                getName(), getCurrentHp(), getMaxHp(), rage));
    }

    // Reset: restore rage to 0, then call super.reset()
    @Override
    public void reset() {
        rage = 0;
        super.reset();
    }

    @Override
    public String getClassName() {
        return "Warrior";
    }

    // getStatusReport() - String method: String.format(), toUpperCase(), trim()
    @Override
    public String getStatusReport() {
        // String method: trim() used on name input
        String trimmedName = getName().trim(); // String method: trim()
        // String method: toUpperCase()
        return String.format("%-20s | HP: %3d/%-3d | Rage: %-3d | Armor: %d",
                trimmedName.toUpperCase() + " (WARRIOR)", // String method: toUpperCase()
                getCurrentHp(), getMaxHp(), rage, armor);
    }
}
