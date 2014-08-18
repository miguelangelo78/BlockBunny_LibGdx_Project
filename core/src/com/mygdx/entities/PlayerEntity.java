package com.mygdx.entities;
import static com.mygdx.globals.B2DVars.*;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BlockBunnyMain;

public class PlayerEntity extends EntityManager{

	//MIGHT IMPLEMENT IN ANOTHER CLASS:
	private int numCrystals;
	private int totalCrystals;
	private boolean framesFlipped;
	private boolean walking;
	
	public boolean isWalking() {
		return walking;
	}
	public void setWalking(boolean walking) {
		this.walking = walking;
	}
	public boolean isFramesFlipped() {
		return framesFlipped;
	}
	public void setFramesFlipped(boolean framesFlipped) {
		this.framesFlipped = framesFlipped;
	}
	public void collectCrystal(){
		numCrystals++;
	}
	public int getNumCrystals(){
		return numCrystals;
	}
	public void setTotalCrystals(int i){
		totalCrystals=i;
	}
	public int getTotalCrystals(){
		return totalCrystals;
	}
	
	public PlayerEntity(Body body) {
		super(body);
		//load sprites
		TextureRegion[] sprites=TextureRegion.split(BlockBunnyMain.res.getTexture("bunny"), 32, 32)[0];
		setAnimation(sprites,1/12f);
	}
	
	public void update(float dt){
		if(isWalking()) animation.update(dt);
		else animation.setCurrentFrame(0);
	}
	
	public PlayerEntity(float x,float y,World world){
		BodyDef bdef=new BodyDef();
		PolygonShape shape=new PolygonShape();
		FixtureDef fdef=new FixtureDef();
		
		bdef.position.set(x/PPM,y/PPM);
		bdef.type=BodyType.DynamicBody;
		body=world.createBody(bdef);
		shape.setAsBox(13/PPM, 13/PPM);
		fdef.shape=shape;
		fdef.filter.categoryBits=BIT_PLAYER; //collide category
		fdef.filter.maskBits=BIT_GROUND | BIT_CRYSTAL;  //allowed to collide
		body.createFixture(fdef).setUserData("player");
		
		// create foot sensor
		shape.setAsBox(13/PPM, 2/PPM,new Vector2(0/PPM,-13f/PPM),0);
		fdef.shape=shape;
		fdef.filter.maskBits=BIT_GROUND;
		body.createFixture(fdef).setUserData("foot");
		shape.dispose();
		
		TextureRegion[] sprites=TextureRegion.split(BlockBunnyMain.res.getTexture("bunny"), 32, 32)[0];
		setAnimation(sprites,1/12f);
		body.setUserData(this);
	}
}
