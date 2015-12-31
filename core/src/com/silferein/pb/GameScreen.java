package com.silferein.pb;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.silferein.pb.systems.BackgroundDrawingSystem;
import com.silferein.pb.systems.DebugSystem;
import com.silferein.pb.systems.LabelDrawingSystem;
import com.silferein.pb.systems.PhysicsSystem;
import com.silferein.pb.systems.TextureDrawingSystem;

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Engine engine;
	private World world;
	
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
		
		//=== Game Physics Setup ===//
		world = new World(new Vector2(0, 0), false); // TODO: Abstract constant
		
		//=== Game Logic Setup ===//
		engine = new PooledEngine();
		PhysicsSystem physics = new PhysicsSystem(world, 1/60f); // TODO: Abstract constant
		engine.addSystem( physics );
		engine.addEntityListener( physics );
		
		engine.addSystem( new BackgroundDrawingSystem( camera, batch ) );
		engine.addSystem( new TextureDrawingSystem( batch ) );
		engine.addSystem( new LabelDrawingSystem( batch, font ) );
		
		engine.addSystem( new DebugSystem( camera, batch ) );
		
		engine.addEntity(EntityFactory.createEntityAt("asteroid", 500f, 100f));
		engine.addEntity(EntityFactory.createEntityAt("player_ship", 200f, 200f));
	}
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// Draw things!
		batch.setProjectionMatrix(camera.combined);

		// Update the game engines
		engine.update( deltaTime );
		
		debugRenderer.render(world, camera.combined);
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
