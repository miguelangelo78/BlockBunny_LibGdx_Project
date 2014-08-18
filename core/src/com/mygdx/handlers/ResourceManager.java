package com.mygdx.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {
	private HashMap<String,Texture> textures;
	private HashMap<String,Music> music;
	private HashMap<String,Sound> sounds;
	
	public ResourceManager(){
		textures=new HashMap<String,Texture>();
		music=new HashMap<String,Music>();
		sounds=new HashMap<String,Sound>();
	}
	
	// TEXTURES HANDLER:
	public void loadTexture(String path,String key){
		textures.put(key, new Texture(Gdx.files.internal(path)));
	}
	public Texture getTexture(String key){
		return textures.get(key);
	}
	public void disposeTexture(String key){
		Texture tex=textures.get(key);
		if(tex!=null) tex.dispose();
	}
	
	//MUSIC HANDLER:
	public void loadMusic(String path, String key) {
		music.put(key,  Gdx.audio.newMusic(Gdx.files.internal(path)));
	}
	public Music getMusic(String key) {
		return music.get(key);
	}
	public void removeMusic(String key) {
		Music m = music.get(key);
		if(m != null) {
			music.remove(key);
			m.dispose();
		}
	}
	//SOUND HANDLER:
	public void loadSound(String path, String key) {
		sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(path)));
	}
	public Sound getSound(String key) {
		return sounds.get(key);
	}
	public void removeSound(String key) {
		Sound sound = sounds.get(key);
		if(sound != null) {
			sounds.remove(key);
			sound.dispose();
		}
	}
	public void cleanUp(){
		for(Object o : textures.values()) ((Texture) o).dispose();
		textures.clear();
		
		for(Object o : music.values()) ((Music) o).dispose();
		music.clear();
		
		for(Object o : sounds.values()) ((Sound) o).dispose();
		sounds.clear();
	}
}
