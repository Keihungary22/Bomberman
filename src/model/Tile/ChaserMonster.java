package model.Tile;

import java.util.Random;
import model.Game;

public class ChaserMonster extends Monster {
    public ChaserMonster(String visual) {
        super(visual);
        this.setSpeed(3); // SmartMonsterは基本的なモンスターよりも速く設定
    }

    @Override
    protected void changeDirectionRandomly() {
        // プレイヤーに最も近い方向を選ぶ
        Player closestPlayer = findClosestPlayer();
        if (closestPlayer != null) {
            this.setDirection(getDirectionTowardsPlayer(closestPlayer));
        } else {
            // ランダムな方向を選ぶ
            super.changeDirectionRandomly();
        }
    }

    private Player findClosestPlayer() {
        Player closestPlayer = null;
        int minDistance = Integer.MAX_VALUE;
        for (Player player : Game.players) {
            if (player.isAlive()) {
                int distance = Math.abs(player.getX() - this.getX()) + Math.abs(player.getY() - this.getY());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPlayer = player;
                }
            }
        }
        return closestPlayer;
    }

    private Direction getDirectionTowardsPlayer(Player player) {
        int dx = player.getX() - this.getX();
        int dy = player.getY() - this.getY();

        // プレイヤーに向かう方向を選ぶ
        if (Math.abs(dx) > Math.abs(dy)) {
            // X軸の方が離れている場合はX軸方向へ
            return dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            // Y軸の方が離れている場合はY軸方向へ
            return dy > 0 ? Direction.DOWN : Direction.UP;
        }
    }

    @Override
    protected boolean shouldChangeDirection() {
        // 障害物にぶつかると方向を変えるが、このメソッドはその判断を行うためにオーバーライドする必要がある
        return super.shouldChangeDirection(); // 基本のロジックを利用する
    }

    @Override
    public void move() {
        // SmartMonsterの移動ロジック
        int newX = this.getX() + getDX();
        int newY = this.getY() + getDY();

        if (isValidPosition(newX, newY)) {
            // 有効な位置であれば移動する
            this.setX(newX);
            this.setY(newY);
        } else {
            // 移動できない場合はプレイヤーに向かう新しい方向を選ぶ
            changeDirectionRandomly();
        }
    }

    private int getDX() {
        return this.getDirection() == Direction.RIGHT ? 1 : this.getDirection() == Direction.LEFT ? -1 : 0;
    }

    private int getDY() {
        return this.getDirection() == Direction.DOWN ? 1 : this.getDirection() == Direction.UP ? -1 : 0;
    }
}
