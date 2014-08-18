package com.mygdx.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class CContactListener implements ContactListener{
	private int numFootContacts;
	private Array<Body> bodiesToRemove;
	
	public CContactListener(){
		super();
		bodiesToRemove=new Array<Body>();
	}
	
	private boolean collisionBetween(Contact contact,String object1, String object2){
		Fixture fa=contact.getFixtureA();
		Fixture fb=contact.getFixtureB();
		if(fa==null || fb==null) return false;
		return ((fa.getUserData()!=null && fa.getUserData().equals(object1) && fb.getUserData()!=null && fb.getUserData().equals(object2)) ||
				(fa.getUserData()!=null && fa.getUserData().equals(object2) && fb.getUserData()!=null && fb.getUserData().equals(object1)));
	}
	
	public void beginContact(Contact contact) {
		//WRITE ALL COLLISIONS
		if(collisionBetween(contact, "foot","ground")) numFootContacts++;
		
		if(collisionBetween(contact,"player","crystal")) //remove crystal:
			if(contact.getFixtureA().getUserData().equals("player")) bodiesToRemove.add(contact.getFixtureB().getBody());
			else bodiesToRemove.add(contact.getFixtureA().getBody());
	}

	public void endContact(Contact contact) {
		if(collisionBetween(contact, "ground","foot")) numFootContacts--;
	}

	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
	
	public Array<Body> getBodiesToRemove(){
		return bodiesToRemove;
	}
	
	public boolean isPlayerOnGround(){
		return numFootContacts>0;
	}
}