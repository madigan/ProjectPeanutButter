package com.silferein.pb;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.silferein.pb.components.AccelerationComponent;
import com.silferein.pb.components.BoundsComponent;
import com.silferein.pb.components.LabelComponent;
import com.silferein.pb.components.MovementComponent;
import com.silferein.pb.components.PhysicsComponent;
import com.silferein.pb.components.PlayerComponent;
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
			entity.add(new TransformComponent(0, 0, MathUtils.random(0, 360)));
			entity.add(new BoundsComponent(MathUtils.random(1.0f, 3.0f)));
			entity.add(new TextureComponent( new TextureRegion(new Texture(Gdx.files.internal("imgs/asteroid2.png"))) ));
			entity.add(new MovementComponent(MathUtils.random(2f, 4f), 4f, MathUtils.random(-2f, 2f), 2f));
			entity.add(new PhysicsComponent());
			break;
		case "player_ship":
			entity.add(new TransformComponent(0, 0, 0));
			entity.add(new BoundsComponent(2f, 2f));
			entity.add(new TextureComponent( new TextureRegion(new Texture(Gdx.files.internal("imgs/ship1.png"))) ));
			entity.add(new LabelComponent("Fred"));
			entity.add(new MovementComponent(0, 20f, 0, 10f));
			entity.add(new AccelerationComponent(0, 10f, 0, 7f));
			entity.add(new PhysicsComponent());
			entity.add(new PlayerComponent());
			break;
		default:
			//TODO: Decide what to do if a bad tag is passed. Do we break?
			Gdx.app.error("EntityFactory", String.format("Unknown tag '%s'.", tag));
			break;
		}
		
		return entity;
	}
	
	public static Entity createEntityAt(String tag, float x, float y) {
		Entity entity = createEntity(tag);
		
		entity.getComponent(TransformComponent.class).set(x, y);
		
		return entity;
	}
}
