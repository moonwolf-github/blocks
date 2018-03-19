package pl.moonwolf.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class ExplosionLight implements Pool.Poolable
{
    private PointLight light;

    public ExplosionLight()
    {

    }

    public void setParams(RayHandler rayHandler, float x, float y) //, Color color)
    {
        light = new PointLight(rayHandler, 6,
                new Color(1,.4f,0.1f,1),
                1,
                x,
                y);
    }

    @Override
    public void reset()
    {
        light.remove(true);
    }

    public void setDistance(float dist)
    {
        light.setDistance(dist);
    }

    public void setActive(boolean active)
    {
        light.setActive(active);
    }
}
