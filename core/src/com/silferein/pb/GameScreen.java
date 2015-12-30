package com.silferein.pb;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Engine engine;
	
	private BitmapFont font;
	private Texture background;

	@Override
	public void show() {
		//=== libGDX Setup ===//
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		
		batch = new SpriteBatch();
		
		//=== Resource Loading ===//
		// Load Fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Smokum-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
		parameter.size = 8;
		//font = generator.generateFont(parameter);
		font = new BitmapFont();
		generator.dispose();
		
		// Load Textures
		background = new Texture(Gdx.files.internal("imgs/starfield.png"));
		
		//=== Game Logic Setup ===//
		engine = new Engine();
		engine.addSystem( new DebugSystem( camera, batch ) );
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// Draw things!
		batch.setProjectionMatrix(camera.combined);
		
		// Draw background
		batch.begin();
		drawBackground();
		batch.end();

		// Update the game engines
		engine.update( deltaTime );
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		// DROID: Implement later.
		System.out.println("Pause");
	}

	@Override
	public void resume() {
		// DROID: Implement later.
		System.out.println("Resume");
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
	
	/**
	 * Draws the background.
	 * TODO: Move this to some sort of engine in ashley?
	 */
	private void drawBackground() {
		// To simplify the code a little, do these calculations ahead of time
		// To speed things up, we might optimize this.
		float cameraLeft = camera.position.x - camera.viewportWidth / 2f;
		float cameraRight = camera.position.x + camera.viewportWidth / 2f;
		float cameraTop = camera.position.y + camera.viewportHeight / 2f;
		float cameraBottom = camera.position.y - camera.viewportHeight / 2f;
		
		float width = background.getWidth();
		float height = background.getHeight();
		
		for(float column = (float) Math.floor(cameraLeft / width) * width; column < cameraRight; column += width) {
			for(float row = (float) Math.floor(cameraBottom / height) * height; row < cameraTop; row += height) {
				batch.draw(background, column, row);
			}
		}
	}
}
