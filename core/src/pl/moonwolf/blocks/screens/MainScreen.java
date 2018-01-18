package pl.moonwolf.blocks.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import pl.moonwolf.blocks.Blocks;
import pl.moonwolf.blocks.components.MultiTexturesComponent;
import pl.moonwolf.blocks.components.PlayerComponent;
import pl.moonwolf.blocks.input.GestureListener;
import pl.moonwolf.blocks.components.BodyComponent;
import pl.moonwolf.blocks.components.PositionComponent;
import pl.moonwolf.blocks.components.TextureComponent;
import pl.moonwolf.blocks.systems.Box2dDebugSystem;
import pl.moonwolf.blocks.systems.Box2dSystem;
import pl.moonwolf.blocks.systems.InputResponeSystem;
import pl.moonwolf.blocks.systems.RenderSystem;

public class MainScreen extends ScreenAdapter
{
    private final Viewport viewport;
    private final World world;
    private final SpriteBatch batch;
    private PooledEngine engine;
    private TextureComponent tc;
    private TextureComponent floor;

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        viewport.update(width, height);
    }

    public MainScreen(SpriteBatch batch, Viewport viewport)
    {
        this.batch = batch;
        this.viewport = viewport;
        tc = new TextureComponent();
        tc.texture = new Texture("stone.png");
        tc.size = tc.texture.getWidth() / 64f; // 64 pixels is 1 meter
        floor = new TextureComponent();
        floor.texture = new Texture("floor.png");
        floor.size = floor.texture.getWidth() / 64f;
        engine = new PooledEngine();
        engine.addSystem(new RenderSystem(batch, viewport.getCamera()));
        world = new World(new Vector2(0f, 0f), true);
        World.setVelocityThreshold(0f);
        engine.addSystem(new Box2dSystem(world));
        float x = 0;
        float y = 0;
        while (y < Blocks.VIRTUAL_HEIGHT + 1)
        {
            while (x < Blocks.VIRTUAL_WIDTH + 1)
            {
                PositionComponent pc = new PositionComponent();
                pc.pos = new Vector2(x, y);
                engine.addEntity(engine.createEntity().add(floor).add(pc));
                x += floor.size;
            }
            x = 0;
            y += floor.size;
        }
        Entity block = engine.createEntity();
        block.add(tc);
        block.add(new PositionComponent());
        block.add(new PlayerComponent());
        BodyComponent bc = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Set our body's starting position in the world
        bodyDef.position.set(0.5f, 0.5f);

        bodyDef.angularDamping = 0f;
        bodyDef.linearDamping = 0f;

        // Create our body in the world using our body definition
        bc.body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(25 / 64f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1.0f; // perfectly elastic collision

        // Create our fixture and attach it to the body
        bc.body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
        block.add(bc);
        engine.addEntity(block);

        // upper barrier
        createBarrier(0f, Blocks.VIRTUAL_HEIGHT / 2f,0, 0, Blocks.VIRTUAL_WIDTH,0, true);
        // bottom barrier
        createBarrier(0, 0, 0, 27/64f, Blocks.VIRTUAL_WIDTH, 27/64f, true);
        // left barrier
        createBarrier(0, 0, 27/64f, 0, 27/64f, Blocks.VIRTUAL_WIDTH / 2, false);
        // rigth barrier
        createBarrier(Blocks.VIRTUAL_WIDTH - 27f/64f, 0f, 0, 0, 0, Blocks.VIRTUAL_WIDTH / 2, false);

        InputResponeSystem irs = new InputResponeSystem();
        Gdx.input.setInputProcessor(new GestureDetector(new GestureListener(irs)));
        engine.addSystem(irs);

        engine.addSystem(new Box2dDebugSystem(world, viewport.getCamera()));
    }

    private void createBarrier(float x, float y, float x1, float y1, float x2, float y2, boolean horizontal)
    {
        BodyDef bodyDef = new BodyDef();
        BodyComponent bc = new BodyComponent();
        EdgeShape border = new EdgeShape();
        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        Entity entity = engine.createEntity();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bc.body = world.createBody(bodyDef);
        border.set(x1, y1, x2, y2);
        fixtureDef.shape = border;
        bc.body.createFixture(fixtureDef);
        float length = x2 - x1;
        if (!horizontal)
        {
            length = y2 - y1;
        }
        entity.add(new PositionComponent()).add(bc).add(new MultiTexturesComponent(new Texture("hedge.png"), 27f/64f, (int) (length / (27f/64f)) + 1, horizontal));
        engine.addEntity(entity);
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
        floor.texture.dispose();
        world.dispose();
    }
}
