package com.mygdx.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class CustomInputProcessor extends InputAdapter{
	
	public boolean keyDown(int k){
		if(k==Keys.Z){
			CustomInput.setKey(CustomInput.BUTTON1,true);
		}
		if(k==Keys.X){
			CustomInput.setKey(CustomInput.BUTTON2,true);
		}
		return true;
	}
	
	public boolean keyUp(int k){
		if(k==Keys.Z){
			CustomInput.setKey(CustomInput.BUTTON1,false);
		}
		if(k==Keys.X){
			CustomInput.setKey(CustomInput.BUTTON2,false);
		}
		return true;
	}
}
