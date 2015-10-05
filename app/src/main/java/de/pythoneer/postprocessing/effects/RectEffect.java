package de.pythoneer.postprocessing.effects;

import android.graphics.Rect;
import android.opengl.GLES20;

import com.self.viewtoglrendering.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.pythoneer.postprocessing.EffectItem;

/**
 * Created by dustin on 05.10.15.
 */
public class RectEffect extends EffectItem {

    public class GLColor {
        private float r;
        private float g;
        private float b;

        public GLColor(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    List<GLColor> colors;

    public RectEffect() {
        super(R.raw.vertex_shader, R.raw.rect_fragment_shader);

        Random r = new Random();
        colors = Arrays.asList(
                new GLColor(r.nextFloat(), r.nextFloat(), r.nextFloat()),
                new GLColor(r.nextFloat(), r.nextFloat(), r.nextFloat()),
                new GLColor(r.nextFloat(), r.nextFloat(), r.nextFloat()),
                new GLColor(r.nextFloat(), r.nextFloat(), r.nextFloat())
        );
    }

    @Override
    public void draw(int programHandle) {


        List<Rect> rectList = Arrays.asList(
                new Rect(100, 100, 300, 300),
                new Rect(200, 200, 400, 400),
                new Rect(400, 400, 500, 500),
                new Rect(400, 400, 500, 500)
        );

        int idx = 0;
        for(Rect rect: rectList) {

            int color = GLES20.glGetUniformLocation(programHandle, String.format("u_Rects[%d].color", idx));
            int left = GLES20.glGetUniformLocation(programHandle, String.format("u_Rects[%d].left", idx));
            int top = GLES20.glGetUniformLocation(programHandle, String.format("u_Rects[%d].top", idx));
            int right = GLES20.glGetUniformLocation(programHandle, String.format("u_Rects[%d].right", idx));
            int bottom = GLES20.glGetUniformLocation(programHandle, String.format("u_Rects[%d].bottom", idx));

            GLES20.glUniform4f(color, colors.get(idx).r, colors.get(idx).g, colors.get(idx).b, 0.0f);

            GLES20.glUniform1f(left, 0.1f * (idx + 1));
            GLES20.glUniform1f(top, 0.1f * (idx + 1));
            GLES20.glUniform1f(right, 0.2f * (idx + 1));
            GLES20.glUniform1f(bottom, 0.2f * (idx + 1));

            idx++;
        }
    }
}
