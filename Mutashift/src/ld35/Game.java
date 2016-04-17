package ld35;

import Scenes.GameScene;
import Scenes.MenuScene;
import Scenes.Scene;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    
    public int screenWidth, screenHeight;
    protected boolean running, paused;
    private Thread gameThread;
    private Scene scene;
    public InputsListener listener;
    public int time = 0;
    public int timeF = 0;
    public boolean timer = false;
    
    public Game(int screenWidth, int screenHeight){
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        
        this.setMaximumSize(new Dimension(this.screenWidth, this.screenHeight));
        this.setMinimumSize(new Dimension(this.screenWidth, this.screenHeight));
        this.setSize(new Dimension(this.screenWidth, this.screenHeight));
        
        this.running = true;
        this.paused = false;
        
        this.listener = new InputsListener(this);
        
        this.scene = new MenuScene(this.screenWidth, this.screenHeight, this);
        
        this.timer = true;
        this.timeF = TimerThread.MILLI;
    }
    
    public void reinitTimer(){
        this.timeF = TimerThread.MILLI;
    }
    
    public void start(){
        this.running = true;
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }
    
    public void stop(){
        this.running= false;
    }
    
    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        int fps = 0;
        double nsToMs = 1000000000 / 60;
        
        boolean update = true;
        
        while(this.running)
        {
            long current = System.nanoTime();
            
            try{
                Thread.sleep(2);
            }
            catch(InterruptedException e){}
            
            update = false;
            
            if((current - lastTime) / nsToMs >= 1){
                fps++;
                lastTime = current;
                update = true;
            }
            
            this.render();
            
            if(update){
                this.update();
            }
            
            if(System.currentTimeMillis() - startTime >= 1000){
                //System.out.println("FPS : " + fps);
                fps = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }

    public void update(){
        this.requestFocus();
        
        this.scene = this.scene.update();
    }
        
    public void render(){

        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null){
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(153, 217, 234));
        g.fillRect(0, 0, this.screenWidth, this.screenHeight);
        
        this.scene.render(g);
        
        g.dispose();
        bs.show();
    }
}
