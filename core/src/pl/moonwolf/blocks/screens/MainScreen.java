package pl.moonwolf.blocks.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainScreen extends ScreenAdapter
{
    private SpriteBatch batch;
    private Texture img;

    public MainScreen(SpriteBatch batch)
    {
        this.batch = batch;
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose()
    {
        super.dispose();
        img.dispose();
    }
}
