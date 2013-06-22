package com.me.model;

import com.badlogic.gdx.math.Rectangle;

public class Platform {
	public Rectangle bounds;
	
	private static float randomNumber(){
		return (float)Math.random();
	}
	
	public Platform(float x, float height){
		bounds = new Rectangle();
		bounds.x = x;
		bounds.y = 0f;
		bounds.width = (randomNumber() * 12) + 2;
		bounds.height = height;
	}
}
