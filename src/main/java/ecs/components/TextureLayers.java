package ecs.components;

import graphics.Texture;
import org.w3c.dom.Text;

public class TextureLayers extends Component{
    public Texture albedoTexture;
    public TextureLayers(String albedoPath){
        this.albedoTexture = new Texture(albedoPath);
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
