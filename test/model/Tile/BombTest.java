package model.Tile;

import model.EventListener.BombExplodeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BombTest {
    private Bomb bomb;
    private Player player;

    @BeforeEach
    void setUp() {
        // Player オブジェクトを初期化します。ここでは適当なビジュアル文字列とIDを渡しています。
        player = new Player("player_visual", 1);
        bomb = new Bomb(0, 0, player);
    }

    @Test
    void testSetAndGetExploded() {
        bomb.setExploded(true);
        assertTrue(bomb.isExploded(), "Exploded should return true after being set.");
    }

    @Test
    void testGetOwner() {
        assertEquals(player, bomb.getOwner(), "GetOwner should return the player who owns the bomb.");
    }

    @Test
    void testEquals() {
        Bomb anotherBomb = new Bomb(0, 0, player);
        assertEquals(bomb, anotherBomb, "Two bombs with the same coordinates and owner should be equal.");
    }
}
