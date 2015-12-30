package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;

public class BoundsComponent implements Component {

	public float width;
	public float height;
	public float radius;
	public Shape shape;
	
	public BoundsComponent() {
		this(0f, 0f);
	}
	public BoundsComponent(float width, float height) {
		this.width = width;
		this.height = height;
		this.radius = width / 2;
		this.shape = Shape.RECTANGLE;
	}
	public BoundsComponent(float radius) {
		this.width = radius * 2;
		this.height = radius * 2;
		this.radius = radius;
		this.shape = Shape.CIRCLE;
	}
	
	public enum Shape {
		CIRCLE,
		RECTANGLE
	}
}
