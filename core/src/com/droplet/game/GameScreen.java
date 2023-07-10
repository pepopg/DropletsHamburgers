package com.droplet.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final DropletsHamburgers game;

	Texture dropImage;
	Texture plateImage;
	Texture background;
	Sound dropSound;
	//Music rainMusic;
	OrthographicCamera camera;
	Rectangle plate;
	Array<Rectangle> hamburgers;
	long lastDropTime;
	int dropsGathered;

	public GameScreen(final DropletsHamburgers game) {
		this.game = game;

		// load the images for the droplet and the bucket, 64x64 pixels each
		background = new Texture(Gdx.files.internal("background/GameBackground.png"));
		dropImage = new Texture(Gdx.files.internal("sprites/hamburger.png"));
		plateImage = new Texture(Gdx.files.internal("sprites/plate.png"));
		dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/card.wav"));

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		// create a Rectangle to logically represent the bucket
		plate = new Rectangle();
		plate.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		plate.y = 0; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		plate.width = 128;
		plate.height = 128;

		// create the raindrops array and spawn the first raindrop
		hamburgers = new Array<Rectangle>();
		spawnRaindrop();

	}

	private void spawnRaindrop() {
		Rectangle hamburger = new Rectangle();
		hamburger.x = MathUtils.random(0, 800 - 64);
		hamburger.y = 600;
		hamburger.width = 64;
		hamburger.height = 64;
		hamburgers.add(hamburger);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		ScreenUtils.clear(186, 166, 142, 0);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		
		game.batch.draw(background, 0, 0);
		game.font.draw(game.batch, "Hamburgers Collected: " + dropsGathered, 0, 600);
		game.batch.draw(plateImage, plate.x, plate.y);
		
		for (Rectangle hamburger : hamburgers) 
		{
			game.batch.draw(dropImage, hamburger.x, hamburger.y);
		}
		
		game.batch.end();

		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			plate.x = touchPos.x - 128 / 2;
		}
		if (Gdx.input.isKeyPressed(Keys.A))
			plate.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.D))
			plate.x += 200 * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		if (plate.x < 0)
			plate.x = 0;
		if (plate.x > 800 - 64)
			plate.x = 800 - 64;
		
		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iter = hamburgers.iterator();
		while (iter.hasNext()) 
		{
			Rectangle hamburger = iter.next();
			hamburger.y -= 200 * Gdx.graphics.getDeltaTime();
			if (hamburger.y + 64 < 0)
				iter.remove();
			if (hamburger.overlaps(plate)) 
			{
				dropsGathered++;
				dropSound.play();
				iter.remove();
			}
		}
		
		if (dropsGathered >= 20) 
		{
			game.batch.begin();
			
			plateImage = new Texture(Gdx.files.internal("sprites/plate20.png"));
			
			game.batch.end();
			
			/* game.setScreen(new EndingMenu(game));
			dispose(); */
		} 
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		//rainMusic.play();
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
		dropImage.dispose();
		plateImage.dispose();
		dropSound.dispose();
		//rainMusic.dispose();
	}

}