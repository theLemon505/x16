package ecs.entities;

import app.Main;
import ecs.components.Transform;
import io.Display;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class EditorCamera extends Entity{
    public Matrix4f projection = new Matrix4f();
    public Matrix4f view = new Matrix4f();

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
    }

    @Override
    public void loop() {
        updateCameraMatrix();
    }

    @Override
    public void end() {

    }
}
