package pl.moonwolf.blocks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pl.moonwolf.blocks.screens.MainScreen;

public class Blocks extends Game
{
	private SpriteBatch batch;
    private Viewport viewport;
    private Camera camera;

    @Override
	public void create ()
    {
        camera = new OrthographicCamera();
		viewport = new ExtendViewport(800, 600, camera);
		batch = new SpriteBatch();
		setScreen(new MainScreen(batch, viewport));
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
	}
}
