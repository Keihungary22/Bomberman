package view;

import model.*;
import javax.swing.*;
import java.awt.*;

public class MenuBar_GUI {
    private final JFrame gameScreenFrame;
    public MenuBar_GUI(JFrame gameScreenFrame) {
        this.gameScreenFrame = gameScreenFrame;
        JFrame frame = new JFrame("Menu");

        JLabel CurrentRound = new JLabel("Round " + Game.current_round);
        JLabel ElapsedTime = new JLabel("Timer: " + GameScreen_GUI.timer.getElapsedTime());
        JLabel TypeOfMap = new JLabel(Game.type_of_map.getType());


        JButton ContinueButton = new JButton("Continue");
        ContinueButton.addActionListener(e -> {
            frame.dispose();
        });

        JButton EndGameButton = new JButton("End Game");
        EndGameButton.addActionListener(e -> {
            frame.dispose();
            this.gameScreenFrame.dispose();
            Game.RefreshMode();
            new StartScreen_GUI();
        });

        frame.setLayout(new FlowLayout());
        frame.add(CurrentRound);
        frame.add(ElapsedTime);
        frame.add(TypeOfMap);
        frame.add(ContinueButton);
        for (int i = 1; i < Game.number_of_players+1; i++) {
            JLabel Player = new JLabel("Player " + i + ": " + Game.players.get(i-1).getScore());
            frame.add(Player);
        }
        frame.add(EndGameButton);

        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
