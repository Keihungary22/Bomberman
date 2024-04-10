package view;

import model.*;
import model.Timer;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameScreen_GUI extends JFrame implements ActionListener, KeyListener {
    private JPanel MainPanel;
    private JTextArea ElapsedTime;
    private JTextArea CurrentRound;
    private JButton btn_menu;
    private JButton btn_finish;
    private JPanel GameBoard;
    private final JLayeredPane LayeredPane;
    public static Timer timer = new Timer(60);


    private void GenerateGameBoard(){
        int size = Game.map.getSize()*30;
        LayeredPane.setPreferredSize(new Dimension(size, size));

        //region >> generate layers
        JPanel objectsLayer  = new LayerForObjects(Game.map.getSize()).getLayer();
        JPanel backgroundLayer  = new LayerForBackground(Game.map.getSize()).getLayer();
        //endregion

        //region >> add layers into LayeredPane
        LayeredPane.add(Game.map.getLayers().get("background").getLayer(), JLayeredPane.DEFAULT_LAYER);
        LayeredPane.add(Game.map.getLayers().get("object").getLayer(), JLayeredPane.PALETTE_LAYER);
        //endregion

        // add LayeredPane in GameBoard JPane
        GameBoard.setLayout(new BorderLayout());
        GameBoard.add(LayeredPane);
    }

    private void initPlayersPositions(){
        int index = 0;
        int x = 1;
        int y = 1;
        for(Player player : Game.players){
            switch (index){
                case 0:
                    x = 1;
                    y = 1;
                    break;
                case 1:
                    x = 1;
                    y = Game.map.getSize() - 2;
                    break;
                case 2:
                    x = Game.map.getSize() - 2;;
                    y = 1;
                    break;
            }
            player.setX(x);
            player.setY(y);
            index++;
        }
    }

    public GameScreen_GUI() {
        LayeredPane = new JLayeredPane();

        //region >> Register button action listeners
        btn_menu.addActionListener(this);
        btn_finish.addActionListener(this);
        //endregion

        //region >> add event listener for keyboard input
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        //endregion

        ElapsedTime.setText(String.valueOf(timer.getElapsedTime()));
        CurrentRound.setText(String.valueOf(Game.current_round));

        initPlayersPositions();
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

    @Override
    public void keyReleased(KeyEvent e) {
        // Processing when the key is released
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                Game.players.get(0).moveUp();
                Game.map.getLayers().get("object").update();
                LayeredPane.revalidate();
                LayeredPane.repaint();
                break;
            case KeyEvent.VK_DOWN:
                Game.players.get(0).moveDown();
                Game.map.getLayers().get("object").update();
                LayeredPane.revalidate();
                LayeredPane.repaint();
                break;
            case KeyEvent.VK_RIGHT:
                Game.players.get(0).moveRight();
                Game.map.getLayers().get("object").update();
                LayeredPane.revalidate();
                LayeredPane.repaint();
                break;
            case KeyEvent.VK_LEFT:
                Game.players.get(0).moveLeft();
                Game.map.getLayers().get("object").update();
                LayeredPane.revalidate();
                LayeredPane.repaint();
                break;

            case KeyEvent.VK_W:
                Game.players.get(1).moveUp();
                Game.map.getLayers().get("object").update();
                LayeredPane.revalidate();
                LayeredPane.repaint();
                break;
            case KeyEvent.VK_X:
                Game.players.get(1).moveDown();
                Game.map.getLayers().get("object").update();
                LayeredPane.revalidate();
                LayeredPane.repaint();
                break;
            case KeyEvent.VK_D:
                Game.players.get(1).moveRight();
                Game.map.getLayers().get("object").update();
                LayeredPane.revalidate();
                LayeredPane.repaint();
                break;
            case KeyEvent.VK_A:
                Game.players.get(1).moveLeft();
                Game.map.getLayers().get("object").update();
                LayeredPane.revalidate();
                LayeredPane.repaint();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}
