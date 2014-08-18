package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.globals.B2DVars;
import com.mygdx.handlers.Animation;

public class EntityManager {
	protected Body body;
	protected Animation animation;
	protected float width,height;
	
	public EntityManager(Body body){
		this.body=body;
		animation=new Animation();
	}
	public EntityManager(){
		animation=new Animation();
	}
	
	public void setAnimation(TextureRegion[] reg,float delay){
		animation.setFrames(reg, delay);
		width=reg[0].getRegionWidth();
		height=reg[0].getRegionHeight();
	}
	public Animation getAnimation(){
		return animation;
	}
	
	public void update(float dt){
		animation.update(dt);
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		sb.draw(animation.getFrame(),
				body.getPosition().x*B2DVars.PPM-width/2,
				body.getPosition().y*B2DVars.PPM-height/2);
		sb.end();
	}
	
	public void setBody(Body body){
		this.body=body;
	}
	public Body getBody(){
		return body;
	}
	public Vector2 getPosition(){
		return body.getPosition();
	}
	public float getWidth(){
		return width;
	}
	public float getHeight(){
		return height;
	}
}
