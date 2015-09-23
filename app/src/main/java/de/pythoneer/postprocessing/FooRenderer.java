package de.pythoneer.postprocessing;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.self.viewtoglrendering.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Calendar;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FooRenderer extends BaseGLRenderer {


    private final FloatBuffer mCubePositions;
    private final FloatBuffer mCubeTextureCoordinates;

    private int mTextureUniformHandle;
    private int mPositionHandle;
    private int mTextureCoordinateHandle;

    private int mProgramHandle;
    private int mOtherProgramHandle;
    private int mOffsetHandle;

    private final int mBytesPerFloat = 4;
    private final int mPositionDataSize = 3;
    private final int mTextureCoordinateDataSize = 2;

    public boolean otherProgram = false;
    private Context mContext;
    private long startTime;

    public FooRenderer(Context context, int width, int height) {

        super(width, height);

        mContext = context;
        final float[] cubePositionData =
                {
                        // Front face
                        -1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,

                };

        final float[] cubeTextureCoordinateData =
                {
                        // Front face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                };

        mCubePositions = ByteBuffer.allocateDirect(cubePositionData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubePositions.put(cubePositionData).position(0);

        mCubeTextureCoordinates = ByteBuffer.allocateDirect(cubeTextureCoordinateData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeTextureCoordinates.put(cubeTextureCoordinateData).position(0);

        startTime = Calendar.getInstance().getTimeInMillis();
    }

    protected String getVertexShader()
    {
        return RawResourceReader.readTextFileFromRawResource(mContext, R.raw.vertex_shader);
    }

    protected String getFragmentShader()
    {
        return RawResourceReader.readTextFileFromRawResource(mContext, R.raw.fragment_shader);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        // Set the background clear color to black.
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        final String vertexShader = getVertexShader();
        final String fragmentShader = getFragmentShader();

        final int vertexShaderHandle = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        mProgramHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[] {"a_Position", "a_TexCoordinate"});


        final String vertexShader2 = RawResourceReader.readTextFileFromRawResource(mContext, R.raw.vertex_shader);
        final String fragmentShader2 = RawResourceReader.readTextFileFromRawResource(mContext, R.raw.wobble_fragment_shader);

        final int vertexShaderHandle2 = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader2);
        final int fragmentShaderHandle2 = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader2);

        mOtherProgramHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle2, fragmentShaderHandle2, new String[] {"a_Position", "a_TexCoordinate"});

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        // GL Draw code onwards
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


        final int currentProgramHandle = otherProgram ? mOtherProgramHandle : mProgramHandle;

        GLES20.glUseProgram(currentProgramHandle);

        mTextureUniformHandle = GLES20.glGetUniformLocation(currentProgramHandle, "u_Texture");
        mPositionHandle = GLES20.glGetAttribLocation(currentProgramHandle, "a_Position");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(currentProgramHandle, "a_TexCoordinate");

        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, getGLSurfaceTexture());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniform1i(mTextureUniformHandle, 0);

        mOffsetHandle = GLES20.glGetUniformLocation(currentProgramHandle, "offset");

        if(mOffsetHandle != -1) {
            double timeInMs = Calendar.getInstance().getTimeInMillis() - startTime;

            if(timeInMs > 10000) {
                startTime = Calendar.getInstance().getTimeInMillis();
            }

            float timeOffset = (float)(timeInMs / 1000.0f * 2f * 3.14159f * .75f);

            GLES20.glUniform1f(mOffsetHandle, timeOffset);
        }

        drawCube();

    }

    /**
     * Draws a cube.
     */
    private void drawCube()
    {
        // Pass in the position information
        mCubePositions.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
                0, mCubePositions);

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        mCubeTextureCoordinates.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES20.GL_FLOAT, false,
                0, mCubeTextureCoordinates);

        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
    }

}
