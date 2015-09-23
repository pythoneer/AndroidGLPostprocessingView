precision mediump float;

#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;
uniform float offset;

varying vec2 v_TexCoordinate;

void main()                    		
{                              
    vec2 texcoord = v_TexCoordinate;
    texcoord.x += sin(texcoord.y * 4.0 * 2.0 * 3.14159 + offset) / 100.0;
    gl_FragColor = texture2D(u_Texture, texcoord);
}

