package Level.Tiles;

public class Empty extends Tile {

    Empty(int imgX, int imgY, int id){
        super(imgX, imgY, id);
    }

    @Override
    public boolean canPass() {
        return true;
    }

    @Override
    public void update() {
        
    }
    
}
