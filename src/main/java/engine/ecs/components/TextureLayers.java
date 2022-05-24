package engine.ecs.components;

import engine.graphics.ImageTexture;
import engine.graphics.SkyboxTexture;
import engine.graphics.Texture;

public class TextureLayers extends Component{
    public Texture albedoTexture;
    public SkyboxTexture skyboxTexture;
    public boolean skybox = false;

    public TextureLayers(String albedoPath){
        this.albedoTexture = new ImageTexture(albedoPath);
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
