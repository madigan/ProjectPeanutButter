package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;

public class PhysicsComponent implements Component {
	public float density;
	public float restitution;	
	public float friction;
	
	public PhysicsComponent() {
		this(1f, 0, 0);
	}
	public PhysicsComponent(float density, float restitution, float friction) {
		this.density = density;
		this.restitution = restitution;
		this.friction = friction;
	}
}
