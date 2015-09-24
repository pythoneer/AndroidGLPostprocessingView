precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;

varying vec2 v_TexCoordinate;

void main()
{

    vec2 uv = v_TexCoordinate.xy;
//    uv.y = 1.0 - uv.y;
//    uv.x = 1.0 - uv.x;


    vec4 color = texture2D(u_Texture, uv);

    vec4 tmpColor = vec4(0.0, 0.0, 0.0, 0.0);

    if(uv.x > 0.1 && uv.x < 0.4 && uv.y > 0.2 && uv.y < 0.4)  {
        vec4 color2 = vec4(1.0, 0.0, 0.0, 1.0);
        tmpColor += color2;
//        color += vec4(1.0, 0.0, 0.0, 1.0);
    }

    if(uv.x > 0.2 && uv.x < 0.5 && uv.y > 0.3 && uv.y < 0.5)  {
        vec4 color2 = vec4(0.0, 1.0, 0.0, 1.0);
        tmpColor += color2;

//        color += vec4(0.0, 1.0, 0.0, 1.0);
    }

    if ((uv.x > 0.1 && uv.x < 0.4 && uv.y > 0.2 && uv.y < 0.4) || (uv.x > 0.2 && uv.x < 0.5 && uv.y > 0.3 && uv.y < 0.5)) {
            color *= tmpColor;

    }

    if(uv.x > 0.4 && uv.x < 0.7 && uv.y > 0.5 && uv.y < 0.7)  {
        vec4 color2 = vec4(1.0, 0.0, 0.0, 1.0);
        color = mix(color, color2, 0.5);
//        color *= vec4(1.0, 0.0, 0.0, 1.0);
    }

    if(uv.x > 0.5 && uv.x < 0.8 && uv.y > 0.6 && uv.y < 0.8)  {
        vec4 color2 = vec4(0.0, 1.0, 0.0, 1.0);
        color = mix(color, color2, 0.5);
//        color *= vec4(0.0, 1.0, 0.0, 1.0);
    }


    gl_FragColor = color;
//    gl_FragColor = texture2D(u_Texture, gl_FragCoord.xy);
}