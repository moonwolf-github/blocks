package pl.moonwolf.blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.moonwolf.blocks.screens.MainScreen;

public class Blocks extends Game
{
	private SpriteBatch batch;

	@Override
	public void create ()
    {
		batch = new SpriteBatch();
		setScreen(new MainScreen(batch));
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
