precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;
uniform float offset;

varying vec2 v_TexCoordinate;

void main()                    		
{
    gl_FragColor = texture2D(u_Texture, v_TexCoordinate);
}

