package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;

public class MovementComponent implements Component {
	public float speed;
	public float maxSpeed;
	public float angularSpeed;
	public float maxAngularSpeed;
	
	public MovementComponent() {
		this(0f, 10f, 0f, 10f);
	}
	public MovementComponent(float speed, float maxSpeed, float angularSpeed, float maxAngularSpeed) {
		this.speed = speed;
		this.maxSpeed = maxSpeed;
		this.angularSpeed = angularSpeed;
		this.maxAngularSpeed = maxAngularSpeed;
	}
}
