package pl.moonwolf.blocks.components;

import com.badlogic.ashley.core.Component;

import pl.moonwolf.blocks.ExplosionLight;

public class ExplosionComponent implements Component
{
    public float percent = 0;
    public ExplosionLight light;

    public ExplosionComponent(ExplosionLight light)
    {
        this.light = light;
    }
}
