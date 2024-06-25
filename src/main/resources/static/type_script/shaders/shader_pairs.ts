export const simpleShaders = {
    vertex: `#version 300 es
    
    in vec2 position;
    out vec4 colorValue;

    void main() {
        gl_Position = vec4(position, 1.0, 1.0);
        colorValue = vec4(1.0, 1.0, 1.0, 1.0);
    }`,
    fragment:`#version 300 es

    precision highp float;
    out vec4 color;
    in vec4 colorValue;

    void main() {
      color = colorValue;
    }`
};