package ld35;

public class Launcher {

    public static void main(String[] args) {
        TimerThread timer = new TimerThread();
        timer.start();
        GameFrame gf = new GameFrame();
    }
    
}
