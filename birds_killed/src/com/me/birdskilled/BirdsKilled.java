package com.me.birdskilled;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.me.birdskilled.screens.MainMenu;

public class BirdsKilled extends Game {
	@Override
	public void create() {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.input.setCursorCatched(true);
		
		setScreen(new MainMenu(this, false, 0));
		
	}
	
}
