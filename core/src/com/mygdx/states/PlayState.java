package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.CrystalEntity;
import com.mygdx.entities.HUDEntity;
import com.mygdx.entities.PlayerEntity;
import com.mygdx.entities.WorldEntity;
import com.mygdx.globals.B2DVars;
import com.mygdx.globals.StateVars;
import com.mygdx.handlers.CINput;
import com.mygdx.handlers.GameStateManager;
import static com.mygdx.globals.B2DVars.*;

public class PlayState extends GameState{
	
	//ENTITY VARIABLES:
	private PlayerEntity player;
	private Array<CrystalEntity> crystals; 
	private HUDEntity hud;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void handleInput() {
		if(CINput.isPressed(CINput.SPACE) && cl.isPlayerOnGround())	player.getBody().applyForceToCenter(0,9.81f*35,true);
		
		if(CINput.isPressed(CINput.BUTTON2)){
			gsm.setState(StateVars.MENU);
		}
		
		if(CINput.isDown(CINput.LEFT)){
			player.getBody().applyForceToCenter(-7f, 0, true);
			player.setWalking(true);
			if(!player.isFramesFlipped()){
				for(TextureRegion frame:player.getAnimation().getAllFrames()) frame.flip(true,false);
				player.setFramesFlipped(true);
			}
		}
		if(CINput.isDown(CINput.RIGHT)){
			player.getBody().applyForceToCenter(7f, 0, true);
			player.setWalking(true);
			if(player.isFramesFlipped()){
				for(TextureRegion frame:player.getAnimation().getAllFrames()) frame.flip(true,false);
				player.setFramesFlipped(false);
			}
		}
		
		if(CINput.isButtonUp(CINput.RIGHT)||CINput.isButtonUp(CINput.LEFT))	player.setWalking(false);
	}

	public void update(float dt) {
		handleInput();
		world.step(dt, 8, 2);
		updateEntities(dt);
		cleanupWorld();
	}
	
	public void debugRenderer() {
		if(debugMode){
			//draw debug box2d:
			box2dcam.position.set(player.getPosition().x,player.getPosition().y, 0);
			box2dcam.update();
			b2dr.render(world, box2dcam.combined);
		}
	}
	
	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glClearColor(.7f, .7f,.5f, 0);
		sb.setProjectionMatrix(cam.combined);
		
		//set camera to follow player
		cam.position.set(player.getPosition().x*PPM,player.getPosition().y*PPM,0);
		cam.update();
		tmr.setView(cam);
		tmr.render();
		
		debugRenderer();
		renderEntities();
	}

	public void dispose() {
		
	}
	
	protected void createEntities(){
		player=new PlayerEntity(50,1000,world);	// create player
		
		//create crystals:
		crystals=new Array<CrystalEntity>();
		MapLayer crystalLayer=tileMap.getLayers().get("crystals");
		for(MapObject mo:crystalLayer.getObjects()) crystals.add(new CrystalEntity((float)mo.getProperties().get("x",Float.class),(float)mo.getProperties().get("y",Float.class),world));
	
		//create hud:
		hud=new HUDEntity(player,world);
	}
	
	protected void renderEntities(){
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
		for(int i=0;i<crystals.size;i++) crystals.get(i).render(sb);
		//terrain.render(sb);
		sb.setProjectionMatrix(hudCam.combined);
		hud.render(sb);
	}
	
	protected void updateEntities(float dt){
		player.update(dt);
		for(int i=0;i<crystals.size;i++) crystals.get(i).update(dt);
		hud.update(dt);
	}
	
	protected void createWorld(){
		//load tiled map:
		tileMap=new TmxMapLoader().load("maps/test.tmx");
		tmr=new OrthogonalTiledMapRenderer(tileMap);
		tileSize= tileMap.getProperties().get("tilewidth",Integer.class);
		
		//create world:
		worldEntity=new WorldEntity(world);
		worldEntity.createLayer((TiledMapTileLayer) tileMap.getLayers().get("red"),B2DVars.BIT_GROUND,tileSize,"ground");
		worldEntity.createLayer((TiledMapTileLayer) tileMap.getLayers().get("green"),B2DVars.BIT_GROUND,tileSize,"ground");
		worldEntity.createLayer((TiledMapTileLayer) tileMap.getLayers().get("blue"),B2DVars.BIT_GROUND,tileSize,"ground");
		worldEntity.createLayer(tileMap.getLayers().get("terrain").getObjects(),B2DVars.BIT_GROUND,tileSize,"ground");
	}
	
	protected void cleanupWorld(){
		//remove bodies from world (if necessary):
		for(Body body:cl.getBodiesToRemove()){
			if(body.getUserData().getClass().toString().equals("class com.mygdx.entities.CrystalEntity")){
				crystals.removeValue((CrystalEntity)body.getUserData(), true);
				player.collectCrystal();
			}
			world.destroyBody(body);
		}
		cl.getBodiesToRemove().clear();
	}
}