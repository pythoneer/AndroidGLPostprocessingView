package de.pythoneer.postprocessing;

import android.opengl.GLES20;

import com.self.viewtoglrendering.R;

import java.util.Calendar;

/**
 * Created by dustin on 23.09.15.
 */
public class SimpleScanLineEffect extends EffectItem {

    public SimpleScanLineEffect() {
        super(R.raw.vertex_shader, R.raw.simple_scanline_fragment_shader);
    }

    @Override
    public void draw(int programHandle) {

        int resHandle = GLES20.glGetUniformLocation(programHandle, "u_ResolutionY");
        int scaleHandle = GLES20.glGetUniformLocation(programHandle, "u_Scale");

        if(resHandle != -1 && scaleHandle != -1) {
            GLES20.glUniform1f(resHandle, 500f);
            GLES20.glUniform1f(scaleHandle, 2f);
        }

    }
}
