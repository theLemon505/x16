package ecs.entities;

import app.Prefrences;
import ecs.components.Transform;
import io.Input;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Controller extends Entity{
    public Camera camera = new Camera("playerCamera");

    private float distanceFromShip = 10;
    private float angle = 0;
    private float zoom = 1;
    private float pitch = 0;
    private float speed = Prefrences.editorCameraSpeed;

    public Controller() {
        super("playerController");
    }

    @Override
    public void awake() {
        parentScene.addEntity(camera);
    }

    @Override
    public void init() {
        camera.getComponent(Transform.class).position.z -= distanceFromShip;
        camera.getComponent(Transform.class).position.y -= distanceFromShip;
        lowerInit();
    }

    @Override
    public void loop() {
        if(Input.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
            speed = Prefrences.editorCameraSpeed * 2;
        }else{
            speed = Prefrences.editorCameraSpeed;
        }
        zoom();
        pitch();
        angle();
        float dh = getHorizontalDistance();
        float dv = getVerticalDistance();
        setCameraRotation(dh, dv);
        setCameraPosition();

        lowerLoop();
    }

    private void setCameraRotation(float dh, float dv){
        camera.getComponent(Transform.class).rotation.x = pitch * 0.005f;
        camera.getComponent(Transform.class).rotation.y = angle * 0.005f;
    }

    private void setCameraPosition(){
        float x = getSin() * speed;
        float z = getCos() * speed;

        Vector3f position = camera.getComponent(Transform.class).position;
        if(Input.isKeyPressed(GLFW_KEY_W)){
            position.add(new Vector3f(x, 0, z));
        }
        if(Input.isKeyPressed(GLFW_KEY_S)){
            position.add(new Vector3f(-x, 0, -z));
        }
        if(Input.isKeyPressed(GLFW_KEY_A)){
            position.add(new Vector3f(z, 0, -x));
        }
        if(Input.isKeyPressed(GLFW_KEY_D)){
            position.add(new Vector3f(-z, 0, x));
        }
        if(Input.isKeyPressed(GLFW_KEY_E)){
            position.add(new Vector3f(0, -speed, 0));
        }
        if(Input.isKeyPressed(GLFW_KEY_Q)){
            position.add(new Vector3f(0, speed, 0));
        }
    }

    private float getCos(){
        float rotation = (float)Math.toDegrees(camera.getComponent(Transform.class).rotation.y);
        if(rotation > 360){
            rotation = 0;
            angle = 0;
            camera.getComponent(Transform.class).rotation.y = 0;
        }
        else if(rotation < -360){
            rotation = 0;
            angle = 0;
            camera.getComponent(Transform.class).rotation.y = 0;
        }
        return (float)Math.cos(Math.toRadians(-rotation));
    }

    private float getSin(){
        float rotation = (float)Math.toDegrees(camera.getComponent(Transform.class).rotation.y);
        if(rotation > 360){
            rotation = 0;
            angle = 0;
            camera.getComponent(Transform.class).rotation.y = 0;
        }
        else if(rotation < -360){
            rotation = 0;
            angle = 0;
            camera.getComponent(Transform.class).rotation.y = 0;
        }
        return (float)Math.sin(Math.toRadians(-rotation));
    }

    private float getHorizontalDistance(){
        return (float)Math.cos(Math.toRadians(pitch));
    }

    private float getVerticalDistance(){
        return (float)Math.sin(Math.toRadians(pitch));
    }

    private void zoom(){
        zoom = Input.getScrollY();
        distanceFromShip -= zoom * 10;
    }

    private void pitch(){
        if(Input.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
            pitch += Input.getDy() * Prefrences.ySensitivity;
        }
    }

    private void angle(){
        if(Input.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
            angle += Input.getDx() * Prefrences.xSensitivity;
        }
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
