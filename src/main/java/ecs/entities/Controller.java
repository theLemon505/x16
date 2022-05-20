package ecs.entities;

import ecs.components.Transform;
import io.Input;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class Controller extends Entity{
    public Camera camera = new Camera("playerCamera");

    private float distanceFromShip = 1000;
    private float angle = 0;
    private float zoom = 1;
    private float pitch = 0;

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
        lowerInit();
    }

    @Override
    public void loop() {
        zoom();
        pitch();
        angle();
        float dh = getHorizontalDistance();
        float dv = getVerticalDistance();
        setCameraPosition(dh, dv);

        lowerLoop();
    }

    private void setCameraPosition(float dh, float dv){
        float offsetX = dh * (float)Math.sin(Math.toRadians(angle));
        float offsetZ = dh * (float)Math.cos(Math.toRadians(angle));

        float offsetY = (dv * (float)Math.sin(Math.toRadians(0))) + (dv * (float)Math.cos(Math.toRadians(0)));

        camera.getComponent(Transform.class).rotation.x = pitch * 0.005f;
        camera.getComponent(Transform.class).rotation.z = pitch * 0.005f;
        camera.getComponent(Transform.class).rotation.y = angle * 0.005f;
    }

    private float getHorizontalDistance(){
        return distanceFromShip * (float)Math.cos(Math.toRadians(pitch));
    }

    private float getVerticalDistance(){
        return distanceFromShip * (float)Math.sin(Math.toRadians(pitch));
    }

    private void zoom(){
        zoom = Input.getScrollY();
        distanceFromShip -= zoom * 10;
    }

    private void pitch(){
        if(Input.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
            pitch += Input.getDy() * 0.5f;
        }
    }

    private void angle(){
        if(Input.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
            angle += Input.getDx() * 0.5f;
        }
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
