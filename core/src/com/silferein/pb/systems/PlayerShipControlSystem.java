package com.silferein.pb.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.silferein.pb.components.AccelerationComponent;
import com.silferein.pb.components.PhysicsComponent;
import com.silferein.pb.components.PlayerComponent;

public class PlayerShipControlSystem extends IteratingSystem {
	private ComponentMapper<AccelerationComponent> accelerationMapper = ComponentMapper.getFor(AccelerationComponent.class);
	
	public PlayerShipControlSystem() {
		super(Family.all(PlayerComponent.class, PhysicsComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		AccelerationComponent acceleration = accelerationMapper.get(entity);

		if(Gdx.input.isKeyPressed(Keys.W)) {
			acceleration.acceleration = acceleration.accelerationRate;
		} else if(Gdx.input.isKeyPressed(Keys.S)) {
			acceleration.acceleration = -acceleration.accelerationRate;
		} else {
			acceleration.acceleration = 0;
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)) {
			acceleration.angularAcceleration = acceleration.angularAccelerationRate;
		} else if(Gdx.input.isKeyPressed(Keys.D)) {
			acceleration.angularAcceleration = -acceleration.angularAccelerationRate;
		} else {
			acceleration.angularAcceleration = 0;
		}
	}
}
