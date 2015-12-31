package com.silferein.pb.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.silferein.pb.components.BoundsComponent;
import com.silferein.pb.components.TextureComponent;
import com.silferein.pb.components.TransformComponent;

public class TextureDrawingSystem extends IteratingSystem {
	private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
	private ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
	
	private SpriteBatch batch;
	
	public TextureDrawingSystem(SpriteBatch batch) {
		super(Family.all(TransformComponent.class, BoundsComponent.class, TextureComponent.class).get());
		
		this.batch = batch;
	}
	
	@Override
	public void update(float deltaTime) {
		batch.begin();
		super.update(deltaTime);
		batch.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent transform = transformMapper.get(entity);
		BoundsComponent bounds = boundsMapper.get(entity);
		TextureComponent texture = textureMapper.get(entity);
		
		batch.draw(	texture.texture, 
					transform.x, transform.y, 
					bounds.width / 2, bounds.height / 2, 
					bounds.width, bounds.height, 
					1.0f, 1.0f, 
					transform.rotation );
	}
}
