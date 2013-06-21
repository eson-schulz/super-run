package com.me.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	public final float GRAVITY = -20f;
	public final float JUMP_VELOCITY = 6f;
	public final float MAX_JUMP_TIME = 0.3f;
	public final float MAX_VELOCITY_X = 20f;
	public final float MAX_VELOCITY_Y = 10f;
	
	public Rectangle bounds;
	
	public Vector2 acceleration;
	public Vector2 velocity;
	
	public State state;
	
	public float stateTime;
	
	public enum State{
		RUNNING, JUMPING, FALLING, DEAD
	}
	
	public void jump(){
		state = State.JUMPING;
		stateTime = 0f;
		velocity.y = JUMP_VELOCITY; 
	}
	
	public void hitSide(float newX){
		state = State.FALLING;
		velocity.x = 0f;
		if(velocity.y > 0){
			velocity.y = 0f;
		}
		acceleration.x = 0f;
		bounds.x = newX;
	}
	
	public void hitTop(float newY){
		state = State.RUNNING;
		velocity.y = 0f;
		bounds.y = newY;
	}
	
	public void continueJumping(){
		if(stateTime < MAX_JUMP_TIME){
			velocity.y = JUMP_VELOCITY;
		}
		else{
			state = State.FALLING;
		}
	}
	
	public void update(float delta){
//		if(state == State.RUNNING){
//			acceleration.y = 0f;
//		}
		
		velocity.add(acceleration.mul(delta));
		acceleration.mul(1 / delta);
		
		if(Math.abs(velocity.x) > MAX_VELOCITY_X){
			if(velocity.x < 0){
				velocity.x = -1 * MAX_VELOCITY_X;
			}
			else{
				velocity.x = MAX_VELOCITY_X;
			}
		}
		if(Math.abs(velocity.y) > MAX_VELOCITY_Y){
			if(velocity.y < 0){
				velocity.y = -1 * MAX_VELOCITY_Y;
			}
			else{
				velocity.y = MAX_VELOCITY_Y;
			}
		}
		
		bounds.x += (velocity.x * delta);
		bounds.y += (velocity.y * delta);
		
		acceleration.y = GRAVITY;
		
		stateTime += delta;
		System.out.println(state);
		System.out.println(bounds.y);
	}

	public Player(World w, float xStart, float yStart){
		bounds = new Rectangle();
		bounds.x = xStart;
		bounds.y = yStart;
		
		bounds.width = 0.2f;
		bounds.height = 0.4f;
		
		acceleration = new Vector2();
		acceleration.x = 0.5f;
		velocity = new Vector2();
		velocity.x = 1f;
		
		state = State.RUNNING;
		stateTime = 0f;
	}
}
