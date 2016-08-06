package com.me.birdskilled.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.me.birdskilled.ingame.Environment;
import com.me.birdskilled.ingame.HUD;
import com.me.utillities.InputListener;

public class Ingame implements Screen{
	
	Game game;
	SpriteBatch spriteBatch;
	InputListener input;
	
	Environment environment;
	HUD hud;
	
	Music bgm;
	Sound click;
	
	Sprite bg_exit, exit_yes, exit_no, tmp_bg = null;
	Rectangle yes, no;
	
	boolean exiting = false, no_hovered = false, yes_hovered = false, incoming = true;
	
	
	public Ingame(Game game, InputListener input){
		this.game = game;
		this.input = input;
	}
	

	@Override
	public void render(float delta) {
		
		if(incoming){
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			incoming = false;
		}
		
		if(exiting){
			bgm.setVolume(0.2f);
			
			if(tmp_bg == null){
				
				spriteBatch.begin();
					environment.render(delta, spriteBatch, input);
					hud.updateOnlyHud(spriteBatch, input, delta);
				spriteBatch.end();
				
				tmp_bg = new Sprite(ScreenUtils.getFrameBufferTexture());
			}
			
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			spriteBatch.begin();
			
				tmp_bg.draw(spriteBatch);
			
				bg_exit.draw(spriteBatch);
				exit_yes.draw(spriteBatch);
				exit_no.draw(spriteBatch);
				
				hud.updateOnlyMouse(spriteBatch, input, delta);
				
			spriteBatch.end();
			
			float x = input.getX(), y = input.getY();
			
			if(no.contains(x, y)){
				exit_no.setScale(1.01f);
				if(!no_hovered){
					no_hovered = true;
					click.setVolume(click.play(), 0.7f);
				}
			}else{
				exit_no.setScale(1f);
				if(no_hovered) no_hovered = false;
			}
			
			if(yes.contains(x, y)){
				exit_yes.setScale(1.01f);
				if(!yes_hovered){
					yes_hovered = true;
					click.setVolume(click.play(), 0.7f);
				}
			}else{
				exit_yes.setScale(1f);
				if(yes_hovered) yes_hovered = false;
			}
			
			if(no.contains(x, y) && input.isLeftMouseButtonPressed() || input.isKeyPressed(Keys.ESCAPE)){
				tmp_bg.getTexture().dispose();
				tmp_bg = null;
				exiting = false;
				bgm.setVolume(0.6f);
				return;
			}
			
			if(yes.contains(x, y) && input.isLeftMouseButtonPressed() || input.isKeyPressed(Keys.ENTER)){
				game.setScreen(new MainMenu(game, input, null));
			}
			
			return;
		}

		if(input.isKeyPressed(Keys.ESCAPE)){
			if(!exiting) exiting = true;
		}
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		
			environment.render(delta, spriteBatch, input);
			hud.render(spriteBatch, input, delta);
		
		spriteBatch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		
		environment = new Environment();
		hud = new HUD(environment.getBirds(), game);
		
		bgm = Gdx.audio.newMusic(Gdx.files.internal("data/ingame/bgm_ingame.mp3"));
		bgm.setLooping(true);
		bgm.setVolume(0.6f);
		bgm.play();
		
		click = Gdx.audio.newSound(Gdx.files.internal("data/button_click.mp3"));
		
		bg_exit = new Sprite(new Texture(Gdx.files.internal("data/ingame/bg_really.png")));
		bg_exit.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		exit_yes = new Sprite(new Texture(Gdx.files.internal("data/ingame/bg_yes.png")));
		exit_yes.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		exit_no = new Sprite(new Texture(Gdx.files.internal("data/ingame/bg_no.png")));
		exit_no.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		yes = new Rectangle(158, Gdx.graphics.getHeight() - 384, 88, 57);
		no = new Rectangle(463, Gdx.graphics.getHeight() - 380, 84, 47);
	}

	@Override
	public void hide() {
		this.dispose();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {	
		hud.dispose();
		environment.dispose();
		
		spriteBatch.dispose();
		
		bgm.stop();
		bgm.dispose();
		
		bg_exit.getTexture().dispose();
		exit_yes.getTexture().dispose();
		exit_no.getTexture().dispose();
		
	}

}
