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


    private void GenerateGameBoard(){
        int size = Game.map.getSize()*30;

        JLayeredPane LayeredPane = new JLayeredPane();
        LayeredPane.setPreferredSize(new Dimension(size, size));
        GameBoard.setLayout(new BorderLayout());

        JPanel objectsLayer  = new LayerForObjects(Game.map.getSize()).getLayer();
        objectsLayer.setBounds(0, 0, size, size);

        JPanel backgroundLayer  = new LayerForBackground(Game.map.getSize()).getLayer();
        backgroundLayer.setBounds(0, 0, size, size);

        //region >> add layers into LayeredPane
        LayeredPane.add(backgroundLayer, JLayeredPane.DEFAULT_LAYER);
        LayeredPane.add(objectsLayer, JLayeredPane.PALETTE_LAYER);
        //endregion
        GameBoard.add(LayeredPane);
        // add LayeredPane in GameBoard JPane

    }

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
        GenerateGameBoard();

        setTitle("BOMBERMAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.MainPanel);
        this.setSize(1200, 1200);
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
