public class Main {
    public static void main(String[] args) {
        game _game = new game(new int[]{29, 19});
        _game.setCreaturesToCreate(3);
        _game.setCreaturesToRemove(3);
        _game.setEvolutionIterationNumber(1);

        /*_game.collapseCells(new int[][]{
                {5, 5}
        });
*/
        applicationFrame frame = new applicationFrame(_game);
    }
}