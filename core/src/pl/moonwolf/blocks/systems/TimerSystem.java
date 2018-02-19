package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

import pl.moonwolf.blocks.components.CounterComponent;

public class TimerSystem extends IteratingSystem
{
    private ComponentMapper<CounterComponent> counter;

    public TimerSystem()
    {
        super(Family.all(CounterComponent.class).get());
        counter = ComponentMapper.getFor(CounterComponent.class);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        counter.get(entity).time += deltaTime;
        //Gdx.app.log("counter", String.valueOf(counter.get(entity).time));
    }
}
