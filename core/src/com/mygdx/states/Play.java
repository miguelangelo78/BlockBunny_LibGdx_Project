package com.mygdx.states;

import jdk.nashorn.internal.ir.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BlockBunnyMain;
import com.mygdx.handlers.CustomContactListener;
import com.mygdx.handlers.GameStateManager;

import static com.mygdx.handlers.B2DVars.*;

public class Play extends GameState{

	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera box2dcam;
	
	//TEMPORARY:
	BitmapFont font;
	SpriteBatch batch;
	
	
	public Play(GameStateManager gsm) {
		super(gsm);
		font = new BitmapFont();
		batch = new SpriteBatch();
		
		world=new World(new Vector2(0,-9.81f),true);
		world.setContactListener(new CustomContactListener());
		b2dr=new Box2DDebugRenderer();
		
		//create platform
		BodyDef bdef=new BodyDef();
		bdef.position.set(200/PPM,100/PPM);
		bdef.type=BodyType.StaticBody;
		Body body= world.createBody(bdef);
		
		PolygonShape shape=new PolygonShape();
		shape.setAsBox(50/PPM, 5/PPM);
		FixtureDef fdef=new FixtureDef();
		fdef.restitution=0.5f;
		fdef.shape=shape;
		fdef.filter.categoryBits=BIT_GROUND; //collide category
		fdef.filter.maskBits=BIT_BOX | BIT_BALL; //allowed to collide
		body.createFixture(fdef).setUserData("platform");
		
		// create falling box
		bdef.position.set(160/PPM,500/PPM);
		bdef.type=BodyType.DynamicBody;
		body=world.createBody(bdef);
		shape.setAsBox(10/PPM, 10/PPM);
		fdef.shape=shape;
		fdef.filter.categoryBits=BIT_BOX; //collide category
		fdef.filter.maskBits=BIT_GROUND;  //allowed to collide
		body.createFixture(fdef).setUserData("box");
		
		//create sphere
		bdef.position.set(175/PPM,600/PPM);
		body=world.createBody(bdef);
		CircleShape circle_shape=new CircleShape();
		circle_shape.setRadius(10/PPM);
		fdef.shape=circle_shape; //collide category
		fdef.filter.maskBits=BIT_GROUND; //allowed to collide
		fdef.filter.categoryBits=BIT_BALL;
		body.createFixture(fdef).setUserData("sphere");
			
		// set up camera
		box2dcam=new OrthographicCamera();
		box2dcam.setToOrtho(false,BlockBunnyMain.WIDTH/PPM,BlockBunnyMain.HEIGHT/PPM);
	}

	public void handleInput() {
		
	}

	public void update(float dt) {
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
		batch.begin();
		font.draw(batch,getOrientationStr(),100, 300);
		batch.end();
	}

	public void dispose() {
		
	}	
}