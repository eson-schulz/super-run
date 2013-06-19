package com.me.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.me.model.Player.State;

public class World {
	public static final float WORLD_WIDTH = 20;
	public static final float WORLD_HEIGHT = 15;
	
	public Player player;
	public Stage stage;
	public ArrayList<Platform> platforms;
	
	public enum Stage{
		PAUSED, GOING, DONE
	}
	
	public void update(float delta){
		if(stage == Stage.GOING){
			checkCollisions(delta);
			player.update(delta);
		}
	}
	
	public void updatePlatforms(float delta){
		
	}
	
	private void checkCollisions(float delta){
		//float newX = player.bounds.x + player.velocity.x * delta;
		Rectangle newBounds; // = new Rectangle(newX, player.bounds.y, player.bounds.width, player.bounds.height);
//		if(player.state != State.RUNNING){	
//			for(int i = 0; i < platforms.size(); i++){
//				if(inBounds(newBounds, platforms.get(i).bounds)){
//					player.hitSide(platforms.get(i).bounds.x - player.bounds.width - 0.01f);
//					break;
//				}
//			}
//		}
		
		float newY = player.bounds.y + player.velocity.y * delta;
		newBounds = new Rectangle(player.bounds.x, newY, player.bounds.width, player.bounds.height);
		for(int i = 0; i < platforms.size(); i++){
			if(inBounds(newBounds, platforms.get(i).bounds)){
				player.hitTop(platforms.get(i).bounds.y + platforms.get(i).bounds.height + 0.01f);
				break;
			}
			if(i == platforms.size() - 1){
				if(player.state != State.JUMPING){
					player.state = State.FALLING;
				}
			}
		}
	}
	
	private boolean inBounds(Rectangle r1, Rectangle r2){
		if(r1.x + r1.width > r2.x && r1.x < r2.x + r2.width){
			if(r1.y + r1.height > r2.y && r1.y < r2.y + r2.height){
				return true;
			}
		}
		return false;
	}
	
	public World(){
		player = new Player(this, 1, 10);
		platforms = new ArrayList<Platform>();
		platforms.add(new Platform(0));
		platforms.add(new Platform(7));
		
		stage = Stage.GOING;
	}
}
