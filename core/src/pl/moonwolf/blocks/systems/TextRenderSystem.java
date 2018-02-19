package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Locale;
import java.util.Vector;

import pl.moonwolf.blocks.components.CounterComponent;
import pl.moonwolf.blocks.components.PositionComponent;
import pl.moonwolf.blocks.components.ValueComponent;

public class TextRenderSystem extends IteratingSystem
{
    private final SpriteBatch batch;
    private final ComponentMapper<ValueComponent> value;
    private final ComponentMapper<PositionComponent> pos;
    private final BitmapFont font;

    public TextRenderSystem(SpriteBatch batch, BitmapFont font)
    {
        super(Family.all(ValueComponent.class, PositionComponent.class).get());
        this.batch = batch;
        this.font = font;
        value = ComponentMapper.getFor(ValueComponent.class);
        pos = ComponentMapper.getFor(PositionComponent.class);
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
        Vector2 pos = this.pos.get(entity).pos;
        int val = value.get(entity).value;
        font.draw(batch, String.format(Locale.US, "%d", val), pos.x, pos.y);
    }
}
