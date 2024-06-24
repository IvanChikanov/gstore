import Tags from "../enums/Tags";
import { Sizer } from "./Sizer";
import { Ui } from "./Ui";

export class GLScreen extends Ui{
    private ctx: WebGL2RenderingContext;
    private vShader: WebGLShader | undefined;
    private fShader: WebGLShader | undefined;
    private readonly v_shader = `#version 300 es
        void main(void) {
          gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
        }`;
    private readonly f_shader = `#version 300 es
        in vec2 position;
        void main(void) {
          gl_Position = vec4(aVertexPosition, 1.0, 1.0);
        }`;
    constructor(w: number, h: number)
    {
        super(Tags.CNV);
        this.setSize(w, h);
        let c = this.html as HTMLCanvasElement;
        this.ctx = c.getContext("webgl2") as WebGL2RenderingContext;
        this.createProgram();
    }
    private setSize(w: number, h: number)
    {
        this.html.setAttribute("width", `${w}`);
        this.html.setAttribute("height", `${h}`);
    }
    private loadShader(type: number, id: string)
    {
        let code = document.getElementById(id)?.innerHTML;
        let shader =  this.ctx.createShader(type) as WebGLShader;
        this.ctx.shaderSource(shader, code as string);
        this.ctx.compileShader(shader);
        if(!this.ctx.getShaderParameter(shader, this.ctx.COMPILE_STATUS))
        {
            console.log(this.ctx.getShaderInfoLog.toString());
        }
        return shader;
    }
    private createProgram()
    {
        this.vShader = this.loadShader(this.ctx.VERTEX_SHADER, this.v_shader);
        this.fShader = this.loadShader(this.ctx.FRAGMENT_SHADER, this.f_shader);
        let program = this.ctx.createProgram() as WebGL2RenderingContext;
        this.ctx.attachShader(program , this.vShader);
        this.ctx.attachShader(program, this.fShader);
        this.ctx.linkProgram(program);
        if(this.ctx.getProgramParameter(program, this.ctx.LINK_STATUS))
        {
            console.log(this.ctx.getProgramInfoLog);
        }
        let attr = this.ctx.getAttribLocation(program, "position");
        let posBuffer = this.ctx.createBuffer();
        this.ctx.bindBuffer(this.ctx.ARRAY_BUFFER, posBuffer);
        let posDots = [
            -0.5, 0.5, 
            0.5, 0.5,
            0.5, -0,5,
            -0.5, -0.5
        ];
        this.ctx.bufferData(this.ctx.ARRAY_BUFFER, new Float32Array(posDots), this.ctx.STATIC_DRAW)
        let vao = this.ctx.createVertexArray();
        this.ctx.bindVertexArray(vao);
        this.ctx.enableVertexAttribArray(attr);
        this.ctx.vertexAttribPointer(attr, 2, this.ctx.FLOAT, false, 0, 0);
        this.ctx.viewport(0, 0, Sizer.width, Sizer.height);
        this.ctx.clearColor(0,0,0,1);
        this.ctx.useProgram(program);
        this.ctx.drawArrays(this.ctx.TRIANGLES, 0, 4);
    }

}