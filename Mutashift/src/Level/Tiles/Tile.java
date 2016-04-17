package Level.Tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import ld35.Defines;

public abstract class Tile {
    public int imgX, imgY;
    public final int id;
    public BufferedImage tileset, tile;
    
    public Tile(int imgX, int imgY, int id){
        this.imgX = imgX;
        this.imgY = imgY;
        this.id = id;
        
        try{
            URL url = this.getClass().getResource("/tileset.png");
            this.tileset = ImageIO.read(url);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        this.tile = this.tileset.getSubimage(imgX * Defines.TILE_SIZE, imgY * Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
        
        TileAtlas.atlas.add(this);
    }
    
    public abstract boolean canPass();
    public abstract void update();
    
    public void render(Graphics g, int posX, int posY, boolean left, boolean right){
        this.render(g, posX, posY);
    }
    
    public void render(Graphics g, int posX, int posY){
        g.drawImage(this.tile, posX * Defines.TILE_SIZE, posY * Defines.TILE_SIZE, null);
    }
}
