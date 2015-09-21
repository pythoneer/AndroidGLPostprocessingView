package lordnaikon.de.glpostprocessingview;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLView;

    private GLSurfaceView glSurfaceView;
    private GlRenderable glLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        GlViewRenderer renderer = new GlViewRenderer();
        GlViewRenderer renderer = new MyGLRenderer(this);

        glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(renderer);

        glLinearLayout = (GlRenderable) findViewById(R.id.gl_linear_layout);
        glLinearLayout.setViewToRenderer(renderer);
//        glLinearLayout.setViewToRenderer(null);


        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
//        mGLView = new MyGLSurfaceView(this);
//        setContentView(mGLView);
    }
}
