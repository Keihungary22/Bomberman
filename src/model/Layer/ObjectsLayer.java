package model.Layer;

import model.Game;
import model.Tile.Block;
import model.Tile.Brick;
import model.Tile.Empty;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ObjectsLayer extends Layer{
    public ObjectsLayer(int size) {
        super(size);
        updateTiles(size);
        updateLayer(size);
    }

    @Override
    protected void updateTiles(int size) {
        Map<Integer, Point> player_coordinates = new HashMap<>();
        Map<Integer, Point> box_coordinates = new HashMap<>();
        Map<Integer, Point> treasure_coordinates = new HashMap<>();

        for (int i = 0; i < Game.number_of_players; i++) {
            Point newPoint = new Point(Game.players.get(i).getX(), Game.players.get(i).getY());
            player_coordinates.put(i, newPoint);
        }
        for (int i = 0; i < Game.boxes.size(); i++) {
            Point newPoint = new Point(Game.boxes.get(i).getX(), Game.boxes.get(i).getY());
            box_coordinates.put(i, newPoint);
        }
        for (int i = 0; i < Game.treasures.size(); i++) {
            Point newPoint = new Point(Game.treasures.get(i).getX(), Game.treasures.get(i).getY());
            treasure_coordinates.put(i, newPoint);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (player_coordinates.containsValue(new Point(j, i))) {
                    for(int l = 0; l < player_coordinates.size(); l++) {
                        if(player_coordinates.get(l).equals(new Point(j, i))){
                            if(Game.players.get(l).isAlive()){
                                this.tiles.add(Game.players.get(l));
                            }else{
                                this.tiles.add(new Empty(j, i));
                            }
                        }
                    }
                } else if (box_coordinates.containsValue(new Point(j, i))) {
                    for(int l = 0; l < box_coordinates.size(); l++) {
                        if(box_coordinates.get(l).equals(new Point(j, i))){
                            this.tiles.add(Game.boxes.get(l));
                        }
                    }
                }else if (treasure_coordinates.containsValue(new Point(j, i))) {
                    for(int l = 0; l < treasure_coordinates.size(); l++) {
                        if(treasure_coordinates.get(l).equals(new Point(j, i))){
                            this.tiles.add(Game.treasures.get(l));
                        }
                    }
                }
                else  if (i == 0 || j == 0 || i == size-1 || j == size-1) {
                    this.tiles.add(new Brick(j, i));
                }
                else if(j % 2 == 0 && i % 2 == 0){
                    this.tiles.add(new Block(j, i));
                }
                else {
                    this.tiles.add(new Empty(j, i));
                }
            }
        }
    }
}
