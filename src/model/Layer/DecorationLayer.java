package model.Layer;

import model.Tile.*;

public class DecorationLayer extends Layer {
    public DecorationLayer(int size) {
        super(size);
        updateTiles(size);
        updateLayer(size);
    }

    @Override
    protected void updateTiles(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles.add(new Empty(j, i));
            }
        }
    }
}
