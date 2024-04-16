package view;

import model.*;
import model.Tile.Bomb;
import model.Tile.Player;
import model.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameScreen_GUI extends JFrame implements ActionListener, KeyListener, BombExplodeListener {
    private JPanel MainPanel;
    private JTextArea ElapsedTime;
    private JTextArea CurrentRound;
    private JButton btn_menu;
    private JButton btn_finish;
    private JPanel GameBoard;
    private final JLayeredPane LayeredPane;
    public static Timer timer = new Timer(60);

    //constructor
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

        //region >> initiate GUI for game screen
        setTitle("BOMBERMAN");
        ElapsedTime.setText(String.valueOf(timer.getElapsedTime()));
        CurrentRound.setText(String.valueOf(Game.current_round));
        initPlayersPositions();//Initiate players position and put them on the map
        Map.updateMap();//Update map class
        GenerateGameBoard(); //based on the map class, initiate JPanel of the game board
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.MainPanel);
        this.setSize(1200, 1200);
        this.setVisible(true);
        //endregion
    }

    //region >> private functions
    private void GenerateGameBoard(){
        int size = Game.map.getSize()*30;
        LayeredPane.setPreferredSize(new Dimension(size, size));

        //region >> add layers into LayeredPane
        LayeredPane.add(Game.map.getLayers().get("Background").getLayer(), JLayeredPane.DEFAULT_LAYER, 1);
        LayeredPane.add(Game.map.getLayers().get("Decoration").getLayer(), JLayeredPane.DEFAULT_LAYER, 0);
        LayeredPane.add(Game.map.getLayers().get("Bombs").getLayer(), JLayeredPane.PALETTE_LAYER, 1);
        LayeredPane.add(Game.map.getLayers().get("Objects").getLayer(), JLayeredPane.PALETTE_LAYER, 0);
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

    private void playerMove(int player_id, String direction){
        int x = Game.players.get(player_id).getX();
        int y = Game.players.get(player_id).getY();
        int size = Game.map.getSize();

        int dx = 0;
        int dy = 0;

        switch (direction){
            case "up":
                dy = -1;
                break;
            case "down":
                dy = 1;
                break;
            case "left":
                dx = -1;
                break;
            case "right":
                dx = 1;
                break;
        }

        if(Game.map.getLayers().get("Objects").getTiles().get(size*(y+dy)+x+dx).getVisual().equals("Empty.png")){
            switch (direction){
                case "up":
                    Game.players.get(player_id).moveUp();
                    break;
                case "down":
                    Game.players.get(player_id).moveDown();
                    break;
                case "left":
                    Game.players.get(player_id).moveLeft();
                    break;
                case "right":
                    Game.players.get(player_id).moveRight();
                    break;
            }

            Game.map.getLayers().get("Objects").update();
            LayeredPane.revalidate();
            LayeredPane.repaint();
        }
    }

    private Bomb playerPutBomb(int player_id){
        Bomb newBomb = Game.players.get(player_id).putBomb();
        Game.map.getLayers().get("Bombs").update();
        LayeredPane.revalidate();
        LayeredPane.repaint();
        return newBomb;
    }
    //endregion


    //actions for each button
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

    // player controller
    @Override
    public void keyReleased(KeyEvent e) {
        // Processing when the key is released
        switch (e.getKeyCode()) {
            //region >> player1 controller
            case KeyEvent.VK_UP:
                playerMove(0, "up");
                break;
            case KeyEvent.VK_DOWN:
                playerMove(0, "down");
                break;
            case KeyEvent.VK_RIGHT:
                playerMove(0, "right");
                break;
            case KeyEvent.VK_LEFT:
                playerMove(0, "left");
                break;
            case KeyEvent.VK_SHIFT:
                if(Game.players.get(0).isBombPlaceable()){
                    playerPutBomb(0).setBombExplodeListener(this);   //put bomb
                }
                break;
            //endregion

            //region >> player2 controller
            case KeyEvent.VK_W:
                playerMove(1, "up");
                break;
            case KeyEvent.VK_S:
                playerMove(1, "down");
                break;
            case KeyEvent.VK_D:
                playerMove(1, "right");
                break;
            case KeyEvent.VK_A:
                playerMove(1, "left");
                break;
            case KeyEvent.VK_R://put bomb
                if(Game.players.get(1).isBombPlaceable()) {
                    playerPutBomb(1).setBombExplodeListener(this);
                }
                break;
            //endregion

            //region >> player3 controller
            case KeyEvent.VK_U:
                playerMove(2, "up");
                break;
            case KeyEvent.VK_M:
                playerMove(2, "down");
                break;
            case KeyEvent.VK_K:
                playerMove(2, "right");
                break;
            case KeyEvent.VK_H:
                playerMove(2, "left");
                break;
            case KeyEvent.VK_O://put bomb
                if(Game.players.get(2).isBombPlaceable()) {
                    playerPutBomb(2).setBombExplodeListener(this);
                }
                break;
            //endregion
        }
    }

    // Implementation of explosion event listeners
    @Override
    public void bombExploded() {
        Game.map.getLayers().get("Bombs").update();
        LayeredPane.revalidate();
        LayeredPane.repaint();
    }

    //Implementation of finish-explosion event listeners
    @Override
    public void bombFinishExplosion(){
        Game.map.getLayers().get("Bombs").update();
        LayeredPane.revalidate();
        LayeredPane.repaint();
    }







    //region >> we don't need to implement it
    @Override
    public void keyTyped(KeyEvent e) {
    }
    //we don't need to implement it
    @Override
    public void keyPressed(KeyEvent e) {
    }
    //endregion
}
