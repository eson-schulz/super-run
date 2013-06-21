package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.me.arun.SuperRun;
import com.me.model.Player.State;
import com.me.model.World;
import com.me.model.World.Stage;
import com.me.view.WorldRenderer;

public class GameScreen implements Screen {
	private SuperRun game;
	
	private World world;
	private WorldRenderer renderer;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.6f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		updateJump();
		world.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		world.stage = Stage.PAUSED;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	private void updateJump(){
		if(Gdx.input.isTouched()){
			if(world.player.state == State.RUNNING){
				world.player.jump();
			}
			else if(world.player.state == State.JUMPING){
				world.player.continueJumping();
			}
		}
		else{
			if(world.player.state == State.JUMPING){
				world.player.stopJumping();
			}
		}
	}

	public GameScreen(SuperRun g){
		game = g;
		world = new World();
		renderer = new WorldRenderer(world);
	}
}
