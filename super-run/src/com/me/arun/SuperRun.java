package com.me.arun;

import com.badlogic.gdx.Game;
import com.me.screens.GameScreen;

public class SuperRun extends Game{

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
	
}