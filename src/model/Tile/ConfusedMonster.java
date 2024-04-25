package model.Tile;

import java.util.Random;
import model.Game;

public class ConfusedMonster extends Monster {
    private final Random random = new Random();

    public ConfusedMonster(String visual) {
        super(visual);
        this.setSpeed(2); // 基本のモンスターと同じ速度
    }

    @Override
    protected void changeDirectionRandomly() {
        // ここでは方向を変える際にプレイヤーに近づく方向とは限らないランダムな選択を行う
        if(random.nextInt(100) < 30) { // 30%の確率で誤った方向を選ぶ
            Direction[] directions = Direction.values();
            setDirection(directions[random.nextInt(directions.length)]);
        } else {
            // それ以外の場合はプレイヤーに最も近い方向を選ぶ
            super.changeDirectionRandomly();
        }
    }

    @Override
    protected boolean shouldChangeDirection() {
        // 分岐点での判断が必要かどうかを判定する
        // 実際のゲームではここでマップの状態をチェックし、分岐点かどうかを判断する
        // 簡単化のため、ここではランダムに決定する
        return random.nextBoolean();
    }
}
