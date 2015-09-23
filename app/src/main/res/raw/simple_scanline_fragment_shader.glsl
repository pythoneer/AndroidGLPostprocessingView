precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;

varying vec2 v_TexCoordinate;

uniform float u_ResolutionY;
uniform float u_Scale;

void main()
{
    if (mod(floor(v_TexCoordinate.y * u_ResolutionY / u_Scale), 2.0) == 0.0)
    {

//        gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);
        vec4 color = texture2D(u_Texture, v_TexCoordinate);
        gl_FragColor = vec4(color.x - 0.5, color.y - 0.5, color.z - 0.5, 0.0);
    }
    else
    {
        gl_FragColor = texture2D(u_Texture, v_TexCoordinate);
    }
}