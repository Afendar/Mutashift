package Particles;

import java.awt.Graphics;

public abstract class Particle {
    
    public int dx, dy;
    public int x, y;
    public int health;
    
    public Particle(int x, int y, int dx, int dy, int health){
        this.dx = dx;
        this.dy = dy;
        this.x = x;
        this.y = y;
        this.health = health;
    }
    
    public abstract boolean update();
    public abstract void render(Graphics g);
}
