package com.silferein.pb;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.silferein.pb.systems.BackgroundDrawingSystem;
import com.silferein.pb.systems.DebugSystem;
import com.silferein.pb.systems.LabelDrawingSystem;
import com.silferein.pb.systems.TextureDrawingSystem;

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Engine engine;
	
	private BitmapFont font;

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
		
		//=== Game Logic Setup ===//
		engine = new Engine();
		engine.addSystem( new BackgroundDrawingSystem( camera, batch ) );
		engine.addSystem( new TextureDrawingSystem( batch ) );
		engine.addSystem( new LabelDrawingSystem( batch, font ) );
		
		engine.addSystem( new DebugSystem( camera, batch ) );
		
		engine.addEntity(EntityFactory.createEntity("asteroid"));
		engine.addEntity(EntityFactory.createEntityAt("player_ship", 200f, 200f));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// Draw things!
		batch.setProjectionMatrix(camera.combined);

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
}
