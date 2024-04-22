package view;

import model.*;
import model.EventListener.BombExplodeListener;
import model.EventListener.PlayerDieListener;
import model.Tile.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen_GUI extends JFrame implements ActionListener, KeyListener, BombExplodeListener, PlayerDieListener {
    private JPanel MainPanel;
    private JTextArea ElapsedTime;
    private JTextArea CurrentRound;
    private JButton btn_menu;
    private JButton btn_finish;
    private JPanel GameBoard;
    private final JLayeredPane LayeredPane;
    private final Timer timer = new Timer();
    private int short_time = Bomb.time_until_explode;

    //constructor
    public GameScreen_GUI() {
        Game.musicPlayer.battleMusicStart();
        Game.refreshForRound();

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

        //region >> add event listener for PlayerDieListener
        for(Player player : Game.players){
            player.setPlayerDieListener(this);
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

        //region >> These codes are just for in case of displaying new stage correctly when new round starts
        Game.refreshForRound();
        Game.map.getLayers().get("Bombs").update();
        //endregion

        this.setVisible(true);
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
        if(Game.players.get(player_id).move(direction)){
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
        Bomb newBomb;
        switch (e.getKeyCode()) {
            //region >> player1 controller
            case KeyEvent.VK_UP:
                if(Game.players.get(0).isAlive()){
                    playerMove(0, "up");
                }
                break;
            case KeyEvent.VK_DOWN:
                if(Game.players.get(0).isAlive()){
                    playerMove(0, "down");
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(Game.players.get(0).isAlive()){
                    playerMove(0, "right");
                }
                break;
            case KeyEvent.VK_LEFT:
                if(Game.players.get(0).isAlive()){
                    playerMove(0, "left");
                }
                break;
            case KeyEvent.VK_SHIFT:
                if(Game.players.get(0).isAlive() && Game.players.get(0).isBombPlaceable()) {
                    newBomb = playerPutBomb(0);
                    newBomb.setBombExplodeListener(this);
                    for(Player player: Game.players){
                        newBomb.setBombExplodeListener(player);
                    }
                }
                break;
            //endregion

            //region >> player2 controller
            case KeyEvent.VK_W:
                if(Game.players.get(1).isAlive()){
                    playerMove(1, "up");
                }
                break;
            case KeyEvent.VK_S:
                if(Game.players.get(1).isAlive()){
                    playerMove(1, "down");
                }
                break;
            case KeyEvent.VK_D:
                if(Game.players.get(1).isAlive()){
                    playerMove(1, "right");
                }
                break;
            case KeyEvent.VK_A:
                if(Game.players.get(1).isAlive()){
                    playerMove(1, "left");
                }
                break;
            case KeyEvent.VK_R:
                if(Game.players.get(1).isAlive() && Game.players.get(1).isBombPlaceable()) {
                    newBomb = playerPutBomb(1);
                    newBomb.setBombExplodeListener(this);
                    for(Player player: Game.players){
                        newBomb.setBombExplodeListener(player);
                    }
                }
                break;
            //endregion

            //region >> player3 controller
            case KeyEvent.VK_U:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive()){
                    playerMove(2, "up");
                }
                break;
            case KeyEvent.VK_J:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive()){
                    playerMove(2, "down");
                }
                break;
            case KeyEvent.VK_K:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive()){
                    playerMove(2, "right");
                }
                break;
            case KeyEvent.VK_H:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive()){
                    playerMove(2, "left");
                }
                break;
            case KeyEvent.VK_O:
                if(Game.number_of_players == 3 && Game.players.get(2).isAlive() && Game.players.get(2).isBombPlaceable()) {
                    newBomb = playerPutBomb(2);
                    newBomb.setBombExplodeListener(this);
                    for(Player player: Game.players){
                        newBomb.setBombExplodeListener(player);
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
        Game.map.getLayers().get("Objects").update();
        LayeredPane.revalidate();
        LayeredPane.repaint();
        System.out.println(Game.getNumberOfAlivePlayers());
        if(Game.getNumberOfAlivePlayers() == 1){
            short_timer_start();
        }
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
