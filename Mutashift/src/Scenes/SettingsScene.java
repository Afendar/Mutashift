package Scenes;

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
import java.util.ArrayList;
import javax.imageio.ImageIO;
import ld35.Configs;
import ld35.Game;
import ld35.OptionButton;

public class SettingsScene extends Scene {

    public Font font, fontL;
    public String title, btnBack;
    public BufferedImage background;
    public int selectedItem = 0;
    public ArrayList<OptionButton> optionsBtns = new ArrayList<>();
    
    public SettingsScene(int width, int height, Game game){
        
        super(width, height, game);
        
        try{
            URL url = this.getClass().getResource("/fonts/CantoraOne-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
            this.font = this.font.deriveFont(32.0f);
            this.fontL = this.font.deriveFont(52.0f);
            
            url = this.getClass().getResource("/backgroundshort.png");
            this.background = ImageIO.read(url);
            
        }catch(FontFormatException|IOException e){
            e.printStackTrace();
        }
        
        OptionButton btn1 = new OptionButton(
                KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Left"))),
                "Left",
                250,
                200
        );
        btn1.setFont(font);
        this.optionsBtns.add(btn1);
        OptionButton btn2 = new OptionButton(
                KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Right"))),
                "Right",
                250,
                250
        );
        btn2.setFont(font);
        this.optionsBtns.add(btn2);
        OptionButton btn3 = new OptionButton(
                KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Jump"))),
                "Jump",
                250,
                300
        );
        btn3.setFont(font);
        this.optionsBtns.add(btn3);
        OptionButton btn4 = new OptionButton(
                KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Shift"))),
                "Shift",
                250,
                350
        );
        btn4.setFont(font);
        this.optionsBtns.add(btn4);
        OptionButton btn5 = new OptionButton(
                KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Down"))),
                "Down",
                250,
                400
        );
        btn5.setFont(font);
        this.optionsBtns.add(btn5);
        OptionButton btn6 = new OptionButton(
                KeyEvent.getKeyText(Integer.parseInt(Configs.getInstance().getConfigValue("Climb"))),
                "Up",
                250,
                450
        );
        btn6.setFont(font);
        this.optionsBtns.add(btn6);
        
        this.title = "Settings";
        this.btnBack = "Back to main";
    }
    
    @Override
    public Scene update() {
        this.processHover();
        
        if(this.game.listener.e != null){
            this.processKey(this.game.listener.e);
        }
        
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
        
        this.renderControlsSettings(g);
        
        if(this.selectedItem == 1){
            g.setColor(Color.RED);
        }
        else{
            g.setColor(Color.BLACK);
        }
        g.setFont(this.font);
        g.drawString(this.btnBack, 2*this.width/3, 500);
    }
    
    public void processHover(){
        
        int mouseX = this.game.listener.mouseX;
        int mouseY = this.game.listener.mouseY;
        
        //174 btn back width
        if(mouseX > (2*this.width)/3 &&
            mouseX < ((2*this.width)/3) + 174 &&
            mouseY > 470 &&
            mouseY < 500){
            this.selectedItem = 1;
        }
        else{
            this.selectedItem = 0;
        }
    }
    
    public Scene processClick(){
        Scene currentScene = this;
        
        if(this.game.listener.mousePressed && this.game.listener.mouseClickCount == 1){
            
            this.processButtonsClick();
            
            switch(this.selectedItem){
                case 1:
                    Configs.getInstance().saveConfig();
                    currentScene = new MenuScene(this.width, this.height, this.game);
                    break;
                default:
                    currentScene = this;
                    break;
            }
        }
        return currentScene;
    }
    
    public void renderControlsSettings(Graphics g){
        
        g.setFont(font);
        g.drawString("Left :", 100, 200);
        g.drawString("Right :", 100, 250);
        g.drawString("Jump :", 100, 300);
        g.drawString("Shift :", 100, 350);
        g.drawString("Down :", 100, 400);
        g.drawString("Up :", 100, 450);
        
        for(int i=0;i<this.optionsBtns.size();i++){
            this.optionsBtns.get(i).render(g);
        }
    }
    
    public void processKey(KeyEvent e){
        for(int i=0;i<this.optionsBtns.size();i++){
            if(this.optionsBtns.get(i).isEditing()){
                this.optionsBtns.get(i).processKey(e);
            }
        }
    }
    
    public void processButtonsClick(){
        for(int i=0;i<this.optionsBtns.size();i++){
            if(this.optionsBtns.get(i).isEditing())
                return;
        }
        
        for(int i=0;i<this.optionsBtns.size();i++){
            this.optionsBtns.get(i).processClick(this.game.listener.mouseX, this.game.listener.mouseY);
        }
    }
    
}
