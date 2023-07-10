package com.droplet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndingMenu implements Screen {

  final DropletsHamburgers game;
  
	OrthographicCamera camera;
	Texture menubckgrnd;

	public EndingMenu(final DropletsHamburgers gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		menubckgrnd = new Texture(Gdx.files.internal("background/endBckgrnd.png"));
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(255, 255, 255, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		
		game.batch.draw(menubckgrnd, 0, 0);
		
		game.batch.end();

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