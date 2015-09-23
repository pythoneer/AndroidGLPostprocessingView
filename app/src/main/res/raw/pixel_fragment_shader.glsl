

precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;

varying vec2 v_TexCoordinate;

void main()
{
    int si = int(v_TexCoordinate.s * 150.0);
    int sj = int(v_TexCoordinate.t * 150.0);
    gl_FragColor = texture2D(u_Texture, vec2(float(si) / 150.0, float(sj) / 150.0)) ;
}