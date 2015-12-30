package com.silferein.pb.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent implements Component {
	public TextureComponent(TextureRegion textureRegion) {
		this.texture = textureRegion;
	}

	public TextureRegion texture;
}
