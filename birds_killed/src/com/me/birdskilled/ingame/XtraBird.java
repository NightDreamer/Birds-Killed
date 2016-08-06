package com.me.birdskilled.ingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class XtraBird {
	
	Sprite body;
	
	
	public XtraBird(float left_level_border, float right_level_border){
		float x = 0;
		if(0-left_level_border > right_level_border-800){
			x = MathUtils.random(left_level_border, 0-128);
		}else{
			x = MathUtils.random(Gdx.graphics.getWidth(), right_level_border);
		}
		
		body = new Sprite(new Texture(Gdx.files.internal("")));
		body.setSize(128, 128);
		body.setOrigin(body.getWidth()/2, body.getHeight()/2);
		body.setPosition(x, 0);
	}
	
	
	public void render(float delta, SpriteBatch spriteBatch){
		this.update(delta);
		
		body.draw(spriteBatch);
		
	}
	
	private void update(float delta){
		
		
	}
	
	public void dispoase(){
		body.getTexture().dispose();
	}

}
