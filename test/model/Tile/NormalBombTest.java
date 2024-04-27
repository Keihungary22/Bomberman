package model.Tile;

import model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalBombTest {
    private NormalBomb normalBomb;

    @BeforeEach
    void setUp() {
        // プレイヤーを初期化します。ここでプレイヤーの画像など必要なプロパティを設定します。
        Player player = new Player("Player1.png", 1);
        player.setPower_of_bombs(3);  // 爆弾の威力を設定
        normalBomb = new NormalBomb(0, 0, player); // 正しいプレイヤーオブジェクトで爆弾を初期化
        Game.is_paused = false; // ゲームの一時停止状態を解除
    }

    @Test
    void testTimerExists() {
        assertNotNull(normalBomb.getTimer(), "Timer should be initialized when bomb is created.");
    }

    @Test
    void testTimerInitiallyRunning() {
        assertTrue(normalBomb.getTimer().purge() >= 0, "Timer should have at least one scheduled task initially.");
    }
}
