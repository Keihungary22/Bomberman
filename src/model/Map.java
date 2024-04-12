package model;

import model.Layer.Layer;
import model.Layer.BackgroundLayer;
import model.Layer.ObjectsLayer;
import model.Layer.DecorationLayer;
import model.Tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;;

public class Map {
    private String type;
    private static int size;
    private static List<Tile> tiles;
    private static java.util.Map<String, Layer> layers;

    public static void updateMap(){
        //region >> init layers hashmap
        layers = new HashMap<String, Layer>();
        //endregion

        //region >> generate new layers
        Layer BackgroundLayer  = new BackgroundLayer(size); //background layer
        Layer DecorationLayer  = new DecorationLayer(size); //decoration layer
        Layer ObjectsLayer = new ObjectsLayer(size); //objects layer
        //endregion

        //region >> Add each layer in layers hashmap
        layers.put("Background", BackgroundLayer);
        layers.put("Decoration", DecorationLayer);
        layers.put("Objects", ObjectsLayer);
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
    }
}