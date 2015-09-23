package de.pythoneer.postprocessing;

import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.self.viewtoglrendering.R;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private GLSurfaceView mGLSurfaceView;
    private GLRenderable mGLLinearLayout;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        List<EffectItem> effects = Arrays.asList((EffectItem)new WobbleEffect());

        final BaseGLRenderer baseGlRenderer = new FooRenderer(this, width, height, effects);

        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
        mGLLinearLayout = (GLRenderable) findViewById(R.id.gl_layout);
        mWebView = (WebView) findViewById(R.id.web_view);

        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(baseGlRenderer);

        mGLLinearLayout.setViewToGLRenderer(baseGlRenderer);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl("http://www.golem.de");


        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ((FooRenderer) baseGlRenderer).otherProgram = !((FooRenderer) baseGlRenderer).otherProgram;
//                h.postDelayed(this, 5000);

                ((FooRenderer) baseGlRenderer).currentEffect = 0;
            }
        }, 5000);

    }


}
