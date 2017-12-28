package pl.moonwolf.blocks.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import pl.moonwolf.blocks.input.GestureListener;
import pl.moonwolf.blocks.components.BodyComponent;
import pl.moonwolf.blocks.components.PositionComponent;
import pl.moonwolf.blocks.components.TextureComponent;
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
        tc.width = tc.texture.getWidth() / 64f; // 64 pixels is 1 meter
        floor = new TextureComponent();
        floor.texture = new Texture("floor.png");
        floor.width = floor.texture.getWidth() / 64f;
        engine = new PooledEngine();
        engine.addSystem(new RenderSystem(batch, viewport.getCamera()));
        world = new World(new Vector2(0f, 0f), true);
        World.setVelocityThreshold(0f);
        engine.addSystem(new Box2dSystem(world));
        float x = 0;
        float y = 0;
        while (y < Blocks.VIRTUAL_HEIGHT)
        {
            while (x < Blocks.VIRTUAL_WIDTH)
            {
                PositionComponent pc = new PositionComponent();
                pc.pos = new Vector2(x, y);
                engine.addEntity(engine.createEntity().add(floor).add(pc));
                x += floor.width;
            }
            x = 0;
            y += floor.width;
        }
        Entity block = engine.createEntity();
        block.add(tc);
        block.add(new PositionComponent());
        BodyComponent bc = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Set our body's starting position in the world
        bodyDef.position.set(0f, 0.2f);

        bodyDef.angularDamping = 0f;
        bodyDef.linearDamping = 0f;

        // Create our body in the world using our body definition
        bc.body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(tc.width / 2f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1f;
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
        bc = new BodyComponent();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0f, Blocks.VIRTUAL_HEIGHT / 2f);
        EdgeShape border = new EdgeShape();
        border.set(0, 0, Blocks.VIRTUAL_WIDTH,0);
        fixtureDef.shape = border;
        bc.body = world.createBody(bodyDef);
        bc.body.createFixture(fixtureDef);
        block = engine.createEntity();
        block.add(new PositionComponent()).add(bc).add(new MultiTexturesComponent(new Texture("hedge.png"), 27f/64f, (int) (Blocks.VIRTUAL_WIDTH / (27f/64f)) + 1));
        engine.addEntity(block);

        // bottom barrier
        bc = new BodyComponent();
        bodyDef.position.set(0f, 0f);
        bc.body = world.createBody(bodyDef);
        bc.body.createFixture(fixtureDef);
        block = engine.createEntity();
        block.add(new PositionComponent()).add(bc).add(new MultiTexturesComponent(new Texture("hedge.png"), 27f/64f, (int) (Blocks.VIRTUAL_WIDTH / (27f/64f)) + 1));
        engine.addEntity(block);
        InputResponeSystem irs = new InputResponeSystem();
        Gdx.input.setInputProcessor(new GestureDetector(new GestureListener(irs)));
        engine.addSystem(irs);
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
