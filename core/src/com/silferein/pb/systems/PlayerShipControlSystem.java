package com.silferein.pb.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.silferein.pb.components.PhysicsComponent;
import com.silferein.pb.components.PlayerComponent;

public class PlayerShipControlSystem extends IteratingSystem {
	private ComponentMapper<PhysicsComponent> physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
	
	
	private static final float THRUST = 10000.0f;
	
	public PlayerShipControlSystem() {
		super(Family.all(PlayerComponent.class, PhysicsComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PhysicsComponent physics = physicsMapper.get(entity);
		if(Gdx.input.isKeyPressed(Keys.W)) {
			System.out.println("W");
			physics.body.applyLinearImpulse(
					new Vector2(
							MathUtils.cos(physics.body.getAngle()), 
							MathUtils.sin(physics.body.getAngle()) ).scl(THRUST), 
					physics.body.getLocalCenter(), 
					true);
		} else if(Gdx.input.isKeyPressed(Keys.S)) {
			System.out.println("S");
			physics.body.applyLinearImpulse(
					new Vector2(
							MathUtils.cos(physics.body.getAngle()), 
							MathUtils.sin(physics.body.getAngle()) ).scl(-THRUST), 
					physics.body.getLocalCenter(), 
					true);
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)) {
			System.out.println("A");
			physics.body.applyAngularImpulse(THRUST, true);
		} else if(Gdx.input.isKeyPressed(Keys.D)) {
			System.out.println("D");
			physics.body.applyAngularImpulse(-THRUST, true);
		}
	}
}
