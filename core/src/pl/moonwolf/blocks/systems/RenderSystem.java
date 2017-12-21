package pl.moonwolf.blocks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.moonwolf.blocks.components.PositionComponent;
import pl.moonwolf.blocks.components.TextureComponent;

public class RenderSystem extends IteratingSystem
{
    private final Camera camera;
    private SpriteBatch batch;
    private ComponentMapper<TextureComponent> texture;
    private ComponentMapper<PositionComponent> position;

    public RenderSystem(SpriteBatch batch, Camera camera)
    {
        super(Family.all(TextureComponent.class, PositionComponent.class).get());
        this.batch = batch;
        this.camera = camera;
        texture = ComponentMapper.getFor(TextureComponent.class);
        position = ComponentMapper.getFor(PositionComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        camera.update();
        TextureComponent tex = texture.get(entity);
        PositionComponent pos = position.get(entity);
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(tex.texture, pos.pos.x, pos.pos.y);
        batch.end();
    }
}
