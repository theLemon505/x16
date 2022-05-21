package ecs.components;

import ecs.entities.Skybox;
import graphics.SkyboxTexture;
import graphics.Texture;
import org.w3c.dom.Text;

public class TextureLayers extends Component{
    public Texture albedoTexture;
    public SkyboxTexture skyboxTexture;
    public boolean skybox = false;

    public TextureLayers(String albedoPath){
        this.albedoTexture = new Texture(albedoPath);
    }

    public TextureLayers(String front, String back, String left, String right, String top, String bottom) {
        String[] faces = {left, right, top, bottom, front, back};
        this.skyboxTexture = new SkyboxTexture(faces);
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {

    }
}
