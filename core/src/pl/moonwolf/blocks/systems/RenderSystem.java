package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.moonwolf.blocks.components.MultiTexturesComponent;
import pl.moonwolf.blocks.components.PositionComponent;
import pl.moonwolf.blocks.components.TextureComponent;

public class RenderSystem extends IteratingSystem
{
    private final Camera camera;
    private SpriteBatch batch;
    private ComponentMapper<TextureComponent> texture;
    private ComponentMapper<PositionComponent> position;
    private ComponentMapper<MultiTexturesComponent> multiTexture;

    public RenderSystem(SpriteBatch batch, Camera camera)
    {
        super(Family.all(PositionComponent.class).one(TextureComponent.class, MultiTexturesComponent.class).get());
        this.batch = batch;
        this.camera = camera;
        texture = ComponentMapper.getFor(TextureComponent.class);
        position = ComponentMapper.getFor(PositionComponent.class);
        multiTexture = ComponentMapper.getFor(MultiTexturesComponent.class);
    }

    @Override
    public void update(float deltaTime)
    {
        camera.update();
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        TextureComponent tex = texture.get(entity);
        MultiTexturesComponent mtex = multiTexture.get(entity);
        PositionComponent pos = position.get(entity);
        if (tex != null)
        {
            batch.draw(tex.texture, pos.pos.x, pos.pos.y, tex.width, tex.width);
        }
        else
        {
            if (mtex.horizontal)
            {
                for (int i = 0; i < mtex.count; i++)
                {
                    batch.draw(mtex.texture, pos.pos.x + mtex.size * i, pos.pos.y, mtex.size, mtex.size);
                }
            }
            else
            {
                for (int i = 0; i < mtex.count; i++)
                {
                    batch.draw(mtex.texture, pos.pos.x, pos.pos.y + mtex.size * i, mtex.size, mtex.size);
                }
            }
        }
    }
}
