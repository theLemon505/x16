package ecs.entities;

import ecs.components.Transform;
import ecs.entities.ships.Ship;
import ecs.entities.ships.Shuttle;
import io.Input;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class Controller extends Entity{
    public Ship playerShip = new Shuttle("resonante");
    public Camera camera = new Camera("playerCamera");

    private float distanceFromShip = 50;
    private float angle = 0;
    private float zoom = 1;
    private float pitch = 0;

    public Controller() {
        super("playerController");
    }

    @Override
    public void awake() {
        parentScene.addEntity(playerShip);
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
        float offsetZ = (dh * (float)Math.cos(Math.toRadians(angle)));
        camera.getComponent(Transform.class).position.x = playerShip.getComponent(Transform.class).position.x - offsetX;
        camera.getComponent(Transform.class).position.z = playerShip.getComponent(Transform.class).position.z - offsetZ;
        camera.getComponent(Transform.class).rotation.y = -angle * 0.0175f;
    }

    private float getHorizontalDistance(){
        return distanceFromShip * (float)Math.cos(Math.toRadians(pitch));
    }

    private float getVerticalDistance(){
        return distanceFromShip * (float)Math.sin(Math.toRadians(pitch));
    }

    private void zoom(){
        zoom = Input.getScrollY();
        distanceFromShip -= zoom;
    }

    private void pitch(){
        if(Input.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
            pitch += Input.getDy() * 0.1f;
        }
    }

    private void angle(){
        if(Input.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
            angle += Input.getDx() * 0.1f;
        }
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
