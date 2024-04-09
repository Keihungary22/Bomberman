package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.Game;

import javax.swing.*;

public abstract class Layer {
    protected List<Tile> tiles = new ArrayList<>();
    protected JPanel layer;
    protected GridLayout grid;

    protected Layer() {
        grid = new GridLayout(Game.map.getSize(), Game.map.getSize(), 0, 0);
        layer = new JPanel(grid);
        layer.setOpaque(false);
    }

    //fill tiles array
    protected abstract void updateTiles(int size);
    //based of tiles, fill ImageIcons into layer(GridLayout)
    protected abstract void updateLayer(int size);

    //Getter method to obtain GridLayout for this layer
    public JPanel getLayer() {
        return this.layer;
    }
}
