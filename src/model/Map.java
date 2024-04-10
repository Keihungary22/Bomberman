package model;

import model.Layer.Layer;
import model.Layer.LayerForBackground;
import model.Layer.LayerForCharacters;
import model.Layer.LayerForObjects;
import model.Tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;;

public class Map {
    private String type;
    private static int size;
    private static List<Tile> tiles;
    private static List<Tile> freeFields;
    private static java.util.Map<String, Layer> layers;

    public static void updateMap(){
        //region >> init layers map
        layers = new HashMap<String, Layer>();
        //endregion

        //region >> generate new layers
        Layer  backgroundLayer  = new LayerForBackground(size);
        Layer objectsLayer  = new LayerForObjects(size);
        Layer charactorsLayer = new LayerForCharacters(size);
        //endregion

        //region >> Add each layer in layers map
        layers.put("background", backgroundLayer);
        layers.put("object", objectsLayer);
        layers.put("character", charactorsLayer);
        //endregion
    }

    public Map(String mapType) {
        type = mapType;
        tiles = new ArrayList<>();
        switch (mapType) {
            case "SmallMap":
                size = 13;
                break;
            case "MediumMap":
                size = 15;
                break;
            case "LargeMap":
                size = 19;
                break;
            default:
                throw new IllegalArgumentException("Invalid map name: " + type);
        }
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

    public java.util.Map<String, Layer> getLayers() {
        return layers;
    }

    // Method to remove a tile from the map
    public void removeTile(Tile tile) {
        // Logic to remove a specific tile from the map
        tiles.remove(tile);
        // If the tile is in the freeFields list, remove it as well
        freeFields.remove(tile);
    }
}