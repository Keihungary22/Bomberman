package model.Tile;

import model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfusedMonsterTest {
    private ConfusedMonster confusedMonster;
    private Game game;

    @BeforeEach
    void setUp() {
        confusedMonster = new ConfusedMonster("confusedMonster.png");
    }

    @Test
    void testIsAlive() {
        assertTrue(confusedMonster.isAlive(), "ConfusedMonster should initially be alive.");
    }

    @Test
    void testChangeDirectionRandomly() {
        Monster.Direction originalDirection = confusedMonster.getDirection();
        confusedMonster.changeDirectionRandomly();
        assertNotEquals(originalDirection, confusedMonster.getDirection(), "ConfusedMonster should have a different direction after changeDirectionRandomly call.");
    }

    @Test
    void testUpdateMovement() {
        confusedMonster.setDirection(Monster.Direction.RIGHT);
        confusedMonster.updateMovement();  // Assuming this method exists and it simply changes position based on direction
        assertEquals(1, confusedMonster.getX(), "ConfusedMonster should have moved right from x=0 to x=1");
    }

    @Test
    void testInteractWithMonster() {
        Monster anotherMonster = new Monster("anotherMonster.png");
        confusedMonster.interact(anotherMonster);
        assertNotEquals(Monster.Direction.STOP, confusedMonster.getDirection(), "ConfusedMonster should change direction after interacting with another monster.");
        assertNotEquals(Monster.Direction.STOP, anotherMonster.getDirection(), "The other monster should also change direction.");
    }

    @Test
    void testSetAndGetDirection() {
        confusedMonster.setDirection(Monster.Direction.LEFT);
        assertEquals(Monster.Direction.LEFT, confusedMonster.getDirection(), "ConfusedMonster direction should be set to LEFT.");
    }

    @Test
    void testSetAndGetSpeed() {
        confusedMonster.setSpeed(2);
        assertEquals(2, confusedMonster.getSpeed(), "ConfusedMonster speed should be set to 2.");
    }
}
