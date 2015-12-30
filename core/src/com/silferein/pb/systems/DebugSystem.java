package com.silferein.pb.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DebugSystem extends EntitySystem {
	public boolean enabled = false;
	
	private float height = 200.0f;
	private float width = 200.0f;
	private float padding = 5.0f;
	
	private float speed = 200.0f;
	
	private ShapeRenderer renderer;
	private SpriteBatch batch;
	private BitmapFont font;
	
	private OrthographicCamera camera;
	
	public DebugSystem(OrthographicCamera camera, SpriteBatch batch) {
		this.camera = camera;
		this.batch = batch;
		
		renderer = new ShapeRenderer();
		renderer.setAutoShapeType(true);
		
		font = new BitmapFont();
	}
	
	public void update(float deltaTime) {
		// Let the user toggle debug mode
		if(Gdx.input.isKeyJustPressed(Keys.F5)) {
			enabled = !enabled;
		}
		
		if(enabled) {
			// Handle Camera Changes
			if(Gdx.input.isKeyPressed(Keys.LEFT)) {
				camera.translate(-speed * Gdx.graphics.getDeltaTime(), 0);
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
				camera.translate(speed * Gdx.graphics.getDeltaTime(), 0);
			}
			if(Gdx.input.isKeyPressed(Keys.UP)) {
				camera.translate(0, speed * Gdx.graphics.getDeltaTime());
			}
			if(Gdx.input.isKeyPressed(Keys.DOWN)) {
				camera.translate(0, -speed * Gdx.graphics.getDeltaTime());
			}
			camera.update();
			
			renderer.setProjectionMatrix(camera.combined);
			batch.setProjectionMatrix(camera.combined);
			
			renderer.begin();
			batch.begin();
			
			// Draw measuring lines
			float cameraLeft = camera.position.x - camera.viewportWidth / 2f;
			float cameraRight = camera.position.x + camera.viewportWidth / 2f;
			float cameraTop = camera.position.y + camera.viewportHeight / 2f;
			float cameraBottom = camera.position.y - camera.viewportHeight / 2f;
			
			renderer.setColor(Color.GREEN);
			font.setColor(Color.GREEN);
			
			for(float column = (float) Math.floor(cameraLeft / width) * width; column < cameraRight; column += width) {
				renderer.line(column, cameraTop, column, cameraBottom);
				font.draw(batch, "X: " + column, column + padding, cameraTop);
			}
			for(float row = (float) Math.floor(cameraBottom / height) * height; row < cameraTop; row += height) {
				renderer.line(cameraLeft, row, cameraRight, row);
				font.draw(batch, "Y: " + row, cameraLeft, row);
			}
			
			// Draw crosshairs for the camera
			renderer.setColor(Color.BLUE);
			renderer.line(camera.position.x - padding, camera.position.y, camera.position.x + padding, camera.position.y); // X-axis
			renderer.line(camera.position.x, camera.position.y - padding, camera.position.x, camera.position.y + padding); // Y-axis
			font.setColor(Color.BLUE);
			String coordinates = String.format("(%.2f,%.2f", camera.position.x, camera.position.y);
			font.draw(batch, coordinates, camera.position.x + padding, camera.position.y - padding);
			
			batch.end();
			renderer.end();
		}
	}
}
