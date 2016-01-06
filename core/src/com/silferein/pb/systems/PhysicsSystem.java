package com.silferein.pb.systems;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.silferein.pb.components.BoundsComponent;
import com.silferein.pb.components.MovementComponent;
import com.silferein.pb.components.PhysicsComponent;
import com.silferein.pb.components.TransformComponent;

public class PhysicsSystem extends IntervalIteratingSystem implements EntityListener {
	private ComponentMapper<PhysicsComponent> physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
	private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
	private ComponentMapper<MovementComponent> movementMapper = ComponentMapper.getFor(MovementComponent.class);

	private World world;
	
	// TODO: Abstract this to a settings file
	private int velocityIterations = 3;
	private int positionIterations = 2;
	
	// TODO: use getInterval when Ashley 1.7.1 is published to MavenCentral.
	private float interval;
	
	public PhysicsSystem(World world, float interval) {
		super(Family.all(PhysicsComponent.class, TransformComponent.class, BoundsComponent.class, MovementComponent.class).get(), interval);
		this.world = world;
		this.interval = interval;
		
		Box2D.init();
	}

	@Override
	public void entityAdded(Entity entity) {
		PhysicsComponent physics = physicsMapper.get(entity);
		if(physics != null) {
			TransformComponent transform = transformMapper.get(entity);
			BoundsComponent bounds = boundsMapper.get(entity);
			MovementComponent movement = movementMapper.get(entity);
			
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody; // TODO: Abstract this into physics component
			Body body = world.createBody(bodyDef);
			body.setTransform(transform.position.cpy(), transform.rotation * MathUtils.degreesToRadians);
			
			Shape shape = null;
			switch(bounds.shape) {
			case CIRCLE:
				CircleShape circle = new CircleShape();
				circle.setRadius(bounds.radius);
				circle.setPosition(new Vector2(0, 0));
				shape = circle;
				break;
			case RECTANGLE:
			default:
				PolygonShape rect = new PolygonShape();
				rect.setAsBox(bounds.width / 2, bounds.height / 2, new Vector2(0, 0), 0);
				shape = rect;
				break;
			}
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = physics.density;
			fixtureDef.friction = physics.friction;
			fixtureDef.restitution = physics.restitution;
			
			body.createFixture(fixtureDef);
			body.setAngularDamping(1.0f);
			physics.body = body;
			shape.dispose();
			
			body.setAngularVelocity(movement.angularSpeed * MathUtils.degreesToRadians);
			body.setLinearVelocity(new Vector2(MathUtils.cos(body.getAngle()), MathUtils.sin(body.getAngle())).scl(movement.speed));
		}
	}

	@Override
	public void entityRemoved(Entity entity) {
		PhysicsComponent physics = physicsMapper.get(entity);
		world.destroyBody(physics.body);
		physics.body = null;
	}
	
	@Override
	protected void updateInterval() {
		world.step( interval, velocityIterations, positionIterations);
		super.updateInterval();
	}

	@Override
	protected void processEntity(Entity entity) {
		TransformComponent transform = transformMapper.get(entity);
		PhysicsComponent physics = physicsMapper.get(entity);
		MovementComponent movement = movementMapper.get(entity);
		
		Body body = physics.body;
		transform.position = body.getPosition().cpy();
		transform.rotation = MathUtils.radiansToDegrees * body.getAngle();
		movement.speed = body.getLinearVelocity().len();
		movement.angularSpeed = body.getAngularVelocity();
	}
	
}
