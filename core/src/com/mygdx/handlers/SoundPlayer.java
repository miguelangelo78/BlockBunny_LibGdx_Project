package com.mygdx.handlers;

import com.badlogic.gdx.audio.Music;
import com.mygdx.game.BlockBunnyMain;

public class SoundPlayer {
	private static BlockBunnyMain gameObject;
	
	@SuppressWarnings("static-access")
	public static void playSound(String key,float volume,float pitch,float pan){
		gameObject.res.getSound(key).play(volume,pitch,pan);
	}
	public static void playMusic(String key,boolean looping,float volume,float pan){
		Music musicToPlay=BlockBunnyMain.res.getMusic(key);
		musicToPlay.setLooping(looping);
		musicToPlay.setVolume(volume);
		musicToPlay.setPan(pan, volume);
		musicToPlay.play();
	}
	@SuppressWarnings("static-access")
	public static void stopSound(String key){
		gameObject.res.getSound(key).stop();
	}
	@SuppressWarnings("static-access")
	public static void stopMusic(String key){
		gameObject.res.getMusic(key).stop();
	}
}
