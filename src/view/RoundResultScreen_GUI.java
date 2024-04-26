package view;

import model.Game;
import model.Tile.Player;

import javax.swing.*;
import java.awt.*;
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

        //region >> set text into WinnerField
        Player winner = null;
        for (Player player : Game.players) {
            if(player.isAlive()){
                winner = player;
                break;
            }
        }
        if(winner != null){
            WinnerField.setText(winner.getDisplayName());
            winner.increaseScore();
        }else{
            WinnerField.setText("Draw");
        }
        //endregion

        nextRoundButton.addActionListener(this);
        this.setTitle("Round Result");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RoundNumber.setText(String.valueOf(Game.current_round));
        p1WinCount.setText(String.valueOf(player1.getScore()));
        p2WinCount.setText(String.valueOf(player2.getScore()));
        if(Game.number_of_players == 3){
            Player player3 = Game.players.get(2);
            p3WinCount.setText(String.valueOf(player3.getScore()));
        }else{
            P3WinCountPanel.setVisible(false);
        }


        this.setContentPane(this.MainPanel);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        Game.current_round++;
        if(Game.current_round <= Game.number_of_rounds){
            new GameScreen_GUI();
        }
        else{
            new ResultScreen_GUI();
        }
    }
}
