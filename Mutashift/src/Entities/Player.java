package Entities;

import Audio.Sound;
import Level.Tiles.Coin;
import Level.Tiles.Shifter;
import Level.Tiles.TileAtlas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import ld35.Defines;
import ld35.InputsListener;

public class Player extends Entity {
    
    private Color geneticType, currentType;
    private boolean isFalling, isJumping;
    private InputsListener listener;
    public boolean mutationsAchievements[];
    private int timeToShift, timeToSound;
    public int currentTypeIndex;
    private int timeMutations[];
    BufferedImage spritesheetHuman, spritesheetSpider, spritesheetTorch, spritesheetBulk, spritesheetHidden, sprite;
    int animX, animY, timeAnim;
    private int rotate;
    public int score;
    public boolean hasWin;
    
    private Color mutations[] = {Color.RED, Defines.SPIDER, Defines.HULK, Defines.TORCH, Defines.HIDDEN};
    
    public Player(int posX, int posY, InputsListener listener){
        super(posX, posY);
        
        this.geneticType = Color.RED;
        this.listener = listener;
        this.isJumping = false;
        this.isFalling = true;
        this.currentType = Color.RED;
        this.timeToShift = 0;
        this.currentTypeIndex = 0;
        this.rotate = 0;
        this.score = 0;
        this.timeToSound = 0;
        this.hasWin = false;
        
        this.mutationsAchievements = new boolean[5];
        this.mutationsAchievements[0] = true;
        this.timeMutations = new int[5];
        
        try{
            URL url = this.getClass().getResource("/human.png");
            this.spritesheetHuman = ImageIO.read(url);
            url = this.getClass().getResource("/hidden.png");
            this.spritesheetHidden = ImageIO.read(url);
            url = this.getClass().getResource("/torch.png");
            this.spritesheetTorch = ImageIO.read(url);
            url = this.getClass().getResource("/spider.png");
            this.spritesheetSpider = ImageIO.read(url);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        this.sprite = this.spritesheetHuman.getSubimage(this.animX, this.animY, Defines.TILE_SIZE, Defines.TILE_SIZE);
        
        this.animX = 0;
        this.animY = 1;
        
        this.timeAnim = 5;
    }

    @Override
    public void update() {
        
        if(timeToShift > 0){
            timeToShift--;
        }
        if(this.listener.shifter.enabled && timeToShift == 0){
            Sound.mutation.play();
            this.shiftMutation();
        }

        if(this.listener.jump.enabled && !this.isJumping){
            Sound.jump.play();
            this.isJumping = true;
            this.velY = -9;
        }
        
        if(this.isFalling || this.isJumping){
            this.velY += Defines.GRAVITY;
            if(this.velY > Defines.MAX_SPEED){
                this.velY = Defines.MAX_SPEED;
            }
        }
        
        if(TileAtlas.atlas.get(this.level.getTile((int)(this.posX + Defines.TILE_SIZE)/Defines.TILE_SIZE, (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE)) instanceof Coin){
            Coin coin = (Coin)TileAtlas.atlas.get(this.level.getTile((int)(this.posX + Defines.TILE_SIZE)/Defines.TILE_SIZE, (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE));
            this.score += coin.bonus;
            this.level.nbCoins--;
            Sound.coin.play();
            this.level.removeTile((int)(this.posX + Defines.TILE_SIZE)/Defines.TILE_SIZE, (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE);
        }
        else if(TileAtlas.atlas.get(this.level.getTile((int)(this.posX/Defines.TILE_SIZE), (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE)) instanceof Coin){
            Coin coin = (Coin)TileAtlas.atlas.get(this.level.getTile((int)(this.posX/Defines.TILE_SIZE), (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE));
            this.score += coin.bonus;
            this.level.nbCoins--;
            Sound.coin.play();
            this.level.removeTile((int)(this.posX/Defines.TILE_SIZE), (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE);
        }
        
        if(this.level.nbCoins == 0){
            this.hasWin = true;
        }
        
        if(this.listener.left.enabled){
            this.velX = -(Defines.SPEED);
            
            if(this.timeAnim == 0){
                this.animY = 1;
                this.animX++;
                if(this.animX > 3)
                    this.animX = 0;
                this.timeAnim = 5;
            }
            switch(this.currentTypeIndex){
                case 0:
                    this.sprite = this.spritesheetHuman.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                    break;
                case 1:
                    this.sprite = this.spritesheetSpider.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                    break;
                case 2:
                    break;
                case 3:
                    this.sprite = this.spritesheetTorch.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                    break;
                case 4:
                    this.sprite = this.spritesheetHidden.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                    break;
            }
            
            if(this.timeAnim > 0){
                this.timeAnim--;
            }
        }
        
        if(this.listener.right.enabled){
            this.velX = Defines.SPEED;
            
            if(this.timeAnim == 0){
                this.animY = 0;
                this.animX++;
                if(this.animX > 3)
                    this.animX = 0;
                this.timeAnim = 5;
            }
            
            switch(this.currentTypeIndex){
                case 0:
                    this.sprite = this.spritesheetHuman.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                    break;
                case 1:
                    this.sprite = this.spritesheetSpider.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                    break;
                case 2:
                    break;
                case 3:
                    this.sprite = this.spritesheetTorch.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                    break;
                case 4:
                    this.sprite = this.spritesheetHidden.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                    break;
            }
            
            
            if(this.timeAnim > 0){
                this.timeAnim--;
            }
        }
        
        if(this.currentTypeIndex == 1 && this.listener.climb.enabled){
            if((int)(this.posX + this.velX) / Defines.TILE_SIZE > 0 &&
                    (!TileAtlas.atlas.get(this.level.getTile((int)(this.posX + this.velX) / Defines.TILE_SIZE, (int)(this.posY)/Defines.TILE_SIZE)).canPass() || 
                    !TileAtlas.atlas.get(this.level.getTile((int)(this.posX + this.velX) / Defines.TILE_SIZE, (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE)).canPass()))
            {
                this.velX = 0;
                this.velY = -Defines.SPEED;
                this.rotate = 90;
            }
            //RIGHT
            else if((int)(this.posX + Defines.TILE_SIZE + this.velX)/ Defines.TILE_SIZE < this.level.nbTilesW - 1 &&
                    (!TileAtlas.atlas.get(this.level.getTile((int)(this.posX + Defines.TILE_SIZE + velX) / Defines.TILE_SIZE, (int)this.posY/Defines.TILE_SIZE)).canPass() || 
                    !TileAtlas.atlas.get(this.level.getTile((int)(posX + Defines.TILE_SIZE + velX) / Defines.TILE_SIZE, (int)(this.posY+Defines.TILE_SIZE)/Defines.TILE_SIZE)).canPass()))
            { 
                this.velX = 0;
                this.velY = -Defines.SPEED;
                this.rotate = -90;
            }
            
        }
        else if(this.currentTypeIndex == 1 && this.listener.down.enabled){
            if((int)(this.posX + this.velX) / Defines.TILE_SIZE > 0 &&
                    (!TileAtlas.atlas.get(this.level.getTile((int)(this.posX + this.velX) / Defines.TILE_SIZE, (int)(this.posY)/Defines.TILE_SIZE)).canPass() || 
                    !TileAtlas.atlas.get(this.level.getTile((int)(this.posX + this.velX) / Defines.TILE_SIZE, (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE)).canPass())){
                this.velY = Defines.SPEED;
                this.velX = 0;
                this.animY = 0;
                this.rotate = 90;
            }
            //RIGHT
            else if((int)(this.posX + Defines.TILE_SIZE + this.velX)/ Defines.TILE_SIZE < this.level.nbTilesW - 1 &&
                    (!TileAtlas.atlas.get(this.level.getTile((int)(this.posX + Defines.TILE_SIZE + velX) / Defines.TILE_SIZE, (int)this.posY/Defines.TILE_SIZE)).canPass() || 
                    !TileAtlas.atlas.get(this.level.getTile((int)(posX + Defines.TILE_SIZE + velX) / Defines.TILE_SIZE, (int)(this.posY+Defines.TILE_SIZE)/Defines.TILE_SIZE)).canPass())){
                 this.velY = Defines.SPEED;
                 this.velX = 0;
                 this.animY = 1;
                 this.rotate = -90;
            }
        }
        else
        {
            this.rotate = 0;
        }
        
        if(!this.checkCollisions()){
            this.velX = 0;
            this.velY = 0;
            this.isFalling = false;
            this.isJumping = false;
        }
        else{
            if(this.move(this.velX, this.velY)){
                this.isFalling = true;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        
        if(this.rotate != 0){
            g2d.rotate(Math.toRadians(this.rotate), 
                    (double)((2 * this.posX) + Defines.TILE_SIZE) / 2, 
                    (double)((2 * this.posY) + Defines.TILE_SIZE) / 2);
        }
        
        g.drawImage(this.sprite, (int)this.posX, (int)this.posY, null); 
        
        if(this.rotate != 0){
            g2d.rotate(-Math.toRadians(this.rotate), 
                    (double)((2 * this.posX) + Defines.TILE_SIZE) / 2, 
                    (double)((2 * this.posY) + Defines.TILE_SIZE) / 2);
        }
    }
    
    public boolean move(float x, float y){
        
        float newX = this.posX + x;
        float newY = this.posY + y;
        
        if(newX >= (this.level.nbTilesW-1)*Defines.TILE_SIZE){
            this.isFalling = true;
            this.velX = 0;
            this.posY += y;
            return false;
        }
        if(newY >= (this.level.nbTilesH-1)*Defines.TILE_SIZE){
            this.isFalling = true;
            this.velX = 0;
            return false;
        }
        if(newX <= 0){
            this.isFalling = true;
            this.velX = 0;
            this.posY += y;
            return false;
        }
        if(newY <= 0){
            this.isFalling = true;
            this.velX = 0;
            return false;
        }
        
        if(TileAtlas.atlas.get(
                this.level.getTile(
                        (int)newX/Defines.TILE_SIZE, 
                        (int)(newY+Defines.TILE_SIZE)/Defines.TILE_SIZE)) instanceof Shifter){
            
            Shifter shifter = (Shifter)TileAtlas.atlas.get(this.level.getTile((int)newX/Defines.TILE_SIZE, (int)(newY+Defines.TILE_SIZE)/Defines.TILE_SIZE));
            switch(shifter.type){
                case 0:
                    this.geneticType = Defines.SPIDER;
                    this.currentTypeIndex = 1;
                    this.mutationsAchievements[1] = true;
                    this.timeMutations[1] = Defines.TOTAL_TIME_MUTATION;
                    break;
                case 1:
                    this.currentTypeIndex = 2;
                    this.geneticType = Defines.HULK;
                    this.mutationsAchievements[2] = true;
                    this.timeMutations[2] = Defines.TOTAL_TIME_MUTATION;
                    break;
                case 2:
                    this.currentTypeIndex = 3;
                    this.geneticType = Defines.TORCH;
                    this.mutationsAchievements[3] = true;
                    this.timeMutations[3] = Defines.TOTAL_TIME_MUTATION;
                    break;
                case 3:
                    this.currentTypeIndex = 4;
                    this.geneticType = Defines.HIDDEN;
                    this.mutationsAchievements[4] = true;
                    this.timeMutations[4] = Defines.TOTAL_TIME_MUTATION;
                    break;
            }
            
            if(this.timeToSound > 0){
                this.timeToSound--;
            }
            if(this.timeToSound == 0){
                Sound.mutation.play();
                this.timeToSound = 30;
            }
        }
        else if(TileAtlas.atlas.get(
                        this.level.getTile(
                                (int)(newX+Defines.TILE_SIZE)/Defines.TILE_SIZE, 
                                (int)(newY+Defines.TILE_SIZE)/Defines.TILE_SIZE)) instanceof Shifter){
            Shifter shifter = (Shifter)TileAtlas.atlas.get(this.level.getTile((int)(newX+Defines.TILE_SIZE)/Defines.TILE_SIZE, (int)(newY+Defines.TILE_SIZE)/Defines.TILE_SIZE));
            switch(shifter.type){
                case 0:
                    this.geneticType = Defines.SPIDER;
                    this.currentTypeIndex = 1;
                    this.mutationsAchievements[1] = true;
                    this.timeMutations[1] = Defines.TOTAL_TIME_MUTATION;
                    break;
                case 1:
                    this.currentTypeIndex = 2;
                    this.geneticType = Defines.HULK;
                    this.mutationsAchievements[2] = true;
                    this.timeMutations[2] = Defines.TOTAL_TIME_MUTATION;
                    break;
                case 2:
                    this.currentTypeIndex = 3;
                    this.geneticType = Defines.TORCH;
                    this.mutationsAchievements[3] = true;
                    this.timeMutations[3] = Defines.TOTAL_TIME_MUTATION;
                    break;
                case 3:
                    this.currentTypeIndex = 4;
                    this.geneticType = Defines.HIDDEN;
                    this.mutationsAchievements[4] = true;
                    this.timeMutations[4] = Defines.TOTAL_TIME_MUTATION;
                    break;
            }
            if(this.timeToSound > 0){
                this.timeToSound--;
            }
            if(this.timeToSound == 0){
                Sound.mutation.play();
                this.timeToSound = 30;
            }
        }
        
        this.posX += x;
        this.posY += y;

        return true;
    }
    
    public boolean checkCollisions(){
        
        if(this.currentTypeIndex != 1){
            if((int)(this.posX + this.velX) / Defines.TILE_SIZE > 0 &&
                    (!TileAtlas.atlas.get(this.level.getTile((int)(this.posX + this.velX) / Defines.TILE_SIZE, (int)(this.posY)/Defines.TILE_SIZE)).canPass() || 
                    !TileAtlas.atlas.get(this.level.getTile((int)(this.posX + this.velX) / Defines.TILE_SIZE, (int)(this.posY + Defines.TILE_SIZE)/Defines.TILE_SIZE)).canPass())){
                this.velX = 0;
            }
            //RIGHT
            else if((int)(this.posX + Defines.TILE_SIZE + this.velX)/ Defines.TILE_SIZE < this.level.nbTilesW - 1 &&
                    (!TileAtlas.atlas.get(this.level.getTile((int)(this.posX + Defines.TILE_SIZE + velX) / Defines.TILE_SIZE, (int)this.posY/Defines.TILE_SIZE)).canPass() || 
                    !TileAtlas.atlas.get(this.level.getTile((int)(posX + Defines.TILE_SIZE + velX) / Defines.TILE_SIZE, (int)(this.posY+Defines.TILE_SIZE)/Defines.TILE_SIZE)).canPass())){
                 this.velX = 0; 
            }
        }
        
        float newX = this.posX + this.velX;
        float newY = this.posY + this.velY;
        
        if(newX <= 0)newX = 0;
        if(newY <= 0)newY = 0;
        if(newX >= (this.level.nbTilesW-2)*Defines.TILE_SIZE)newX = (this.level.nbTilesW-2)*Defines.TILE_SIZE;
        if(newY >= (this.level.nbTilesH-2)*Defines.TILE_SIZE)newY = (this.level.nbTilesH-2)*Defines.TILE_SIZE;
        
        if(!TileAtlas.atlas.get(this.level.getTile((int)(newX/Defines.TILE_SIZE), (int)(newY/Defines.TILE_SIZE))).canPass()){
            return false;
        }
        else if(!TileAtlas.atlas.get(this.level.getTile((int)(newX + Defines.TILE_SIZE)/Defines.TILE_SIZE, (int)(newY+Defines.TILE_SIZE)/Defines.TILE_SIZE)).canPass()){
            return false;
        }
        else if(!TileAtlas.atlas.get(this.level.getTile((int)newX / Defines.TILE_SIZE, (int)(newY+Defines.TILE_SIZE)/Defines.TILE_SIZE)).canPass()){
            return false;
        }
        else if(!TileAtlas.atlas.get(this.level.getTile((int)(newX+Defines.TILE_SIZE)/Defines.TILE_SIZE, (int)newY/Defines.TILE_SIZE)).canPass()){
            return false;
        }
        
        return true;
    }
    
    public void hit(int dammages){
        this.life -= dammages;
        if(this.life <= 0){
            this.inLife = false;
        }
        Sound.hit.play();
    }
    
    public void shiftMutation(){
        this.currentTypeIndex++;
            
        if(this.currentTypeIndex > 4){
            this.currentTypeIndex = 0;
        }

        while(!this.mutationsAchievements[this.currentTypeIndex]){
            this.currentTypeIndex++;
            if(this.currentTypeIndex > 4){
                this.currentTypeIndex = 0;
            }
        }
        
        switch(this.currentTypeIndex){
            case 0:
                this.sprite = this.spritesheetHuman.getSubimage(
                        this.animX * Defines.TILE_SIZE, 
                        this.animY * Defines.TILE_SIZE, 
                        Defines.TILE_SIZE, 
                        Defines.TILE_SIZE);
                break;
            case 1:
                this.sprite = this.spritesheetSpider.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                break;
            case 2:
                break;
            case 3:
                this.sprite = this.spritesheetTorch.getSubimage(
                            this.animX * Defines.TILE_SIZE, 
                            this.animY * Defines.TILE_SIZE, 
                            Defines.TILE_SIZE, 
                            Defines.TILE_SIZE);
                break;
            case 4:
                this.sprite = this.spritesheetHidden.getSubimage(
                        this.animX * Defines.TILE_SIZE, 
                        this.animY * Defines.TILE_SIZE, 
                        Defines.TILE_SIZE, 
                        Defines.TILE_SIZE);
                break;
        }
        
        this.timeToShift = 40;
    }
    
    public int getTmeMutation(int index){
        return this.timeMutations[index];
    }
    
    public void updateMutates(){
        for(int i=1;i<this.mutationsAchievements.length;i++){
            if(this.mutationsAchievements[i]){
                this.timeMutations[i]--;
                if(this.timeMutations[i] == 0){
                    if(this.currentTypeIndex == i){
                        this.shiftMutation();
                    }
                    this.mutationsAchievements[i] = false;
                }
            }
        }
    }
}
