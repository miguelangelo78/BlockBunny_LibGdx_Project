package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BlockBunnyMain;
import com.mygdx.globals.B2DVars;

public class HUDEntity extends EntityManager{
	private PlayerEntity player;
	private TextureRegion[]hudBlocks;
	private final int hudBlockCount=3;
	private int game_width,game_height;
	
	public HUDEntity(PlayerEntity player,World world){
		this.player=player;
		Texture tex=BlockBunnyMain.res.getTexture("hud");
		hudBlocks=new TextureRegion[hudBlockCount];
		for(int i=0;i<hudBlockCount;i++) hudBlocks[i]=new TextureRegion(tex,32+(i*16),0,16,16);
		game_width=BlockBunnyMain.WIDTH;
		game_height=BlockBunnyMain.HEIGHT;
	}
	public void render(SpriteBatch sb){
		sb.begin();
		sb.draw(hudBlocks[0],20,game_height-16);
		sb.draw(hudBlocks[1],36,game_height-16);
		sb.draw(hudBlocks[2],52,game_height-16);
		sb.end();
	}
}
