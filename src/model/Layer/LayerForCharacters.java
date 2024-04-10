package model.Layer;

import model.Game;
import model.Tile.Empty;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LayerForCharacters extends Layer{
    public LayerForCharacters(int size) {
        super(size);
        updateTiles(size);
        updateLayer(size);
    }

    @Override
    protected void updateTiles(int size) {
        Map<Integer, Point> coordinates = new HashMap<>();

        for (int i = 0; i < Game.number_of_players; i++) {
            Point newPoint = new Point(Game.players.get(i).getX(), Game.players.get(i).getY());
            coordinates.put(i, newPoint);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (coordinates.containsValue(new Point(j, i))) {
                    for(int l = 0; l < coordinates.size(); l++) {
                        if(coordinates.get(l).equals(new Point(j, i))){
                            this.tiles.add(Game.players.get(l));
                        }
                    }
                }
                else{
                    this.tiles.add(new Empty(j, i));
                }
            }
        }
    }
}
