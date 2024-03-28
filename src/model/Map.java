package model;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private String type;
    private int size;
    private List<Tile> tiles;
    private List<Tile> freeFields;
    public Map(String mapType) {
        type = mapType;
        switch (mapType) {
            case "SmallMap":
                type = "SmallMap";
                tiles = new ArrayList<>();
//                size = 8;
                size = 4;
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (i == 0 || j == 0 || i == size-1 || j == size-1) {
                            tiles.add(new Brick(i, j));
                        } else {
                            tiles.add(new Field(i, j));
                        }
                    }
                }
                break;
            case "MediumMap":
                type = "MediumMap";
                tiles = new ArrayList<>();
//                size = 10;
                size = 4;
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (i == 0 || j == 0 || i == size-1 || j == size-1) {
                            tiles.add(new Brick(i, j));
                        } else {
                            tiles.add(new Field(i, j));
                        }
                    }
                }
                break;
            case "LargeMap":
                type = "LargeMap";
                tiles = new ArrayList<>();
//                size = 12;
                size = 4;
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (i == 0 || j == 0 || i == size-1 || j == size-1) {
                            tiles.add(new Brick(i, j));
                        } else {
                            tiles.add(new Field(i, j));
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid map name: " + type);
        }
        freeFields = new ArrayList<>();
        for (Tile tile : tiles) {
            if (tile instanceof Field && !tile.isBoundary) {
                freeFields.add(tile);
            }
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
}