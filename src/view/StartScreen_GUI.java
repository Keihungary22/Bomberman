package view;

import model.*;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen_GUI extends JFrame implements ActionListener {
    //region >> private attributes
    private JPanel MainPanel;
    private JButton btn_r1;
    private JButton btn_r2;
    private JButton btn_r3;
    private JButton btn_m1;
    private JButton btn_m2;
    private JButton btn_m3;
    private JButton btn_p2;
    private JButton btn_p3;
    private JButton btn_start;
    private JButton btn_exit;
    private JLabel TitleLabel;
    private JTextArea number_of_round;
    private JTextArea number_of_player;
    private JTextArea type_of_map;
    //endregion

    private void update_config_view(){
        number_of_player.setText(String.valueOf(Game.number_of_players));
        number_of_round.setText(String.valueOf(Game.number_of_rounds));
        type_of_map.setText(Game.map.getType());
    }

    public StartScreen_GUI(){

        //region >> change font size and type of Title (BOMBERMAN!)
        Font font = new Font("Arial", Font.BOLD, 24);
        TitleLabel.setFont(font);
        //endregion

        //region >> Form button groups to control input.
        ButtonGroup btn_group_round = new ButtonGroup();
        btn_group_round.add(btn_r1);
        btn_group_round.add(btn_r2);
        btn_group_round.add(btn_r3);

        ButtonGroup btn_group_player = new ButtonGroup();
        btn_group_player.add(btn_p2);
        btn_group_player.add(btn_p3);

        ButtonGroup btn_group_map = new ButtonGroup();
        btn_group_player.add(btn_m1);
        btn_group_player.add(btn_m2);
        btn_group_player.add(btn_m3);
        //endregion

        //region >> Register button action listeners
        btn_p2.addActionListener(this);
        btn_p3.addActionListener(this);
        btn_m1.addActionListener(this);
        btn_m2.addActionListener(this);
        btn_m3.addActionListener(this);
        btn_r1.addActionListener(this);
        btn_r2.addActionListener(this);
        btn_r3.addActionListener(this);
        btn_start.addActionListener(this);
        btn_exit.addActionListener(this);
        //endregion

        //region >> Eliminate the focus function of all buttons. (Not important)
        btn_p2.setFocusable(false);
        btn_p3.setFocusable(false);
        btn_m1.setFocusable(false);
        btn_m2.setFocusable(false);
        btn_m3.setFocusable(false);
        btn_r1.setFocusable(false);
        btn_r2.setFocusable(false);
        btn_r3.setFocusable(false);
        btn_start.setFocusable(false);
        btn_exit.setFocusable(false);
        //endregion


        //region >> Game refresh
        Game.RefreshMode();
        this.update_config_view();
        //endregion

        setTitle("BOMBERMAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The method to set the behavior when the JFrame window is closed by user

//        //region >>  Set Look and feel
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //endregion

        this.setContentPane(this.MainPanel);
        this.setSize(1000, 700);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_m1) {
            Game.map = new Map("SmallMap");
        } else if (e.getSource() == btn_m2) {
            Game.map = new Map("MediumMap");
        } else if (e.getSource() == btn_m3) {
            Game.map = new Map("LargeMap");
        }

        if (e.getSource() == btn_p2) {
            Game.number_of_players = 2;
        } else if (e.getSource() == btn_p3) {
            Game.number_of_players = 3;
        }

        if (e.getSource() == btn_r1) {
            Game.number_of_rounds = 1;
        } else if (e.getSource() == btn_r2) {
            Game.number_of_rounds = 2;
        } else if (e.getSource() == btn_r3) {
            Game.number_of_rounds = 3;
        }

        if(e.getSource() == btn_start){
            this.dispose();
            while(Game.players.size() < 3){
                while(true){
                    Player player = new Player();
                    if(!Game.players.contains(player)){
                        Game.players.add(player);
                        break;
                    }
                }
            }
            new GameScreen_GUI();
        }

        if (e.getSource() == btn_exit) {
            System.exit(0);
        }

        this.update_config_view();
    }
}
