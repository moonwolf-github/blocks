package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.moonwolf.blocks.components.TextureComponent;

public class RenderSystem extends IteratingSystem
{
    private SpriteBatch batch;
    private ComponentMapper<TextureComponent> texture;

    public RenderSystem(SpriteBatch batch)
    {
        super(Family.all(TextureComponent.class).get());
        this.batch = batch;
        texture = ComponentMapper.getFor(TextureComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        TextureComponent tex = texture.get(entity);
        batch.begin();
        batch.draw(tex.texture, 0, 0);
        batch.end();
    }
}
