package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.viewport.Viewport;

import pl.moonwolf.blocks.Blocks;
import pl.moonwolf.blocks.components.BodyComponent;
import pl.moonwolf.blocks.components.PlayerComponent;
import pl.moonwolf.blocks.input.InputHandler;
import pl.moonwolf.blocks.components.PositionComponent;

public class InputResponeSystem extends IteratingSystem implements InputHandler
{
    private float vX;
    private float vY;
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    Vector2 linearVelocity;
    private float x;
    private Viewport viewport;

    public InputResponeSystem(Viewport viewport)
    {
        super(Family.all(PositionComponent.class, BodyComponent.class, PlayerComponent.class).get());
        this.viewport = viewport;
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
            if (x < viewport.getWorldWidth() / 2f)
            {
                if (!body.body.isAwake() && body.body.getPosition().x < Blocks.VIRTUAL_WIDTH / 2f)
                {
                    // move object, force needs to be > 1
                    body.body.applyLinearImpulse(linearVelocity, body.body.getWorldCenter(), true);
                }
            }
            else
            {
                if (!body.body.isAwake() && body.body.getPosition().x > Blocks.VIRTUAL_WIDTH / 2f)
                {
                    // move object, force needs to be > 1
                    body.body.applyLinearImpulse(linearVelocity, body.body.getWorldCenter(), true);
                }
            }
        }
    }

    @Override
    public void setVelocities(float x, float vX, float vY)
    {
        this.vX = vX;
        this.vY = vY;
        this.x = x;
        linearVelocity = new Vector2(vX, -vY).nor();
        Gdx.app.log("InputResponse", "lv: " + linearVelocity);
    }
}
