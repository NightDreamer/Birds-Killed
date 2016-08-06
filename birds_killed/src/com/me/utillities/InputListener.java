package com.me.utillities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;

public class InputListener {
	
	boolean[] toggle;
	boolean[] mouse_toggle;
	
	public InputListener(){
		toggle = new boolean[256];
		for(int i = 0; i < 256; i++){
			toggle[i] = false;
		}
		
		mouse_toggle = new boolean[2];
		for(int i = 0; i < 2; i++){
			mouse_toggle[i] = false;
		}
	}
	
	public boolean isKeyPressed(int key){
		if(Gdx.input.isKeyPressed(key)){
			if(!toggle[key]){
				toggle[key] = true;
				return true;
			}else{
				return false;
			}
		}else{
			toggle[key] = false;
			return false;
		}
	}
	
	public boolean isLeftMouseButtonPressed(){
		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
			if(!mouse_toggle[0]){
				mouse_toggle[0] = true;
				return true;
			}else{
				return false;
			}
		}else{
			mouse_toggle[0] = false;
			return false;
		}
	}
	
	public boolean isRightMouseButtonPressed(){
		if(Gdx.input.isButtonPressed(Buttons.RIGHT)){
			if(!mouse_toggle[1]){
				mouse_toggle[1] = true;
				return true;
			}else{
				return false;
			}
		}else{
			mouse_toggle[1] = false;
			return false;
		}
	}
	
	public float getX(){
		return Gdx.input.getX();
	}
	
	public float getY(){
		return Gdx.graphics.getHeight() - Gdx.input.getY();
	}

}
