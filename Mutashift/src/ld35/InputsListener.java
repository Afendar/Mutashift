package ld35;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class InputsListener implements KeyListener, MouseMotionListener, MouseListener {

    public class Action{
        public boolean enabled, typed;
        
        public Action(){
            actions.add(this);
        }
        
        public void switched(boolean enabled){
            if(enabled != this.enabled)
                this.enabled = enabled;
        }
    }
    
     public boolean[] keys;
    public ArrayList<Action> actions = new ArrayList<Action>();
    
    public Action jump = new Action();
    public Action left = new Action();
    public Action right = new Action();
    public Action shifter = new Action();
    public Action next = new Action();
    public Action climb = new Action();
    public Action down = new Action();
    
    public int mouseX, mouseY, mouseClickCount;
    public boolean mouseExited, mousePressed;
    public KeyEvent e = null;
    
    public InputsListener(Game game){
        
        game.addKeyListener(this);
        game.addMouseMotionListener(this);
        game.addMouseListener(this);
        
        this.mouseX = 0;
        this.mouseY = 0;
        this.mouseClickCount = 0;
        this.mouseExited = true;
        this.mousePressed = false;
        this.keys = new boolean[KeyEvent.KEY_LAST];
    }
    
    public void update(){
        this.mouseClickCount = 0;
    }
    
    public void processKey(KeyEvent e, boolean enabled){
        if(e.getKeyCode() == Integer.parseInt(Configs.getInstance().getConfigValue("Jump")))
            jump.switched(enabled);
        if(e.getKeyCode() == Integer.parseInt(Configs.getInstance().getConfigValue("Left")))
            left.switched(enabled);
        if(e.getKeyCode() == Integer.parseInt(Configs.getInstance().getConfigValue("Right")))
            right.switched(enabled);
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            next.switched(enabled);
        if(e.getKeyCode() == Integer.parseInt(Configs.getInstance().getConfigValue("Shift")))
            shifter.switched(enabled);
        if(e.getKeyCode() == Integer.parseInt(Configs.getInstance().getConfigValue("Climb")))
            climb.switched(enabled);
        if(e.getKeyCode() == Integer.parseInt(Configs.getInstance().getConfigValue("Down")))
            down.switched(enabled);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        processKey(e, true);
        this.e = e;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        processKey(e, false);
        this.e = null;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mousePressed = true;
        this.mouseClickCount = e.getClickCount();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.mouseExited = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.mouseExited = true;
    }
}
