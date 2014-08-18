package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.handlers.CINput;
import com.mygdx.handlers.CustomInputProcessor;
import com.mygdx.handlers.GameStateManager;
import com.mygdx.handlers.ResourceManager;

public class BlockBunnyMain implements ApplicationListener {
	public static final String TITLE="Block Bunny";
	public static final int WIDTH=800,HEIGHT=600,SCALE=2;
	public static final float STEP=1/60f;
	private float accum;
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private GameStateManager gsm;
	public static ResourceManager res;
	
	public SpriteBatch getSpriteBatch(){
		return sb;
	}
	public OrthographicCamera getCamera(){
		return cam;
	}
	public OrthographicCamera getHUDCamera(){
		return hudCam;
	}
	
	public void load_resources(){
		res=new ResourceManager();
		res.loadTexture("images/bunny.png", "bunny");
		res.loadTexture("images/crystal.png","crystal");
		res.loadTexture("images/hud.png","hud");
	}
	
	public void create() {
		Gdx.input.setInputProcessor(new CustomInputProcessor());
		load_resources();
		sb=new SpriteBatch();
		cam=new OrthographicCamera();
		cam.setToOrtho(false,WIDTH,HEIGHT);
		hudCam=new OrthographicCamera();
		hudCam.setToOrtho(false,WIDTH,HEIGHT);
		gsm=new GameStateManager(this);
	}

	public void render() {
		Gdx.graphics.setTitle(TITLE+" -- FPS: "+Gdx.graphics.getFramesPerSecond());
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		CINput.update();
	}

	public void resize(int width, int height) {
		
	}
	
	public void pause() {
		
	}

	public void resume() {
		
	}

	public void dispose() {
		
	}

}