package com.silferein.pb.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.silferein.pb.components.LabelComponent;
import com.silferein.pb.components.TransformComponent;

public class LabelDrawingSystem extends IteratingSystem {
	private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<LabelComponent> labelMapper = ComponentMapper.getFor(LabelComponent.class);
	
	private SpriteBatch batch;
	private BitmapFont font;
	
	public LabelDrawingSystem(SpriteBatch batch, BitmapFont font) {
		super(Family.all(TransformComponent.class, LabelComponent.class).get());
		
		this.batch = batch;
		this.font = font;
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
		LabelComponent label = labelMapper.get(entity);
		
		font.draw(batch, label.label, transform.x, transform.y);
	}
}
