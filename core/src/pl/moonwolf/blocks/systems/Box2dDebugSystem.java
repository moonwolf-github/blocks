package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Box2dDebugSystem extends IteratingSystem
{
    private final Box2DDebugRenderer debugRenderer;
    private final World world;
    private final Camera camera;
    private Matrix4 m;

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        debugRenderer.render(world, m);
    }

    public Box2dDebugSystem(World world, Camera camera)
    {
        super(Family.all().get());
        debugRenderer = new Box2DDebugRenderer();
        this.world = world;
        this.camera = camera;
        m = camera.combined.cpy();
        //m.scale(1f, 1f, 0);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {

    }
}
