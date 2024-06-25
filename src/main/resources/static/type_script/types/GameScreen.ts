import Tags from "../enums/Tags";
import { Ctx } from "../gl_services/context";
import { Sizer } from "./Sizer";
import { Ui } from "./Ui";

export class GLScreen extends Ui{
    private programs: Map<string, WebGLProgram>;
    private readonly v_shader = `#version 300 es
        in vec2 position;
        out vec4 colorValue;
        void main() {
        gl_Position = vec4(position, 1.0, 1.0);
        colorValue = vec4(1.0, 1.0, 1.0, 1.0);
        }`;
    private readonly f_shader = `#version 300 es
        precision highp float;
        out vec4 color;
        in vec4 colorValue;
        void main() {
          color = colorValue;
        }`;
    constructor(w: number, h: number)
    {
        super(Tags.CNV);
        this.setSize(w, h);
        Ctx.set(this.html as HTMLCanvasElement);
        this.programs = new Map();
        this.createProgram();
    }
    private newProgram(type: string, )
    private setSize(w: number, h: number)
    {
        this.html.setAttribute("width", `${w}`);
        this.html.setAttribute("height", `${h}`);
    }
    private loadShader(type: number, code: string)
    {

        let shader =  this.ctx.createShader(type) as WebGLShader;
        this.ctx.shaderSource(shader, code as string);
        this.ctx.compileShader(shader);
        if(!this.ctx.getShaderParameter(shader, this.ctx.COMPILE_STATUS))
        {
            console.log(this.ctx.getShaderInfoLog(shader));
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
            console.log(this.ctx.getProgramInfoLog(program));
        }
        let attr = this.ctx.getAttribLocation(program, "position");
        let posBuffer = this.ctx.createBuffer();
        this.ctx.bindBuffer(this.ctx.ARRAY_BUFFER, posBuffer);
        let posDots = [
            -0.5, 0.5, 
            -0.5, -0.5,
            0.5, -0.5,
            -0.5, 0.5,
            0.5, 0.5
        ];
        this.ctx.bufferData(this.ctx.ARRAY_BUFFER, new Float32Array(posDots), this.ctx.STATIC_DRAW);/*
        let texture = this.ctx.createTexture();
        this.ctx.bindTexture(this.ctx.TEXTURE_2D, texture);
        let img = new Image();
        img.onload = ()=>{
            this.ctx.bindTexture(this.ctx.TEXTURE_2D, texture);
            this.ctx.pixelStorei(this.ctx.UNPACK_FLIP_Y_WEBGL, true);
            this.ctx.texImage2D(this.ctx.TEXTURE_2D, 0, this.ctx.RGBA, this.ctx.RGBA, this.ctx.UNSIGNED_BYTE, img);
            this.ctx.texParameteri(this.ctx.TEXTURE_2D, this.ctx.TEXTURE_MAG_FILTER, this.ctx.NEAREST);
            this.ctx.texParameteri(this.ctx.TEXTURE_2D, this.ctx.TEXTURE_MIN_FILTER, this.ctx.NEAREST);
            this.ctx.activeTexture(this.ctx.TEXTURE0 + 0);
            this.ctx.texImage2D(this.ctx.TEXTURE_2D, 0, this.ctx.RGBA, this.ctx.RGBA, this.ctx.UNSIGNED_BYTE, img);
        }
        img.src = "./mig-3.png";

        let sampler = this.ctx.getUniformLocation(program, "tex");
        this.ctx.uniform1i(sampler, 0);*/
        let vao = this.ctx.createVertexArray();
        this.ctx.bindVertexArray(vao);
        this.ctx.enableVertexAttribArray(attr);
        this.ctx.vertexAttribPointer(attr, 2, this.ctx.FLOAT, false, 0, 0);
        this.ctx.viewport(0, 0, Sizer.width, Sizer.height);
        this.ctx.clearColor(0, 0, 0, 1);
        this.ctx.clear(this.ctx.COLOR_BUFFER_BIT);
        this.ctx.useProgram(program);
        this.ctx.drawArrays(this.ctx.TRIANGLE_STRIP, 0, 5);
    }

}