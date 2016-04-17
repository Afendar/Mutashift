package Level.Tiles;

import java.awt.Graphics;
import ld35.Defines;

public class Floor extends Tile {

    public Floor(int imgX, int imgY, int id){
        super(imgX, imgY, id);
    }
    
    @Override
    public boolean canPass() {
        return false;
    }

    @Override
    public void update() {
        
    }
    
    public void render(Graphics g, int posX, int posY, boolean left, boolean right){
        if(left && right){
            this.tile = this.tileset.getSubimage(Defines.TILE_SIZE, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
        }
        else if(left){
            this.tile = this.tileset.getSubimage(3 * Defines.TILE_SIZE, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
        }
        else if(right){
            this.tile = this.tileset.getSubimage(2 * Defines.TILE_SIZE, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
        }
        else{
            this.tile = this.tileset.getSubimage(0, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
        }
        g.drawImage(this.tile, posX * Defines.TILE_SIZE, posY * Defines.TILE_SIZE, null);
    }
    
}
