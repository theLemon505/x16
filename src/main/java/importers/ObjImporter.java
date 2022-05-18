package importers;

import ecs.components.Vao;
import enums.BufferTypes;
import graphics.Vbo;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjImporter{
    public static Vao loadData(String path) {
        FileReader fileReader = null;
        try{
            fileReader = new FileReader(new File("src/main/resources/" + path));
        }catch (FileNotFoundException e){
            System.err.println("could not find file: " + path);
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fileReader);

        String line;

        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Vector2f> uvs = new ArrayList<Vector2f>();
        List<Integer> indices = new ArrayList<Integer>();

        float[] vertexArray = null;
        float[] normalArray = null;
        float[] uvArray = null;
        int[] indexArray = null;

        try{
            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f();
                    vertex.x = Float.parseFloat(currentLine[1]);
                    vertex.y = Float.parseFloat(currentLine[2]);
                    vertex.z = Float.parseFloat(currentLine[3]);
                    vertices.add(vertex);
                }
                else if(line.startsWith("vt ")){
                    Vector2f uv = new Vector2f();
                    uv.x = Float.parseFloat(currentLine[1]);
                    uv.y = Float.parseFloat(currentLine[2]);
                    uvs.add(uv);
                }
                else if(line.startsWith("vn ")){
                    Vector3f normal = new Vector3f();
                    normal.x = Float.parseFloat(currentLine[1]);
                    normal.y = Float.parseFloat(currentLine[2]);
                    normal.z = Float.parseFloat(currentLine[3]);
                    normals.add(normal);
                }
                else if(line.startsWith("f ")){
                    uvArray = new float[vertices.size() * 2];
                    normalArray = new float[vertices.size() * 3];
                    break;
                }
            }

            while(line != null){
                if(!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, uvs, normals, uvArray, normalArray);
                processVertex(vertex2, indices, uvs, normals, uvArray, normalArray);
                processVertex(vertex3, indices, uvs, normals, uvArray, normalArray);

                line = reader.readLine();
            }
            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        vertexArray = new float[vertices.size() * 3];
        indexArray = new int[indices.size()];

        int vertexPointer = 0;

        for(Vector3f vertex:vertices){
            vertexArray[vertexPointer++] = vertex.x;
            vertexArray[vertexPointer++] = vertex.y;
            vertexArray[vertexPointer++] = vertex.z;
        }
        for(int i = 0; i < indices.size(); i++){
            indexArray[i] = indices.get(i);
        }

        Vbo vertexBuffer = new Vbo(vertexArray, 3);
        Vbo indexBuffer = new Vbo(indexArray, 1);
        Vbo uvBuffer = new Vbo(uvArray, 2);

        Vao arrayBuffer = new Vao();

        arrayBuffer.uploadBuffer(vertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        arrayBuffer.uploadBuffer(indexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        arrayBuffer.uploadBuffer(uvBuffer, BufferTypes.UV_ARRAY_DATA);

        return arrayBuffer;
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> uvs, List<Vector3f> normals, float[] uvArray, float[] normalArray){
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = uvs.get(Integer.parseInt(vertexData[1]) - 1);
        uvArray[currentVertexPointer * 2] = currentTex.x;
        uvArray[currentVertexPointer * 2 + 1] = currentTex.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalArray[currentVertexPointer * 3] = currentNorm.x;
        normalArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalArray[currentVertexPointer * 3 + 2] = currentNorm.z;
    }
}
