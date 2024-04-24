package model.Layer;

import model.Game;
import model.Tile.Empty;
import model.Tile.Player;

import java.util.ArrayList;

public class PlayersLayer extends Layer{
    public PlayersLayer(int size) {
        super(size);
        updateTiles(size);
        updateLayer(size);
    }

    @Override
    protected void updateTiles(int size) {
        ArrayList<Player> living_players = new ArrayList<>();

        for(Player player : Game.players){
            if(player.isAlive()){
                living_players.add(player);
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boolean is_player_exists = false;
                for (Player player : living_players) {
                    if (player.getX() == j && player.getY() == i) {
                        is_player_exists = true;
                        this.tiles.add(player);
                    }
                }
                if (!is_player_exists) {
                    this.tiles.add(new Empty(j, i));
                }
            }
        }
    }
}
