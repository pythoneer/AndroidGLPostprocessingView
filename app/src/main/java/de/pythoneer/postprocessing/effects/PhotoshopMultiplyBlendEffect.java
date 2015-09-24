package de.pythoneer.postprocessing.effects;

import android.graphics.Rect;
import android.opengl.GLES20;

import com.self.viewtoglrendering.R;

import java.util.Calendar;

import de.pythoneer.postprocessing.EffectItem;

/**
 * Created by dustin on 24.09.15.
 */
public class PhotoshopMultiplyBlendEffect extends EffectItem {

    Rect r1 = new Rect();

    private long startTime;

    public PhotoshopMultiplyBlendEffect() {
        super(R.raw.vertex_shader, R.raw.photoshop_multiply_blend_fragment);

        startTime = Calendar.getInstance().getTimeInMillis();

        r1.set(100, 100, 200, 200);
    }

    @Override
    public void draw(int programHandle) {

        int timeHandle = GLES20.glGetUniformLocation(programHandle, "u_GlobalTime");

        if(timeHandle != -1) {
            float timeInMs = Calendar.getInstance().getTimeInMillis() - startTime;

            if(timeInMs > 20000) {
                startTime = Calendar.getInstance().getTimeInMillis();
            }

            GLES20.glUniform1f(timeHandle, timeInMs / 100);
        }

    }
}
