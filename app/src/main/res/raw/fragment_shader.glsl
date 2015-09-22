// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require

//make sure to use samplerExternalOES instead of sampler2D
uniform samplerExternalOES u_Texture;    // The input texture.

uniform float offset;

precision mediump float;       	// Set the default precision to medium. We don't need as high of a

varying vec2 v_TexCoordinate;   // Interpolated texture coordinate per fragment.

void main()                    		
{                              

//    gl_FragColor = texture2D(u_Texture, v_TexCoordinate);

    vec2 texcoord = v_TexCoordinate;
    texcoord.x += sin(texcoord.y * 4.0 * 2.0 * 3.14159 + offset) / 100.0;
    gl_FragColor = texture2D(u_Texture, texcoord);

}

