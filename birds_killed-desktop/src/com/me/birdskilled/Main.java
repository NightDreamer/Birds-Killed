package com.me.birdskilled;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title		= "Bird's Killed v1.0";
		cfg.useGL20		= false;
		cfg.width		= 800;
		cfg.height		= 600;
		cfg.resizable	= false;
		
		new LwjglApplication(new BirdsKilled(), cfg);
	}
}
