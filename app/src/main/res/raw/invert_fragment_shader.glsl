precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;

varying vec2 v_TexCoordinate;

void main()
{
    vec4 color = texture2D(u_Texture, v_TexCoordinate);
    gl_FragColor = vec4(vec3(1.0, 1.0, 1.0) - color.rgb, color.a);
}