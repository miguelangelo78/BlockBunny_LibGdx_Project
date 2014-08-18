package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BlockBunnyMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title=BlockBunnyMain.TITLE;
		config.width=BlockBunnyMain.WIDTH;
		config.height=BlockBunnyMain.HEIGHT;
		new LwjglApplication(new BlockBunnyMain(), config);
	}
}