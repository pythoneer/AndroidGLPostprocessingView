package lordnaikon.de.glpostprocessingview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by naikon on 20.09.15.
 */
public class GlLinearLayout extends LinearLayout implements GlRenderable {

    private GlViewRenderer glViewRenderer;

    // default constructors

    public GlLinearLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public GlLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public GlLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);
    }

    // drawing magic
    @Override
    public void draw(Canvas canvas) {
//        super.draw(canvas);
        System.out.println("LL draw");
        Canvas glAttachedCanvas = glViewRenderer.onDrawViewBegin();
        if(glAttachedCanvas != null) {
            //prescale canvas to make sure content fits
            float xScale = glAttachedCanvas.getWidth() / (float)canvas.getWidth();
            glAttachedCanvas.scale(xScale, xScale);
            //draw the view to provided canvas
            super.draw(glAttachedCanvas);
        }
        // notify the canvas is updated
        glViewRenderer.onDrawViewEnd();
    }

    @Override
    public void setViewToRenderer(GlViewRenderer viewToRenderer) {
        glViewRenderer = viewToRenderer;
    }
}
