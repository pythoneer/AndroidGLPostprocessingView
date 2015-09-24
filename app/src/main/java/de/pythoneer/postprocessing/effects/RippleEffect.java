package de.pythoneer.postprocessing.effects;

import android.opengl.GLES20;
import android.os.Handler;

import com.self.viewtoglrendering.R;

import java.util.Calendar;
import java.util.Random;

import de.pythoneer.postprocessing.EffectItem;

/**
 * Created by dustin on 24.09.15.
 */
public class RippleEffect extends EffectItem {

    private long startTime;
    private float x = 0.5f;
    private float y = 0.5f;

    public RippleEffect(boolean testMode) {
        super(R.raw.vertex_shader, R.raw.ripple_fragment_shader);
        startTime = Calendar.getInstance().getTimeInMillis();
        if(testMode) {
            final Random r = new Random();

            final Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onTuch(r.nextInt(800), r.nextInt(1280));
                    h.postDelayed(this, 2500);
                }
            }, 2500);

        }
    }

    @Override
    public void draw(int programHandle) {

        int resHandle = GLES20.glGetUniformLocation(programHandle, "u_Resolution");
        int touchCenterHandle = GLES20.glGetUniformLocation(programHandle, "u_TouchCenter");
        int touchStartTimeHandle= GLES20.glGetUniformLocation(programHandle, "u_TouchStartTime");

        if(resHandle != -1 && touchCenterHandle != -1 && touchStartTimeHandle != -1) {

            float timeInMs = (Calendar.getInstance().getTimeInMillis() - startTime) / 2000f;

            System.out.println("send to shader " + x + " " + y + " " +  (timeInMs ) );
            GLES20.glUniform2f(touchCenterHandle, x, y);
            GLES20.glUniform1f(touchStartTimeHandle, timeInMs);
            GLES20.glUniform2f(resHandle, 800f, 1280f);
        }

    }

    public void onTuch(int x, int y) {
        System.out.println("Ripple on touch");

        this.x = x / 800f;
        this.y = y / 1280f;

        startTime = Calendar.getInstance().getTimeInMillis();
    }
}
