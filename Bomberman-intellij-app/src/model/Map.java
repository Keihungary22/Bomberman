package model;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private String type;
    private static int size;
    private static List<Tile> tiles;
    private static List<Tile> freeFields;

    public static void updateMap(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || i == size-1 || j == size-1) {
                    tiles.add(new Brick(i, j));
                } else if(!Game.players.isEmpty()) {
                    for(int index = 0; index < Game.number_of_players; index++){
                        if(Game.players.get(index).getX() == i && Game.players.get(index).getY() == j){
                            Game.map.getTiles().set(i*Game.map.getSize() + j, Game.players.get(index));
                        }
                    }
                } else {
                    tiles.add(new Field(i, j));
                }
            }
        }

        freeFields = new ArrayList<>();
        for (Tile tile : tiles) {
            if (tile instanceof Field) {
                freeFields.add(tile);
            }
        }
    }

    public Map(String mapType) {
        type = mapType;
        tiles = new ArrayList<>();
        switch (mapType) {
            case "SmallMap":
                size = 6;
                break;
            case "MediumMap":
                size = 8;
                break;
            case "LargeMap":
                size = 12;
                break;
            default:
                throw new IllegalArgumentException("Invalid map name: " + type);
        }

        updateMap();
    }

    public String getType() {
        return type;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Tile> getFreeFields() {
        return freeFields;
    }

    public int getSize() {
        return size;
    }

    // Method to remove a tile from the map
    public void removeTile(Tile tile) {
        // Logic to remove a specific tile from the map
        tiles.remove(tile);
        // If the tile is in the freeFields list, remove it as well
        freeFields.remove(tile);
    }
}