package pl.moonwolf.blocks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pl.moonwolf.blocks.screens.MainScreen;

public class Blocks extends Game
{
    private SpriteBatch batch;
    private SpriteBatch textBatch;
    public static final float VIRTUAL_HEIGHT = 10;
    public static final float VIRTUAL_WIDTH = 10;
    private BitmapFont textFont;

    @Override
    public void create ()
    {
        Viewport viewport = new ExtendViewport(800, 600);
        ((OrthographicCamera) viewport.getCamera()).setToOrtho(false, Blocks.VIRTUAL_WIDTH, Blocks.VIRTUAL_HEIGHT);
        batch = new SpriteBatch();
        textBatch = new SpriteBatch();
        textFont = new BitmapFont(Gdx.files.internal("fonts/nuskool_krome_64x64.fnt"));
        batch.setProjectionMatrix(((OrthographicCamera) viewport.getCamera()).combined);
        setScreen(new MainScreen(batch, viewport, textBatch, textFont));
    }

    @Override
    public void render ()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void dispose ()
    {
        batch.dispose();
        textFont.dispose();
    }
}
