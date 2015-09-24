package de.pythoneer.postprocessing.effects;

import android.opengl.GLES20;

import com.self.viewtoglrendering.R;

import java.util.Calendar;

import de.pythoneer.postprocessing.EffectItem;

/**
 * Created by dustin on 23.09.15.
 */
public class WobbleEffect extends EffectItem {

    private long startTime;

    public WobbleEffect() {
        super(R.raw.vertex_shader, R.raw.wobble_fragment_shader);
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void draw(int programHandle) {


        int mOffsetHandle = GLES20.glGetUniformLocation(programHandle, "offset");

        if(mOffsetHandle != -1) {
            double timeInMs = Calendar.getInstance().getTimeInMillis() - startTime;

            if(timeInMs > 20000) {
                startTime = Calendar.getInstance().getTimeInMillis();
            }

            float timeOffset = (float)(timeInMs / 1000.0f * 2f * 3.14159f * .75f);

            GLES20.glUniform1f(mOffsetHandle, timeOffset);
        }

    }
}
