package view;

import model.Game;
import model.Player;
import model.Timer;

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


        frame.setLayout(new FlowLayout());
        frame.add(CurrentRound);
        frame.add(ElapsedTime);
        frame.add(MenuButton);
        frame.add(FinishRound);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
