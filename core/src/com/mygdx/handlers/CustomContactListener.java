package com.mygdx.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CustomContactListener implements ContactListener{
	private boolean playerOnGround;
	
	public void beginContact(Contact contact) {
		Fixture fa=contact.getFixtureA();
		Fixture fb=contact.getFixtureB();
		
		if((fa.getUserData()!=null && fa.getUserData().equals("left foot")) || (fb.getUserData()!=null && fb.getUserData().equals("left foot")))
			playerOnGround=true;
		
	}

	public void endContact(Contact contact) {
		Fixture fa=contact.getFixtureA();
		Fixture fb=contact.getFixtureB();
		
		if((fa.getUserData()!=null && fa.getUserData().equals("left foot")) || (fb.getUserData()!=null && fb.getUserData().equals("left foot")))
			playerOnGround=false;
	}

	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
	
	public boolean isPlayerOnGround(){
		return playerOnGround;
	}
}