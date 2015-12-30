package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;

public class TransformComponent implements Component {
	public float x;
	public float y;
	public float rotation;

	public TransformComponent() {
		x = 0f;
		y = 0f;
		rotation = 0f;
	}
	public TransformComponent(float x, float y, float rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
