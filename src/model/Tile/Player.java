package model.Tile;

import model.Game;

import javax.swing.plaf.TreeUI;

public class Player extends Tile {
    private int score = 0;
    private boolean is_alive = true;
    private int current_number_of_bomb = 0;
    private int max_number_of_bombs = 5;
    private int power_of_bombs = 2;

    public Player(String visual) {
        super(0, 0);
        this.x = 0;
        this.y = 0;
        this.destructible = true;
        this.visual = visual;
    }

    //region >> movement
    public void moveUp(){
        this.y -= 1;
    }
    public void moveDown(){
        this.y += 1;
    }
    public void moveRight(){
        this.x += 1;
    }
    public void moveLeft(){
        this.x -= 1;
    }
    //endregion

    //region >> bomb

    private Bomb generateBomb(){
        return new Bomb(this.x,this.y, this);
    }
    public Bomb putBomb(){
        //if there is not a bomb in the same cell, player can put new bomb
        if(isBombPlaceable()){
            Bomb newBomb = generateBomb();
            Game.bombs.add(newBomb);
            current_number_of_bomb++;
            return newBomb;
        }
        return null;
    }

    private boolean isABombAtMyFeet(){
        return !Game.map.getLayers().get("Bombs").getTiles().get(Game.map.getSize()*this.y + x).getVisual().equals("Empty.png");
    }
    public boolean isBombPlaceable(){
        return !isABombAtMyFeet() && (current_number_of_bomb < max_number_of_bombs);
    }
    //endregion


    //region >> getter/setter
    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }

    public boolean isAlive() {
        return is_alive;
    }
    public void setAlive(boolean is_alive) {
        this.is_alive = is_alive;
    }

    public int getNumber_of_bombs() {
        return max_number_of_bombs;
    }
    public void setNumber_of_bombs(int number_of_bombs) {
        this.max_number_of_bombs = number_of_bombs;
    }

    public void setPower_of_bombs(int power_of_bombs) {
        this.power_of_bombs = power_of_bombs;
    }
    public int getPower_of_bombs() {
        return power_of_bombs;
    }

    public void setCurrent_number_of_bomb(int current_number_of_bomb){
        this.current_number_of_bomb = current_number_of_bomb;
    }
    public int getCurrent_number_of_bomb() {
        return current_number_of_bomb;
    }
    //endregion
}