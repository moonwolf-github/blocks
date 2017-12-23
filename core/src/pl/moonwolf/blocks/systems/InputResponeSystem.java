package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;

import pl.moonwolf.blocks.components.BodyComponent;
import pl.moonwolf.blocks.input.InputHandler;
import pl.moonwolf.blocks.components.PositionComponent;

public class InputResponeSystem extends IteratingSystem implements InputHandler
{
    private float vX;
    private float vY;
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    public InputResponeSystem()
    {
        super(Family.all(PositionComponent.class, BodyComponent.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        vY = 0;
        vX = 0;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        if (vY < 0)
        {
            Gdx.app.log("InputResponse", "vY < 0");
            BodyComponent body = bm.get(entity);
            if (!body.body.isAwake() && body.body.getType() == BodyDef.BodyType.DynamicBody)
            {
                // move object, force needs to be > 1
                body.body.applyLinearImpulse(0f, 1.01f, 0f, 0f, true);
            }
        }
    }

    @Override
    public void setVelocities(float vX, float vY)
    {
        this.vX = vX;
        this.vY = vY;
    }
}
