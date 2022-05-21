package graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class PassShader {
    private String vertexSource = "";
    private String fragmentSource = "";
    public int vid, fid, pid;

    public PassShader(String source){
        loadShader(source);
        compileShader();
    }

    private void compileShader(){
        pid = glCreateProgram();
        vid = createShader(vertexSource, GL_VERTEX_SHADER);
        fid = createShader(fragmentSource, GL_FRAGMENT_SHADER);
    }

    public void uploadMatrix(Matrix4f matrix, String name){
        int loc = glGetUniformLocation(pid, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(loc, false, buffer);
    }

    public void uploadVector3f(Vector3f vector, String name){
        int loc = glGetUniformLocation(pid, name);
        glUniform3f(loc, vector.x, vector.y, vector.z);
    }

    public void uploadTexture(int slot, String name){
        int loc = glGetUniformLocation(pid, name);
        glUniform1i(loc, slot);
    }

    private int createShader(String shaderCode, int shaderType){
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            System.err.println("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            System.err.println("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(pid, shaderId);

        return shaderId;
    }

    public void link(){
        glLinkProgram(pid);
        if (glGetProgrami(pid, GL_LINK_STATUS) == 0) {
            System.err.println("Error linking Shader code: " + glGetProgramInfoLog(pid, 1024));
        }

        if (vid != 0) {
            glDetachShader(pid, vid);
        }
        if (fid != 0) {
            glDetachShader(pid, fid);
        }

        glValidateProgram(pid);
        if (glGetProgrami(pid, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(pid, 1024));
        }
    }

    public void loadShader(String source){
        StringBuilder result = new StringBuilder();
        InputStream in = ClassLoader.getSystemResourceAsStream("passShaders/" + source);
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String buffer = "";
            while((buffer = bufferedReader.readLine()) != null)
                result.append(buffer + "\n");
            bufferedReader.close();
        }catch (IOException e1){
            e1.printStackTrace();
        }

        String[] arrays = result.toString().split("#fragment");
        String[] v = arrays[0].split("#vertex");
        vertexSource = v[1];

        String f = arrays[1];
        fragmentSource = f;
    }

    public void bind() {
        glUseProgram(pid);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (pid != 0) {
            glDeleteProgram(pid);
        }
    }
}
