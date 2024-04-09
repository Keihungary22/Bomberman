package view;

import model.Game;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultScreen_GUI extends JFrame implements ActionListener {
    private JPanel MainPanel;
    private JButton btn_startsc;
    private JPanel Player;

    public ResultScreen_GUI() {
        btn_startsc.addActionListener(this);
        this.setTitle("RESULT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 1; i < Game.number_of_players+1; i++) {
            Player.add(new JLabel("Player " + i + ": " + Game.players.get(i - 1).getScore()));
        }

        this.setContentPane(this.MainPanel);
        this.setSize(1000, 700);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_startsc) {
            this.dispose();
            Game.RefreshMode();
            new StartScreen_GUI();
        }
    }
}
