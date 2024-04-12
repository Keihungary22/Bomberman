//package model.Layer;
//
//public class BombLayer extends Layer{
//
//}
package model.Layer;

import model.Game;
import model.Tile.Empty;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BombsLayer extends Layer{
    public BombsLayer(int size) {
        super(size);
        updateTiles(size);
        updateLayer(size);
    }

    @Override
    protected void updateTiles(int size) {
        Map<Integer, Point> coordinates = new HashMap<>();

        for (int i = 0; i < Game.bombs.size(); i++) {
            Point newPoint = new Point(Game.bombs.get(i).getX(), Game.bombs.get(i).getY());
            coordinates.put(i, newPoint);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (coordinates.containsValue(new Point(j, i))) {
                    for(int l = 0; l < coordinates.size(); l++) {
                        if(coordinates.get(l).equals(new Point(j, i))){
                            this.tiles.add(Game.bombs.get(l));
                        }
                    }
                }
                else {
                    this.tiles.add(new Empty(j, i));
                }
            }
        }
    }
}
