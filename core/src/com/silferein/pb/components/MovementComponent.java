package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;

public class MovementComponent implements Component {
	public float speed;
	public float angularSpeed;
	
	public MovementComponent() {
		this(0f, 0f);
	}
	public MovementComponent(float speed, float angularSpeed) {
		this.speed = speed;
		this.angularSpeed = angularSpeed;
	}
}
