package pl.moonwolf.blocks.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import pl.moonwolf.blocks.components.PositionComponent;
import pl.moonwolf.blocks.components.TextureComponent;
import pl.moonwolf.blocks.systems.RenderSystem;

public class MainScreen extends ScreenAdapter
{
    private final Viewport viewport;
    private SpriteBatch batch;
    private Texture img;
    private PooledEngine engine;
    private TextureComponent tc;
    private TextureComponent floor;

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        viewport.update(width, height);
    }

    public MainScreen(SpriteBatch batch, Viewport viewport)
    {
        this.batch = batch;
        this.viewport = viewport;
        tc = new TextureComponent();
        tc.texture = new Texture("badlogic.jpg");
        floor = new TextureComponent();
        floor.texture = new Texture("floor.png");
        engine = new PooledEngine();
        engine.addSystem(new RenderSystem(batch, viewport.getCamera()));
        for (int y = 0; y <= 600 / 64; y++)
        {
            for (int x = 0; x <= 800 / 64; x++)
            {
                PositionComponent pc = new PositionComponent();
                pc.pos = new Vector2(x * 64, y * 64);
                engine.addEntity(engine.createEntity().add(floor).add(pc));
            }
        }
        engine.addEntity(engine.createEntity().add(tc).add(new PositionComponent()));
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);
        engine.update(delta);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        tc.texture.dispose();
        floor.texture.dispose();
    }
}
