package model;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LayerForObjects extends Layer {
    public LayerForObjects(int size) {
        super(size);
        updateTiles(size);
        updateLayer(size);
    }

    @Override
    protected void updateTiles(int size) {
        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || i == size-1 || j == size-1) {
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
