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
		System.out.println(delta);
		if(delta > 0.1f){
			delta = 0.1f;
		}
		if(player.isDone()){
			stage = Stage.DONE;
		}
		else{
			player.update(delta);
			checkCollisions(delta);
			updatePlatforms(delta);
		}
	}
	
	private void updatePlatforms(float delta){
		//so we won't get an exception when removing platforms
		ArrayList<Platform> allPlatforms = new ArrayList<Platform>(platforms);
		for(Platform p : allPlatforms){
			if(p.bounds.x + p.bounds.width < (player.bounds.x + World.WORLD_WIDTH / 3) - World.WORLD_WIDTH / 2){
				platforms.remove(p);
				addPlatform(allPlatforms.get(allPlatforms.size() - 1));
			}
		}
	}
	
	private void addPlatform(Platform previousPlat){
		//Approximate time in seconds player can jump
		float airTime = 0.88f;
		//Adjusts the platform's x location
		float randomDif = (float) (Math.random() * (player.velocity.x * airTime));
		
		if(randomDif < 0.22f){
			randomDif = 0f;
		}
		
		//Adjusts the height of the platform
		float randomHeightDif = 0f;
		if(randomDif < (player.velocity.x * airTime) / 2 && previousPlat.bounds.height < 10){
			randomHeightDif = (float) (Math.random() * 1.5);
			if(randomHeightDif < 0.1){
				randomHeightDif = 0f;
			}
		}
		else if(previousPlat.bounds.height > 2.1){
			randomHeightDif = (float) (Math.random() * -2);
			if(randomHeightDif > -0.1){
				randomHeightDif = 0f;
			}
		}
		platforms.add(new Platform(previousPlat.bounds.x + previousPlat.bounds.width + randomDif, previousPlat.bounds.height + randomHeightDif));
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
				player.hitSide(platforms.get(i).bounds.x - player.bounds.width - 0.001f);
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
	public void reset(){
		player = new Player(this, 1, 5.2f);
		platforms = new ArrayList<Platform>();
		platforms.add(new Platform(1, 5));
		Platform lastPlatform;
		for(int i = 0; i < 5; i++){
				lastPlatform = platforms.get(i);
				addPlatform(lastPlatform);
		}
		stage = Stage.GOING;
	}
	
	public World(){
		reset();
	}
}
