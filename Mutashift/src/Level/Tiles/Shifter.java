package Level.Tiles;


public class Shifter extends Tile {
    
    public int type;
    
    public Shifter(int imgX, int imgY, int id, int type){
        super(imgX, imgY, id);
        this.type = type;
    }

    @Override
    public boolean canPass() {
        return true;
    }

    @Override
    public void update() {
        
    }
}
