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
        Map<Integer, Point> bombs_coordinates = new HashMap<>();
        Map<Integer, Point> explosions_coordinates = new HashMap<>();

        for (int i = 0; i < Game.bombs.size(); i++) {
            Point newPoint = new Point(Game.bombs.get(i).getX(), Game.bombs.get(i).getY());
            bombs_coordinates.put(i, newPoint);
        }
        for (int i = 0; i < Game.explosions.size(); i++) {
            Point newPoint = new Point(Game.explosions.get(i).getX(), Game.explosions.get(i).getY());
            explosions_coordinates.put(i, newPoint);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //add bombs
                if (bombs_coordinates.containsValue(new Point(j, i))) {
                    for(int l = 0; l < bombs_coordinates.size(); l++) {
                        if(bombs_coordinates.get(l).equals(new Point(j, i))){
                            this.tiles.add(Game.bombs.get(l));
                        }
                    }
                }
                //add explosions
                else if(explosions_coordinates.containsValue(new Point(j, i))) {
                    for(int l = 0; l < explosions_coordinates.size(); l++) {
                        if(explosions_coordinates.get(l).equals(new Point(j, i))){
                            if(this.tiles.contains(Game.explosions.get(l))){
                                this.tiles.remove(Game.explosions.get(l));
                            }
                            this.tiles.add(Game.explosions.get(l));
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
