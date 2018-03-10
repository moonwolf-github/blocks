package pl.moonwolf.blocks.components;

import com.badlogic.ashley.core.Component;

import box2dLight.PointLight;

public class ExplosionComponent implements Component
{
    public float percent = 0;
    public PointLight light;

    public ExplosionComponent(PointLight light)
    {
        this.light = light;
    }
}
