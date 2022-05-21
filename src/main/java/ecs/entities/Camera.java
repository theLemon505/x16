package ecs.entities;

import ecs.components.Transform;
import io.Display;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Entity{
    public Matrix4f projection = new Matrix4f();
    public Matrix4f view = new Matrix4f();
    public Vector3f front = new Vector3f(0, 0, 1);
    public Vector3f back = new Vector3f(0, 0, -1);
    public Vector3f left = new Vector3f(-1, 0, 0);
    public Vector3f right = new Vector3f(1, 0, 0);
    public Vector3f up = new Vector3f(0, 1, 1);
    public Vector3f down = new Vector3f(0, -1, 1);
    public float speed = 0.1f;


    public Camera(String name){
        super(name);
    }

    @Override
    public void awake() {

    }

    private void updateCameraMatrix(){
        projection.identity();
        projection.perspective(45, Display.aspectWidth / Display.aspectHeight, 0.01f, 500);
        view.identity();
        view.rotateX(getComponent(Transform.class).rotation.x);
        view.rotateY(getComponent(Transform.class).rotation.y);
        view.rotateZ(getComponent(Transform.class).rotation.z);
        view.translate(getComponent(Transform.class).position);
    }

    @Override
    public void init() {
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
