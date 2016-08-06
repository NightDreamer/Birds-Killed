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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.utillities.InputListener;

public class MainMenu implements Screen {

	Game game;
	InputListener input;
	SpriteBatch spriteBatch;
	Sprite cursor, font_birds, font_killed, font_v10, credits, start_button, highscore_button, exit_button;
	Array<Sprite> cracks = new Array<Sprite>();
	Sound fx_shot, fx_click;
	Music bgm;
	Rectangle start, highscore, exit;
	HighscoreMenu highscore_menu;
	
	boolean intro_finished = false, anim1_finished = false, anim2_finished = false, anim3_finished = false, start_button_hovered = false, highscore_button_hovered = false, exit_button_hovered = false;
	boolean show_highscore = false;
	
	float button_visibillity;
	
	public MainMenu(Game game, InputListener input, Sprite cursor){
		this.game = game;
		this.input = input;
		fx_click = Gdx.audio.newSound(Gdx.files.internal("data/button_click.mp3"));
		if(cursor != null) this.cursor = cursor;
		highscore_menu = new HighscoreMenu(false, 0, input, fx_click);
		
	}
	
	public MainMenu(Game game, boolean show_highscore, int points){
		this.game = game;
		this.input = new InputListener();
		this.show_highscore = show_highscore;
		fx_click = Gdx.audio.newSound(Gdx.files.internal("data/button_click.mp3"));
		if(!show_highscore) highscore_menu = new HighscoreMenu(false, 0, input, fx_click);
		else highscore_menu = new HighscoreMenu(true, points, input, fx_click);
	}
	
	@Override
	public void render(float delta) {
		if(show_highscore){
			update_highscore(delta);
			draw_highscore(delta);
			return;
		}
		
		update(delta);
		draw(delta);
		
	}
	
	private void draw_highscore(float delta){
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		
			highscore_menu.render(spriteBatch);
			cursor.draw(spriteBatch);
			
		spriteBatch.end();
			
	}
	
	private void update_highscore(float delta){
		// keyboard input
		if(input.isKeyPressed(Keys.ESCAPE)){
			show_highscore = false;
		}
		
		// mouse input
		cursor.rotate(200*delta);
		// x
		if(input.getX() <= 0f){
			Gdx.input.setCursorPosition(0, (int)input.getY());
		}
		else if(input.getX() >= Gdx.graphics.getWidth()){
			Gdx.input.setCursorPosition(Gdx.graphics.getWidth(), (int)input.getY());
		}
		// y
		if(input.getY() <= 0f){
			Gdx.input.setCursorPosition((int)input.getX(), 0);
		}
		else if(input.getY() >= Gdx.graphics.getHeight()){
			Gdx.input.setCursorPosition((int)input.getX(), Gdx.graphics.getHeight());
		}
		// set cursor position
		cursor.setPosition(input.getX() - cursor.getWidth()/2, input.getY() - cursor.getHeight()/2);
		
		if(input.isLeftMouseButtonPressed()){
			
			Sprite tmp = new Sprite(new Texture(Gdx.files.internal("data/crack_0"+MathUtils.random(1, 3)+".png")));
			tmp.setSize(16, 16);
			tmp.setPosition(input.getX() - tmp.getWidth()/2, input.getY() - tmp.getHeight()/2);
			highscore_menu.cracks.add(tmp);
			fx_shot.setVolume(fx_shot.play(), 0.7f);
			
			if(highscore_menu.back_button_hovered){
				show_highscore = false;
			}
			
			if(highscore_menu.ok_button_hovered){
				highscore_menu.setNewHighscore();
			}
		}
		
	}
	
	private void update(float delta){		
		if(!intro_finished){
			
			if(!anim1_finished){
				
				if(font_birds.getX() < 144){
					
					font_birds.setX(font_birds.getX() + (delta*2048));
					if(font_birds.getX() > 144){
						font_birds.setX(144);
						fx_shot.setVolume(fx_shot.play(), 0.65f);
					}
					return;
					
				}else{
					
					anim1_finished = true;
					return;
					
				}
				
			}
			
			if(!anim2_finished){
				
				if(font_killed.getX() > 400){
					
					font_killed.setX(font_killed.getX() - (delta*2048));
					if(font_killed.getX() < 400){
						font_killed.setX(400);
						fx_shot.setVolume(fx_shot.play(), 0.65f);
					}
					return;
					
				}else{
					
					anim2_finished = true;
					return;
					
				}
			}
			
			if(!anim3_finished){
				
				if(font_v10.getY() < 15){
					
					font_v10.setY(font_v10.getY() + (delta*768));
					if(font_v10.getY() > 15){
						font_v10.setY(15);
						fx_shot.setVolume(fx_shot.play(), 0.65f);
					}
					return;
					
				}else{
					
					anim3_finished = true;
					return;
					
				}
				
			}
			
			intro_finished = true;
			Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
			return;
			
		}else{
			
			if(button_visibillity < 1.0f){
				
				start_button.setColor(1.0f, 1.0f, 1.0f, button_visibillity);
				exit_button.setColor(1.0f, 1.0f, 1.0f, button_visibillity);
				highscore_button.setColor(1.0f, 1.0f, 1.0f, button_visibillity);
				button_visibillity += delta*1.0f;
				
			}
			
		}
		
		// keyboard input
		if(input.isKeyPressed(Keys.ESCAPE)){
			Gdx.app.exit();
		}
		
		// mouse input
		cursor.rotate(200*delta);
		// x
		if(input.getX() <= 0f){
			Gdx.input.setCursorPosition(0, (int)input.getY());
		}
		else if(input.getX() >= Gdx.graphics.getWidth()){
			Gdx.input.setCursorPosition(Gdx.graphics.getWidth(), (int)input.getY());
		}
		// y
		if(input.getY() <= 0f){
			Gdx.input.setCursorPosition((int)input.getX(), 0);
		}
		else if(input.getY() >= Gdx.graphics.getHeight()){
			Gdx.input.setCursorPosition((int)input.getX(), Gdx.graphics.getHeight());
		}
		// set cursor position
		cursor.setPosition(input.getX() - cursor.getWidth()/2, input.getY() - cursor.getHeight()/2);
		
		// button hover
		float x = input.getX(), y = input.getY();
		
		if(intro_finished){
			
			if(start.contains(x, y)){
				start_button.setScale(1.04f);
				if(!start_button_hovered){
					start_button_hovered = true;
					fx_click.setVolume(fx_click.play(), 0.9f);
				}
			}else{
				start_button.setScale(1.0f);
				if(start_button_hovered) start_button_hovered = false;
			}
			
			if(highscore.contains(x, y)){
				highscore_button.setScale(1.055f);
				if(!highscore_button_hovered){
					highscore_button_hovered = true;
					fx_click.setVolume(fx_click.play(), 0.9f);
				}
			}else{
				highscore_button.setScale(1.0f);
				if(highscore_button_hovered) highscore_button_hovered = false;
			}
		
			if(exit.contains(x, y)){
				exit_button.setScale(1.04f);
				if(!exit_button_hovered){
					exit_button_hovered = true;
					fx_click.setVolume(fx_click.play(), 0.9f);
				}
			}else{
				exit_button.setScale(1.0f);
				if(exit_button_hovered) exit_button_hovered = false;
			}
			
		}

		
		// mouse clicks
		if(input.isLeftMouseButtonPressed()){
			
			Sprite tmp = new Sprite(new Texture(Gdx.files.internal("data/crack_0"+MathUtils.random(1, 3)+".png")));
			tmp.setSize(16, 16);
			tmp.setPosition(x - tmp.getWidth()/2, y - tmp.getHeight()/2);
			cracks.add(tmp);
			fx_shot.setVolume(fx_shot.play(), 0.7f);
			
			if(start.contains(x, y)){
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				this.dispose();
				game.setScreen(new Ingame(game, input));
			}else if(highscore.contains(x, y)){
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				show_highscore = true;
			}else if(exit.contains(x, y)){
				Gdx.app.exit();
			}
			
		}
		
	}
	
	private void draw(float delta){
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		
			font_birds.draw(spriteBatch);
			font_killed.draw(spriteBatch);
			font_v10.draw(spriteBatch);
			
			if(intro_finished){
				start_button.draw(spriteBatch);
				highscore_button.draw(spriteBatch);
				exit_button.draw(spriteBatch);
			}
			
			for(Sprite sprite : cracks){
				sprite.draw(spriteBatch);
			}
			
			credits.draw(spriteBatch);
			
			if(intro_finished){				
				cursor.draw(spriteBatch);
			}
			
		spriteBatch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// graphics
		spriteBatch = new SpriteBatch();
		
		
		cursor = new Sprite(new Texture(Gdx.files.internal("data/cursor.png")));
		cursor.setSize(32, 32);
		cursor.setPosition(Gdx.graphics.getWidth()/2 - cursor.getWidth()/2, Gdx.graphics.getHeight()/2 - cursor.getHeight()/2);
		cursor.setOrigin(cursor.getWidth()/2, cursor.getHeight()/2);
		Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
		
		font_birds = new Sprite(new Texture(Gdx.files.internal("data/mainmenu/birds_art.png")));
		font_birds.setSize(256, 256);
		font_birds.setPosition(0 - font_birds.getWidth(), 143);
		
		font_killed = new Sprite(new Texture(Gdx.files.internal("data/mainmenu/killed_art.png")));
		font_killed.setSize(256, 256);
		font_killed.setPosition(Gdx.graphics.getWidth(), 143);
		
		font_v10 = new Sprite(new Texture(Gdx.files.internal("data/mainmenu/v10_art.png")));
		font_v10.setSize(128, 128);
		font_v10.setPosition(Gdx.graphics.getWidth()/2 - font_v10.getWidth()/2, 0 - font_v10.getHeight());
		
		credits = new Sprite(new Texture(Gdx.files.internal("data/credits.png")));
		credits.setSize(288, 288);
		
		// Buttons
		button_visibillity = 0.0f;
		
		start_button = new Sprite(new Texture(Gdx.files.internal("data/mainmenu/start_button.png")));
		start_button.setSize(64, 64);
		start_button.setPosition(Gdx.graphics.getWidth()/6 - start_button.getWidth()/2, Gdx.graphics.getHeight()-100);
		start_button.setOrigin(start_button.getWidth()/2, start_button.getHeight()/2);
		start_button.setColor(1f, 1f, 1f, button_visibillity);
		
		highscore_button = new Sprite(new Texture(Gdx.files.internal("data/mainmenu/highscore_button.png")));
		highscore_button.setSize(64, 64);
		highscore_button.setPosition((Gdx.graphics.getWidth()/6)*3 - highscore_button.getWidth()/2, Gdx.graphics.getHeight()-100);
		highscore_button.setOrigin(highscore_button.getWidth()/2, highscore_button.getHeight()/2);
		highscore_button.setColor(1f, 1f, 1f, button_visibillity);
		
		exit_button = new Sprite(new Texture(Gdx.files.internal("data/mainmenu/exit_button.png")));
		exit_button.setSize(64, 64);
		exit_button.setPosition((Gdx.graphics.getWidth()/6)*5 - exit_button.getWidth()/2, Gdx.graphics.getHeight()-100);
		exit_button.setOrigin(exit_button.getWidth()/2, exit_button.getHeight()/2);
		exit_button.setColor(1f, 1f, 1f, button_visibillity);
		
		// music and sounds
		fx_shot = Gdx.audio.newSound(Gdx.files.internal("data/fx_shot.wav"));
		
		
		bgm = Gdx.audio.newMusic(Gdx.files.internal("data/mainmenu/bgm_mainmenu.mp3"));
		bgm.setLooping(true);
		bgm.setVolume(0.8f);
		bgm.play();
		
		start = start_button.getBoundingRectangle();
		highscore = highscore_button.getBoundingRectangle();
		exit = exit_button.getBoundingRectangle();
		
		
	}

	@Override
	public void hide() {
		
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
		font_birds.getTexture().dispose();
		font_killed.getTexture().dispose();
		font_v10.getTexture().dispose();
		
		cursor.getTexture().dispose();
		
		for(Sprite tmp : cracks){
			tmp.getTexture().dispose();
		}
		cracks.clear();
		
		credits.getTexture().dispose();
		
		spriteBatch.dispose();
		
		fx_shot.stop();
		fx_shot.dispose();
		
		fx_click.stop();
		fx_click.dispose();
		
		start_button.getTexture().dispose();
		exit_button.getTexture().dispose();
		
		bgm.stop();
		bgm.dispose();
		
	}

}
