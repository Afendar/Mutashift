package Level.Tiles;

import java.util.ArrayList;

public class TileAtlas {
    
    public static ArrayList<Tile> atlas = new ArrayList<>();

    public static Tile empty = new Empty(0, 2, 0);
    public static Tile floor = new Floor(0, 0, 1);
    public static Tile spiderGenetic = new Shifter(4, 0, 2, 0);
    public static Tile hulkGenetic = new Shifter(0, 1, 3, 1);
    public static Tile torchGenetic = new Shifter(2, 1, 4, 2);
    public static Tile hiddenGenetic = new Shifter(1, 1, 5, 3);
    public static Tile copperCoin = new Coin(0, 0, 6, 0);
    public static Tile silverCoin = new Coin(0, 1, 7, 1);
    public static Tile goldCoin = new Coin(0, 2, 8, 2);
}
