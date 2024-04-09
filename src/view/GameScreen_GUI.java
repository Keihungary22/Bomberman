package view;

import model.*;
import model.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameScreen_GUI extends JFrame implements ActionListener {
    private JPanel MainPanel;
    private JTextArea ElapsedTime;
    private JTextArea CurrentRound;
    private JButton btn_menu;
    private JButton btn_finish;
    private JPanel GameBoard;
    public static Timer timer = new Timer(60);

    public GameScreen_GUI() {
        //region >> Register button action listeners
        btn_menu.addActionListener(this);
        btn_finish.addActionListener(this);
        //endregion

        ElapsedTime.setText(String.valueOf(timer.getElapsedTime()));
        CurrentRound.setText(String.valueOf(Game.current_round));

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
        Map.updateMap();

        GameBoard.setLayout(new GridLayout(Game.map.getSize(), Game.map.getSize(), 0, 0));
        for (int i = 0; i < Game.map.getSize(); i++) {
            for (int j = 0; j < Game.map.getSize(); j++) {
                JLabel tileLabel = new JLabel();
                ImageIcon icon = new ImageIcon("assets/" + Game.map.getTiles().get(i*Game.map.getSize() + j).getVisual());
                tileLabel.setIcon(icon);
                GameBoard.add(tileLabel);
            }
        }

        setTitle("BOMBERMAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.MainPanel);
        this.setSize(1000, 700);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_menu) {
            new MenuBar_GUI(this);
        }else if(e.getSource() == btn_finish) {
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
}
