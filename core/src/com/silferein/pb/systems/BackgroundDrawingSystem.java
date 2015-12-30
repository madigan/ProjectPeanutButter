package com.silferein.pb.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundDrawingSystem extends EntitySystem {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture background;
	
	public BackgroundDrawingSystem( OrthographicCamera camera, SpriteBatch batch ) {
		this.batch = batch;
		this.camera = camera;

		// Load Textures
		background = new Texture(Gdx.files.internal("imgs/starfield.png"));
	}
	
	@Override
	public void update(float deltaTime) {
		// To simplify the code a little, do these calculations ahead of time
		// To speed things up, we might optimize this.
		float cameraLeft = camera.position.x - camera.viewportWidth / 2f;
		float cameraRight = camera.position.x + camera.viewportWidth / 2f;
		float cameraTop = camera.position.y + camera.viewportHeight / 2f;
		float cameraBottom = camera.position.y - camera.viewportHeight / 2f;
		
		float width = background.getWidth();
		float height = background.getHeight();
		
		batch.begin();
		for(float column = (float) Math.floor(cameraLeft / width) * width; column < cameraRight; column += width) {
			for(float row = (float) Math.floor(cameraBottom / height) * height; row < cameraTop; row += height) {
				batch.draw(background, column, row);
			}
		}
		batch.end();
	}
}
