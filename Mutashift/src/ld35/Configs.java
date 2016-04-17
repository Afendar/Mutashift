package ld35;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Configs {
    public String[] configsLabel = {"Jump", "Left", "Right", "Climb", "Down", "Shift"};
    public String fileOptions = "settings.dat";
    public String[] configsDefault = {"" + KeyEvent.VK_SPACE, "" + KeyEvent.VK_Q, "" + KeyEvent.VK_D, "" + KeyEvent.VK_Z, "" + KeyEvent.VK_S,"" + KeyEvent.VK_R};
    public String[] configsValues;
    
    private Configs(){
        this.loadConfigs();
    }
    
    private static Configs instance = new Configs();
    
    public static Configs getInstance(){
        return instance;
    }
    
    private void loadConfigs(){
        File f = new File(this.fileOptions);
        this.configsValues = this.configsDefault;
        
        if(f.exists() && !f.isDirectory()){
            try{
                BufferedReader br = new BufferedReader(new FileReader(this.fileOptions));
                String line = null;
                while((line = br.readLine()) != null){
                    String[] strSplited = line.split(":");
                    for(int i=0;i<this.configsLabel.length;i++){
                        if(this.configsLabel[i].equals(strSplited[0])){
                            this.configsValues[i] = strSplited[1];
                        }
                    }
                }
                br.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            this.saveConfig();
        }
    }
    
    public void saveConfig(){
        try{
            PrintWriter pw = new PrintWriter(
                                new BufferedWriter(
                                    new FileWriter(this.fileOptions)));
            for(int i=0;i<this.configsValues.length;i++){
                pw.println(this.configsLabel[i] + ":" + this.configsValues[i]);
            }
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void setConfigValue(String key, String value){
        for(int i=0;i<this.configsLabel.length;i++){
            if(this.configsLabel[i].equals(key)){
                this.configsValues[i] = value;
            }
        }
    }
    
    public String getConfigValue(String key){
        for(int i=0;i<this.configsLabel.length;i++){
            if(this.configsLabel[i].equals(key)){
                return this.configsValues[i];
            }
        }
        return null;
    }
}
