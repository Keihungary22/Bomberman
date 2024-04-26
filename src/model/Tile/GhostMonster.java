package model.Tile;
import model.Game;

// Monster.java の内部に新しいサブクラスを作成
public class GhostMonster extends Monster {
    public GhostMonster(String visual) {
        super(visual);
        this.setSpeed(1); // GhostMonsterの速度を通常のモンスターより遅く設定
    }

    @Override
    protected boolean shouldChangeDirection() {
        // 障害物を通過できるので、障害物の前では方向を変えない
        // ただし、ボムの場合は方向を変える
        for (Bomb bomb : Game.bombs) {
            if (isNextToBomb(bomb)) {
                return true; // 障害物がボムの場合は方向を変える
            }
        }
        return false; // 障害物がボムでなければ方向を変えない
    }

    private boolean isNextToBomb(Bomb bomb) {
        // 次の移動でボムに衝突するかを判定
        int nextX = this.getX() + getDX();
        int nextY = this.getY() + getDY();
        return bomb.getX() == nextX && bomb.getY() == nextY;
    }

    private int getDX() {
        return this.getDirection() == Direction.RIGHT ? 1 : this.getDirection() == Direction.LEFT ? -1 : 0;
    }

    private int getDY() {
        return this.getDirection() == Direction.DOWN ? 1 : this.getDirection() == Direction.UP ? -1 : 0;
    }

    @Override
    protected boolean isValidPosition(int x, int y) {
        // マップの範囲内かどうかのチェックを行う
        if (x < 0 || y < 0 || x >= Game.map.getSize() || y >= Game.map.getSize()) {
            return false;
        }
        // 通過できるのはボム以外。壁や箱は通過可能とする。
        for (Bomb bomb : Game.bombs) {
            if (bomb.getX() == x && bomb.getY() == y) {
                return false; // ボムがある位置には移動できない
            }
        }

        for (Tile tile : Game.map.getLayers().get("Objects").getTiles()) {
            if ((tile instanceof Brick) && tile.getX() == x && tile.getY() == y) {
                return false; // Collision detected, position is not valid
            }
        }

        for(Tile characters_tile : Game.map.getLayers().get("Characters").getTiles()) {
            if((characters_tile instanceof Monster) && characters_tile.getX() == x && characters_tile.getY() == y) {
                return false;
            }
        }
        return true; // それ以外の場合は移動可能
    }

    @Override
    public void move() {
        // モンスターの移動処理
        int newX = this.getX() + getDX();
        int newY = this.getY() + getDY();

        if (isValidPosition(newX, newY)) {
            // マップの範囲内であれば移動
            this.setX(newX);
            this.setY(newY);
            for(Player player:Game.players) {
                if (player.getX() == x && player.getY() == y) {
                    // Player is at the position the monster is trying to move to
                    player.die(); // Here, you would call the method that handles player death
                }
            }
        } else {
            // 移動できなければ方向を変える
            changeDirectionRandomly();
        }
    }
}
