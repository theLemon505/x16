package engine.ecs.entities;

import engine.ecs.components.TextureLayers;
import engine.ecs.components.Vao;
import engine.graphics.Shader;
import engine.importers.ObjImporter;

public class TestMap extends Entity{
    private Vao mesh;
    private TextureLayers textures;

    public TestMap() {
        super("test_map");
    }

    @Override
    public void awake() {
        mesh = ObjImporter.loadData("test_map.obj");
        mesh.shader = new Shader("Lit.glsl");
        textures = new TextureLayers("testTexture.png");
    }

    @Override
    public void init() {
        addComponent(mesh);
        addComponent(textures);
        lowerInit();
    }

    @Override
    public void loop() {
        lowerLoop();
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
