import Services.SoundPlayer.SongManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class MainGameEngine extends JPanel implements ActionListener {
    public RenderEngine renderEngine;
    private int heroX, heroY, view_dx, view_dy;
    private  Timer timer;
    private long startTime=System.currentTimeMillis();



    public MainGameEngine() {
        renderEngine = new RenderEngine(this);
        initVariables();
        initBoard();
        AppServices.MusicPlayer.RepeatMusic(true);
        AppServices.MusicPlayer.playAsync(SongManager.BackgroundMusic);
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
    }

    private void initVariables() {
        timer=new Timer(10,this);
        timer.start();

    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderGame(g);
    }

    private void renderGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (!renderEngine.isGameStart){
            renderEngine.showWelcome(g2d,this);
            return;
        }
        if (renderEngine.isWin){
            renderEngine.showWinView(g2d,this,startTime);
            return;
        }


        renderEngine.setBackgroundImage(g2d);
        renderEngine.drawMath(g2d,this);
        renderEngine.drawScore(g2d,this);
        renderEngine.drawTime(g2d,startTime,this);
        renderEngine.drawAuthors(g2d,this);
        renderEngine.drawHero(g2d,null);

        renderEngine.fruitControl(startTime,this);
        renderEngine.drawFruits(g2d);
        renderEngine.fruitAction();



    }

    @Override
    public void addNotify() {
        super.addNotify();
        initGame();
    }

    private void initGame() {

    }

    private void continueLevel() {
    }

     class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            renderEngine.hero.onTapped(e);

        }

         @Override
         public void keyReleased(KeyEvent e) {
             renderEngine.hero.onTapRelased(e);

         }
     }

}
