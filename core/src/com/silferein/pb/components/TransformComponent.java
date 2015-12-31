package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {
	public Vector2 position;
	public float rotation;

	public TransformComponent() {
		position = new Vector2(0, 0);
		rotation = 0f;
	}
	public TransformComponent(float x, float y, float rotation) {
		position = new Vector2(x, y);
		this.rotation = rotation;
	}
	
	public void set(float x, float y) {
		position.set(x, y);
	}
}
