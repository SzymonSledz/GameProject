package engine;

import components.Sprite;
import components.SpriteRenderer;
import components.SpriteSheet;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.system.CallbackI;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private GameObject ob1 = new GameObject("Object1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), -1);


    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());

        SpriteSheet sprites = AssetPool.getSpriteSheet("assets/images/sprite_sheet.png");

        ob1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addObjectToScene(ob1);

        GameObject ob2 = new GameObject("Object2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), -2);
        ob2.addComponent(new SpriteRenderer(sprites.getSprite(1)));
        this.addObjectToScene(ob2);



    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/sprite_sheet.png", new SpriteSheet(AssetPool.getTexture("assets/images/sprite_sheet.png"), 32,32,4, 0));
    }

    @Override
    public void update(float dt) {

        ob1.transform.position.x += 1;

        System.out.println("" + (1.0f/dt) + "FPS");

//        camera.position.x -= dt * 50f;
//        camera.position.y -= dt * 20f;

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();

    }

}
