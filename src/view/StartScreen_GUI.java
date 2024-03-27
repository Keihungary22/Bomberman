package view;

import model.Game;
import model.Map;

import javax.swing.*;
import java.awt.*;

public class StartScreen_GUI {
    public StartScreen_GUI() {
        JFrame frame = new JFrame("Bomberman");
        JLabel logo = new JLabel("Bomberman");

        Game.RefreshMode();

        Integer[] players = {2, 3};
        JComboBox<Integer> NumberOfPlayersBox = new JComboBox<>(players);
        NumberOfPlayersBox.setSelectedItem(Game.number_of_players);
        NumberOfPlayersBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            Game.number_of_players = (Integer) cb.getSelectedItem();
        });

        String[] maps = {"SmallMap", "MediumMap", "LargeMap"};
        JComboBox<String> TypeOfMapBox = new JComboBox<>(maps);
        TypeOfMapBox.setSelectedItem(Game.type_of_map.getType());
        TypeOfMapBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            Game.type_of_map = new Map((String) cb.getSelectedItem());
        });

        Integer[] rounds = {1, 2, 3};
        JComboBox<Integer> NumberOfRoundsBox = new JComboBox<>(rounds);
        NumberOfRoundsBox.setSelectedItem(Game.number_of_rounds);
        NumberOfRoundsBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            Game.number_of_rounds = (Integer) cb.getSelectedItem();
        });

        JButton ExitButton = new JButton("EXIT");
        ExitButton.addActionListener(e -> {
            System.exit(0);
        });

        JButton StartGameButton = new JButton("START");
        StartGameButton.addActionListener(e -> {
            frame.dispose();
            new GameScreen_GUI();
        });

        frame.setLayout(new FlowLayout());
        frame.add(logo);
        frame.add(NumberOfPlayersBox);
        frame.add(TypeOfMapBox);
        frame.add(NumberOfRoundsBox);
        frame.add(ExitButton);
        frame.add(StartGameButton);

        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}