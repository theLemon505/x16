package gui;

import engine.ecs.components.Vao;
import engine.enums.BufferTypes;
import engine.graphics.Shader;
import engine.graphics.Vbo;
import gui.elements.Element;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public abstract class Window {
    public List<Element> elements = new ArrayList<Element>();
    public String title;
    public float width, height;
    public Vector2f position = new Vector2f();
    private Vector3f color = new Vector3f(0.15f, 0.15f, 0.15f);
    private GuiVao screen;
    private GuiShader shader;
    private Matrix4f matrix = new Matrix4f();
    float[] vertices = {
            1, -1, 0,
            -1, 1, 0,
            1, 1, 0,
            -1, -1, 0
    };
    Vbo vertexBuffer = new Vbo(vertices, 3);

    int[] indices = {
            2, 1, 0,
            0, 1, 3
    };
    Vbo indexBuffer = new Vbo(indices, 1);

    float[] uvs = {
            1, 0,
            0, 1,
            1, 1,
            0, 0
    };
    Vbo uvBuffer = new Vbo(uvs, 2);

    public Window(String title, float width, float height){
        this.title = title;
        this.width = width;
        this.height = height;
    }
    public Window(String title, float width, float height, float x, float y){
        this.title = title;
        this.width = width;
        this.height = height;
        this.position.x = x;
        this.position.y = y;
    }

    public void addElement(Element element){
        elements.add(element);
    }

    public void init(){
        shader = new GuiShader("Gui.glsl");
        screen = new GuiVao();
        screen.shader = shader;
        screen.uploadBuffer(vertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        screen.uploadBuffer(indexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        screen.uploadBuffer(uvBuffer, BufferTypes.UV_ARRAY_DATA);
        updateMatrix();
        screen.init();
        for(Element element : elements){
            element.init();
        }
    }

    public void loop(){
        updateMatrix();
        screen.loop();
        for(Element element : elements){
            element.loop();
        }
    }

    private void updateMatrix(){
        matrix.identity();
        matrix.translate(position.x, position.y, 0);
        matrix.scale(width / 100, height / 100, 1);
    }

    public void draw(){
        shader.bind();

        glBindVertexArray(screen.id);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        shader.uploadMatrix(matrix, "transform");
        shader.uploadVector3f(color, "element_color");

        int[] vertexSize = (int[]) screen.buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
        glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shader.unbind();

        for(Element element : elements){
            element.draw();
        }
    }

    public void end(){
        for(Element element : elements){
            element.end();
        }
        screen.end();
    }
}
