package ld35;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;

public class OptionButton extends JComponent {
    
    private boolean isEditing;
    private String value;
    private int x, y, w, h;
    private String text, name;
    
    public OptionButton(String value, String name, int x, int y){
        super();
        this.text = value.toString();
        this.name = name;
        this.value = value;
        this.x = x;
        this.y = y;
        this.setFont(new Font("Arial", Font.PLAIN, 12));
        
        FontMetrics fm = this.getFontMetrics(this.getFont());
        this.h = fm.getHeight();
        this.w = fm.stringWidth(this.text);
    }
    
    public OptionButton(String value, String name){
        this(value, name, 0, 0);
    }
    
    public void setText(String text){
        this.text = text;
        FontMetrics fm = this.getFontMetrics(this.getFont());
        this.w = fm.stringWidth(this.text);
    }
    
    public void setFont(Font font){
        super.setFont(font);
        FontMetrics fm = this.getFontMetrics(this.getFont());
        this.w = fm.stringWidth(this.text);
    }
    
    public boolean isEditing(){
        return this.isEditing;
    }
    
    private void configure(KeyEvent e){
        this.isEditing = false;
        this.value = Integer.toString(e.getKeyCode());
        Configs.getInstance().setConfigValue(this.name, this.value);
        this.setText(KeyEvent.getKeyText(e.getKeyCode()));
    }
    
    private Object getValue(){
        return this.value;
    }
    
    private void editing(){
        this.requestFocus(true);
        this.isEditing = true;
        this.setText("Press a key");
    }
    
    public void render(Graphics g){
        g.setColor(new Color(200, 200, 200));
        g.fillRect(this.x - 15, this.y - this.h - 7, this.w + 30, this.h + 14);
        g.setColor(Color.BLACK);
        g.setFont(this.getFont());
        g.drawString(this.text, this.x, this.y);
    }
    
    public void processClick(int x, int y){
        if(x > this.x - 15 && x < this.x + this.w + 15 && y < this.y + 7 && y > this.y - this.h - 7){
            editing();
        }
    }
    
    public void processKey(KeyEvent e){
        if(this.isEditing){
            configure(e);
        }
    }
}
