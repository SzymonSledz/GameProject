package scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.*;
import engine.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderer.DebugDraw;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private GameObject ob1 = new GameObject("Object1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), -1);
    private SpriteSheet sprites;
    SpriteRenderer ob1Sprite;

    MouseControls mouseControls = new MouseControls();

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());
        sprites = AssetPool.getSpriteSheet("assets/images/sprite_sheet.png");
        DebugDraw.addLine2D(new Vector2f(600, 400), new Vector2f(0, 0), new Vector3f(0, 0, 1));
        if (loadedLevel) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        ob1Sprite = new SpriteRenderer();
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

        AssetPool.addSpriteSheet("assets/images/sprite_sheet.png", new SpriteSheet(AssetPool.getTexture("assets/images/sprite_sheet.png"), 32,32,16, 0));
    }

    @Override
    public void update(float dt) {

        mouseControls.update(dt);


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

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)){
//                System.out.println("Button " + i + " clicked");
                GameObject object = Prefabs.generateSpriteObject(sprite, spriteWidth, spriteHeight);
                //Attach this to mouse cursor
                mouseControls.pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i+1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }

}
