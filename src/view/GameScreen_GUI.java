package view;

import model.*;
import model.EventListener.BombExplodeListener;
import model.EventListener.PlayerDieListener;
import model.EventListener.PlayerGetTreasureListener;
import model.EventListener.PlayerStatusChangeListener;
import model.Layer.Layer;
import model.Tile.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen_GUI extends JFrame implements ActionListener, KeyListener, BombExplodeListener, PlayerDieListener, PlayerStatusChangeListener, PlayerGetTreasureListener {
    private JPanel MainPanel;
    private JTextArea ElapsedTime;
    private JTextArea CurrentRound;
    private JButton MenuButton;
    private JButton FinishButton;
    private JPanel GameBoard;
    private JPanel Player3StatusPanel;
    private JLabel Player1Image;
    private JLabel Player3Image;
    private JLabel Player2Image;
    private JPanel Player1Status;
    private JPanel Player2Status;
    private JPanel Player3Status;
    private JLabel Player1Ghost;
    private JLabel Player2Ghost;
    private JLabel Player3Ghost;
    private JLabel Player1RollerSkate;
    private JLabel Player2RollerSkate;
    private JLabel Player3RollerSkate;
    private JLabel Player1Obstacle;
    private JLabel Player2Obstacle;
    private JLabel Player3Obstacle;
    private JLabel Player1Detonator;
    private JLabel Player2Detonator;
    private JLabel Player3Detonator;
    private JLabel Player1Invincibility;
    private JLabel Player2Invincibility;
    private JLabel Player3Invincibility;
    private JPanel Player1StatusPanel;
    private JPanel Player2StatusPanel;
    private final JLayeredPane LayeredPane;
    private final Timer timer = new Timer();
    private int short_time = NormalBomb.time_until_explode;

    //constructor
    public GameScreen_GUI() {
        Game.musicPlayer.battleMusicStart();
        Game.refreshForRound();
        LayeredPane = new JLayeredPane();
        initPlayerStatusImage();
//
        //region >> Register button action listeners
        MenuButton.addActionListener(this);
        FinishButton.addActionListener(this);
        //endregion

        //region >> add event listener for keyboard input
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        //endregion

        //region >> add event listener for PlayerDieListener
        for(Player player : Game.players){
            player.setPlayerDieListener(this);
            player.setPlayerStatusChangeListener(this);
            player.setPlayerGetTreasureListener(this);
        }
        //endregion

        //region >> initiate GUI for game screen
        setTitle("BOMBERMAN");
        CurrentRound.setText(String.valueOf(Game.current_round));
        initPlayersPositions();//Initiate players position and put them on the map for each round
        Game.map.updateMap();//Update map class
        GenerateGameBoard(); //based on the map class, initiate JPanel of the game board
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.MainPanel);
        this.setSize(1200, 1200);
        //endregion

//        Game.map.getLayers().get("Bombs").update();

        this.setVisible(true);
    }

    //region >> public method
    public void PlayerStatusImageAdd(int player_id, TreasureType treasure_type) throws Exception {
        String image_path = switch(treasure_type){
            case BOMB_POWER_UP -> "assets/item_bomb_power_up.png";
            case BOMB_INCREASE -> "assets/item_bomb_increase.png";
            case INVINCIBILITY -> "assets/item_invincibility.png";
            case ROLLERSKATE -> "assets/item_roller_skate.png";
            case OBSTACLE -> "assets/item_obstacle.png";
            case GHOST -> "assets/item_ghost.png";
            case DETONATOR -> "assets/item_detonator.png";
        };

        try{
            JLabel target_label = getTargetStatusLabel(player_id, treasure_type);
            target_label.setIcon(new ImageIcon(image_path));
        }
        catch (Exception e){
            System.err.println("error: " + e.getMessage());
        }
    }
    public void PlayerStatusImageRemove(int player_id, TreasureType treasure_type){
        try{
            JLabel target_label = getTargetStatusLabel(player_id, treasure_type);
            target_label.setIcon(null);
        }
        catch (Exception e){
            System.err.println("error: " + e.getMessage());
        }
    }
    //endregion


    //region >> private functions
    private void GenerateGameBoard(){
        int size = Game.map.getSize()*30;
        LayeredPane.setPreferredSize(new Dimension(size, size));

        //region >> add layers into LayeredPane
        LayeredPane.add(Game.map.getLayers().get("Background").getLayer(), JLayeredPane.DEFAULT_LAYER, 1);
        LayeredPane.add(Game.map.getLayers().get("Decoration").getLayer(), JLayeredPane.DEFAULT_LAYER, 0);
        LayeredPane.add(Game.map.getLayers().get("Bombs").getLayer(), JLayeredPane.PALETTE_LAYER, 2);
        LayeredPane.add(Game.map.getLayers().get("Objects").getLayer(), JLayeredPane.PALETTE_LAYER, 1);
        LayeredPane.add(Game.map.getLayers().get("Players").getLayer(), JLayeredPane.PALETTE_LAYER, 0);
        //endregion

        // add LayeredPane in GameBoard JPane
        GameBoard.setLayout(new BorderLayout());
        GameBoard.add(LayeredPane);
    }
    private void initPlayerStatusImage(){
        //region >> display player image
        ImageIcon player1Image = new ImageIcon("assets/player1.png");
        Player1Image.setIcon(player1Image);
        ImageIcon player2Image = new ImageIcon("assets/player2.png");
        Player2Image.setIcon(player2Image);
        if(Game.number_of_players == 3){
            ImageIcon player3Image = new ImageIcon("assets/player3.png");
            Player3Image.setIcon(player3Image);
        }else{
            Player3StatusPanel.setVisible(false);
        }
        //endregion
    }
    private JLabel getTargetStatusLabel(int player_id, TreasureType treasure_type) throws Exception {
        JLabel target_label;
        if(player_id == 1){
            target_label = switch (treasure_type){
                case GHOST -> Player1Ghost;
                case ROLLERSKATE -> Player1RollerSkate;
                case OBSTACLE -> Player1Obstacle;
                case INVINCIBILITY -> Player1Invincibility;
                case DETONATOR -> Player1Detonator;
                default -> throw new Exception("Invalid Treasure type : " + treasure_type);
            };
        }else if(player_id == 2){
            target_label = switch (treasure_type){
                case GHOST -> Player2Ghost;
                case ROLLERSKATE -> Player2RollerSkate;
                case OBSTACLE -> Player2Obstacle;
                case INVINCIBILITY -> Player2Invincibility;
                case DETONATOR -> Player2Detonator;
                default -> throw new Exception("Invalid Treasure type : " + treasure_type);
            };
        } else if (player_id == 3) {
            target_label = switch (treasure_type){
                case GHOST -> Player3Ghost;
                case ROLLERSKATE -> Player3RollerSkate;
                case OBSTACLE -> Player3Obstacle;
                case INVINCIBILITY -> Player3Invincibility;
                case DETONATOR -> Player3Detonator;
                default -> throw new Exception("Invalid Treasure type : " + treasure_type);
            };
        }else{
            throw new Exception("Invalid player ID: " + player_id);
        }

        return target_label;
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
    private void playerMove(int player_id, String direction) throws Exception {
        if(Game.players.get(player_id).move(direction)){
            Game.map.getLayers().get("Players").update();
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
    private void short_timer_start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (short_time <= 0) {
                    // short_timeが0になったらGameScreen_GUIを閉じる
                    dispose();
                    timer.cancel();
                    new RoundResultScreen_GUI();
                } else {
                    // short_timeを減らす
                    short_time--;
                }
            }
        };

        // タイマーを1秒ごとに実行する
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    private void updateLayer(String layer_name){
        Game.map.getLayers().get(layer_name).update();
        LayeredPane.revalidate();
        LayeredPane.repaint();
    }
    //endregion

    //actions for each button
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == MenuButton) {
            Game.is_paused = true;
            MenuBar_GUI menuBarGUI =  new MenuBar_GUI(this);
            menuBarGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // メニュー画面が閉じた後に、ゲーム画面にフォーカスを戻す
                    requestFocus();
                    Game.is_paused = false;
                }
            });
        }else if(e.getSource() == FinishButton) {
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
        Bomb newBomb;
        switch (e.getKeyCode()) {
            //region >> player1 controller
            case KeyEvent.VK_UP:
                if(Game.players.get(0).isAlive()){
                    try {
                        playerMove(0, "up");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if(Game.players.get(0).isAlive()){
                    try {
                        playerMove(0, "down");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(Game.players.get(0).isAlive()){
                    try {
                        playerMove(0, "right");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if(Game.players.get(0).isAlive()){
                    try {
                        playerMove(0, "left");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_SHIFT:
                if(Game.players.get(0).isAlive()){
                    if(Game.players.get(0).hasDetonatorBomb()){
                        Game.players.get(0).explodeDetonatorBomb();
                    }else if (Game.players.get(0).isBombPlaceable()){
                        newBomb = playerPutBomb(0);
                        newBomb.setBombExplodeListener(this);
                        for(Player player: Game.players){
                            newBomb.setBombExplodeListener(player);
                        }
                    }
                }
                break;
            //endregion

            //region >> player2 controller
            case KeyEvent.VK_W:
                if(Game.players.get(1).isAlive()){
                    try {
                        playerMove(1, "up");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_S:
                if(Game.players.get(1).isAlive()){
                    try {
                        playerMove(1, "down");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_D:
                if(Game.players.get(1).isAlive()){
                    try {
                        playerMove(1, "right");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_A:
                if(Game.players.get(1).isAlive()){
                    try {
                        playerMove(1, "left");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_R:
                if(Game.players.get(1).isAlive()){
                    if(Game.players.get(1).hasDetonatorBomb()){
                        Game.players.get(1).explodeDetonatorBomb();
                    }else if (Game.players.get(1).isBombPlaceable()){
                        newBomb = playerPutBomb(1);
                        newBomb.setBombExplodeListener(this);
                        for(Player player: Game.players){
                            newBomb.setBombExplodeListener(player);
                        }
                    }
                }
                break;
            //endregion

            //region >> player3 controller
            case KeyEvent.VK_U:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive()){
                    try {
                        playerMove(2, "up");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_J:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive()){
                    try {
                        playerMove(2, "down");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_K:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive()){
                    try {
                        playerMove(2, "right");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_H:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive()){
                    try {
                        playerMove(2, "left");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case KeyEvent.VK_O:
                if(Game.players.get(2).isAlive()){
                    if(Game.players.get(2).hasDetonatorBomb()){
                        Game.players.get(2).explodeDetonatorBomb();
                    }else if (Game.players.get(2).isBombPlaceable()){
                        newBomb = playerPutBomb(2);
                        newBomb.setBombExplodeListener(this);
                        for(Player player: Game.players){
                            newBomb.setBombExplodeListener(player);
                        }
                    }
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
    @Override
    public void bombDestroyedBox() {
        Game.map.getLayers().get("Objects").update();
        LayeredPane.revalidate();
        LayeredPane.repaint();
    }
    @Override
    public void playerDie() {
        updateLayer("Players");
        System.out.println(Game.getNumberOfAlivePlayers());
        if(Game.getNumberOfAlivePlayers() == 1){
            short_timer_start();
        }
    }
    @Override
    public void PlayerStatusChanged(int player_id, TreasureType treasure_type) throws Exception {
        PlayerStatusImageAdd(player_id, treasure_type);
        updateLayer("Players");
    }
    @Override
    public void PlayerStatusChangedTimeUp(int player_id, TreasureType treasure_type) throws Exception {
        PlayerStatusImageRemove(player_id, treasure_type);
        updateLayer("Players");
    }
    @Override
    public void PlayerGetTreasure(){
        updateLayer("Objects");
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
