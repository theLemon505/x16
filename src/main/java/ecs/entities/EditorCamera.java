package ecs.entities;

import app.Main;
import ecs.components.Transform;
import io.Display;
import io.Input;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class EditorCamera extends Entity{
    public Matrix4f projection = new Matrix4f();
    public Matrix4f view = new Matrix4f();
    public Vector3f front = new Vector3f(0, 0, 1);
    public Vector3f back = new Vector3f(0, 0, -1);
    public Vector3f left = new Vector3f(-1, 0, 0);
    public Vector3f right = new Vector3f(1, 0, 0);
    public Vector3f up = new Vector3f(0, 1, 1);
    public Vector3f down = new Vector3f(0, -1, 1);
    public float speed = 0.1f;


    public EditorCamera(String name){
        super(name);
    }

    private void updateCameraMatrix(){
        projection.identity();
        projection.perspective(70, Display.width/Display.height, 0.01f, 100);
        view.identity();
        Transform transform = getComponent(Transform.class);
        view.rotateX(transform.rotation.x);
        view.rotateY(transform.rotation.y);
        view.rotateZ(transform.rotation.z);
        view.translate(transform.position);
    }

    @Override
    public void init() {
        getComponent(Transform.class).position.z = -5;
        updateCameraMatrix();
        lowerInit();
    }

    @Override
    public void loop() {
        updateCameraMatrix();
        lowerLoop();
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
