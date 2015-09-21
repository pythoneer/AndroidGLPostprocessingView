package lordnaikon.de.glpostprocessingview;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by naikon on 20.09.15.
 */
public class Square {

    private static final int BYTES_PER_FLOAT = 4;
    private final int mTextureCoordinateDataSize = 2;

    private final int mProgram;
    private final FloatBuffer mCubePositions;
    private int mTextureCoordinateHandle;

    private Context context;
    private GlViewRenderer renderer;

//    private final String vertexShaderCode =
//            "attribute vec4 vPosition;" +
//            "void main() {" +
//            "  gl_Position = vPosition;" +
//            "}";

//    private final String fragmentShaderCode =
//            "precision mediump float;" +
//            "void main() {" +
//            "  gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);" +
//            "}";

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {

//            -0.5f,  0.5f, 0.0f,   // top left
//            -0.5f, -0.5f, 0.0f,   // bottom left
//            0.5f, -0.5f, 0.0f,   // bottom right
//            0.5f,  0.5f, 0.0f     // top right

            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

    };

//    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    private final FloatBuffer textureCoordinates;
    final float[] textureCoordinatesDate =
            {
                    // Front face
                    0.0f, 0.0f,
                    0.0f, 0.5f,
                    0.5f, 0.0f,
                    0.0f, 0.5f,
                    0.5f, 0.5f,
                    0.5f, 0.0f,

//                    1.0f, 1.0f,
//                    1.0f, 0.0f,
//                    0.0f, 0.0f,
//                    0.0f, 1.0f,
            };

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private int mTextureUniformHandle;

    private int mPositionDataSize = 3;

    public Square(Context context, GlViewRenderer renderer) {

        this.context = context;
        this.renderer = renderer;

//        // initialize vertex byte buffer for shape coordinates
//        ByteBuffer bb = ByteBuffer.allocateDirect(
//                // (# of coordinate values * 4 bytes per float)
//                squareCoords.length * 4);
//        bb.order(ByteOrder.nativeOrder());
//        vertexBuffer = bb.asFloatBuffer();
//        vertexBuffer.put(squareCoords);
//        vertexBuffer.position(0);
//
//        // initialize byte buffer for the draw list
//        ByteBuffer dlb = ByteBuffer.allocateDirect(
//                // (# of coordinate values * 2 bytes per short)
//                drawOrder.length * 2);
//        dlb.order(ByteOrder.nativeOrder());
//        drawListBuffer = dlb.asShortBuffer();
//        drawListBuffer.put(drawOrder);
//        drawListBuffer.position(0);

        // Initialize the buffers.
        mCubePositions = ByteBuffer.allocateDirect(squareCoords.length * BYTES_PER_FLOAT) .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubePositions.put(squareCoords).position(0);

        textureCoordinates = ByteBuffer.allocateDirect(textureCoordinatesDate.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates.put(textureCoordinatesDate).position(0);

//        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
//        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
//
//        // create empty OpenGL ES Program
//        mProgram = GLES20.glCreateProgram();
//
//        // add the vertex shader to program
//        GLES20.glAttachShader(mProgram, vertexShader);
//
//        // add the fragment shader to program
//        GLES20.glAttachShader(mProgram, fragmentShader);
//
//        // creates OpenGL ES program executables
//        GLES20.glLinkProgram(mProgram);

        final String vertexShader = RawResourceReader.readTextFileFromRawResource(context, R.raw.vertex_shader);
        final String fragmentShader = RawResourceReader.readTextFileFromRawResource(context, R.raw.fragment_shader);

        final int vertexShaderHandle = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        mProgram = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[] {"vPosition"});
    }

    public void draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // prepare texture
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, renderer.getGLSurfaceTexture());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniform1i(mTextureUniformHandle, 0);


        // Pass in the position information
        mCubePositions.position(0);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false, 0, mCubePositions);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

//        // Prepare the triangle coordinate data
//        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
//        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
//        GLES20.glEnableVertexAttribArray(mPositionHandle);


        // Pass in the texture coordinate information
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");
        textureCoordinates.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, textureCoordinates);
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);



        // get handle to fragment shader's vColor member
//        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        //GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // Draw the triangle
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // Draw the square
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);



        // Draw the cube.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureCoordinateHandle);
    }
}