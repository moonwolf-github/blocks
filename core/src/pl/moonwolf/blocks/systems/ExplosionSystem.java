package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import pl.moonwolf.blocks.components.ExplosionComponent;

public class ExplosionSystem extends IteratingSystem
{
    private final ComponentMapper<ExplosionComponent> explosions;
    private final Engine engine;

    public ExplosionSystem(Engine engine)
    {
        super(Family.all(ExplosionComponent.class).get());
        explosions = ComponentMapper.getFor(ExplosionComponent.class);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        ExplosionComponent explosion = explosions.get(entity);
        explosion.percent += deltaTime * 8;
        explosion.light.setDistance(300 * MathUtils.sin(explosion.percent));
        Gdx.app.log("explode", String.valueOf(explosion.percent));
        if (explosion.percent > 3 * MathUtils.PI / 3f)
        {
            engine.removeEntity(entity);
            explosion.light.setActive(false);
        }
    }
}
