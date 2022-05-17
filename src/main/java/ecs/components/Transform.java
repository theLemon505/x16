package ecs.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform extends Component{
    public Matrix4f matrix = new Matrix4f();
    public Vector3f position = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public Vector3f scale = new Vector3f(1,1f,1);

    public Transform(){
        updateMatrix();
    }

    private void updateMatrix(){
        matrix.identity();
        matrix.translate(position);
        matrix.rotateX(rotation.x);
        matrix.rotateY(rotation.y);
        matrix.rotateZ(rotation.z);
        matrix.scale(scale);
    }

    @Override
    public void init() {
    }

    @Override
    public void loop() {
        updateMatrix();
    }

    @Override
    public void end() {

    }
}
