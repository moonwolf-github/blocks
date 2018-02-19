package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;

import pl.moonwolf.blocks.components.CounterComponent;

public class TextRenderSystem extends IteratingSystem
{
    private final SpriteBatch batch;
    private final ComponentMapper<CounterComponent> counter;
    private final BitmapFont font;

    public TextRenderSystem(SpriteBatch batch, BitmapFont font)
    {
        super(Family.all(CounterComponent.class).get());
        this.batch = batch;
        this.font = font;
        counter = ComponentMapper.getFor(CounterComponent.class);
    }

    @Override
    public void update(float deltaTime)
    {
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        font.draw(batch, String.format(Locale.US, "%.2f", counter.get(entity).time), 10, 64 * 9 + 30);
    }
}
