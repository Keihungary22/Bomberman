package view;

import model.Field;
import model.Game;
import model.Player;
import model.Timer;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen_GUI {
    public static Timer timer = new Timer(60);

    public GameScreen_GUI() {
        Game.players = new ArrayList<>();
        for(int i = 0; i < Game.number_of_players; i++){
            Game.players.add(new Player());
        }

        JFrame frame = new JFrame("Bomberman");

        JLabel CurrentRound = new JLabel("Round " + Game.current_round);

        JLabel ElapsedTime = new JLabel("Time: " + timer.getElapsedTime());

        JButton MenuButton = new JButton("MENU");
        MenuButton.addActionListener(e -> {
            new MenuBar_GUI(frame);
        });

        JButton FinishRound = new JButton("Finish round");
        FinishRound.addActionListener(e -> {
            frame.dispose();
            Game.current_round++;
            if(Game.current_round <= Game.number_of_rounds){
                new GameScreen_GUI();
            }
            else{
                new ResultScreen_GUI();
            }
        });

        for(int index = 0; index < Game.number_of_players; index++) {
            int x = Game.players.get(index).getX();
            int y = Game.players.get(index).getY();
            Game.map.getTiles().set(x * Game.map.getSize() + y, new Field(x,y));

            while(true){
                Player player = new Player();

                if(!Game.players.contains(player)){
                    Game.players.get(index).setX(player.getX());
                    Game.players.get(index).setY(player.getY());
                    break;
                }
            }
        }
        Game.map.updateMap();

        //draw a matrix of tiles
        JPanel gameBoard = new JPanel(new GridLayout(Game.map.getSize(), Game.map.getSize()));
        for (int i = 0; i < Game.map.getSize(); i++) {
            for (int j = 0; j < Game.map.getSize(); j++) {
                JLabel tileLabel = new JLabel();
                try {
                    Image img = ImageIO.read(getClass().getResource("/assets/" + Game.map.getTiles().get(i*Game.map.getSize() + j).getVisual()));
                    tileLabel.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                gameBoard.add(tileLabel);
            }
        }

        frame.setLayout(new FlowLayout());
        frame.add(CurrentRound);
        frame.add(ElapsedTime);
        frame.add(MenuButton);
        frame.add(FinishRound);
        frame.add(gameBoard);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
