package view;

import model.Game;

import javax.swing.*;
import java.awt.*;

public class ResultScreen_GUI {
    public ResultScreen_GUI() {
        JFrame frame = new JFrame("Bomberman");

        JButton HomeButton = new JButton("HOME");
        HomeButton.addActionListener(e -> {
            frame.dispose();
            Game.RefreshMode();
            new StartScreen_GUI();
        });

        frame.setLayout(new FlowLayout());
        for (int i = 1; i < Game.number_of_players+1; i++) {
            JLabel Player = new JLabel("Player " + i + ": " + Game.players.get(i-1).getScore());
            frame.add(Player);
        }
        frame.add(HomeButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
