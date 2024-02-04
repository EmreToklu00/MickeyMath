
import Services.SoundPlayer.SongManager;
import Services.SoundPlayer.SoundPlayer;
import Utility.Items;
import models.FruitModel;
import models.HeroModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.exit;

public class RenderEngine {

    private  MainGameEngine gameEngine;
    private final AssetManager assetManager;
    private Color defaultTransparentColor = new Color(0, 32, 48, 150);
    Image backgroundImage;
    public HeroModel hero = new HeroModel(400,400);

    private  FruitModel[] fruits= new FruitModel[4];
    private int differenceLimit=65;

    public int mathValue = 0;
    public int userMathValue=0;
    private int userScore=0;


    private Map<String, JButton> buttonMap = new LinkedHashMap<>();
    public double leftint;
    public double rightint;
    public String operator="";

    public boolean isGameStart=false;
    public  String errorMessage="";
    public boolean isWin=false;
    private long endTime;



    public RenderEngine(MainGameEngine engine) {
        this.gameEngine = engine;
        this.assetManager = new AssetManager(gameEngine);
        for (int i =0;i<4;i++){
            fruits[i]= new FruitModel(150+150*i,50,assetManager.fruits[i],Math.random()*0.5+1);
        }
    }

    public void setBackgroundImage(Graphics2D g){
        backgroundImage = assetManager.getBackground();
        g.drawImage(backgroundImage, 0, 0,800,650,null);
    }

    public void drawHero(Graphics2D g2d,ImageObserver observer ) {
        Image currentHero;
        if (userScore==0){
            currentHero = assetManager.getHeroEmpty();
        }
        else {
            currentHero = assetManager.getHeroFull();
        }

        if (hero.isUpTapped && hero.getSpeed()<20 && hero.getSpeed()>0)
        {
            hero.incrementSpeed();
        }
        else if (hero.isDownTapped && hero.getSpeed()>=5 && hero.getSpeed()>0)
        {
            hero.decrementSpeed();
        }

        if (hero.moveRight && currentHero.getWidth(observer)+hero.getX()<=800){
            hero.setX(hero.getX()+hero.getSpeed());
        }
        else if (hero.moveLeft  && hero.getX()>=0){
            hero.setX(hero.getX()-hero.getSpeed());
        }
        g2d.drawImage(currentHero,hero.getX(),hero.getY(),140,206,observer);

    }

    public void drawFruits(Graphics2D g2d) {
        for (int i = 0; i <fruits.length ; i++) {
            g2d.drawImage(fruits[i].fruid.image,fruits[i].x,(int)fruits[i].y,40,40,null);
        }
    }

    public void fruitControl(long startTime,JComponent jc){
        for (int i = 0; i <fruits.length ; i++) {
            if(hero.getY()-fruits[i].y<-10&&(Math.abs(hero.getX()-fruits[i].x)) <= 40 && hero.getY()+50>fruits[i].y){
                userMathValue+=fruits[i].fruid.scoreValue;
                int time = (int)(System.currentTimeMillis()-startTime) / 1000;
                if (time>1){
                    userScore+=(30/time)*userMathValue;
                }
                if (userMathValue == mathValue) {
                    jc.removeAll();
                    isWin=true;
                    if (!isMusicStopped) {
                        AppServices.MusicPlayer.StopMusic();
                        isMusicStopped = true;
                    }
                    AppServices.MusicPlayer.playAsync(SongManager.WinMusic);
                    endTime=System.currentTimeMillis();
                }
                resetFruit(fruits[i]);

            }
            else if ((Math.abs(fruits[i].y-650) <= differenceLimit)) {
                resetFruit(fruits[i]);
            }
        }
    }

    private void resetFruit(FruitModel fruit){
        fruit.y=50;
        fruit.speed=Math.random()*0.5+2;
        fruit.fruid=assetManager.fruits[(int)(Math.random()*4)];
    }

    public void fruitAction(){
        for (int i = 0; i < fruits.length; i++) {
            fruits[i].y+= fruits[i].speed;
        }
    }


    public void drawMath(Graphics2D g2d,JComponent jc) {
        g2d.setColor(defaultTransparentColor);
        g2d.drawRect(0,0,50,25);
        g2d.fillRect(0,0,50,25);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        String mathString = (int)leftint+operator+(int)rightint;
        g2d.drawString(mathString,10,20);

        g2d.setFont(new Font("Arial", Font.PLAIN, 60));
        g2d.drawString(String.valueOf(userMathValue),jc.getWidth()/2,50);
    }

    public void drawScore(Graphics2D g2d,JComponent jc) {
        g2d.setColor(defaultTransparentColor);
        g2d.drawRect(jc.getWidth()-100,0,100,25);
        g2d.fillRect(jc.getWidth()-100,0,100,25);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        String scoreText="Score: "+ userScore;
        int titleTextX = (jc.getWidth() - g2d.getFontMetrics().stringWidth(scoreText)) ;
        g2d.drawString(scoreText,titleTextX,20);
    }

    public void drawTime(Graphics2D g2d,long startTime,JComponent jc) {
        g2d.setColor(defaultTransparentColor);
        g2d.drawRect(jc.getWidth()-100,jc.getHeight()-25,100,25);
        g2d.fillRect(jc.getWidth()-100,jc.getHeight()-25,100,25);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        int time = (int)(System.currentTimeMillis()-startTime) / 1000;
        String scoreText="Time: "+time;
        int titleTextX = (jc.getWidth() - g2d.getFontMetrics().stringWidth(scoreText))-8 ;
        int titleTextY = jc.getHeight()-4;
        g2d.drawString(scoreText,titleTextX,titleTextY);
    }

    public void drawAuthors(Graphics2D g2d,JComponent jc) {
        String[] authors = {"Osman Emre", "Emre", "Berkay", "Oğuzhan","Engin","Neriman","Ömer Faruk"};
        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        int authorY = 50;
        for (String author : authors) {
            int authorX = (jc.getWidth() - g2d.getFontMetrics().stringWidth(author))-8;
            g2d.drawString(author, authorX, authorY);
            authorY += 20;
        }
    }

    public void showWelcome(Graphics2D g2d,JComponent jc) {
        g2d.setColor(Color.black);
        g2d.drawRect(0,0,jc.getWidth(),jc.getHeight());
        g2d.fillRect(0,0,jc.getWidth(),jc.getHeight());


        Image background = assetManager.getBackground();
        g2d.drawImage(background, 0, 0,800,650, null);


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setOpaque(false);

        g2d.drawImage(assetManager.getWelcomePng(),200,450,400,350,null);

        buttonMap.put("Quit To Desktop", Items.Button("Quit To Desktop",false));

        gbc.gridx = 3;
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 10, 1);
        JButton  quitbutton = buttonMap.get("Quit To Desktop");
        panel.add(buttonMap.get("Quit To Desktop"), gbc);


        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 1);
        JTextField leftText = Items.TextField(2);
        setNumericFilter(leftText,1,9);
        leftText.setFont(new Font("Arial", Font.BOLD, 60));
        leftText.setHorizontalAlignment(JTextField.CENTER);
        panel.add(leftText,gbc);

        gbc.gridx = 9;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 1);
        JTextField rightText = Items.TextField(2);
        setNumericFilter(rightText,1,9);
        rightText.setFont(new Font("Arial", Font.BOLD, 60));
        rightText.setHorizontalAlignment(JTextField.CENTER);
        panel.add(rightText,gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 10, 1);
        JButton toplama = Items.Button("",false);
        toplama.setIcon(new ImageIcon("resources/images/math/toplama.png"));

        panel.add(toplama,gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 50, 1);
        JButton cikarma = Items.Button("",false);
        cikarma.setIcon(new ImageIcon("resources/images/math/cikarma.png"));
        panel.add(cikarma,gbc);
        panel.add(Items.Button("",false),gbc);

        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 10, 1);
        JButton carpma = Items.Button("",false);
        carpma.setIcon(new ImageIcon("resources/images/math/carpma.png"));
        panel.add(carpma,gbc);
        panel.add(Items.Button("",false),gbc);

        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 10, 1);
        JButton bolme = Items.Button("",false);
        bolme.setIcon(new ImageIcon("resources/images/math/bolme.png"));
        panel.add(bolme,gbc);
        panel.add(Items.Button("",false),gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.insets = new Insets(0,0,10,1);
        JButton volumeManager = Items.Button("",false);
        volumeManager.setIcon(new ImageIcon("resources/images/others/speaker.png"));
        panel.add(volumeManager,gbc);

        jc.add(panel);

        addButtonClick(quitbutton,"quit",panel);
        addButtonClick(toplama,"+",panel);
        addButtonClick(cikarma,"-",panel);
        addButtonClick(carpma,"*",panel);
        addButtonClick(bolme,"/",panel);
        addButtonClick(volumeManager,"volume",panel);

        tests(leftText,"left");
        tests(rightText,"right");

        g2d.setColor(Color.red);
        g2d.setFont(new Font("Arial", Font.PLAIN, 35));
        g2d.drawString(errorMessage,0,600);

    }


    private void tests(JTextField textField,String job){
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Yazı eklendiğinde yapılacak işlemler
                System.out.println("Yazı Eklendi: " + textField.getText());

                if(job == "left"){
                    leftint = Integer.parseInt(textField.getText());
                }else{
                    rightint = Integer.parseInt(textField.getText());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Yazı silindiğinde yapılacak işlemler
                System.out.println("Yazı Silindi: " + textField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Yazıda değişiklik olduğunda yapılacak işlemler (genellikle stil değişiklikleri)
            }
        });
    }
    private void addButtonClick(JButton button,String job,JPanel panel) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (job == "quit") {
                    exit(0);
                }else if (job == "volume"){

                    if(button.getIcon().toString() == "resources/images/others/speaker.png"){
                        AppServices.MusicPlayer.StopMusic();
                        button.setIcon(new ImageIcon("resources/images/others/speaker-off.png"));
                    } else {
                        AppServices.MusicPlayer.playAsync(SongManager.BackgroundMusic);
                        button.setIcon(new ImageIcon("resources/images/others/speaker.png"));
                    }
                }else{
                    double islem = 0;
                    if (job == "+") {
                        islem = leftint + rightint;
                    }
                    if (job == "-"){
                        islem = leftint - rightint;

                        if(islem == 0){
                            errorMessage="0 Olamaz";
                            return;
                        }
                    }
                    if (job == "*"){
                        islem = leftint * rightint;

                    }
                    if (job == "/"){
                        if( leftint % rightint != 0){
                            errorMessage="Bölünmesi zor işlem";
                            return;
                        }
                        islem = leftint / rightint;
                    }

                    //OYUN BURADA AÇILACAK
                    isGameStart=true;
                    operator=job;
                    mathValue=(int)islem;
                    panel.removeAll();
                }
            }
        });
    }

    private static void setNumericFilter(JTextField textField, int min, int max) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (isNumeric(newText) && isInRange(Integer.parseInt(newText), min, max)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength())
                        + text + fb.getDocument().getText(offset + length,
                        fb.getDocument().getLength() - offset - length);
                if (isNumeric(newText) && isInRange(Integer.parseInt(newText), min, max)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isNumeric(String str) {
                return str.matches("-?\\d+");
            }

            private boolean isInRange(int value, int min, int max) {
                return value >= min && value <= max;
            }
        });
    }

    private JButton button;
    private static boolean isMusicStopped = false;

    public void showWinView(Graphics2D g, JPanel panel,long startTime) {


        Image backgroundImage = assetManager.getWinBackground();
        g.drawImage(backgroundImage, 0, 0, 800, 650, null);

        int rectWidth = 300;
        int rectHeight = 100;
        int rectX = (800 - rectWidth) / 2;
        int rectY = 200;

        // Dikdörtgeni çiz
        g.setColor(new Color(0, 0, 0, 150)); // RGB ve saydamlık (0-255 arası)
        g.fillRect(rectX, rectY, rectWidth, rectHeight);
        // "You Win" metnini çiz
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String youWinText = "You Win";
        int youWinTextX = (800 - g.getFontMetrics().stringWidth(youWinText)) / 2;
        int youWinTextY = 190;
        g.drawString(youWinText, youWinTextX, youWinTextY);

        // Sadece WinMusic çalması için
        // AppServices.MusicPlayer.playAsync(SongManager.WinMusic);
        // Score ve Time değerlerini çiz
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String scoreText = "Score: "+userScore;
        int time = (int)(endTime-startTime) / 1000;

        String timeText = "Time: "+time;
        int textX = (800 - g.getFontMetrics().stringWidth(scoreText)) / 2 - 100;
        int textY = 250;
        g.drawString(scoreText, textX, textY);
        g.drawString(timeText, textX, textY + 30);

        // Buton ekleyin
        if (button == null) {
            button = createButton();
            panel.add(button);
        }
        button.setBounds((800 - 100) / 2, rectY + rectHeight + 10, 100, 40); // Butonun konumunu ve boyutunu ayarlayın

        button.setBackground(new Color(0, 0, 0, 150)); // Siyah renk, 150 saydamlık
        button.setForeground(new Color(255, 255, 255, 200)); // Beyaz renk, 200 saydamlık
        button.setOpaque(true);
        button.setBorderPainted(false); // Kenarlığı kaldır
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // Font'u JButton'e uygula
        button.setFont(buttonFont);
    }

    private JButton createButton() {
        JButton button = new JButton("Kapat");
        button.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit(0);
            }
        });
        return button;
    }

}