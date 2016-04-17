package Audio;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    
    public static Sound music = new Sound("/song.wav", true);
    public static Sound coin = new Sound("/coin.wav", false);
    public static Sound hit = new Sound("/hit.wav", false);
    public static Sound mutation = new Sound("/mutation.wav", false);
    public static Sound jump = new Sound("/jump.wav", false);
    public static Sound shoot = new Sound("/shoot.wav", false);

    public String path;
    public int volumeEffects, volumeMusic;
    public boolean isMusic;

    private Sound(String path, boolean isMusic){
        this.path = path;
        this.isMusic = isMusic;
    }

    public void play(){
        try{
            URL url = this.getClass().getResource(this.path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float attenuation = 0f;
            if(this.isMusic){
                attenuation = -10.0f;
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else
                attenuation = -15.0f;
            gainControl.setValue(attenuation);
            clip.start();
        }catch(IOException|UnsupportedAudioFileException|LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
}
