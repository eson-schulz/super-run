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
		if(stage == Stage.GOING){
			if(player.isDone()){
				stage = Stage.DONE;
			}
			else{
				player.update(delta);
				checkCollisions(delta);
				updatePlatforms(delta);
			}
		}
	}
	
	private void updatePlatforms(float delta){
		//so we won't get an exception when removing platforms
		ArrayList<Platform> allPlatforms = new ArrayList<Platform>(platforms);
		for(Platform p : allPlatforms){
			if(p.bounds.x + p.bounds.width < (player.bounds.x - WORLD_WIDTH / 2.1)){
				platforms.remove(p);
				createPlatform(allPlatforms.get(allPlatforms.size() - 1));
			}
		}
	}
	
	private void createPlatform(Platform previousPlat){
		//Approximate time in seconds player can jump
		float airTime = 0.88f;
		//Adjusts the platform
		float randomDif = (float) (Math.random() * 5f);
		//Adjusts the height of the platform
		float randomHeightDif = 0f;
		if(randomDif > 2.5 && previousPlat.bounds.height < 10){
			randomHeightDif = (float) (Math.random() * 1.5);
		}
		else if(previousPlat.bounds.height > 2.1){
			randomHeightDif = (float) (Math.random() * -2);
		}
		platforms.add(new Platform(previousPlat.bounds.x + previousPlat.bounds.width + (player.velocity.x * airTime) - randomDif, previousPlat.bounds.height + randomHeightDif));
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
		Platform oldPlat = new Platform(1, 5);
		platforms.add(oldPlat);
		for(int i = 0; i < 5; i++){
				createPlatform(oldPlat);
				oldPlat = platforms.get(platforms.size() - 1);
		}
		
		stage = Stage.GOING;
	}
}
