package engine;

import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.system.CallbackI;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());

        GameObject ob1 = new GameObject("Object1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        ob1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/test.png")));
        this.addObjectToScene(ob1);

        GameObject ob2 = new GameObject("Object2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        ob2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/test1.jpg")));
        this.addObjectToScene(ob2);

        loadResources();

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

    }

    @Override
    public void update(float dt) {

        System.out.println("" + (1.0f/dt) + "FPS");

//        camera.position.x -= dt * 50f;
//        camera.position.y -= dt * 20f;

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();

    }

}
