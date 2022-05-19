package importers;

import ecs.components.Vao;
import enums.BufferTypes;
import graphics.Vbo;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjImporter{
    public static Vao loadData(String modelpath) {
        ArrayList<Float> positions = new ArrayList<Float>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        ArrayList<Float> texCoords = new ArrayList<Float>();
        ArrayList<Float> normals = new ArrayList<Float>();
        ArrayList<Float> colors = new ArrayList<Float>();

        String path = "src/main/resources/" + modelpath;

        loadModelFile(path, positions, indices, texCoords, normals, colors);

        float[] vertices = toFloatArray(positions);
        int[] inds = toIntArray(indices);
        float[] uvs = toFloatArray(texCoords);
        float[] norms = toFloatArray(normals);

        Vbo vertexBuffer = new Vbo(vertices, 3);
        Vbo indexBuffer = new Vbo(inds, 1);
        Vbo uvBuffer = new Vbo(uvs, 2);
        Vbo normalBuffer = new Vbo(norms, 3);


        Vao array = new Vao();

        array.uploadBuffer(vertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        array.uploadBuffer(indexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        array.uploadBuffer(uvBuffer, BufferTypes.UV_ARRAY_DATA);
        array.uploadBuffer(normalBuffer, BufferTypes.NORMAL_ARRAY_DATA);

        return array;
    }

    private static float[] toFloatArray(ArrayList<Float> arrList){
        final float[] arr = new float[arrList.size()];
        int index = 0;
        for (final Float value: arrList) {
            arr[index++] = value;
        }
        return arr;
    }

    private static int[] toIntArray(ArrayList<Integer> arrList){
        final int[] arr = new int[arrList.size()];
        int index = 0;
        for (final Integer value: arrList) {
            arr[index++] = value;
        }
        return arr;
    }

    private static void loadModelFile(String filepath, ArrayList<Float> positions, ArrayList<Integer> indices, ArrayList<Float> texCoords, ArrayList<Float> normals, ArrayList<Float> colors)
    {
        AIScene scene = Assimp.aiImportFile(filepath, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs);

        System.out.println(scene.mMeshes().limit());System.out.println(scene.mNumMeshes());
        PointerBuffer buffer = scene.mMeshes();

        for(int i = 0; i < buffer.limit(); i++)
        {
            AIMesh mesh = AIMesh.create(buffer.get(i));
            processMesh(mesh, positions, indices, texCoords, normals, colors);
        }
    }

    private static void processMesh(AIMesh mesh, ArrayList<Float> positions, ArrayList<Integer> indices, ArrayList<Float> texCoords, ArrayList<Float> normals, ArrayList<Float> colors)
    {
        AIVector3D.Buffer vectors = mesh.mVertices();

        for(int i = 0; i < vectors.limit(); i++)
        {
            indices.add(i);

            AIVector3D vector = vectors.get(i);

            positions.add(vector.x());
            positions.add(vector.y());
            positions.add(vector.z());
        }

        for(int i = 0; i < vectors.limit(); i++)
        {
            AIVector3D vector = vectors.get(i);

            positions.add(vector.x());
            positions.add(vector.y());
            positions.add(vector.z());
        }

        AIVector3D.Buffer coords = mesh.mTextureCoords(0);

        for(int i = 0; i < coords.limit(); i++)
        {
            AIVector3D coord = coords.get(i);

            texCoords.add(coord.x());
            texCoords.add(coord.y());
        }

        AIVector3D.Buffer norms = mesh.mNormals();

        for(int i = 0; i < norms.limit(); i++)
        {
            AIVector3D norm = norms.get(i);

            normals.add(norm.x());
            normals.add(norm.y());
            normals.add(norm.z());
        }

        AIColor4D.Buffer vertexColors = mesh.mColors(0);

        if(vertexColors != null) {
            for (int i = 0; i < vertexColors.limit(); i++) {
                AIColor4D vertexColor = vertexColors.get(i);

                colors.add(vertexColor.r());
                colors.add(vertexColor.g());
                colors.add(vertexColor.b());
                colors.add(vertexColor.a());
            }
        }
    }
}
