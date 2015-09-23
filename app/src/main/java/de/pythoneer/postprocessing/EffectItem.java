package de.pythoneer.postprocessing;

import android.content.Context;
import android.opengl.GLES20;

import com.self.viewtoglrendering.R;

/**
 * Created by dustin on 23.09.15.
 */
public abstract class EffectItem {

    private int vertexShaderResource;
    private int fragmentShaderResource;

    private int programHandle;

    public EffectItem(int vertexShaderResource, int fragmentShaderResource) {
        this.vertexShaderResource = vertexShaderResource;
        this.fragmentShaderResource = fragmentShaderResource;
    }

    public abstract void draw(int programHandle);

    public void init(Context context) {
        final String vertexShader = RawResourceReader.readTextFileFromRawResource(context, vertexShaderResource);
        final String fragmentShader = RawResourceReader.readTextFileFromRawResource(context, fragmentShaderResource);

        final int vertexShaderHandle = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        programHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[] {"a_Position", "a_TexCoordinate"});
    }


    public int getProgramHandle() {
        return programHandle;
    }
}
