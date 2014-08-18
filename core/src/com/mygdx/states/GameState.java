package com.mygdx.states;

import static com.mygdx.globals.B2DVars.PPM;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.WorldEntity;
import com.mygdx.game.BlockBunnyMain;
import com.mygdx.handlers.CContactListener;
import com.mygdx.handlers.GameStateManager;

public abstract class GameState {
	protected GameStateManager gsm;
	protected BlockBunnyMain game;
	protected SpriteBatch sb;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	
	//STATE VARIABLES:
	protected boolean debugMode;
	protected static World world;
	protected Box2DDebugRenderer b2dr;
	protected OrthographicCamera box2dcam;
	protected CContactListener cl;
	protected TiledMap tileMap;
	protected float tileSize;
	protected OrthogonalTiledMapRenderer tmr;
	protected WorldEntity worldEntity;
	
	public static World getWorld(){
		return world;
	}
	
	protected GameState(GameStateManager gsm) {
		this.gsm=gsm;
		game=gsm.getGame();
		sb=game.getSpriteBatch();
		cam=game.getCamera();
		hudCam=game.getHUDCamera();
		debugMode=true;
		// set up debug camera
		box2dcam=new OrthographicCamera();
		box2dcam.setToOrtho(false,BlockBunnyMain.WIDTH/PPM,BlockBunnyMain.HEIGHT/PPM);
		//set up box2d:
		cl=new CContactListener();
		world=new World(new Vector2(0,-9.81f),true);
		world.setContactListener(cl);
		b2dr=new Box2DDebugRenderer();
		//CREATE EVERYTHING ELSE:
		createWorld();
		createEntities();
	}
	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void debugRenderer();
	public abstract void render();
	public abstract void dispose();
	protected abstract void createWorld();
	protected abstract void createEntities();
	protected abstract void renderEntities();
	protected abstract void updateEntities(float dt);
	protected abstract void cleanupWorld();
}
