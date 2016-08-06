package com.me.birdskilled.ingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.me.birdskilled.screens.HighscoreMenu;
import com.me.birdskilled.screens.MainMenu;
import com.me.utillities.InputListener;

public class HUD {
	
	Game game;
	
	Sprite ammunition, crosshair, numbers[], str_time, str_points, str_doppelpunkt;
	Sound shot_fx, no_ammo_fx, reloading_fx;
	Array<Bird> birds;
	TextureRegion[][] region;
	
	int available_ammo = 5, points = 0;
	float time = 75f;
	
	public HUD(Array<Bird> birds, Game game){
		this.game = game;
		this.birds = birds;
		
		ammunition = new Sprite(new Texture(Gdx.files.internal("data/ingame/hud/ammo_v2.png")));
		ammunition.setSize(128, 128);
		ammunition.setOrigin(ammunition.getWidth()/2, ammunition.getHeight()/2);
		
		Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		crosshair = new Sprite(new Texture(Gdx.files.internal("data/cursor.png")));
		crosshair.setSize(32, 32);
		crosshair.setPosition(Gdx.graphics.getWidth()/2 - crosshair.getWidth() / 2, Gdx.graphics.getHeight()/2 - crosshair.getHeight() / 2);
		crosshair.setOrigin(crosshair.getWidth()/2, crosshair.getHeight()/2);
		
		shot_fx = Gdx.audio.newSound(Gdx.files.internal("data/fx_shot.wav"));
		
		region = TextureRegion.split(new Texture(Gdx.files.internal("data/numbers.png")), 36, 52);
		numbers = new Sprite[10];
		numbers[0] = new Sprite(region[1][2]);
		numbers[1] = new Sprite(region[0][0]);
		numbers[2] = new Sprite(region[0][1]);
		numbers[3] = new Sprite(region[0][2]);
		numbers[4] = new Sprite(region[0][3]);
		numbers[5] = new Sprite(region[0][4]);
		numbers[6] = new Sprite(region[0][5]);
		numbers[7] = new Sprite(region[0][6]);
		numbers[8] = new Sprite(region[1][0]);
		numbers[9] = new Sprite(region[1][1]);
		for(int i = 0; i < numbers.length; i++){
			numbers[i].setSize(36, 50);
			numbers[i].setOrigin(numbers[i].getWidth()/2, numbers[i].getHeight()/2);
		}
		
		str_time = new Sprite(new Texture(Gdx.files.internal("data/ingame/hud/string_time.png")));
		str_time.setSize(128, 128);
		str_time.setPosition(Gdx.graphics.getWidth() - str_time.getWidth()*2, Gdx.graphics.getHeight() - str_time.getHeight()/1.5f);
		str_time.setOrigin(str_time.getWidth()/2, str_time.getHeight()/2);
		
		str_points = new Sprite(new Texture(Gdx.files.internal("data/ingame/hud/string_points.png")));
		str_points.setSize(128, 128);
		str_points.setPosition(0, Gdx.graphics.getHeight() - str_points.getHeight()/1.5f);
		str_points.setOrigin(str_points.getWidth()/2, str_points.getHeight()/2);
		
		str_doppelpunkt = new Sprite(new Texture(Gdx.files.internal("data/ingame/hud/string_doppelpunkt.png")));
		str_doppelpunkt.setSize(128, 128);
		str_doppelpunkt.setPosition(Gdx.graphics.getWidth() - str_doppelpunkt.getWidth()*1.2f, Gdx.graphics.getHeight() - str_doppelpunkt.getHeight()/1.5f);
		str_doppelpunkt.setOrigin(str_doppelpunkt.getWidth()/2, str_doppelpunkt.getHeight()/2);
		
	}
	
	public void render(SpriteBatch spriteBatch, InputListener input, float delta){
		this.update(input, delta);
		
		for(int i = 0; i < available_ammo; i++){
			ammunition.setX(Gdx.graphics.getWidth() - 96 - i*38);
			ammunition.draw(spriteBatch);
		}
		
		str_points.draw(spriteBatch);
		
		String tmp_points = String.valueOf(points);
		for(int i = 0; i < tmp_points.length(); i++){
			int tmp = Integer.valueOf(tmp_points.charAt(i)) - 48;
			numbers[tmp].setPosition(str_points.getWidth() + 4 + i*20, Gdx.graphics.getHeight() - 46);
			numbers[tmp].draw(spriteBatch);
		}
		
		int mins = 0, secs = 0;
		for(int i = 0; i < MathUtils.roundPositive(time); i++){
			secs++;
			if(secs == 60){
				mins++;
				secs = 0;
			}
		}
		
		String tmp_mins = String.valueOf(mins);
		for(int i = 0; i < tmp_mins.length(); i++){
			int tmp = Integer.valueOf(tmp_mins.charAt(i)) - 48;
			numbers[tmp].setPosition(str_doppelpunkt.getX() + str_doppelpunkt.getWidth()/2 - 30 + i*20, Gdx.graphics.getHeight() - 46);
			numbers[tmp].draw(spriteBatch);
		}
		
		str_doppelpunkt.draw(spriteBatch);
		
		String tmp_secs = String.valueOf(secs);
		if(secs < 10) tmp_secs = "0"+tmp_secs;
		for(int i = 0; i < tmp_secs.length(); i++){
			int tmp = Integer.valueOf(tmp_secs.charAt(i)) - 48;
			numbers[tmp].setPosition(str_doppelpunkt.getX() + 8 + str_doppelpunkt.getWidth()/2 + i*20, Gdx.graphics.getHeight() - 46);
			numbers[tmp].draw(spriteBatch);
		}
		
		crosshair.draw(spriteBatch);
		
	}
	
	public void updateOnlyHud(SpriteBatch spriteBatch, InputListener input, float delta){
		
		str_points.draw(spriteBatch);
		
		String tmp_points = String.valueOf(points);
		for(int i = 0; i < tmp_points.length(); i++){
			int tmp = Integer.valueOf(tmp_points.charAt(i)) - 48;
			numbers[tmp].setPosition(str_points.getWidth() + 4 + i*20, Gdx.graphics.getHeight() - 46);
			numbers[tmp].draw(spriteBatch);
		}
		
		int mins = 0, secs = 0;
		for(int i = 0; i < MathUtils.roundPositive(time); i++){
			secs++;
			if(secs == 60){
				mins++;
				secs = 0;
			}
		}
		
		String tmp_mins = String.valueOf(mins);
		for(int i = 0; i < tmp_mins.length(); i++){
			int tmp = Integer.valueOf(tmp_mins.charAt(i)) - 48;
			numbers[tmp].setPosition(str_doppelpunkt.getX() + str_doppelpunkt.getWidth()/2 - 30 + i*20, Gdx.graphics.getHeight() - 46);
			numbers[tmp].draw(spriteBatch);
		}
		
		str_doppelpunkt.draw(spriteBatch);
		
		String tmp_secs = String.valueOf(secs);
		if(secs < 10) tmp_secs = "0"+tmp_secs;
		for(int i = 0; i < tmp_secs.length(); i++){
			int tmp = Integer.valueOf(tmp_secs.charAt(i)) - 48;
			numbers[tmp].setPosition(str_doppelpunkt.getX() + 8 + str_doppelpunkt.getWidth()/2 + i*20, Gdx.graphics.getHeight() - 46);
			numbers[tmp].draw(spriteBatch);
		}
	}
	
	public void updateOnlyMouse(SpriteBatch spriteBatch, InputListener input, float delta){
		// x
		if (input.getX() <= 0f) {
			Gdx.input.setCursorPosition(0, (int) input.getY());
		} else if (input.getX() >= Gdx.graphics.getWidth()) {
			Gdx.input.setCursorPosition(Gdx.graphics.getWidth(), (int) input.getY());
		}
		// y
		if (input.getY() <= 0f) {
			Gdx.input.setCursorPosition((int) input.getX(), 0);
		} else if (input.getY() >= Gdx.graphics.getHeight()) {
			Gdx.input.setCursorPosition((int) input.getX(), Gdx.graphics.getHeight());
		}
		// set cursor position
		crosshair.setPosition(input.getX() - crosshair.getWidth() / 2, input.getY() - crosshair.getHeight() / 2);
		
		crosshair.draw(spriteBatch);
	}
	
	private void update(InputListener input, float delta){
		if(input.isKeyPressed(Keys.F)) points = 2355;
		// x
		if (input.getX() <= 0f) {
			Gdx.input.setCursorPosition(0, (int) input.getY());
		} else if (input.getX() >= Gdx.graphics.getWidth()) {
			Gdx.input.setCursorPosition(Gdx.graphics.getWidth(), (int) input.getY());
		}
		// y
		if (input.getY() <= 0f) {
			Gdx.input.setCursorPosition((int) input.getX(), 0);
		} else if (input.getY() >= Gdx.graphics.getHeight()) {
			Gdx.input.setCursorPosition((int) input.getX(), Gdx.graphics.getHeight());
		}
		// set cursor position
		crosshair.setPosition(input.getX() - crosshair.getWidth() / 2, input.getY() - crosshair.getHeight() / 2);
		
		if(input.isLeftMouseButtonPressed()) shoot(input.getX(), input.getY());
		if(input.isRightMouseButtonPressed()) reload();
		
		time -= delta;
		if(time < 0){
			game.setScreen(new MainMenu(game, true, points));
		}
	}
	
	public void shoot(float x, float y){
		if(available_ammo > 0){
			available_ammo--;
			shot_fx.play();
			for(Bird tmp : birds){
				if(tmp.hit(x, y)){
					if(tmp.headshot){
						points += (4-tmp.layer())*50;
					}else{
						switch(tmp.layer()){
							case 1:
								points += 30;
								break;
							case 2:
								points += 15;
								break;
							case 3:
								points += 5;
								break;
						}
					}
					break;
				}
			}
		}else{
//			no_ammo_fx.play();
		}
	}
	
	public void reload(){
//		reloading_fx.play();
		points -= available_ammo*25;
		if(points < 0) points = 0;
		available_ammo = 5;
	}
	
	public void addPoints(int points){
		this.points += points;
	}
	
	public void dispose(){
		birds = null;
		
		shot_fx.stop();
		shot_fx.dispose();
		
		str_time.getTexture().dispose();
		str_points.getTexture().dispose();
		str_doppelpunkt.getTexture().dispose();
		
		for(int i = 0; i < numbers.length; i ++){
			numbers[i].getTexture().dispose();
		}
		
		ammunition.getTexture().dispose();
		crosshair.getTexture().dispose();
		
	}

}
