package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsComponent implements Component {
	public float density;
	public float restitution;	
	public float friction;
	public Body body;
	
	public PhysicsComponent() {
		this(1f, 0, 0);
	}
	public PhysicsComponent(float density, float restitution, float friction) {
		this.density = density;
		this.restitution = restitution;
		this.friction = friction;
	}
}
