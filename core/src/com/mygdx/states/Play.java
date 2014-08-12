package com.mygdx.states;

import jdk.nashorn.internal.ir.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BlockBunnyMain;
import com.mygdx.handlers.CustomContactListener;
import com.mygdx.handlers.CustomInput;
import com.mygdx.handlers.GameStateManager;

import static com.mygdx.handlers.B2DVars.*;

public class Play extends GameState{

	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera box2dcam;
	private CustomContactListener cl;
	
	private TiledMap tileMap;
	private float tileSize;
	private OrthogonalTiledMapRenderer tmr;
	private Body playerBody;
	
	
	//TEMPORARY:
	BitmapFont font;
	SpriteBatch batch;
	
	
	public Play(GameStateManager gsm) {
		super(gsm);
		font = new BitmapFont();
		batch = new SpriteBatch();
		
		cl=new CustomContactListener();
		world=new World(new Vector2(0,-9.81f),true);
		world.setContactListener(cl);
		b2dr=new Box2DDebugRenderer();
		
		//world objects: 
		BodyDef bdef=new BodyDef();
		PolygonShape shape=new PolygonShape();
		FixtureDef fdef=new FixtureDef();
		
		// create player
		bdef.position.set(160/PPM,500/PPM);
		bdef.type=BodyType.DynamicBody;
		playerBody=world.createBody(bdef);
		shape.setAsBox(10/PPM, 10/PPM);
		fdef.shape=shape;
		fdef.filter.categoryBits=BIT_PLAYER; //collide category
		fdef.filter.maskBits=BIT_RED | BIT_GREEN | BIT_BLUE;  //allowed to collide
		playerBody.createFixture(fdef).setUserData("player");
		
		// create foot sensor
		//left foot
		shape.setAsBox(2/PPM, 2/PPM,new Vector2(-5/PPM,-12.5f/PPM),0);
		fdef.shape=shape;
		playerBody.createFixture(fdef).setUserData("left foot");
		//right foot
		shape.setAsBox(2/PPM, 2/PPM,new Vector2(5/PPM,-12.5f/PPM),0);
		fdef.shape=shape;
		fdef.isSensor=true;
		playerBody.createFixture(fdef).setUserData("right foot");
		
		// set up camera
		box2dcam=new OrthographicCamera();
		box2dcam.setToOrtho(false,BlockBunnyMain.WIDTH/PPM,BlockBunnyMain.HEIGHT/PPM);
		//load tiled map
		tileMap=new TmxMapLoader().load("maps/test.tmx");
		tmr=new OrthogonalTiledMapRenderer(tileMap);
		
		TiledMapTileLayer layer=(TiledMapTileLayer) tileMap.getLayers().get("red");
		tileSize=layer.getTileWidth();
		
		//go through all the cells in the layer
		for(int row=0;row<layer.getHeight();row++){
			for(int col=0;col<layer.getWidth();col++){
				Cell cell=layer.getCell(col, row);
				if(cell==null) continue; if(cell.getTile()==null) continue;
				//create a body + fixture from cell
				bdef.type=BodyType.StaticBody;
				bdef.position.set(new Vector2((col+.5f)*tileSize/PPM,(row+.5f)*tileSize/PPM));
				
				ChainShape cs=new ChainShape();
				Vector2[]v=new Vector2[3];
				v[0]=new Vector2(-tileSize/2/PPM,-tileSize/2/PPM);
				v[1]=new Vector2(-tileSize/2/PPM,tileSize/2/PPM);
				v[2]=new Vector2(tileSize/2/PPM,tileSize/2/PPM);
				cs.createChain(v);
				fdef.friction=0;
				fdef.shape=cs;
				fdef.filter.categoryBits=BIT_RED;
				fdef.filter.maskBits=-1;
				fdef.isSensor=false;
				world.createBody(bdef).createFixture(fdef);
				
			}
		}
	}

	public void handleInput() {
		if(CustomInput.isPressed(CustomInput.BUTTON1) && cl.isPlayerOnGround()){
			playerBody.applyForceToCenter(0,9.81f*30,true);
		}
		if(CustomInput.isDown(CustomInput.BUTTON2)){
		
		}
	}

	public void update(float dt) {
		handleInput();
		world.step(dt, 8, 2);
	}
	
	public String getOrientationStr(){
		StringBuilder builder=new StringBuilder();
		builder.append(" azimuth: "); builder.append((int)Gdx.input.getAzimuth());
		builder.append(" pitch: "); builder.append((int)Gdx.input.getPitch());
		builder.append(" roll: "); builder.append((int)Gdx.input.getRoll());
		return builder.toString();
	}
	
	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		b2dr.render(world, box2dcam.combined);
		tmr.setView(cam);
		tmr.render();
		
		batch.begin();
		font.draw(batch,getOrientationStr(),100, 300);
		batch.end();
	}

	public void dispose() {
		
	}	
}