package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.handlers.GameStateManager;

public class BlockBunnyMain implements ApplicationListener {
	public static final String TITLE="Block Bunny";
	public static final int WIDTH=800,HEIGHT=600,SCALE=2;
	public static final float STEP=1/60f;
	private float accum;
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private GameStateManager gsm;
	
	public SpriteBatch getSpriteBatch(){return sb;}
	public OrthographicCamera getCamera(){return cam;}
	public OrthographicCamera getHUDCamera(){return hudCam;}
	
	public void create() {
		sb=new SpriteBatch();
		cam=new OrthographicCamera();
		cam.setToOrtho(false,WIDTH,HEIGHT);
		hudCam=new OrthographicCamera();
		hudCam.setToOrtho(false,WIDTH,HEIGHT);
		gsm=new GameStateManager(this);
		
	}

	public void resize(int width, int height) {
		
	}

	public void render() {
		accum+=Gdx.graphics.getDeltaTime();
		while(accum>=STEP){
			accum-=STEP;
			gsm.update(STEP);
			gsm.render();
		}
	}

	public void pause() {
		
	}

	public void resume() {
		
	}

	public void dispose() {
		System.out.println("DISPOSED");
	}

}