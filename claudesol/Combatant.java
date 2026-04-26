public interface Combatant {
    void attack(Combatant target);
    void defend(int damage);
    boolean isAlive();
}
