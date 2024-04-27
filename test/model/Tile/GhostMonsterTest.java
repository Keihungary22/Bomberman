package model.Tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostMonsterTest {
    private GhostMonster ghostMonster;

    @BeforeEach
    void setUp() {
        ghostMonster = new GhostMonster("ghostMonster.png");
    }

    @Test
    void testIsAlive() {
        assertTrue(ghostMonster.isAlive(), "GhostMonster should initially be alive.");
    }

    @Test
    void testInteractWithMonster() {
        Monster anotherMonster = new Monster("anotherMonster.png");
        ghostMonster.interact(anotherMonster);
        assertNotEquals(Monster.Direction.STOP, ghostMonster.getDirection(), "GhostMonster should change direction after interacting with another monster.");
        assertNotEquals(Monster.Direction.STOP, anotherMonster.getDirection(), "The other monster should also change direction.");
    }

    @Test
    void testSetAndGetDirection() {
        ghostMonster.setDirection(GhostMonster.Direction.LEFT);
        assertEquals(GhostMonster.Direction.LEFT, ghostMonster.getDirection(), "GhostMonster direction should be set to LEFT.");
    }

    @Test
    void testSetAndGetSpeed() {
        ghostMonster.setSpeed(2);
        assertEquals(2, ghostMonster.getSpeed(), "GhostMonster speed should be set to 2.");
    }
}
