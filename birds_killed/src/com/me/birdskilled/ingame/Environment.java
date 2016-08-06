package com.me.birdskilled.ingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.me.utillities.InputListener;

public class Environment {
	
	Sprite bg, layer[];
	Array<Bird> birds;
	
	public Environment(){
		birds = new Array<Bird>();
		for(int i = 0; i < 5; i++){
			Bird tmp = new Bird(true, 0, 0);
			birds.add(tmp);
		}
		
		bg = new Sprite(new Texture(Gdx.files.internal("data/ingame/background.png")));
		bg.setSize(Gdx.graphics.getWidth()*4, Gdx.graphics.getHeight());
		bg.setPosition(0 - bg.getWidth()/3, 0);
		bg.setOrigin(bg.getWidth()/2, bg.getHeight()/2);
		
		layer = new Sprite[3];
		
		layer[0] = new Sprite(new Texture(Gdx.files.internal("data/ingame/layer_1.png")));
		layer[0].setSize(Gdx.graphics.getWidth()*4, Gdx.graphics.getHeight());
		layer[0].setPosition(0 - layer[0].getWidth()/3, 0);
		layer[0].setOrigin(layer[0].getWidth()/2, layer[0].getHeight()/2);
		
		layer[1] = new Sprite(new Texture(Gdx.files.internal("data/ingame/layer_2.png")));
		layer[1].setSize(Gdx.graphics.getWidth()*4, Gdx.graphics.getHeight());
		layer[1].setPosition(0 - layer[1].getWidth()/3, 0);
		layer[1].setOrigin(layer[1].getWidth()/2, layer[1].getHeight()/2);
		
		layer[2] = new Sprite(new Texture(Gdx.files.internal("data/ingame/layer_3.png")));
		layer[2].setSize(Gdx.graphics.getWidth()*4, Gdx.graphics.getHeight());
		layer[2].setPosition(0 - layer[2].getWidth()/3, 0);
		layer[2].setOrigin(layer[2].getWidth()/2, layer[2].getHeight()/2);
		
	}
	
	public void render(float delta, SpriteBatch spriteBatch, InputListener input){
		this.update(delta, input);
		
		for(int i = 0; i < 3; i++) layer[i].setX(bg.getX());
		
		bg.draw(spriteBatch);
		
		for(Bird tmp : birds){
			if(tmp.layer == 1) tmp.render(delta, spriteBatch);
		}
		
		layer[0].draw(spriteBatch);
		
		for(Bird tmp : birds){
			if(tmp.layer == 2) tmp.render(delta, spriteBatch);
		}
		
		layer[1].draw(spriteBatch);
		
		for(Bird tmp : birds){
			if(tmp.layer == 3) tmp.render(delta, spriteBatch);
		}
		
		layer[2].draw(spriteBatch);
		
	}
	
	private void update(float delta, InputListener input){
		float movement = delta*300;
		
		for(Bird tmp : birds){
			if(tmp.isFacingRight() && tmp.getX() > bg.getX()+bg.getWidth()){
				tmp.dispose();
				birds.removeIndex(birds.indexOf(tmp, true));
				birds.add(new Bird(false, bg.getX(), bg.getX()+bg.getWidth()));
				continue;
			}
			else if(!tmp.isFacingRight() && tmp.getX()+tmp.getWidth() < bg.getX()){
				tmp.dispose();
				birds.removeIndex(birds.indexOf(tmp, true));
				birds.add(new Bird(false, bg.getX(), bg.getX()+bg.getWidth()));
				continue;
			}
			if(tmp.getY()+tmp.getHeight() < 0){
				tmp.dispose();
				birds.removeIndex(birds.indexOf(tmp, true));
				birds.add(new Bird(false, bg.getX(), bg.getX()+bg.getWidth()));
				continue;
			}
		}
		
		if(input.getX() < 10){
			bg.setX(bg.getX() + movement);
			
			
			// level border
			if(bg.getX() > 0){
				bg.setX(0);
				return;
			}
			
			for(Bird tmp : birds){
				tmp.setX(tmp.getX() + movement);
			}
		}
		else if(input.getX() > Gdx.graphics.getWidth() - 10){
			bg.setX(bg.getX() - movement);
			
			// level border
			if(bg.getX() + bg.getWidth() < Gdx.graphics.getWidth()){
				bg.setX(Gdx.graphics.getWidth() - bg.getWidth());
				return;
			}
			
			for(Bird tmp : birds){
				tmp.setX(tmp.getX() - movement);
			}
		}
		
	}
	
	public Array<Bird> getBirds(){
		return birds;
	}
	
	public void dispose(){
		for(Bird tmp : birds) tmp.dispose();
		birds.clear();
		
		for(int i = 0; i < layer.length; i++){
			layer[i].getTexture().dispose();
		}
		
		bg.getTexture().dispose();
	}

}
