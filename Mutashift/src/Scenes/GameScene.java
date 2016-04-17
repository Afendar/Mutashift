package Scenes;

import Audio.Sound;
import Entities.Mob;
import Entities.Player;
import Level.Level;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import ld35.Camera;
import ld35.Configs;
import ld35.Defines;
import ld35.Game;
import ld35.TimerThread;

public class GameScene extends Scene {
    
    public Player player;
    public Level level;
    public Mob[] mobs;
    public Camera view;
    public int secondes = 0;
    public Font font;
    public String lifeTxt, controls, ctrlJump, ctrlChange, ctrlLeft, ctrlUp, winConditions, spiderRad, hiddenRad, towerTxt, spider2, startTxt;
    public Color mutationsTypes[] = {Defines.HUMAN, Defines.SPIDER, Defines.HULK, Defines.TORCH, Defines.HIDDEN };
    public BufferedImage tileset, lifebarContainer, lifebar, radiationsContainer, background, startBackground;
    public int[][] coordsRadiationsBar;
    public int backgroundX = 0;
    public int alpha, maxalpha;
    
    public boolean displayStart, displayEnd;
    
    public GameScene(int width, int height, Game game){
        super(width, height, game);
        
        this.level = new Level();
        this.view = new Camera(0, 0, width, height, this.level);
        this.player = new Player(1 * Defines.TILE_SIZE, 26 * Defines.TILE_SIZE, this.game.listener);
        this.level.add(this.player);
        
        try{
            URL url = this.getClass().getResource("/fonts/CantoraOne-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
            this.font = this.font.deriveFont(22.0f);
            
            url = this.getClass().getResource("/gui.png");
            this.tileset = ImageIO.read(url);
            
            url = this.getClass().getResource("/backgroundshort.png");
            this.background = ImageIO.read(url);
            
            url = this.getClass().getResource("/start.png");
            this.startBackground = ImageIO.read(url);
        }
        catch(FontFormatException|IOException e){
            e.printStackTrace();
        }
        
        this.alpha = 255;
        this.maxalpha = 128;
        
        this.lifebarContainer = this.tileset.getSubimage(0, 0, 205, 25);
        this.lifebar = this.tileset.getSubimage(0, 26, 200, 20);
        this.radiationsContainer = this.tileset.getSubimage(0, 46, 22, 102);
        
        int[][] coords = {
            {0, 0},
            {22, 47},
            {38 ,47},
            {70, 47},
            {54, 47}
        };
        
        this.coordsRadiationsBar = coords;
        
        this.displayStart = true;
        this.displayEnd = false;
        
        this.controls = "Controls";
        this.ctrlChange = "Press " + KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Shift"))) + " to change your mophology if you have competence.";
        this.ctrlJump = "Press " + KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Jump"))) + " to jump";
        this.ctrlUp = "Press " + KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Climb"))) + " or " + KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Down"))) + " to move up/down in spider mode";
        this.ctrlLeft = "Press " + KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Left"))) + " or " + KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Right"))) + " to move left/right";
        this.winConditions = "Collect all of cois to win";
        this.spider2 = "To clim wall press left or right key and move up or down key";
        this.spiderRad = "Get spider power for 30s";
        this.hiddenRad = "You are not visible for tower during 30s";
        this.towerTxt = "Tower want to hit you";
        this.startTxt = "Press enter to play";
        
        Sound.music.play();
    }

    @Override
    public Scene update() {
        if(this.player.inLife()){
            
            if(this.player.hasWin){
                this.displayEnd = true;
            }
            else{
                if(this.displayStart){
                    if(this.game.listener.next.enabled){
                        this.displayStart = false;
                        this.alpha = 0;
                    }
                }
                else{
                    this.level.update();
                    this.player.update();

                    if(this.player.getLife() > 0){
                        this.lifebar = this.tileset.getSubimage(0, 26, 2*this.player.getLife(), 20);
                    }

                    if(this.game.timer && (int)((double)(TimerThread.MILLI - this.game.timeF)/1000) > this.secondes){
                        this.secondes = (int)((double)(TimerThread.MILLI - this.game.timeF)/1000);
                        this.player.updateMutates();
                    }

                    this.view.update(this.player);
                }
            }
        }
        return this;
    }

    @Override
    public void render(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(this.displayStart){
            
            g.drawImage(this.startBackground, 0, 0, null);
            
            g.setColor(Color.BLACK);
            g.setFont(this.font);
            g.drawString(this.controls, 80, 80);
            g.drawString(this.ctrlJump, 80, 150);
            g.drawString(this.ctrlLeft, 80, 120);
            g.drawString(this.ctrlChange, 80, 180);
            
            g.drawString(this.spiderRad, 130, 220);
            g.drawString(this.ctrlUp, 130, 250);
            g.drawString(this.spider2, 130, 280);
            
            g.drawString(this.hiddenRad, 130, 330);
            
            g.drawString(this.towerTxt, 130, 400);
            
            g.drawString(this.winConditions, 130, 470);
            
            FontMetrics fm = g.getFontMetrics(this.font);
            int startTxtW = fm.stringWidth(this.startTxt);
            g.drawString(this.startTxt, (this.width - startTxtW) / 2, 520);
        }
        else{
            g.drawImage(this.background, this.backgroundX, 0, null);

            g2d.translate(-this.view.x, -this.view.y);

            this.level.render(g);

            g2d.translate(this.view.x, this.view.y);

            this.renderGui(g);

            if(!this.player.inLife()){
                g.setColor(new Color(255, 0, 0, 165));
                g.fillRect(0, 0, this.width, this.height);
                g.setFont(this.font);
                FontMetrics fm = g.getFontMetrics(this.font);
                String deathTxt = "You are dead !";
                int deathTxtW = fm.stringWidth(deathTxt);
                g.setColor(Color.BLACK);
                g.drawString(deathTxt, (this.width-deathTxtW)/2 , (this.height-9)/2);
            }
            if(this.displayEnd){
                g.setColor(new Color(255, 255, 255, 165));
                g.fillRect(0, 0, this.width, this.height);
                g.setFont(this.font);
                FontMetrics fm = g.getFontMetrics(this.font);
                String winTxt = "You win !";
                String thanks = "Thanks for playing :)";
                int winTxtW = fm.stringWidth(winTxt);
                g.setColor(Color.BLACK);
                g.drawString(winTxt, (this.width-winTxtW)/2 , (this.height-9)/2);
            
                int thanksW = fm.stringWidth(thanks);
                g.drawString(thanks, (this.width - thanksW)/2, 350);
            }
        }
    }
    
    public void renderGui(Graphics g){

        if(this.player.getLife() > 0){
            g.drawImage(this.lifebar, 13, 13, null);
        }
        g.drawImage(this.lifebarContainer, 10, 10, null);
        
        g.setFont(this.font);
        g.setColor(Color.BLACK);
        String score = "score: "+this.player.score;
        g.drawString(score, 10, 70);
        
        String remainingCoins = "Remaining coins :"+this.level.nbCoins;
        g.drawString(remainingCoins, 10, 100);
        
        int posX = 590;
        int offset = 40;
        
        for(int i=1;i<this.player.mutationsAchievements.length;i++){
            if(this.player.mutationsAchievements[i]){
                BufferedImage bar = this.tileset.getSubimage(this.coordsRadiationsBar[i][0],
                                                                this.coordsRadiationsBar[i][1], 16, (this.player.getTmeMutation(i) * 100)/Defines.TOTAL_TIME_MUTATION);
                g.drawImage(bar, posX + 3 + (offset * i), 11 + (100-(this.player.getTmeMutation(i) * 100) / Defines.TOTAL_TIME_MUTATION), null);
            }
            g.drawImage(this.radiationsContainer, posX + (offset * i), 10, null);
        }
    }
}
