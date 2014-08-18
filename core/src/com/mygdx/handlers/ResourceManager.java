package com.mygdx.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {
	private HashMap<String,Texture> textures;
	
	public ResourceManager(){
		textures=new HashMap<String,Texture>();
	}
	
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
}
