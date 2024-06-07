import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class gameTest {
    @Test
    public void createThenRemove() {
        game _game = new game(new int[]{5, 5});
        _game.setCreaturesToCreate(1);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(1);

        _game.interactWithCell(1, 1);
        _game.interactWithCell(1, 1);

        Assertions.assertFalse(_game.getField().getCell(1, 1).isContainCreature());
    }

    @Test
    public void boxShapeFromEvolution() {
        game _game = new game(new int[]{10, 10});
        _game.setCreaturesToCreate(3);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(5);

        _game.interactWithCell(1, 1);
        _game.interactWithCell(2, 1);
        _game.interactWithCell(1, 2);

        _game.switchPlayer();

        _game.interactWithCell(5, 0);
        _game.interactWithCell(7, 1);
        _game.interactWithCell(5, 4);

        _game.initiateEvolution();

        Assertions.assertTrue(_game.getField().getCell(1, 1).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(1, 2).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(2, 2).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(2, 1).isContainCreature());
    }

    @Test
    public void rotationShape() {
        game _game = new game(new int[]{10, 5});
        _game.setCreaturesToCreate(3);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(3);

        _game.interactWithCell(1, 2);
        _game.interactWithCell(2, 2);
        _game.interactWithCell(3, 2);

        _game.switchPlayer();

        _game.interactWithCell(5, 0);
        _game.interactWithCell(7, 2);
        _game.interactWithCell(9, 4);

        _game.initiateEvolution();

        Assertions.assertTrue(_game.getField().getCell(2, 1).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(2, 2).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(2, 3).isContainCreature());
    }

    @Test
    public void planeShape() {
        game _game = new game(new int[]{10, 10});
        _game.setCreaturesToCreate(5);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(8);

        _game.interactWithCell(1, 0);
        _game.interactWithCell(2, 1);
        _game.interactWithCell(2, 2);
        _game.interactWithCell(1, 2);
        _game.interactWithCell(0, 2);

        _game.switchPlayer();

        _game.interactWithCell(5, 0);
        _game.interactWithCell(7, 1);
        _game.interactWithCell(5, 4);
        _game.interactWithCell(7, 6);
        _game.interactWithCell(5, 8);

        _game.initiateEvolution();

        Assertions.assertTrue(_game.getField().getCell(3, 2).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(4, 3).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(4, 4).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(3, 4).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(2, 4).isContainCreature());
    }

    @Test
    public void outOfBoundsFromLeft() {
        game _game = new game(new int[]{10, 10});
        _game.setCreaturesToCreate(3);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(1);

        _game.interactWithCell(0, 0);
        _game.interactWithCell(0, 1);
        _game.interactWithCell(0, 2);

        _game.switchPlayer();

        _game.interactWithCell(5, 0);
        _game.interactWithCell(7, 1);
        _game.interactWithCell(5, 4);

        _game.initiateEvolution();

        Assertions.assertTrue(_game.getField().getCell(1, 1).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(0, 1).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(9, 1).isContainCreature());
    }

    @Test
    public void outOfBoundsFromTop() {
        game _game = new game(new int[]{10, 10});
        _game.setCreaturesToCreate(3);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(3);

        _game.interactWithCell(0, 0);
        _game.interactWithCell(1, 0);
        _game.interactWithCell(2, 0);

        _game.switchPlayer();

        _game.interactWithCell(5, 0);
        _game.interactWithCell(7, 1);
        _game.interactWithCell(5, 4);

        _game.initiateEvolution();

        Assertions.assertTrue(_game.getField().getCell(1, 1).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(1, 9).isContainCreature());
        Assertions.assertTrue(_game.getField().getCell(1, 0).isContainCreature());
    }

    @Test
    public void raceCheck() {
        game _game = new game(new int[]{6, 10});
        _game.setCreaturesToCreate(4);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(1);

        _game.interactWithCell(2, 0);
        _game.interactWithCell(1, 0);
        _game.interactWithCell(2, 1);
        _game.interactWithCell(1, 1);

        _game.switchPlayer();

        _game.interactWithCell(4, 1);
        _game.interactWithCell(3, 7);
        _game.interactWithCell(4, 7);
        _game.interactWithCell(5, 7);

        _game.initiateEvolution();

        Assertions.assertTrue(_game.getField().getCell(3, 1).isContainCreature());
        Assertions.assertEquals(0, _game.getField().getCell(3, 1).getCreatureRace());
    }

    @Test
    public void undoCreation() {
        game _game = new game(new int[]{10, 10});
        _game.setCreaturesToCreate(4);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(1);

        _game.interactWithCell(2, 2);
        _game.interactWithCell(2, 2);

        Assertions.assertFalse(_game.getField().getCell(2, 2).isContainCreature());
        Assertions.assertEquals(0, _game.getField().getCreatedThisTurn());
        Assertions.assertEquals(0, _game.getField().getRemovedThisTurn());
    }

    @Test
    public void undoDeletion() {
        game _game = new game(new int[]{10, 10});
        _game.setCreaturesToCreate(6);
        _game.setCreaturesToRemove(5);
        _game.setEvolutionIterationNumber(1);

        _game.interactWithCell(1, 2);
        _game.interactWithCell(2, 2);
        _game.interactWithCell(1, 1);
        _game.interactWithCell(2, 1);

        _game.initiateEvolution();

        _game.interactWithCell(2, 2);
        _game.interactWithCell(2, 2);

        Assertions.assertTrue(_game.getField().getCell(2, 2).isContainCreature());
        Assertions.assertEquals(4, _game.getField().getCreatedThisTurn());
        Assertions.assertEquals(0, _game.getField().getRemovedThisTurn());
    }
}