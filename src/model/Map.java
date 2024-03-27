package model;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private static String type;
    private static List<Tile> tiles;

    public Map(String mapType) {
        type = mapType;
        switch (mapType) {
            case "SmallMap":
                type = "SmallMap";
                tiles = new ArrayList<>();
//                tiles.add(new Box());
                break;
            case "MediumMap":
                type = "MediumMap";
                tiles = new ArrayList<>();
//                tiles.add(new Box());
                break;
            case "LargeMap":
                type = "LargeMap";
                tiles = new ArrayList<>();
//                tiles.add(new Box());
                break;
            default:
                throw new IllegalArgumentException("Invalid map name: " + type);
        }
    }

    public static String getType() {
        return type;
    }

    public static List<Tile> getTiles() {
        return tiles;
    }
}