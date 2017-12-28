package pl.moonwolf.blocks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class MultiTexturesComponent implements Component
{
    public Texture texture;
    public float size;
    public int count;

    public MultiTexturesComponent(Texture tex, float size, int count)
    {
        texture = tex;
        this.size = size;
        this.count = count;
    }
}
