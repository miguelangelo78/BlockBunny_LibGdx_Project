package com.mygdx.entities;

import static com.mygdx.globals.B2DVars.*;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BlockBunnyMain;

public class CrystalEntity extends EntityManager{
	public CrystalEntity(Body body){
		super(body);
		TextureRegion[] sprites=TextureRegion.split(BlockBunnyMain.res.getTexture("crystal"),16,16)[0];
		setAnimation(sprites,1/12f);
	}
	public CrystalEntity(float x,float y,World world){
		BodyDef bdef=new BodyDef();
		CircleShape cshape=new CircleShape();
		FixtureDef fdef=new FixtureDef();
		
		//CONFIGURE BODY,SHAPE AND FIXTURES
		bdef.type=BodyType.StaticBody;
		bdef.position.set(x/PPM,y/PPM);
		body=world.createBody(bdef);
		
		cshape.setRadius(8/PPM); // CONSTANT RADIUS
		fdef.shape=cshape;
		fdef.isSensor=true;
		fdef.filter.categoryBits=BIT_CRYSTAL;
		fdef.filter.maskBits=BIT_PLAYER;
		body.createFixture(fdef).setUserData("crystal");
		cshape.dispose();
		
		TextureRegion[] sprites=TextureRegion.split(BlockBunnyMain.res.getTexture("crystal"), 16, 16)[0];
		setAnimation(sprites,1/12f);
		body.setUserData(this);
	}
}
