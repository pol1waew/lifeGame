public class crossField extends field {
    public crossField(game _game, int xSize, int ySize, int length) {
        super(_game, xSize, ySize);

        for (int y = 0; y < ySize; y++) {
            if (y >= ySize / 2 - length / 2 && y < ySize / 2 + length / 2 + length % 2) continue;

            for (int x = 0; x < xSize; x++) {
                if (x < xSize / 2 - 1 || x >= xSize / 2 + 1) collapseCell(x, y);
            }
        }
    }
}
