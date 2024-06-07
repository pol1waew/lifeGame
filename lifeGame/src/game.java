import javax.swing.*;

public class game {
    private int creaturesToCreate = 0;
    private int creaturesToRemove = 0;
    private int evolutionIterationNumber = 0;

    private int currentPlayer = 0;
    private int winnerPlayer = 0;

    private boolean isGameEnded = false;

    private field _field;

    private JLabel currentTurnLabel;

    public game(int[] fieldSize) {
        //_field = new field(this, fieldSize[0], fieldSize[1]);
        _field = new crossField(this, fieldSize[0], fieldSize[1], 3);
    }

    public void initiateEvolution() {
        if (isGameEnded) return;

        if (_field.getCreatedThisTurn() != creaturesToCreate) {
            System.out.println("Ошибка: нужно разместить ещё " + (creaturesToCreate - _field.getCreatedThisTurn()) + " существ");
            return;
        }
        if (currentPlayer != 1) {
            System.out.println("Ошибка: нужно передать ход другому игроку");
            return;
        }

        winnerPlayer = _field.evolve(evolutionIterationNumber);
        if (winnerPlayer != 0) {
            if (currentTurnLabel != null) currentTurnLabel.setText("Победил игрок " + winnerPlayer);
            isGameEnded = true;
        }
        _field.clearCounters();
        currentPlayer = 0;
    }

    public void interactWithCell(int xPos, int yPos) {
        if (isGameEnded) return;

        switch (_field.interactWithCell(currentPlayer, xPos, yPos)) {
            case (0):
                System.out.println("Ошибка: взаимодействие с половиной поля другого игрока");
                break;
            case (-1):
                System.out.println("Существо успешно удалено");
                break;
            case (1):
                System.out.println("Существо успешно создано");
                break;
            case (2):
                System.out.println("Успешный откат удаления");
                break;
            case (-2):
                System.out.println("Успешный откат создания");
                break;
            case (-3):
                System.out.println("Ошибка: превышено количество удалений существ");
                break;
            case (3):
                System.out.println("Ошибка: превышено количество созданий существ");
                break;
            case (4):
                System.out.println("Ошибка: взаимодействие с существом чужой расы");
                break;
            case (5):
                System.out.println("Ошибка: выход за поле");
                break;
        }
    }

    public void switchPlayer() {
        if (isGameEnded) return;

        if (_field.getCreatedThisTurn() != creaturesToCreate) {
            System.out.println("Ошибка: нужно разместить ещё " + (creaturesToCreate - _field.getCreatedThisTurn()) + " существ");
            return;
        }

        currentPlayer += 1;
        if (currentPlayer > 1)  initiateEvolution();
        if (currentTurnLabel != null) currentTurnLabel.setText("Ход игрока " + (currentPlayer + 1));
        _field.clearCounters();
    }

    public void collapseCells(int[][] positions) {
        for (int i = 0; i < positions.length; i++) {
            _field.collapseCell(positions[i][0], positions[i][1]);
        }
    }

    public void setCreaturesToCreate(int creaturesToCreate) { this.creaturesToCreate = creaturesToCreate; }

    public void setCreaturesToRemove(int creaturesToRemove) { this.creaturesToRemove = creaturesToRemove; }

    public void setEvolutionIterationNumber(int evolutionIterationNumber) { this.evolutionIterationNumber = evolutionIterationNumber; }

    public void setCurrentTurnLabel(JLabel label) { currentTurnLabel = label; }

    public int getCreaturesToCreate() { return creaturesToCreate; }

    public int getCreaturesToRemove() { return creaturesToRemove; }

    public int getEvolutionIterationNumber() { return evolutionIterationNumber; }

    public int getCurrentPlayer() { return currentPlayer; }

    public field getField() { return _field; }
}
