package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import pl.moonwolf.blocks.components.BodyComponent;
import pl.moonwolf.blocks.components.PositionComponent;

public class Box2dSystem extends IteratingSystem
{
    private final World world;
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public Box2dSystem(World world)
    {
        super(Family.all(BodyComponent.class, PositionComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        world.step(1/30f, 6, 2);
        PositionComponent pos = pm.get(entity);
        BodyComponent body = bm.get(entity);
        Vector2 bpos = body.body.getPosition();
        pos.pos.x = bpos.x;
        pos.pos.y = bpos.y;
    }
}
