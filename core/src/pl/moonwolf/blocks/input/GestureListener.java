package pl.moonwolf.blocks.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import pl.moonwolf.blocks.systems.InputResponeSystem;

public class GestureListener implements GestureDetector.GestureListener
{
    private InputResponeSystem listener;
    private float touchX;

    public GestureListener(InputResponeSystem listener)
    {
        this.listener = listener;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        Gdx.app.log("Gesture", "touchDown");
        touchX = x;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        Gdx.app.log("Gesture", "tap");
        return false;
    }

    @Override
    public boolean longPress(float x, float y)
    {
        Gdx.app.log("Gesture", "longPress");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        Gdx.app.log("Gesture", String.format("fling - x: %.2f, vX: %.2f, vY: %.2f, button: %d", touchX, velocityX, velocityY, button));
        listener.setVelocities(touchX, velocityX, velocityY);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        Gdx.app.log("Gesture", "pan");
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button)
    {
        Gdx.app.log("Gesture", "panstop");
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        Gdx.app.log("Gesture", "zoom");
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        Gdx.app.log("Gesture", "pinch");
        return false;
    }

    @Override
    public void pinchStop()
    {
        Gdx.app.log("Gesture", "pinchStop");
    }
}
