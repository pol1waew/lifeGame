public class field {
    private int createdThisTurn = 0;
    private int removedThisTurn = 0;

    private cell[][] playground;
    private game _game;

    public field(game _game, int xSize, int ySize) {
        this._game = _game;

        generateField(xSize, ySize);
    }

    private void generateField(int xSize, int ySize) {
        playground = new cell[ySize][xSize];
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                playground[y][x] = new cell();
            }
        }

        int[][] div = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                for (int i = 0; i < div.length; i++) {
                    if (x + div[i][1] < 0 && y + div[i][0] >= 0 && y + div[i][0] <= ySize - 1) {
                        playground[y][0].setConnection(i, playground[y + div[i][0]][xSize - 1]);
                        continue;
                    }

                    if (y + div[i][0] < 0 && x + div[i][1] >= 0 && x + div[i][1] <= xSize - 1) {
                        playground[0][x].setConnection(i, playground[ySize - 1][x + div[i][1]]);
                        continue;
                    }

                    if (x + div[i][1] >= xSize && y + div[i][0] >= 0 && y + div[i][0] <= ySize - 1) {
                        playground[y][xSize - 1].setConnection(i, playground[y + div[i][0]][0]);
                        continue;
                    }

                    if (x + div[i][1] >= 0 && y + div[i][0] >= 0 && x + div[i][1] < xSize && y + div[i][0] < ySize) {
                        playground[y][x].setNeighbour(i, playground[y + div[i][0]][x + div[i][1]]);
                    }
                }
            }
        }

        playground[0][0].setConnection(0, playground[ySize - 1][xSize - 1]);
        playground[0][xSize - 1].setConnection(2, playground[ySize - 1][0]);
    }

    public int evolve(int iterationNumber) {
        for (int i = 0; i < iterationNumber; i++) {
            for (int y = 0; y < playground.length; y++) {
                for (int x = 0; x < playground[0].length; x++) {
                    if (playground[y][x] != null) playground[y][x].evolveCreature();
                }
            }

            for (int y = 0; y < playground.length; y++) {
                for (int x = 0; x < playground[0].length; x++) {
                    if (playground[y][x] != null) playground[y][x].applyBackupCreature();
                }
            }
        }

        // Проверка окончания игры
        return checkGameEnd();
    }

    private int checkGameEnd() {
        boolean containFirstPlayerCreatures = false;
        boolean containSecondPlayerCreatures = false;

        for (int y = 0; y < playground.length && (!containFirstPlayerCreatures || !containSecondPlayerCreatures); y++) {
            for (int x = 0; x < playground[0].length; x++) {
                if (playground[y][x] == null) continue;
                if (playground[y][x].getCreatureRace() == 0)        containFirstPlayerCreatures = true;
                else if (playground[y][x].getCreatureRace() == 1)   containSecondPlayerCreatures = true;
            }
        }

        if (containFirstPlayerCreatures && !containSecondPlayerCreatures) return 1;
        if (containSecondPlayerCreatures && !containFirstPlayerCreatures) return 2;
        return 0;
    }

    // Код возвращаемого значения
    // 0    - взаимодействие с половиной поля другого игрока
    // 1    - существо успешно создано
    // -1   - существо успешно удалено
    // 2    - успешный откат удаления
    // -2   - успешный откат создания
    // 3    - превышено количество созданий существ
    // -3   - превышено количество удалений существ
    // 4    - взаимодействие с существом чужой расы
    // 5    - выход за поле
    public int interactWithCell(int playerId, int xPos, int yPos) {
        if (playerId == 0 && xPos >= playground[0].length / 2)                           return 0;
        if (playerId == 1 && xPos < playground[0].length - playground[0].length / 2)    return 0;

        if (playground[yPos][xPos].isContainCreature() && playerId != playground[yPos][xPos].getCreatureRace()) return 4;
        if (xPos < 0 || yPos < 0 || xPos >= playground[0].length || yPos >= playground.length) return 5;

        int returnValue = playground[yPos][xPos].interactWithCreature(playerId, createdThisTurn >= _game.getCreaturesToCreate(), removedThisTurn >= _game.getCreaturesToRemove());
        switch (returnValue) {
            case (-2):
                createdThisTurn -= 1;
                if (createdThisTurn < 0) createdThisTurn = 0;
                break;
            case (-1):
                removedThisTurn += 1;
                break;
            case (1):
                createdThisTurn += 1;
                break;
            case (2):
                removedThisTurn -= 1;
                if (removedThisTurn < 0) removedThisTurn = 0;
                break;
        }

        return returnValue;
    }

    public void clearCounters() {
        createdThisTurn = 0;
        removedThisTurn = 0;
    }

    public void collapseCell(int xPos, int yPos) {
        playground[yPos][xPos].collapseSelf();
        playground[yPos][xPos] = null;
    }

    public int getCreatedThisTurn() { return createdThisTurn; }

    public int getRemovedThisTurn() { return removedThisTurn; }

    public int[] getSize() { return new int[]{playground.length, playground[0].length}; }

    public cell getCell(int x, int y) { return playground[y][x]; }
}
