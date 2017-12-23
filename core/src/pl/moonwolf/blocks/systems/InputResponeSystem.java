package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

import pl.moonwolf.blocks.input.InputHandler;
import pl.moonwolf.blocks.components.PositionComponent;

public class InputResponeSystem extends IteratingSystem implements InputHandler
{
    private float vX;
    private float vY;

    public InputResponeSystem()
    {
        super(Family.all(PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        if (vY < 0)
        {
            vY = 0;
            vX = 0;
            Gdx.app.log("InputResponse", "vY < 0");
        }
    }

    @Override
    public void setVelocities(float vX, float vY)
    {
        this.vX = vX;
        this.vY = vY;
    }
}
