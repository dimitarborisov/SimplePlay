package sample;

import java.net.URL;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {
	
	boolean loaded;
	boolean completed;
	boolean paused;
	boolean playing;

	private Duration saveDuration;
	
	MediaPlayer mediaPlayer;
	
	public MusicPlayer(){
		new JFXPanel();
		saveDuration = null;
	}
	
	public void load(String url){
		loaded = false;
		completed = false;
		playing = false;
		
		mediaPlayer = new MediaPlayer(new Media(url.toString()));
		
		//set loader options for starting
		mediaPlayer.setOnReady(new Runnable(){

			@Override
			public void run() {
				loaded = true;
			}
			
		});
		
		
		mediaPlayer.setOnEndOfMedia(new Runnable(){
			@Override
			public void run() {
				completed = true;
			}
		});
	}
	
	public void play(){
		mediaPlayer.play();
        playing = true;
	}

	
	public void stop(){
		mediaPlayer.stop();
        playing = false;
	}

	public void pause(){
		paused = true;
		playing = false;
		mediaPlayer.pause();
	}


	public void seek(double time){
		mediaPlayer.seek(new Duration(time));
	}
	
	public double getCurrentTime(){
		return mediaPlayer.getCurrentTime().toMillis();
	}
	
	public boolean isReady(){
		return loaded;
	}
	
	public double getDuartion(){
		return mediaPlayer.getTotalDuration().toMillis();
	}

	public boolean isCompleted(){
		return completed;
	}

	public boolean isPlaying(){
		return playing;
	}
}
