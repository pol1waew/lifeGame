public class creature {
    private static int[] numbersToEvole = new int[]{3};
    private static int[] numbersToDie = new int[]{0, 1, 4, 5, 6, 7, 8};
    private int race;

    public creature(int race) {
       setRace(race);
    }

    public creature(creature _creature) {
        this(_creature.race);
    }

    public void setRace(int race) {
        this.race = race;
    }

    // -1   - удалить существо
    // 0    - оставить существо
    // 1    - создать существо
    static int evolve(int creaturesAround, boolean isEmptyCell) {
        if (isEmptyCell) {
            for (int i : numbersToEvole) {
                if (creaturesAround == i) return 1;
            }
            return 0;
        }

        for (int i : numbersToDie) {
            if (creaturesAround == i) return -1;
        }
        return 0;
    }

    public boolean compareRace(int race) { return this.race == race; }

    public int getRace() { return race; }
}
