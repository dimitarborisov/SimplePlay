package sample;


import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

import java.awt.*;

public class ThreadSlider extends Thread{
    private Slider slider;
    private MusicPlayer musicPlayer;
    private boolean update;
    private boolean isRunning;

    public ThreadSlider(Slider slider, MusicPlayer musicPlayer){
        this.slider = slider;
        this.musicPlayer = musicPlayer;
        update = true;
    }

    @Override
    public void run() {
        isRunning = true;

        while(isRunning) {
            try {
                sleep(100);
                if (update) {
                    slider.setValue((musicPlayer.getCurrentTime() / musicPlayer.getDuartion() * 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void setUpdate(boolean update){
        this.update = update;
    }

    public void setRunning(boolean running){
        isRunning = running;
    }
}
