package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;

public class LabelComponent implements Component {
	public String label;
	
	public LabelComponent() {
		this("");
	}
	public LabelComponent(String label) {
		this.label = label;
	}
}
