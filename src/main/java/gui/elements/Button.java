package gui.elements;

import engine.enums.BufferTypes;
import gui.GuiShader;
import gui.GuiVao;
import gui.Quad;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Button extends Element{
    private GuiVao mesh;
    private GuiShader shader;
    private Vector3f color = new Vector3f(1,0.25f, 0);
    private Matrix4f matrix = new Matrix4f();
    public float x, y = 0;
    public float w, h = 1;
    @Override
    public void init() {
        shader = new GuiShader("Gui.glsl");
        mesh = new GuiVao();
        mesh.shader = shader;
        mesh.uploadBuffer(Quad.vertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        mesh.uploadBuffer(Quad.indexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        mesh.uploadBuffer(Quad.uvBuffer, BufferTypes.UV_ARRAY_DATA);
        updateMatrix();
        mesh.init();
    }

    private void updateMatrix(){
        matrix.identity();
        matrix.translate(x, y, 0);
        matrix.scale(w / 100, h / 100, 1);
    }

    @Override
    public void loop() {
        updateMatrix();
        mesh.loop();
    }

    @Override
    public void draw() {
        shader.bind();

        shader.uploadMatrix(matrix, "transform");
        shader.uploadVector3f(color, "element_color");

        glBindVertexArray(mesh.id);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        int[] vertexSize = (int[]) mesh.buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
        glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shader.unbind();
    }

    @Override
    public void end() {
        mesh.end();
    }
}
