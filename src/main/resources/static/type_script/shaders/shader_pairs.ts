export const simpleShaders = {
    vertex:`#version 300 es

    uniform mat4 m_projection;
    in vec2 position;
    out vec4 colorValue;

    void main() {
        gl_Position = m_projection * vec4(position, 1.0, 1.0);
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