public class Main {
    public static void main(String[] args) {
        game _game = new game(new int[]{20, 10});
        _game.setCreaturesToCreate(5);
        _game.setCreaturesToRemove(3);
        _game.setEvolutionIterationNumber(8);

        /*_game.collapseCells(new int[][]{
                {5, 5}
        });
*/
        applicationFrame frame = new applicationFrame(_game);
    }
}