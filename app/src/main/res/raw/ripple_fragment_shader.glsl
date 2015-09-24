precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;


uniform vec2 u_Resolution;
uniform float u_GlobalTime;

uniform vec2 u_TouchCenter;
uniform float u_TouchStartTime;


varying vec2 v_TexCoordinate;

void main()
{


//    float distance = distance(v_TexCoordinate.st, u_TouchCenter);
//    if (distance <= u_TouchStartTime + 0.025 && distance >= u_TouchStartTime - 0.025) {
//
//        float ecart = (distance - u_TouchStartTime); // value between -0.02 & 0.02
//
//        float powEcart = 1.0-pow(abs(ecart*40.0),0.4); // value between -1 & 1 (because 0.02 * 50 = 1)
//
//        float ecartTime = ecart  * powEcart; // value between -0.02 & 0.02
//
//        vec2 diff = normalize(v_TexCoordinate.st - u_TouchCenter); // get the direction
//
//        vec2 newTexCoord = v_TexCoordinate.st + (diff * ecartTime);
//
//        gl_FragColor = texture2D(u_Texture, newTexCoord.st);// * gl_Color;
//    } else {
//        gl_FragColor = texture2D(u_Texture, v_TexCoordinate);
//    }





    vec3 shockParams = vec3(5.0, 0.5, 0.2);

//    vec2 uv = v_TexCoordinate.xy;
    vec2 uv = gl_FragCoord.xy / u_Resolution.xy;
    uv.y = 1.0 - uv.y;
    vec2 texCoord = uv;

    float distance = distance(uv, u_TouchCenter);
    if ( (distance <= (u_TouchStartTime + shockParams.z)) &&  (distance >= (u_TouchStartTime - shockParams.z)) )
    {
        float diff = (distance - u_TouchStartTime);
        float powDiff = 1.0 - pow(abs(diff*shockParams.x), shockParams.y);

        float diffTime = diff  * powDiff;
        vec2 diffUV = normalize(uv - u_TouchCenter);

        texCoord = uv + (diffUV * diffTime);

//        gl_FragColor = vec4(1.0, 0.0, 0.0, 0.0);
        gl_FragColor = texture2D(u_Texture, texCoord);

    } else {
        gl_FragColor = texture2D(u_Texture, texCoord);
    }



//    uv.y = 1.0 - uv.y;
//    uv.x = 1.0 - uv.x;
//    gl_FragColor = texture2D(u_Texture, uv);
//    gl_FragColor = texture2D(u_Texture, gl_FragCoord.xy);
}
