package de.pythoneer.postprocessing.effects;

import android.opengl.GLES20;

import com.self.viewtoglrendering.R;

import java.util.Calendar;

import de.pythoneer.postprocessing.EffectItem;

/**
 * Created by dustin on 24.09.15.
 */
public class VhsEffect extends EffectItem {

    private long startTime;

    public VhsEffect() {
        super(R.raw.vertex_shader, R.raw.vhs_fragment_shader);
        startTime = Calendar.getInstance().getTimeInMillis();

    }

    @Override
    public void draw(int programHandle) {
        int resHandle = GLES20.glGetUniformLocation(programHandle, "u_Resolution");
        int timeHandle = GLES20.glGetUniformLocation(programHandle, "u_GlobalTime");

        if(resHandle != -1 && timeHandle != -1) {
            float timeInMs = Calendar.getInstance().getTimeInMillis() - startTime;

            if(timeInMs > 20000) {
                startTime = Calendar.getInstance().getTimeInMillis();
            }

            GLES20.glUniform2f(resHandle, 800f, 1280f);
            GLES20.glUniform1f(timeHandle, timeInMs / 100);
        }
    }
}
