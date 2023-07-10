package com.droplet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu implements Screen {

  final DropletsHamburgers game;
  
	OrthographicCamera camera;
	Texture gamelogo;
	Texture devlogo;
	Texture menubckgrnd;

	public MainMenu(final DropletsHamburgers gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		gamelogo = new Texture(Gdx.files.internal("sprites/gamelogo.png"));
		devlogo = new Texture(Gdx.files.internal("sprites/devlogo.png"));
		menubckgrnd = new Texture(Gdx.files.internal("background/MenuBckgrnd.png"));
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(255, 255, 255, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		
		game.batch.draw(menubckgrnd, 0, 0);
		game.batch.draw(gamelogo, 370, 400);
		game.batch.draw(devlogo, 385, 470);
		game.font.draw(game.batch, "During a stormy night, hamburgers suddenly started falling from the clouds with the raindrops!", 10, 100);
		game.font.draw(game.batch, "Your mission? Collect those damn hamburgers!!! It's free food! ", 8, 75);
		game.font.draw(game.batch, "Left click anywhere to begin!", 10, 50);
		
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}