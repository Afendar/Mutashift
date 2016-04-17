package ld35;

import java.awt.Dimension;
import javax.swing.JFrame;

public class GameFrame extends JFrame{
    
    public GameFrame(){
        Game g = new Game(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT);
        
        this.setTitle("Mutashift - LD35");
        this.add(g);
        this.setPreferredSize(new Dimension(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        g.start();
    }
    
}
