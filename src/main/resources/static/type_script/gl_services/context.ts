import { mat4 } from "../node_modules/gl-matrix-ts/dist/index";
import { simpleShaders } from "../shaders/shader_pairs";
import { Sizer } from "../types/Sizer";

export class Ctx{

    private programs: Map<string, WebGL2RenderingContext> = new Map();
    private gl: WebGL2RenderingContext;
    private h: number;
    private w: number;

    constructor(canvas: HTMLCanvasElement){
        this.gl = canvas.getContext("webgl2") as WebGL2RenderingContext
        this.h = canvas.height;
        this.w = canvas.width;
        let vertex = this.initShader(this.gl.VERTEX_SHADER, simpleShaders.vertex)  as WebGLShader;
        let fragment = this.initShader(this.gl.FRAGMENT_SHADER, simpleShaders.fragment) as WebGLShader;
        this.initProgram("simple", vertex, fragment);

        let vertexBuffer = this.gl.createBuffer();
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, vertexBuffer);
        let posDots = [
            -0.5, 0.5, 
            -0.5, -0.5,
            0.5, -0.5,
            0.5, 0.5
        ];
        this.gl.bufferData(this.gl.ARRAY_BUFFER, new Float32Array(posDots), this.gl.STATIC_DRAW);
        let attrLoc = this.gl.getAttribLocation(this.programs.get("simple") as WebGL2RenderingContext, "position");
        this.gl.enableVertexAttribArray(attrLoc);
        this.gl.vertexAttribPointer(attrLoc, 2, this.gl.FLOAT, false, 0, 0);

        let indexBuffer = this.gl.createBuffer();
        this.gl.bindBuffer(this.gl.ELEMENT_ARRAY_BUFFER, indexBuffer);
        let indxs = [0, 1, 2, 3, 0];
        this.gl.bufferData(this.gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indxs), this.gl.STATIC_DRAW);


        this.gl.clearColor(0, 0, 0, 1);
        this.gl.clear(this.gl.COLOR_BUFFER_BIT);
        this.gl.viewport(0, 0, this.w, this.h);


        this.gl.useProgram(this.programs.get("simple") as WebGL2RenderingContext);

        let projMatrix = this.gl.getUniformLocation(this.programs.get("simple") as WebGL2RenderingContext, "m_projection");
        let projection = mat4.create();
        mat4.ortho(projection, -5, 5, -10, 10, -1, 1);
        console.log(projection);
        this.gl.uniformMatrix4fv(projMatrix, false, projection);

        this.gl.drawElements(this.gl.TRIANGLE_STRIP, 5, this.gl.UNSIGNED_SHORT, 0);

    }

    private initShader(type: number, code: string)
    {
        let shader = this.gl.createShader(type) as WebGLShader;
        this.gl.shaderSource(shader, code);
        this.gl.compileShader(shader);

        if(this.gl.getShaderParameter(shader, this.gl.COMPILE_STATUS)){
            return shader;
        }

        console.warn(this.gl.getShaderInfoLog(shader));
        this.gl.deleteShader(shader);

    }

    private initProgram(name: string, vs: WebGLShader, fs: WebGLShader)
    {
        let prgrm = this.gl.createProgram() as WebGL2RenderingContext;
        this.programs.set(name, prgrm);
        this.gl.attachShader(prgrm, vs);
        this.gl.attachShader(prgrm, fs);
        this.gl.linkProgram(prgrm);

        if(this.gl.getProgramParameter(prgrm, this.gl.LINK_STATUS))
        {
            return;
        }

        console.warn(this.gl.getProgramInfoLog(prgrm));
        throw new Error(this.gl.getProgramInfoLog(prgrm) as string);
    }

}