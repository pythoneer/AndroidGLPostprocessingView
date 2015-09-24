precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;

varying vec2 v_TexCoordinate;

void main()                    		
{

    vec2 uv = v_TexCoordinate.xy;

    uv.y = 1.0 - uv.y;
    uv.x = 1.0 - uv.x;
    gl_FragColor = texture2D(u_Texture, uv);
//    gl_FragColor = texture2D(u_Texture, gl_FragCoord.xy);
}

