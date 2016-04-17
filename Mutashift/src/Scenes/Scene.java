package Scenes;

import java.awt.Graphics;
import ld35.Game;

public abstract class Scene {
    public int width, height;
    public Game game;
    public Class runtimeClass;
    
    public Scene(int width, int height, Game game){
        this.width = width;
        this.height = height;
        this.game = game;
        this.runtimeClass = this.getClass();
    }
    
    public abstract Scene update();
    public abstract void render(Graphics g);
}
