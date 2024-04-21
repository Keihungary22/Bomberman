package view;

import model.Game;
import model.Tile.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoundResultScreen_GUI extends JFrame implements ActionListener {
    private JPanel MainPanel;
    private JTextArea RoundNumber;
    private JButton nextRoundButton;
    private JTextField WinnerField;
    private JTextField p1WinCount;
    private JTextField p2WinCount;
    private JTextField p3WinCount;
    private JPanel P3WinCountPanel;

    public RoundResultScreen_GUI() {
        Player player1 = Game.players.get(0);
        Player player2 = Game.players.get(1);
        Player player3 = Game.players.get(2);

        nextRoundButton.addActionListener(this);
        this.setTitle("Round Result");
        RoundNumber.setText(String.valueOf(Game.current_round));
        p1WinCount.setText(String.valueOf(player1.getScore()));
        p2WinCount.setText(String.valueOf(player2.getScore()));
        if(Game.number_of_players == 3){
            p3WinCount.setText(String.valueOf(player3.getScore()));
        }else{
            P3WinCountPanel.setVisible(false);
        }

        //region >> set text into WinnerField
            //implement the code here
        //endregion

        this.setContentPane(this.MainPanel);
        this.setSize(1000, 700);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
