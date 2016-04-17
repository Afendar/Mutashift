package Entities;

import Audio.Sound;
import Particles.Bullet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import ld35.Defines;

public class Mob extends Entity {

    public int bulletVelX, bulletVelY;
    public boolean doFire;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public long startTime = System.currentTimeMillis();
    public BufferedImage tileset, tile;
    
    public Mob(int posX, int posY){
        super(posX, posY);
        this.doFire = false;
        
        try{
            URL url = this.getClass().getResource("/tileset.png");
            this.tileset = ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        this.tile = this.tileset.getSubimage(3*Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
    }
    
    @Override
    public void update() {
        
        if(this.level.player != null && 
            ( this.level.player.posX > (this.posX - (5*Defines.TILE_SIZE)) && this.level.player.posX < (this.posX + (5*Defines.TILE_SIZE))) &&
                this.level.player.currentTypeIndex != 4){
            if(System.currentTimeMillis() - startTime >= 1000){
                startTime = System.currentTimeMillis();
                this.fire();
            }
        }
        else
        {
            this.doFire = false;
        }
        
        for(int i=0;i<this.bullets.size();i++){
            Bullet b = this.bullets.get(i);
            
            if(b.x > this.level.player.posX && 
                    b.x < this.level.player.posX + Defines.TILE_SIZE &&
                    b.y > this.level.player.posY && 
                    b.y < this.level.player.posY + Defines.TILE_SIZE){
                //touched
                this.level.player.hit(10);
                this.bullets.remove(i);
            }
            else{
                if(b.update()){
                    this.bullets.remove(i);
                }
            }
        }
        
    }

    @Override
    public void render(Graphics g) {
        
        for(int i=0;i<this.bullets.size();i++){
            this.bullets.get(i).render(g);
        }
        
        g.drawImage(this.tile, (int)this.posX, (int)this.posY, null);
    }
    
    
    public void fire(){
        
        if(this.bullets.size() < 4){
        
            this.doFire = true;
            int velx = 0;

            if(this.posX+(Defines.TILE_SIZE/2) > this.level.player.posX){
                velx = -4;
            }
            if(this.posX+(Defines.TILE_SIZE/2) < this.level.player.posX){
                velx = 4;
            }

            Bullet b = new Bullet(
                    (int)this.posX + (Defines.TILE_SIZE/2), 
                    (int)this.posY + 18,
                    velx, 0, 300);
            Sound.shoot.play();
            this.bullets.add(b);
        }
    }
}
