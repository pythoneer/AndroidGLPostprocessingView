precision mediump float;

// important to include in order to use rendered Android View to gl texture
#extension GL_OES_EGL_image_external : require
uniform samplerExternalOES u_Texture;

uniform vec2 u_Resolution;
uniform float u_GlobalTime;

varying vec2 v_TexCoordinate;


//float noise(vec2 p)
//{
//	float sample = texture2D(iChannel1,vec2(1.,2.*cos(iGlobalTime))*iGlobalTime*8. + p*1.).x;
//	sample *= sample;
//	return sample;
//}

float noise( vec2 n ) {
  return fract(sin(dot(n.xy, vec2(12.9898, 78.233)))* 43758.5453);
}

float onOff(float a, float b, float c)
{
	return step(c, sin(u_GlobalTime + a*cos(u_GlobalTime*b)));
}

float ramp(float y, float start, float end)
{
	float inside = step(start,y) - step(end,y);
	float fact = (y-start)/(end-start)*inside;
	return (1.-fact) * inside;

}

float stripes(vec2 uv)
{

	float noi = noise(uv*vec2(0.5,1.) + vec2(1.,3.));
	return ramp(mod(uv.y*4. + u_GlobalTime/2.+sin(u_GlobalTime + sin(u_GlobalTime*0.63)),1.),0.5,0.6)*noi;
}

vec3 getVideo(vec2 uv)
{
	vec2 look = uv;
	float window = 1./(1.+20.*(look.y-mod(u_GlobalTime/4.,1.))*(look.y-mod(u_GlobalTime/4.,1.)));
	look.x = look.x + sin(look.y*10. + u_GlobalTime)/50.*onOff(4.,4.,.3)*(1.+cos(u_GlobalTime*80.))*window;
	float vShift = 0.4*onOff(2.,3.,.9)*(sin(u_GlobalTime)*sin(u_GlobalTime*20.) +
										 (0.5 + 0.1*sin(u_GlobalTime*200.)*cos(u_GlobalTime)));
	look.y = mod(look.y + vShift, 1.);
	vec3 video = vec3(texture2D(u_Texture,look));
	return video;
}

vec2 screenDistort(vec2 uv)
{
	uv -= vec2(.5,.5);
	uv = uv*1.2*(1./1.2+2.*uv.x*uv.x*uv.y*uv.y);
	uv += vec2(.5,.5);
	return uv;
}

//void mainImage( out vec4 fragColor, in vec2 fragCoord )
//{
//	vec2 uv = fragCoord.xy / iResolution.xy;
//	uv = screenDistort(uv);
//	vec3 video = getVideo(uv);
//	float vigAmt = 3.+.3*sin(iGlobalTime + 5.*cos(iGlobalTime*5.));
//	float vignette = (1.-vigAmt*(uv.y-.5)*(uv.y-.5))*(1.-vigAmt*(uv.x-.5)*(uv.x-.5));
//
//	video += stripes(uv);
//	video += noise(uv*2.)/2.;
//	video *= vignette;
//	video *= (12.+mod(uv.y*30.+iGlobalTime,1.))/13.;
//
//	fragColor = vec4(video,1.0);
//}



void main()
{
    vec2 uv = gl_FragCoord.xy / u_Resolution.xy;
	uv.y = 1.0 - uv.y;
//	vec2 uv = fragCoord.xy / iResolution.xy;
	uv = screenDistort(uv);
	vec3 video = getVideo(uv);
	float vigAmt = 3.+.3*sin(u_GlobalTime + 5.*cos(u_GlobalTime*5.));
	float vignette = (1.-vigAmt*(uv.y-.5)*(uv.y-.5))*(1.-vigAmt*(uv.x-.5)*(uv.x-.5));

	video += stripes(uv);
	video += noise(uv*2.)/2.;
	video *= vignette;
	video *= (12.+mod(uv.y*30.+u_GlobalTime,1.))/13.;

//	fragColor = vec4(video,1.0);

    gl_FragColor = vec4(video,1.0);;


//    vec2 uv = v_TexCoordinate.xy;
//
//    uv.y = 1.0 - uv.y;
//    uv.x = 1.0 - uv.x;
//    gl_FragColor = texture2D(u_Texture, uv);
////    gl_FragColor = texture2D(u_Texture, gl_FragCoord.xy);
}