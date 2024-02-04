package models;

import java.awt.event.KeyEvent;

public class HeroModel {
    private int speed=10;
    private int x;
    private int y;
    public  boolean moveRight=false;
    public  boolean moveLeft=false;
    public  boolean isUpTapped=false;
    public  boolean isDownTapped=false;

    public HeroModel(int x, int y){
        this.x=x;
        this.y=y;
    }




    public void onTapped(KeyEvent keyEvent){
        int key = keyEvent.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT -> moveLeft=true;
            case KeyEvent.VK_RIGHT -> moveRight=true;
            case KeyEvent.VK_UP -> isUpTapped=true;
            case KeyEvent.VK_DOWN -> isDownTapped=true;
        }
    }

    public void onTapRelased(KeyEvent keyEvent){
        int key = keyEvent.getKeyCode();

        switch (key){
            case KeyEvent.VK_LEFT -> moveLeft=false;
            case KeyEvent.VK_RIGHT -> moveRight=false;
            case KeyEvent.VK_UP -> isUpTapped=false;
            case KeyEvent.VK_DOWN -> isDownTapped=false;
        }
    }

    public void incrementSpeed(){
        speed+=1;
    }

    public void decrementSpeed(){
        speed-=1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }
}
