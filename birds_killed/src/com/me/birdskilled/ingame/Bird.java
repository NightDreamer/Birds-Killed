package com.me.birdskilled.ingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Bird {
	
	Sprite bird_body, bird_headshot, headshot_anim[], wound, wings[];
	Rectangle hitbox_body, hitbox_head;
	
	boolean dir_right, alive = true, headshot = false;
	int layer;
	float size = 32f, x, y, pos_step = 75f, x_wound;
	
	// timer
	int anim_hs = 0, wing_state = 0;
	
	
	public Bird(boolean spawn_in_screen, float left_level_border, float right_level_border){
		dir_right = MathUtils.randomBoolean();
		layer = MathUtils.random(1, 9);
		if(layer >= 2 && layer <= 4) layer = 2;
		else if(layer >= 5 && layer <= 9) layer = 3;
		else layer = 1;
		
		if(spawn_in_screen){
			x = MathUtils.random(-2400, 2400f);
			y = MathUtils.random(128, Gdx.graphics.getHeight() - 128 - layer*size);
		}else{
			if(dir_right){
				float range = (0 - left_level_border) + 2*layer*size;
				x = 0 - MathUtils.random(range);
				y = MathUtils.random(128, Gdx.graphics.getHeight() - layer*size);
			}else{
				float range = right_level_border + layer*size - Gdx.graphics.getWidth();
				x = Gdx.graphics.getWidth() + MathUtils.random(range);
				y = MathUtils.random(128, Gdx.graphics.getHeight() - 128 - layer*size);
			}
		}
		
		// body
		
		bird_body = new Sprite(new Texture(Gdx.files.internal("data/ingame/bird/bird_1_body.png")));
		bird_body.setSize(layer*size, layer*size);
		bird_body.setOrigin(bird_body.getWidth()/2, bird_body.getHeight()/2);
		bird_body.setPosition(x, y);
		
		bird_headshot = new Sprite(new Texture(Gdx.files.internal("data/ingame/bird/bird_1_body_headshot.png")));
		bird_headshot.setSize(layer*size, layer*size);
		bird_headshot.setOrigin(bird_body.getWidth()/2, bird_body.getHeight()/2);
		bird_headshot.setPosition(x, y);
		
		// utils
		
		wound = new Sprite(new Texture(Gdx.files.internal("data/ingame/wound.png")));
		wound.setSize(layer*8, layer*8);
		wound.setOrigin(wound.getWidth()/2, wound.getHeight()/2);
		
		// anims
		
		headshot_anim = new Sprite[3];
		for(int i = 0; i < 3; i++){
			headshot_anim[i] = new Sprite(new Texture(Gdx.files.internal("data/ingame/bird/headshot_anim_"+(i+1)+".png")));
			headshot_anim[i].setSize(layer*21, layer*21);
			headshot_anim[i].setOrigin(headshot_anim[i].getWidth()/2, headshot_anim[i].getHeight()/2);
		}
		
		
		wings = new Sprite[8];
		for(int i = 0; i < 8; i++){
			wings[i] = new Sprite(new Texture(Gdx.files.internal("data/ingame/bird/wings_little_0"+(i+1)+".png")));
			wings[i].setSize(layer*size, layer*size);
			wings[i].setPosition(bird_body.getX(), bird_body.getY());
		}
		
		
		// hitboxes
		
		if(dir_right){
			bird_body.flip(true, false);
			bird_headshot.flip(true, false);
			wound.flip(true, false);
			for(int i = 0; i < 3; i++) headshot_anim[i].flip(true, false);
			for(int i = 0; i < 8; i++) wings[i].flip(true, false);
			
			hitbox_body = new Rectangle(bird_body.getX() + bird_body.getWidth()*0.15f,
										bird_body.getY() + bird_body.getHeight()*0.25f,
										bird_body.getWidth()*0.55f, bird_body.getHeight()*0.6f);
			hitbox_head = new Rectangle(hitbox_body.x + hitbox_body.width/2,
										hitbox_body.y + hitbox_body.height/2,
										hitbox_body.width/2, hitbox_body.height/2);
			
		}else{
			
			hitbox_body = new Rectangle(bird_body.getX() + bird_body.getWidth()*0.3f,
										bird_body.getY() + bird_body.getHeight()*0.25f,
										bird_body.getWidth()*0.55f, bird_body.getHeight()*0.6f);
			hitbox_head = new Rectangle(hitbox_body.x,
										hitbox_body.y + hitbox_body.height/2,
										hitbox_body.width/2, hitbox_body.height/2);
			
		}
		
		
	}
	
	public void render(float delta, SpriteBatch spriteBatch){
		this.update(delta);
		
		if(alive){
			// set pos of and draw normal bird
			bird_body.setX(x);
			bird_body.draw(spriteBatch);
			
			// set pos of and draw wings
			if(wing_state < 6){
				wings[0].setX(bird_body.getX());
				wings[0].draw(spriteBatch);
			}else if(wing_state < 12){
				wings[1].setX(bird_body.getX());
				wings[1].draw(spriteBatch);
			}else if(wing_state < 18){
				wings[2].setX(bird_body.getX());
				wings[2].draw(spriteBatch);
			}else if(wing_state < 24){
				wings[3].setX(bird_body.getX());
				wings[3].draw(spriteBatch);
			}else if(wing_state < 30){
				wings[4].setX(bird_body.getX());
				wings[4].draw(spriteBatch);
			}else if(wing_state < 36){
				wings[5].setX(bird_body.getX());
				wings[5].draw(spriteBatch);
			}else if(wing_state < 42){
				wings[6].setX(bird_body.getX());
				wings[6].draw(spriteBatch);
			}else if(wing_state < 48){
				wings[7].setX(bird_body.getX());
				wings[7].draw(spriteBatch);
			}else if(wing_state < 54){
				wings[6].setX(bird_body.getX());
				wings[6].draw(spriteBatch);
			}else if(wing_state < 60){
				wings[5].setX(bird_body.getX());
				wings[5].draw(spriteBatch);
			}else if(wing_state < 66){
				wings[4].setX(bird_body.getX());
				wings[4].draw(spriteBatch);
			}else if(wing_state < 72){
				wings[3].setX(bird_body.getX());
				wings[3].draw(spriteBatch);
			}else if(wing_state < 78){
				wings[2].setX(bird_body.getX());
				wings[2].draw(spriteBatch);
			}else if(wing_state < 84){
				wings[1].setX(bird_body.getX());
				wings[1].draw(spriteBatch);
			}
			
			// handle wings
			if(wing_state < 84) wing_state++;
			else{
				wing_state = 0;
			}
			
			// handle hitboxes
			if(dir_right){
				hitbox_body.setX(bird_body.getX() + bird_body.getWidth()*0.15f);
				hitbox_head.setX(hitbox_body.x + hitbox_body.width/2);
			}else{
				hitbox_body.setX(bird_body.getX() + bird_body.getWidth()*0.3f);
				hitbox_head.setX(hitbox_body.x);
			}
			
		}else
			if(headshot){
				bird_headshot.setX(x);
				bird_headshot.setY(y);
				bird_headshot.draw(spriteBatch);
				wings[0].setX(x);
				wings[0].setY(y);
				wings[0].draw(spriteBatch);
				
				if(anim_hs < 4){
					headshot_anim[0].setPosition(bird_headshot.getX() + bird_headshot.getWidth()/2 - headshot_anim[0].getWidth()/2, bird_headshot.getY() + bird_headshot.getHeight()/2);
					headshot_anim[0].draw(spriteBatch);
				}else if(anim_hs < 8){
					headshot_anim[1].setPosition(bird_headshot.getX() + bird_headshot.getWidth()/2 - headshot_anim[1].getWidth()/2, bird_headshot.getY() + bird_headshot.getHeight()/2);
					headshot_anim[1].draw(spriteBatch);
				}else if(anim_hs < 12){
					headshot_anim[2].setPosition(bird_headshot.getX() + bird_headshot.getWidth()/2 - headshot_anim[2].getWidth()/2, bird_headshot.getY() + bird_headshot.getHeight()/2);
					headshot_anim[2].draw(spriteBatch);
				}
				if(anim_hs < 12) anim_hs++;
				else{
					y -= 2*delta*pos_step*layer;
					bird_headshot.setOrigin(bird_headshot.getWidth()/2, bird_headshot.getHeight()/2);
					wings[0].setOrigin(wings[0].getWidth()/2, wings[0].getHeight()/2);
					if(dir_right){
						bird_headshot.rotate(-90f*delta);
						wings[0].rotate(-90f*delta);
					}
					else{
						bird_headshot.rotate(90f*delta);
						wings[0].rotate(90f*delta);
					}
				}
				
			}else{
				float move = 2*delta*pos_step;
				y -= move*layer;
				
				bird_body.setX(x);
				bird_body.setY(y);
				wound.setX(x + x_wound);
				wound.setY(wound.getY() - move*layer);
				wings[0].setX(x);
				wings[0].setY(y);
				
				bird_body.draw(spriteBatch);
				wings[0].draw(spriteBatch);
				wound.draw(spriteBatch);
				
			}
	}
	
	private void update(float delta){
		float move = pos_step*delta;
		
		if(dir_right){
			switch(layer){
				case 1:
					x += move*4;
					if(!alive) wound.setX(wound.getX() + move*4);
					break;
				case 2:
					x += move*3;
					if(!alive) wound.setX(wound.getX() + move*3);
					break;
				case 3:
					x += move*2;
					if(!alive) wound.setX(wound.getX() + move*2);
					break;
			}
		}else{
			switch(layer){
				case 1:
					x -= move*4;
					if(!alive) wound.setX(wound.getX() - move*4);
					break;
				case 2:
					x -= move*3;
					if(!alive) wound.setX(wound.getX() - move*3);
					break;
				case 3:
					x -= move*2;
					if(!alive) wound.setX(wound.getX() - move*2);
					break;
			}
		}
	
	}
	
	public int layer(){
		return layer;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getWidth(){
		return layer*size;
	}
	
	public float getHeight(){
		return layer*size;
	}
	
	public void setX(float x){
		this.x = x;
	}

	public void dispose(){
		bird_body.getTexture().dispose();
		bird_headshot.getTexture().dispose();
		wound.getTexture().dispose();
		for(int i = 0; i < headshot_anim.length; i++){
			headshot_anim[i].getTexture().dispose();
		}
		for(int i = 0; i < wings.length; i++){
			wings[i].getTexture().dispose();
		}
	}
	
	public boolean hit(float x_pointer, float y_pointer){
		if(!alive) return false;
		if(hitbox_body.contains(x_pointer, y_pointer)){
			alive = false;
			if(hitbox_head.contains(x_pointer, y_pointer)){
				headshot = true;
				return true;
			}
			wound.setPosition(x_pointer - wound.getWidth()/2, y_pointer - wound.getHeight()/2);
			x_wound = wound.getX() - bird_body.getX();
			return true;
		}
		return false;
	}
	
	public boolean isFacingRight(){
		return dir_right;
	}
	
	public Rectangle getBodyBounds(){
		return hitbox_body;
	}
	
	public Rectangle getHeadBounds(){
		return hitbox_head;
	}
	
}
