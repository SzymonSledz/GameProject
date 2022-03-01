package engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Rigidbody;
import components.Sprite;
import components.SpriteRenderer;
import components.SpriteSheet;
import imgui.ImGui;
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
        if (loadedLevel) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        SpriteSheet sprites = AssetPool.getSpriteSheet("assets/images/sprite_sheet.png");
        SpriteRenderer ob1Sprite = new SpriteRenderer();
        ob1Sprite.setColor(new Vector4f(1, 0, 0, 1));
        ob1.addComponent(ob1Sprite);
        this.addObjectToScene(ob1);
        ob1.addComponent(new Rigidbody());

        GameObject ob2 = new GameObject("Object2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), -2);
        SpriteRenderer ob2SpriteRenderer = new SpriteRenderer();
        Sprite ob2Sprite = new Sprite();
        ob2Sprite.setTexture(AssetPool.getTexture("assets/images/test.png"));
        ob2SpriteRenderer.setSprite(ob2Sprite);
        ob2.addComponent(ob2SpriteRenderer);
        this.addObjectToScene(ob2);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();
        String serialized = gson.toJson(ob1);
        gson.fromJson(serialized, SpriteRenderer.class);
        System.out.println(serialized);
        GameObject go = gson.fromJson(serialized, GameObject.class);
        System.out.println(go);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.getTexture("assets/images/test.png");

        AssetPool.addSpriteSheet("assets/images/sprite_sheet.png", new SpriteSheet(AssetPool.getTexture("assets/images/sprite_sheet.png"), 32,32,4, 0));
    }

    @Override
    public void update(float dt) {

        ob1.transform.position.x += 1;

//        System.out.println("" + (1.0f/dt) + "FPS");

//        camera.position.x -= dt * 50f;
//        camera.position.y -= dt * 20f;

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();

    }

    @Override
    public void imgui() {
        ImGui.begin("test window");
        ImGui.text("Some random text");
        ImGui.end();
    }

}
