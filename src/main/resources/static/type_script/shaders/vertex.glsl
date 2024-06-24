uniform float layer;
attribute vec2 position;
void main(){
    gl_Posiotion = vec4(position, layer, 1.0);
}