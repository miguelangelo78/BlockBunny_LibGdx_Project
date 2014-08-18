package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.globals.StateVars;
import com.mygdx.handlers.CINput;
import com.mygdx.handlers.GameStateManager;

public class MenuState extends GameState{

	public MenuState(GameStateManager gsm) {
		super(gsm);
	
	}

	public void handleInput() {
		if(CINput.isPressed(CINput.BUTTON2)){
			gsm.setState(StateVars.PLAY);
		}
	}

	public void update(float dt) {
		handleInput();
		world.step(dt, 8, 2);
		updateEntities(dt);
		cleanupWorld();
	}

	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glClearColor(.7f, .7f,.5f, 0);
		sb.setProjectionMatrix(cam.combined);
		
	}

	public void debugRenderer() {
		if(debugMode){
			b2dr.render(world, box2dcam.combined);
		}
	}

	public void dispose() {
		
	}

	protected void createWorld() {
		
	}

	protected void createEntities() {
		
	}

	protected void renderEntities() {
		
	}

	protected void updateEntities(float dt) {
		
	}

	protected void cleanupWorld() {
		
	}
}