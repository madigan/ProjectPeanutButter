package com.silferein.pb;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.silferein.pb.components.BoundsComponent;
import com.silferein.pb.components.TextureComponent;
import com.silferein.pb.components.TransformComponent;

/**
 * Contains the logic to build entities.
 * @author john
 *
 */
public class EntityFactory {
	//TODO: Abstract with interfaces
	//TODO: Load entities from files
	//TODO: Re-evaluate whether this is good being static or not. It doesn't have state...
	
	public static Entity createEntity(String tag) {
		Entity entity = new Entity();
		
		switch(tag) {
		case "asteroid":
			entity.add(new TransformComponent(0, 0, 0));
			entity.add(new BoundsComponent(100f, 100f));
			entity.add(new TextureComponent( new TextureRegion(new Texture(Gdx.files.internal("imgs/asteroid2.png"))) ));
			break;
		default:
			//TODO: Decide what to do if a bad tag is passed. Do we break?
			Gdx.app.error("EntityFactory", String.format("Unknown tag '%s'.", tag));
			break;
		}
		
		return entity;
	}
}
