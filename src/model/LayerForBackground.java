package model;

import javax.swing.*;
import java.awt.*;

public class LayerForBackground extends Layer{
    public LayerForBackground(int size) {
        super(size);
        updateTiles(size);
        updateLayer(size);
    }

    @Override
    protected void updateTiles(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles.add(new Field(i, j));
            }
        }
    }

    @Override
    protected void updateLayer(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JLabel tileLabel = new JLabel();
                ImageIcon icon = new ImageIcon("assets/" + this.tiles.get(i*size + j).getVisual());
                tileLabel.setIcon(icon);
                tileLabel.setPreferredSize(new Dimension(30, 30));
                layer.add(tileLabel);
            }
        }
    }
}
