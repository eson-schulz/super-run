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
			player.update(delta);
			checkCollisions(delta);
		}
	}
	
	public void updatePlatforms(float delta){
		
	}
	
	private void checkCollisions(float delta){
		Rectangle newBounds;
		
		float newY = player.bounds.y + player.velocity.y * delta;
		newBounds = new Rectangle(player.bounds.x, newY, player.bounds.width, player.bounds.height);
		
		for(int i = 0; i < platforms.size(); i++){
			if(inBounds(newBounds, platforms.get(i).bounds)){
				player.hitTop(platforms.get(i).bounds.y + platforms.get(i).bounds.height + 0.001f);
				break;
			}
			if(i == platforms.size() - 1){
				if(player.state != State.JUMPING){
					player.state = State.FALLING;
				}
			}
		}
		
		float newX = player.bounds.x + player.velocity.x * delta;
		newBounds = new Rectangle(newX, player.bounds.y, player.bounds.width, player.bounds.height);
		for(int i = 0; i < platforms.size(); i++){
			if(inBounds(newBounds, platforms.get(i).bounds)){
				player.hitSide(platforms.get(i).bounds.x - player.bounds.width - 0.01f);
				break;
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
		player = new Player(this, 1, 15);
		platforms = new ArrayList<Platform>();
		for(int i = 0; i < 40; i++){
			if(i < 6){
				platforms.add(new Platform(1 + i * 9));
			}
			else if(i >= 6 && i < 8){
				platforms.add(new Platform(1 + i * 10));
			}
			else if(i >= 8 && i <= 12){
				platforms.add(new Platform(1 + i * 11));
			}
			else if(i > 12 && i < 15){
				platforms.add(new Platform(1 + i * 12));
			}
			else platforms.add(new Platform(1 + i * 13));
		}
		
		stage = Stage.GOING;
	}
}
