package Level.Tiles;

import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import ld35.Defines;

public class Coin extends Tile {

    public int type;
    public int bonus;
    public int timeAnim;
    public int animX;
    
    public Coin(int imgX, int imgY, int id, int type){
        
        super(imgX, imgY, id);
        
        try{
            URL url = this.getClass().getResource("/tileset.png");
            this.tileset = ImageIO.read(url);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        this.tile = this.tileset.getSubimage(imgX * Defines.TILE_SIZE, imgY * 17 + 96, Defines.TILE_SIZE, 17);
        
        this.type = type;
        
        switch(type){
            case 0:
                this.bonus = 10;
                break;
            case 1:
                this.bonus = 50;
                break;
            case 2:
                this.bonus = 100;
                break;
        }
        
        this.timeAnim = 10;
        this.animX = 0;
    }
    
    @Override
    public boolean canPass() {
        return true;
    }

    @Override
    public void update() {
        
    }
    
    public void render(Graphics g, int posX, int posY){

        if(this.timeAnim == 0){
            this.animX++;
            if(this.animX > 3)
                this.animX = 0;
            this.timeAnim = 10;
            this.tile = this.tileset.getSubimage(this.animX * Defines.TILE_SIZE, (imgY * 17) + 96, Defines.TILE_SIZE, 17);
        }
        
        g.drawImage(this.tile, posX * Defines.TILE_SIZE, posY * Defines.TILE_SIZE, null);
        
        if(this.timeAnim > 0){
                this.timeAnim--;
            }
    }
}
