package Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import ld35.Game;

public class MenuScene extends Scene {

    public Font font, fontL, fontS;
    public String title, btnNewGame, btnSettings, author;
    public BufferedImage background;
    public int selectedItem = 0;
    
    public MenuScene(int width, int height, Game game){
        super(width, height, game);
        
        try{
            URL url = this.getClass().getResource("/fonts/CantoraOne-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
            this.font = this.font.deriveFont(32.0f);
            this.fontL = this.font.deriveFont(52.0f);
            this.fontS = this.font.deriveFont(18.0f);
            
            url = this.getClass().getResource("/backgroundshort.png");
            this.background = ImageIO.read(url);
            
        }catch(FontFormatException|IOException e){
            e.printStackTrace();
        }
        
        this.title = "Mutashift";
        this.btnNewGame = "New Game";
        this.btnSettings = "Settings";
        this.author = "Game made in 48h by Afendar ( Mickaël )";
    }
    
    @Override
    public Scene update() {
        
        this.processHover();
        
        return this.processClick();
    }

    @Override
    public void render(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.drawImage(this.background, 0, 0, null);
        
        FontMetrics fm = g.getFontMetrics(this.fontL);
        int titleW = fm.stringWidth(this.title);
        g.setFont(this.fontL);
        g.setColor(Color.BLACK);
        g.drawString(this.title, (this.width - titleW) / 2, 100);
        
        if(this.selectedItem == 1){
            g.setColor(Color.RED);
        }
        else{
            g.setColor(Color.BLACK);
        }
        g.setFont(this.font);
        fm = g.getFontMetrics(this.font);
        int btnNewGameW = fm.stringWidth(this.btnNewGame);
        g.drawString(this.btnNewGame, (this.width - btnNewGameW)/2, 320);
        
        if(this.selectedItem == 2){
            g.setColor(Color.RED);
        }
        else{
            g.setColor(Color.BLACK);
        }
        int btnSettingsW = fm.stringWidth(this.btnSettings);
        g.drawString(this.btnSettings, (this.width - btnSettingsW)/2, 400);
        
        g.setFont(this.fontS);
        fm = g.getFontMetrics(this.fontS);
        g.setColor(Color.BLACK);
        int authorW = fm.stringWidth(this.author);
        g.drawString(this.author, (this.width - authorW)/2, 520);
    }
    
    public void processHover(){
        
        int mouseX = this.game.listener.mouseX;
        int mouseY = this.game.listener.mouseY;
        
        if(mouseX > (this.width - 147)/2 &&
            mouseX < ((this.width - 147)/2) + 147 &&
            mouseY > 290 &&
            mouseY < 320){
            this.selectedItem = 1;
        }
        else if(mouseX > (this.width - 110)/2 &&
            mouseX < ((this.width - 110)/2) + 110 &&
            mouseY > 370 &&
            mouseY < 400){
        
            this.selectedItem = 2;
        }
        else{
            this.selectedItem = 0;
        }
    }
    
    public Scene processClick(){
        
        Scene currentScene = this;
        
        if(this.game.listener.mousePressed && this.game.listener.mouseClickCount == 1){
            switch(this.selectedItem){
                case 1:
                    currentScene = new GameScene(this.width, this.height, this.game);
                    break;
                case 2:
                    currentScene = new SettingsScene(this.width, this.height, this.game);
                    break;
            }
        }
        
        return currentScene;
    }
}
