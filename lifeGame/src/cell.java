import javax.swing.*;

public class cell {
    // Соседи считаются с верхнего левого угла по часовой стрелке
    private cell[] neighbours = new cell[8];

    private creature storedCreature = null;
    private creature currentCreature = null;
    private creature evolveCreature = null;

    private JButton button;

    public void evolveCreature() {
        evolveCreature = null;
        if (currentCreature != null) evolveCreature = new creature(currentCreature);

        switch(creature.evolve(getAroundCreatureCount(), evolveCreature == null)) {
            case (-1):
                evolveCreature = null;
                break;
            case (1):
                if (evolveCreature == null) evolveCreature = new creature(getFrequentRaceAround());
                break;
        }
    }

    public int interactWithCreature(int playerId, boolean preventFromCreating, boolean preventFromRemoving) {
        // Процесс удаления существа из клетки
        if (currentCreature != null) {
            if (preventFromRemoving && storedCreature != null) return -3;
            currentCreature = null;
            if (button != null) button.setText(" ");
            if (storedCreature == null)     return -2;
            else                            return -1;
        }

        // Процесс создания существа в клетке
        if (preventFromCreating && storedCreature == null) return 3;
        currentCreature = new creature(playerId);
        if (button != null) button.setText("" + (currentCreature.getRace() + 1));
        if (storedCreature != null)     return 2;
        else                            return 1;
    }

    public void applyBackupCreature() {
        storedCreature = null;
        currentCreature = null;
        if (button != null) button.setText(" ");

        if (evolveCreature != null) {
            storedCreature = new creature(evolveCreature);
            currentCreature = new creature(evolveCreature);
            if (button != null) button.setText("" + (currentCreature.getRace() + 1));
            evolveCreature = null;
        }
    }

    public void collapseSelf() {
        neighbours[0].setNeighbour(4, neighbours[4]);
        neighbours[1].setNeighbour(5, neighbours[5]);
        neighbours[2].setNeighbour(6, neighbours[6]);
        neighbours[3].setNeighbour(7, neighbours[7]);
        neighbours[4].setNeighbour(0, neighbours[0]);
        neighbours[5].setNeighbour(1, neighbours[1]);
        neighbours[6].setNeighbour(2, neighbours[2]);
        neighbours[7].setNeighbour(3, neighbours[3]);
    }

    public int getAroundCreatureCount() {
        int creatureCount = 0;

        for (int i = 0; i < neighbours.length; i++)
            if (neighbours[i] != null && neighbours[i].currentCreature != null) creatureCount += 1;

        return creatureCount;
    }

    private int getFrequentRaceAround() {
        int firstPlayerRaceCount = 0;
        int secondPlayerRaceCount = 0;

        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] != null && neighbours[i].currentCreature != null) {
                if (neighbours[i].currentCreature.compareRace(0))   firstPlayerRaceCount += 1;
                else                                                secondPlayerRaceCount += 1;
            }
        }

        if (firstPlayerRaceCount == secondPlayerRaceCount) return (int)(Math.random() * 2);
        return (firstPlayerRaceCount > secondPlayerRaceCount) ? 0 : 1;
    }

    public boolean isContainCreature() { return currentCreature != null; }

    // Возвращает расу существа, если существо присутствует в клетке
    public int getCreatureRace() { return isContainCreature() ? currentCreature.getRace() : -1; }

    public void setNeighbour(int i, cell neighbour) { neighbours[i] = neighbour; }

    public void setConnection(int i, cell neighbour) {
        setNeighbour(i, neighbour);

        int index = i + 4;
        if (index > 7) index = index - 7;
        neighbour.setNeighbour(index, this);
    }

    public void setButton(JButton button) { this.button = button; }
}
