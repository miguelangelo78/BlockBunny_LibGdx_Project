package com.mygdx.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class CustomInputProcessor extends InputAdapter{
	
	public boolean keyDown(int k){
		if(k==Keys.SPACE) CINput.setKey(CINput.SPACE,true);
		if(k==Keys.X) CINput.setKey(CINput.BUTTON2,true);
		if(k==Keys.LEFT) CINput.setKey(CINput.LEFT,true);
		if(k==Keys.RIGHT) CINput.setKey(CINput.RIGHT,true);
		return true;
	}
	
	public boolean keyUp(int k){
		if(k==Keys.SPACE) CINput.setKey(CINput.SPACE,false);
		if(k==Keys.X) CINput.setKey(CINput.BUTTON2,false);
		if(k==Keys.LEFT) CINput.setKey(CINput.LEFT,false);
		if(k==Keys.RIGHT) CINput.setKey(CINput.RIGHT,false);
		return true;
	}
}
