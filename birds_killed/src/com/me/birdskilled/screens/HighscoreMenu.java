package com.me.birdskilled.screens;

import java.io.*;

import javax.swing.JDialog;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.me.utillities.InputListener;

public class HighscoreMenu {
	
	Sprite bg, enter_name, ok_button, back_button;
	String[] names = new String[5];
	String[] points = new String[5];
	Array<Sprite> cracks = new Array<Sprite>();
	InputListener input;
	BitmapFont font;
	int new_place;
	Document file;
	boolean new_highscore = false, enter_name_visible = false, back_button_hovered = false, ok_button_hovered = false;
	String new_name ="";
	Sound fx_click;
	
	
	public HighscoreMenu(boolean new_highscore, int new_points, InputListener input, Sound fx_click){
		this.new_highscore = new_highscore;
		this.input = input;
		this.fx_click = fx_click;
		
		bg = new Sprite(new Texture(Gdx.files.internal("data/highscoremenu/highscore_menu.png")));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		enter_name = new Sprite(new Texture(Gdx.files.internal("data/highscoremenu/enter_name.png")));
		enter_name.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		ok_button = new Sprite(new Texture(Gdx.files.internal("data/highscoremenu/ok_button.png")));
		ok_button.setSize(64, 64);
		ok_button.setPosition((Gdx.graphics.getWidth() - ok_button.getWidth())/2, Gdx.graphics.getHeight()/2 - (ok_button.getHeight()*1.15f));
		ok_button.setOrigin(ok_button.getWidth()/2, ok_button.getHeight()/2);
		
		back_button = new Sprite(new Texture(Gdx.files.internal("data/highscoremenu/back_button.png")));
		back_button.setSize(64, 64);
		back_button.setPosition(25, Gdx.graphics.getHeight() - 25 - 56);
		back_button.setOrigin(back_button.getWidth()/2, back_button.getHeight()/2);
		
		file = read_xml_file();
		
		font = new BitmapFont();
		font.setColor(Color.RED);
		font.setScale(1.035f);
		
		if(new_highscore && new_points < Integer.valueOf(points[4])) this.new_highscore = false;
		
		if(this.new_highscore){
			
			new_place = 0;
			for(int i = 0; i < 5; i++){
				if(new_points >= Integer.valueOf(points[i])){
					new_place = i;
					break;
				}
			}
			
			for(int i = 4; i > new_place; i--){
				points[i] = points[i-1];
				names[i] = names[i-1];
			}
			
			points[new_place] = String.valueOf(new_points);
			names[new_place] = "";
			
		}
		
	}
	
	public void render(SpriteBatch spriteBatch){
		bg.draw(spriteBatch);
		
		if(back_button.getBoundingRectangle().contains(input.getX(), input.getY())){
			back_button.setScale(1.055f);
			if(!back_button_hovered){
				back_button_hovered = true;
				fx_click.setVolume(fx_click.play(), 0.9f);
			}
		}else{
			back_button.setScale(1.0f);
			if(back_button_hovered) back_button_hovered = false;
		}
		
		back_button.draw(spriteBatch);
		
		font.draw(spriteBatch, names[0], 194, 335);
		font.draw(spriteBatch, "-", 270, 335);
		font.draw(spriteBatch, points[0], 310, 335);
		
		font.draw(spriteBatch, names[1], 194, 270);
		font.draw(spriteBatch, "-", 270, 270);
		font.draw(spriteBatch, points[1], 310, 270);
		
		font.draw(spriteBatch, names[2], 194, 205);
		font.draw(spriteBatch, "-", 270, 205);
		font.draw(spriteBatch, points[2], 310, 205);
		
		font.draw(spriteBatch, names[3], 194, 140);
		font.draw(spriteBatch, "-", 270, 140);
		font.draw(spriteBatch, points[3], 310, 140);
		
		font.draw(spriteBatch, names[4], 194, 75);
		font.draw(spriteBatch, "-", 270, 75);
		font.draw(spriteBatch, points[4], 310, 75);
		
		for(Sprite tmp : cracks){
			tmp.draw(spriteBatch);
		}
		
		
		if(new_highscore){
			
			enter_name.draw(spriteBatch);
			ok_button.draw(spriteBatch);
			
			getInput();
			
			font.draw(spriteBatch, new_name, 370, 325);
			
			if(ok_button.getBoundingRectangle().contains(input.getX(), input.getY())){
				ok_button.setScale(1.055f);
				if(!ok_button_hovered){
					ok_button_hovered = true;
					fx_click.setVolume(fx_click.play(), 0.9f);
				}
			}else{
				ok_button.setScale(1.0f);
				if(ok_button_hovered) ok_button_hovered = false;
			}
			
			if(input.isKeyPressed(Keys.ENTER)){
				setNewHighscore();
				return;
			}
			
		}
		
		
	}
	
	public void setNewHighscore(){
		names[new_place] = new_name;
		save_xml_file();
		new_highscore = false;
	}
	
	public void getInput(){
		
		
		if(input.isKeyPressed(Keys.BACKSPACE)){
			if(new_name.length() > 0) new_name = new_name.substring(0, new_name.length()-1);
		}
		
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)){
			
			if(input.isKeyPressed(Keys.A)) new_name = new_name+"A";
			if(input.isKeyPressed(Keys.B)) new_name = new_name+"B";
			if(input.isKeyPressed(Keys.C)) new_name = new_name+"C";
			if(input.isKeyPressed(Keys.D)) new_name = new_name+"D";
			if(input.isKeyPressed(Keys.E)) new_name = new_name+"E";
			if(input.isKeyPressed(Keys.F)) new_name = new_name+"F";
			if(input.isKeyPressed(Keys.G)) new_name = new_name+"G";
			if(input.isKeyPressed(Keys.H)) new_name = new_name+"H";
			if(input.isKeyPressed(Keys.I)) new_name = new_name+"I";
			if(input.isKeyPressed(Keys.J)) new_name = new_name+"J";
			if(input.isKeyPressed(Keys.K)) new_name = new_name+"K";
			if(input.isKeyPressed(Keys.L)) new_name = new_name+"L";
			if(input.isKeyPressed(Keys.M)) new_name = new_name+"M";
			if(input.isKeyPressed(Keys.N)) new_name = new_name+"N";
			if(input.isKeyPressed(Keys.O)) new_name = new_name+"O";
			if(input.isKeyPressed(Keys.P)) new_name = new_name+"P";
			if(input.isKeyPressed(Keys.Q)) new_name = new_name+"Q";
			if(input.isKeyPressed(Keys.R)) new_name = new_name+"R";
			if(input.isKeyPressed(Keys.S)) new_name = new_name+"S";
			if(input.isKeyPressed(Keys.T)) new_name = new_name+"T";
			if(input.isKeyPressed(Keys.U)) new_name = new_name+"U";
			if(input.isKeyPressed(Keys.V)) new_name = new_name+"V";
			if(input.isKeyPressed(Keys.W)) new_name = new_name+"W";
			if(input.isKeyPressed(Keys.X)) new_name = new_name+"X";
			if(input.isKeyPressed(Keys.Y)) new_name = new_name+"Y";
			if(input.isKeyPressed(Keys.Z)) new_name = new_name+"Z";
			
		}else{
			
			if(input.isKeyPressed(Keys.A)) new_name = new_name+"a";
			if(input.isKeyPressed(Keys.B)) new_name = new_name+"b";
			if(input.isKeyPressed(Keys.C)) new_name = new_name+"c";
			if(input.isKeyPressed(Keys.D)) new_name = new_name+"d";
			if(input.isKeyPressed(Keys.E)) new_name = new_name+"e";
			if(input.isKeyPressed(Keys.F)) new_name = new_name+"f";
			if(input.isKeyPressed(Keys.G)) new_name = new_name+"g";
			if(input.isKeyPressed(Keys.H)) new_name = new_name+"h";
			if(input.isKeyPressed(Keys.I)) new_name = new_name+"i";
			if(input.isKeyPressed(Keys.J)) new_name = new_name+"j";
			if(input.isKeyPressed(Keys.K)) new_name = new_name+"k";
			if(input.isKeyPressed(Keys.L)) new_name = new_name+"l";
			if(input.isKeyPressed(Keys.M)) new_name = new_name+"m";
			if(input.isKeyPressed(Keys.N)) new_name = new_name+"n";
			if(input.isKeyPressed(Keys.O)) new_name = new_name+"o";
			if(input.isKeyPressed(Keys.P)) new_name = new_name+"p";
			if(input.isKeyPressed(Keys.Q)) new_name = new_name+"q";
			if(input.isKeyPressed(Keys.R)) new_name = new_name+"r";
			if(input.isKeyPressed(Keys.S)) new_name = new_name+"s";
			if(input.isKeyPressed(Keys.T)) new_name = new_name+"t";
			if(input.isKeyPressed(Keys.U)) new_name = new_name+"u";
			if(input.isKeyPressed(Keys.V)) new_name = new_name+"v";
			if(input.isKeyPressed(Keys.W)) new_name = new_name+"w";
			if(input.isKeyPressed(Keys.X)) new_name = new_name+"x";
			if(input.isKeyPressed(Keys.Y)) new_name = new_name+"y";
			if(input.isKeyPressed(Keys.Z)) new_name = new_name+"z";
			
		}
		
	}
		

	public void dispose() {
		bg.getTexture().dispose();
		ok_button.getTexture().dispose();
		back_button.getTexture().dispose();
		enter_name.getTexture().dispose();
		font.dispose();
		
	}
	
	
	private Document read_xml_file(){
		try{
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse( new File("./data/data.xml") );
			
			NodeList list = doc.getFirstChild().getChildNodes();
			int order = 0;
			for(int i = 0; i < list.getLength(); i++){
				if(list.item(i).getNodeName() == "#text") continue;

				names[order] = list.item(i).getAttributes().getNamedItem("name").getNodeValue();
				points[order] = list.item(i).getAttributes().getNamedItem("points").getNodeValue();
				
				order++;
			}
			
			return doc;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void save_xml_file(){
		try{
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse( new File("./data/data.xml") );
			
			NodeList list = doc.getFirstChild().getChildNodes();
			int order = 0;
			for(int i = 0; i < list.getLength(); i++){
				if(list.item(i).getNodeName() == "#text") continue;

				doc.getFirstChild().getChildNodes().item(i).getAttributes().getNamedItem("name").setNodeValue(names[order]);
				doc.getFirstChild().getChildNodes().item(i).getAttributes().getNamedItem("points").setNodeValue(points[order]);
				
				order++;
			}
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("./data/data.xml"));
	 
			transformer.transform(source, result);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
