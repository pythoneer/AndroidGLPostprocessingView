package de.pythoneer.postprocessing;

import com.self.viewtoglrendering.R;

/**
 * Created by dustin on 23.09.15.
 */
public class InvertEffect extends EffectItem {
    public InvertEffect() {
        super(R.raw.vertex_shader, R.raw.invert_fragment_shader);
    }

    @Override
    public void draw(int programHandle) {

    }
}
