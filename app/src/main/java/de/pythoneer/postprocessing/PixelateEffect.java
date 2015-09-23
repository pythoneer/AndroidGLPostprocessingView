package de.pythoneer.postprocessing;

import com.self.viewtoglrendering.R;

/**
 * Created by dustin on 23.09.15.
 */
public class PixelateEffect extends EffectItem {
    public PixelateEffect() {
        super(R.raw.vertex_shader, R.raw.pixel_fragment_shader);
    }

    @Override
    public void draw(int programHandle) {

    }
}
