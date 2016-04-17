package Level;

import Entities.Entity;
import Entities.Mob;
import Entities.Player;
import Level.Tiles.TileAtlas;
import java.awt.Graphics;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import ld35.Defines;

public class Level {
    
    public int w, h;
    public int nbTilesW, nbTilesH;
    public int[][] map;
    public List<Entity> entities = new ArrayList<Entity>();
    public Player player;
    public int nbCoins;
    
    public static final int WHITE = 16777215; //RGB(0, 0, 0) 
    public static final int GREEN = 32768; //RGB(0, 128, 0) //Radiations Hulk
    public static final int BLACK = 0; //RGB(255, 255, 255) 
    public static final int ORANGE = 16744231;//RGB(255, 165, 0) //Radiations torch
    public static final int BLUE = 255;//RGB(0, 0, 255) //Radiations hidden
    public static final int PURPLE = 10701220;//RGB(158, 0, 158) //Radiations spider
    public static final int RED = 15539236;//RGB(237, 28, 36) //mob
    
    public static final int BROWN = 12155479;//RGB(185,122,87) //copper coin
    public static final int GRAY = 8355711;//RGB(127,127,127) //silver coin
    public static final int YELLOW = 16773632;//RGB(255,242,0) //gold coin
    
    public Level(){
        this.nbCoins = 0;
        
        this.loadLevel();
    }
    
    public void update(){
        
        for(int i=0;i<this.entities.size();i++){
            Entity e = this.entities.get(i);
            if(!(e instanceof Player)){
                e.update();
            }
        }
        
    }
    
    public void render(Graphics g){
        for(int i=0;i<this.nbTilesW;i++){
            for(int j=0;j<this.nbTilesH;j++){
                switch(this.map[i][j]){
                    case 1:
                        boolean left = false;
                        boolean right = false;
                        if(i-1 > 0 && map[i-1][j] == 1){
                            left = true;
                        }
                        if(i+1 < this.nbTilesW && map[i+1][j] == 1){
                            right = true;
                        }
                        TileAtlas.floor.render(g, i, j, left, right);
                        break;
                    case 2:
                        TileAtlas.spiderGenetic.render(g, i, j);
                        break;
                    case 3:
                        TileAtlas.hulkGenetic.render(g, i, j);
                        break;
                    case 4:
                        TileAtlas.torchGenetic.render(g, i, j);
                        break;
                    case 5:
                        TileAtlas.hiddenGenetic.render(g, i, j);
                        break;
                    case 6:
                        TileAtlas.copperCoin.render(g, i, j);
                        break;
                    case 7:
                        TileAtlas.silverCoin.render(g, i, j);
                        break;
                    case 8:
                        TileAtlas.goldCoin.render(g, i, j);
                        break;
                    default:
                        break;
                }
                
            }
        }
        
        for(int i=0;i<this.entities.size();i++){
            this.entities.get(i).render(g);
        }
    }
    
    public int getTile(int x, int y){
        return this.map[x][y];
    }
    
    public void removeTile(int x, int y){
        this.map[x][y] = 0;
    }
    
    public void loadLevel(){
        try{
            URL url = this.getClass().getResource("/lvl.png");
            BufferedImage lvlImg = ImageIO.read(url);
            
            byte[] pixels = ((DataBufferByte) lvlImg.getRaster().getDataBuffer()).getData();
            int width = lvlImg.getWidth();
            int height = lvlImg.getHeight();
            
            this.w = width * Defines.TILE_SIZE;
            this.h = height * Defines.TILE_SIZE;
            this.nbTilesH = height;
            this.nbTilesW = width;
            
            this.map = new int[width][height];
            
            boolean hasAlpha = true;
            
            if (hasAlpha) {
                final int pixelLength = 4;
                for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                   int argb = 0;
                   argb += 0; // alpha
                   argb += ((int) pixels[pixel + 1] & 0xff); // blue
                   argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                   argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                   //System.out.println(argb);
                   switch(argb){
                        case WHITE:
                            map[col][row] = 0;
                            break;
                        case BLACK:
                            map[col][row] = 1;
                            break;
                        case PURPLE:
                            map[col][row] = 2;
                            break;
                        case GREEN:
                            map[col][row] = 3;
                            break;
                        case ORANGE:
                            map[col][row] = 4;
                            break;
                        case BLUE:
                            map[col][row] = 5;
                            break;
                        case RED:
                            Mob m = new Mob(col * Defines.TILE_SIZE, row * Defines.TILE_SIZE);
                            this.add(m);
                            break;
                        case BROWN:
                            this.nbCoins++;
                            map[col][row] = 6;
                            break;
                        case GRAY:
                            this.nbCoins++;
                            map[col][row] = 7;
                            break;
                        case YELLOW:
                            this.nbCoins++;
                            map[col][row] = 8;
                            break;
                        default:
                            break;
                   }

                   col++;
                   if (col == width) {
                      col = 0;
                      row++;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void add(Entity entity){
        if(entity instanceof Player){
            this.player = (Player) entity;
        }
        entity.removed = false;
        this.entities.add(entity);
        entity.addLevel(this);
    }
}