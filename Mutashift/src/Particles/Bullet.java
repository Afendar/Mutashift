package Particles;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Particle {
    
    public Bullet(int x, int y, int dx, int dy, int health){
        super(x, y, dx, dy, health);
    }

    @Override
    public boolean update() {
        this.x += this.dx;
        this.y += this.dy;
        
        this.health--;
        if(this.health <= 0)
            return true;
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(this.x - 3, this.y - 3, 6, 6);
    }
}
