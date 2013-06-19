package com.me.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.model.Platform;
import com.me.model.World;

public class WorldRenderer{
	private World world;
	
	private float width;
	private float height;
	
	private float ppuX;
	private float ppuY;
	
	private TextureRegion playerTexture;
	private TextureRegion buildingTexture;
	
	private SpriteBatch batch;
	
	private OrthographicCamera cam;
	
	public void render(){
		batch.begin();
		batch.draw(playerTexture, world.player.bounds.x * ppuX, world.player.bounds.y * ppuY,  world.player.bounds.width * ppuX, world.player.bounds.height * ppuY);
		for(Platform p : world.platforms){
			batch.draw(buildingTexture, p.bounds.x * ppuX, p.bounds.y * ppuY, p.bounds.width * ppuX, p.bounds.height * ppuY);
		}
		batch.end();
	}
	
	public void resize(float x, float y){
		width = x;
		height = y;
		
		ppuX = x / World.WORLD_WIDTH;
		ppuY = y / World.WORLD_HEIGHT;
		
		this.cam = new OrthographicCamera(x, y);
		cam.update();
	}
	
	private void loadTextures(){
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/textures/textures.pack"));
		playerTexture = atlas.findRegion("player");
		buildingTexture = atlas.findRegion("building");
	}
	
	
	public WorldRenderer(World w){
		loadTextures();
	    world = w;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(width, height);
		cam.update();
		batch = new SpriteBatch();
	}
}
