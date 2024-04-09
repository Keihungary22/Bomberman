package view;

import model.Game;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar_GUI extends JFrame implements ActionListener{
    private JPanel MainPanel;
    private JTextArea CurrentRound;
    private JTextArea ElapsedTime;
    private JButton btn_continue;
    private JButton btn_end;
    private JPanel Player;
    private JLabel TypeOfMap;
    private final JFrame gameScreenFrame;


    public MenuBar_GUI(GameScreen_GUI gameScreenFrame) {
        //region >> Register button action listeners
        btn_continue.addActionListener(this);
        btn_end.addActionListener(this);
        //endregion


        ElapsedTime.setText(String.valueOf(GameScreen_GUI.timer.getElapsedTime()));
        CurrentRound.setText(String.valueOf(Game.current_round));
        TypeOfMap.setText(Game.map.getType());
        for (int i = 1; i < Game.number_of_players+1; i++) {
            Player.add(new JLabel("Player " + i + ": " + Game.players.get(i-1).getScore()));
        }
        this.gameScreenFrame = gameScreenFrame;
        setTitle("BOMBERMAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(this.MainPanel);
        this.setSize(1000, 700);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_continue){
            this.dispose();
        }else if(e.getSource() == btn_end){
            this.gameScreenFrame.dispose();
            this.dispose();
            Game.RefreshMode();
            new StartScreen_GUI();
        }
    }
}
