package model;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LayerForObjects extends Layer {
    public LayerForObjects(int size) {
        super();
        updateTiles(size);
        updateLayer(size);
    }


    @Override
    protected void updateTiles(int size) {
        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || i == size-1 || j == size-1) {
                    this.tiles.add(new Brick(i, j));
                } else if(index < Game.number_of_players && Game.players.get(index).getX() == i && Game.players.get(index).getY() == j){
                    this.tiles.add(Game.players.get(index));
                    index++;
                } else {
                    this.tiles.add(new Empty(i, j));
                }
            }
        }
    }

    //based of tiles, generate GridLayout as layer
    @Override
    protected void updateLayer(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JLabel tileLabel = new JLabel();
                ImageIcon icon = new ImageIcon("assets/" + this.tiles.get(i*Game.map.getSize() + j).getVisual());
                tileLabel.setIcon(icon);
                System.out.println(icon);
                tileLabel.setPreferredSize(new Dimension(30, 30));
                layer.add(tileLabel);
            }
        }
    }
}
