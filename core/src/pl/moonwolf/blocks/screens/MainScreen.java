package pl.moonwolf.blocks.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.moonwolf.blocks.components.TextureComponent;
import pl.moonwolf.blocks.systems.RenderSystem;

public class MainScreen extends ScreenAdapter
{
    private SpriteBatch batch;
    private Texture img;
    private PooledEngine engine;
    private TextureComponent tc;

    public MainScreen(SpriteBatch batch)
    {
        this.batch = batch;
        tc = new TextureComponent();
        tc.texture = new Texture("badlogic.jpg");
        engine = new PooledEngine();
        engine.addSystem(new RenderSystem(batch));
        engine.addEntity(engine.createEntity().add(tc));
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
    }
}
