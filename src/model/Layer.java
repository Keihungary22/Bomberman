package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.Game;

import javax.swing.*;

public abstract class Layer {
    protected List<Tile> tiles = new ArrayList<>();
    protected JPanel layer;

    protected Layer(int size) {
        GridLayout grid = new GridLayout(size, size, 0, 0);
        layer = new JPanel(grid);
        layer.setOpaque(false);
        layer.setBounds(0, 0, size*30, size*30);
    }

    //fill tiles array
    protected abstract void updateTiles(int size);
    //based of tiles, fill ImageIcons into layer(GridLayout)
    protected abstract void updateLayer(int size);

    //Getter method to obtain JPanel for this layer
    public JPanel getLayer() {
        return this.layer;
    }
}
