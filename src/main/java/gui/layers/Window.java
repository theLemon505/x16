package gui.layers;

import ecs.components.Transform;
import ecs.components.Vao;
import ecs.entities.Entity;
import enums.BufferTypes;
import graphics.Shader;
import graphics.Vbo;
import org.joml.Vector3f;

public class Window extends Entity {

    public Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);

    private Vao viewport;
    private Shader shader;

    private float[] vertices = {
            1, -1, 0,
            -1, 1, 0,
            1, 1, 0,
            -1, -1, 0
    };
    private Vbo vertexBuffer = new Vbo(vertices, 3);

    private int[] indices = {
            2, 1, 0,
            0, 1, 3
    };
    private Vbo indexBuffer = new Vbo(indices, 1);
    private float[] uvs = {
            0, 1,
            0, 0,
            1, 1,
            1, 0
    };
    private Vbo uvBuffer = new Vbo(uvs, 2);

    public Window(String name) {
        super(name);
    }

    @Override
    public void awake() {
        viewport = new Vao();
        shader = new Shader("Gui.glsl");
        viewport.shader = shader;
    }

    @Override
    public void init() {
        viewport.uploadBuffer(vertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        viewport.uploadBuffer(indexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        viewport.uploadBuffer(uvBuffer, BufferTypes.UV_ARRAY_DATA);
        addComponent(viewport);
        getComponent(Transform.class).scale = new Vector3f(0.2f, 0.2f, 0.2f);
        getComponent(Transform.class).position = new Vector3f(0.75f, 0.5f, 0);
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
