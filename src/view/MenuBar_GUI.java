package view;

import model.Game;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MenuBar_GUI extends JFrame implements ActionListener{
    private JPanel MainPanel;
    private final JFrame gameScreenFrame;

    private JLabel CurrentRound;
    private JLabel ElapsedTime;
    private JLabel TypeOfMap;
    private JPanel Players;
    private JButton btn_continue;
    private JButton btn_end;

    public MenuBar_GUI(GameScreen_GUI gameScreenFrame) {
        //region >> Set game status style
        CurrentRound.setText("Round " + String.valueOf(Game.current_round));
        CurrentRound.setFont(new Font("Courier New", Font.PLAIN, 50));
        CurrentRound.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        CurrentRound.setOpaque(true);
        CurrentRound.setBackground(Color.WHITE);

        ElapsedTime.setText("Time:" + String.valueOf(60)); // gameScreenFrame.ElapsedTime is needed instead of 60
        ElapsedTime.setFont(new Font("Courier New", Font.PLAIN, 50));
        ElapsedTime.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        ElapsedTime.setOpaque(true);
        ElapsedTime.setBackground(Color.WHITE);

        TypeOfMap.setText(Game.map.getType());
        TypeOfMap.setFont(new Font("Courier New", Font.PLAIN, 50));
        TypeOfMap.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        TypeOfMap.setOpaque(true);
        TypeOfMap.setBackground(Color.WHITE);
        //endregion

        //region >> Register button action listeners and set button style
        btn_end.addActionListener(this);
        btn_end.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn_end.setPreferredSize(new Dimension(400, 100));
        btn_end.setFont(new Font("Courier New", Font.BOLD, 50));
        btn_end.setBackground(new Color(255,0,0,255));

        btn_continue.addActionListener(this);
        btn_continue.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn_continue.setFont(new Font("Courier New", Font.BOLD, 50));
        btn_continue.setPreferredSize(new Dimension(400, 100));
        btn_continue.setBackground(new Color(71,212,90,255));
        //endregion

        //region >> Display player statuses
        Players.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 150));

        for (int i = 1; i <= Game.number_of_players; i++) {
            JLabel playerLabel = new JLabel();
            playerLabel.setText("Player " + i + ": " + Game.players.get(i-1).getScore());
            playerLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
            playerLabel.setPreferredSize(new Dimension(400, 200));
            playerLabel.setOpaque(true);
            Color color = switch (i) {
                case 1 -> new Color(246,198,173,255);
                case 2 -> new Color(229,158,221,255);
                default -> new Color(180,229,162,255);
            };
            playerLabel.setBackground(color);
            playerLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel playerPanel = new JPanel();
            playerPanel.add(playerLabel);

            int radius = 3;
            Border paddingBorder = new MatteBorder(radius, radius, radius, radius, new Color(193,229,245,255));
            Border marginBorder = new MatteBorder(radius, radius, radius, radius, new Color(193,229,245,255));
            playerPanel.setBorder(new CompoundBorder(marginBorder, paddingBorder));
            playerPanel.setBackground(new Color(0, 0, 0));

            Players.add(playerPanel);
        }
        //endregion

        this.gameScreenFrame = gameScreenFrame;
        setTitle("BOMBERMAN MENUBAR");
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
