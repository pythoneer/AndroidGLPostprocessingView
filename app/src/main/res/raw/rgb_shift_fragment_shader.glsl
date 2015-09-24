precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;

uniform vec2 u_Resolution;
uniform float u_GlobalTime;

varying vec2 v_TexCoordinate;



#define AMPLITUDE 0.1
#define SPEED 0.015

vec4 rgbShift( in vec2 p , in vec4 shift) {
    shift *= 2.0 * shift.w - 1.0;
    vec2 rs = vec2(shift.x,-shift.y);
    vec2 gs = vec2(shift.y,-shift.z);
    vec2 bs = vec2(shift.z,-shift.x);

    float r = texture2D(u_Texture, p+rs).x;
    float g = texture2D(u_Texture, p+gs).y;
    float b = texture2D(u_Texture, p+bs).z;

    return vec4(r,g,b,1.0);
}

//vec4 noise( in vec2 p ) {
//    return texture2D(iChannel1, p, 0.0);
//}

vec4 noiseX( in vec2 co){

    float v1 = fract(sin(dot(co.xy ,vec2(12.9898,75.233))) * 41758.5453);
    float v2 = fract(sin(dot(co.xy ,vec2(13.9898,76.233))) * 42758.5453);
    float v3 = fract(sin(dot(co.xy ,vec2(14.9898,77.233))) * 44758.5453);
    float v4 = fract(sin(dot(co.xy ,vec2(15.9898,78.233))) * 46758.5453);

//    float v1 = 1.0;
//    float v2 = 0.2;
//    float v3 = 0.3;
//    float v4 = 0.5;

    vec4 ret = vec4(v1, v2, v3, v4);

    return ret;
}

vec4 vec4pow( in vec4 v, in float p ) {
    // Don't touch alpha (w), we use it to choose the direction of the shift
    // and we don't want it to go in one direction more often than the other
    return vec4(pow(v.x,p),pow(v.y,p),pow(v.z,p),v.w);
}


void main()
{

	vec2 p = gl_FragCoord.xy / u_Resolution.xy;
	p.y = 1.0 - p.y;
    vec4 c = vec4(0.0,0.0,0.0,1.0);

    // Elevating shift values to some high power (between 8 and 16 looks good)
    // helps make the stuttering look more sudden
    vec4 shift = vec4pow(noiseX(vec2(SPEED*u_GlobalTime,2.0*SPEED*u_GlobalTime/25.0 )),8.0)
        		*vec4(AMPLITUDE,AMPLITUDE,AMPLITUDE,1.0);;

    c += rgbShift(p, shift);

	gl_FragColor = c;

}