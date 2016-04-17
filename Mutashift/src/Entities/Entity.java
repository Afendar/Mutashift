package Entities;

import Level.Level;
import java.awt.Graphics;

public abstract class Entity {
    
    protected float posX, posY;
    protected float velX, velY;
    protected int life = 100;
    protected Level level;
    protected boolean inLife;
    public boolean removed;
    
    public Entity(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        this.velX = 0;
        this.velY = 0;
        this.inLife = true;
    }
    
    public Entity(){
        this(0, 0);
    }
    
    public void setPosX(int posX){
        this.posX = posX;
    }
    
    public float getPosX(){
        return this.posX;
    }
    
    public void setPosY(int posY){
        this.posY = posY;
    }
    
    public float getPosY(){
        return this.posY;
    }
    
    public boolean inLife(){
        return this.inLife;
    }
    
    public int getLife(){
        return this.life;
    }
    
    public void addLevel(Level level){
        this.level = level;
    }
    
    public abstract void update();
    public abstract void render(Graphics g);
}
