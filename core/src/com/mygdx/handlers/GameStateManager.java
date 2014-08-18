package com.mygdx.handlers;

import java.util.Stack;

import com.mygdx.game.BlockBunnyMain;
import com.mygdx.globals.StateVars;
import com.mygdx.states.GameState;
import com.mygdx.states.MenuState;
import com.mygdx.states.PlayState;

public class GameStateManager {
	private BlockBunnyMain game;
	private Stack<GameState> gameStates;
	public GameStateManager(BlockBunnyMain game,int state){
		this.game=game;
		gameStates=new Stack<GameState>();
		pushState(state);
	}

	public BlockBunnyMain getGame(){
		return game;
	}
	
	public void update(float dt){
		gameStates.peek().update(dt);
	}
	
	public void render(){
		gameStates.peek().render();
		
	}
	private GameState getState(int state){
		if(state==StateVars.MENU) return new MenuState(this);
		if(state==StateVars.PLAY) return new PlayState(this);
		return null;
	}
	
	public void setState(int state){
		popState();
		pushState(state);
	}
	
	public void pushState(int state){
		gameStates.push(getState(state));
	}
	public void popState(){
		GameState g=gameStates.pop();
		g.dispose();
	}
}