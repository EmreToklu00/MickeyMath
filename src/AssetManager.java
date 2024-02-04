import models.FruitValue;

import javax.swing.*;
import java.awt.*;

public class AssetManager {

    private Image background;
    private Image hero;
    private Image heroEmpty;
    private Image heroFull;
    public FruitValue[]fruits=new FruitValue[4];
    private Image winBackground;
    private Image welcomePng;




    public AssetManager(MainGameEngine gameEngine) {
        loadImages();

    }

    private void loadImages() {
        String prePath = "resources/images/";
        background= new ImageIcon(prePath+"wallpapers/main-wallpaper.jpg").getImage();
        hero= new ImageIcon(prePath+"/character/mickey-mouse.png").getImage();
        heroEmpty= new ImageIcon(prePath+"/character/mickey-mouse-bos.png").getImage();
        heroFull= new ImageIcon(prePath+"/character/mickey-mouse-dolu.png").getImage();

        fruits[0]=new FruitValue(new ImageIcon(prePath+"/fruits/banana.png").getImage(),-1);
        fruits[1]=new FruitValue(new ImageIcon(prePath+"/fruits/armut.png").getImage(),1);
        fruits[2]=new FruitValue(new ImageIcon(prePath+"/fruits/cilek.png").getImage(),-2);
        fruits[3]=new FruitValue(new ImageIcon(prePath+"/fruits/elma.png").getImage(),2);
        winBackground= new ImageIcon(prePath+"wallpapers/winmouse.gif").getImage();
        welcomePng = new ImageIcon(prePath+"wallpapers/welcome.png").getImage();


    }

    public Image getBackground() {
        return background;
    }

    public Image getHero() {
        return hero;
    }

    public Image getHeroEmpty() {
        return heroEmpty;
    }

    public Image getHeroFull() {
        return heroFull;
    }

    public Image getWinBackground() {
        return winBackground;
    }
    public Image getWelcomePng() {
        return welcomePng;
    }
}