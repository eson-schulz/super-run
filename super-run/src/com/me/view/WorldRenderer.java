package com.me.view;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	
	private float cameraX;
	
	private TextureRegion playerTexture;
	private TextureRegion buildingTexture;
	
	private SpriteBatch batch;
	
	private BitmapFont font;
	
	private OrthographicCamera cam;
	
	public void render(){
		cameraX = (world.player.bounds.x + World.WORLD_WIDTH / 3);
		cam.position.set(cameraX * ppuX, World.WORLD_HEIGHT / 2 * ppuY, 0);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.enableBlending();
		drawPlayer();
		drawScore();
		batch.disableBlending();
		drawPlatforms();
		batch.end();
	}
	
	private void drawPlayer(){
		batch.draw(playerTexture, world.player.bounds.x * ppuX, world.player.bounds.y * ppuY,  world.player.bounds.width * ppuX, world.player.bounds.height * ppuY);
	}
	
	private void drawPlatforms(){
		for(Platform p : world.platforms){
			if(onScreen(p)){
				batch.draw(buildingTexture, p.bounds.x * ppuX, p.bounds.y * ppuY, p.bounds.width * ppuX, p.bounds.height * ppuY);
			}
		}
	}
	
	private void drawScore(){
		float xLoc = (World.WORLD_WIDTH / 1.3f + world.player.bounds.x);
		if(Float.parseFloat(world.score) > 100){
			xLoc = (World.WORLD_WIDTH / 1.32f + world.player.bounds.x);
		}
		font.draw(batch, world.score, xLoc * ppuX, ppuY * World.WORLD_HEIGHT - font.getCapHeight());
	}
	
	private boolean onScreen(Platform p){
		if(p.bounds.x + p.bounds.width > cameraX - World.WORLD_WIDTH / 2 && p.bounds.x < cameraX + World.WORLD_WIDTH / 2){
			return true;
		}
		else return false;
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
		font = new BitmapFont();
		font.setScale(2f);
		font.setUseIntegerPositions(false);
	}
}
