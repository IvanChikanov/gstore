import { ProgramType } from "../enums/pType";
import { mat4 } from "../node_modules/gl-matrix-ts/dist/index";
import { simpleShaders } from "../shaders/shader_pairs";
import { Sizer } from "../types/Sizer";
import { GLObjectFactory } from "./objectFactory";
import { ProgramKeeper } from "./programKeeper";

export class Ctx{

    private programs: Map<string, WebGL2RenderingContext> = new Map();
    private gl: WebGL2RenderingContext;
    private h: number;
    private w: number;
    private projMatrixLocation: WebGLUniformLocation | null;

    private programKeeper: ProgramKeeper;
    private objectFactory: GLObjectFactory;

    constructor(canvas: HTMLCanvasElement){
        this.gl = canvas.getContext("webgl2") as WebGL2RenderingContext
        this.h = canvas.height;
        this.w = canvas.width;
        this.gl.viewport(0, 0, this.w, this.h);
        this.programKeeper = new ProgramKeeper(this.gl);
        this.objectFactory = new GLObjectFactory();

        let object = this.objectFactory.create(ProgramType.TEXTURE_SQUARE);
        object.setProgram(this.programKeeper.getProgram(ProgramType.TEXTURE_SQUARE));
        Object.keys(ProgramType).forEach( k => console.log(k));

        // create shaders & program
        let vertex = this.initShader(this.gl.VERTEX_SHADER, simpleShaders.vertex)  as WebGLShader;
        let fragment = this.initShader(this.gl.FRAGMENT_SHADER, simpleShaders.fragment) as WebGLShader;
        this.initProgram("simple", vertex, fragment);

        // get matrix pos
        this.projMatrixLocation = this.gl.getUniformLocation(this.programs.get("simple") as WebGL2RenderingContext, "m_projection");

        // create and binds buffers
        let vertexBuffer = this.gl.createBuffer();
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, vertexBuffer);
        let attrLoc = this.gl.getAttribLocation(this.programs.get("simple") as WebGL2RenderingContext, "position");
        this.gl.enableVertexAttribArray(attrLoc);
        this.gl.vertexAttribPointer(attrLoc, 2, this.gl.FLOAT, false, 0, 0);


        let indexBuffer = this.gl.createBuffer();
        this.gl.bindBuffer(this.gl.ELEMENT_ARRAY_BUFFER, indexBuffer);

        //set data on buffers
        let posDots = [
            -0.5, 0.5, 
            -0.5, -0.5,
            0.5, -0.5,
            0.5, 0.5
        ];
        this.gl.bufferData(this.gl.ARRAY_BUFFER, new Float32Array(posDots), this.gl.STATIC_DRAW);

        let indxs = [0, 1, 2, 3, 0];
        this.gl.bufferData(this.gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indxs), this.gl.STATIC_DRAW);

        //clear screen
        this.gl.clearColor(0, 0, 0, 1);
        this.gl.clear(this.gl.COLOR_BUFFER_BIT);


        // draw
        this.gl.useProgram(this.programs.get("simple") as WebGL2RenderingContext);
        this.gl.uniformMatrix4fv(this.projMatrixLocation, false, this.setOrthoMatrix(10));
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

    private setOrthoMatrix(unitsOnMinAxis: number)
    {
        let min = Math.min(Sizer.width, Sizer.height);
        let max = Math.max(Sizer.width, Sizer.height);
        let unitPixels = max / (min / unitsOnMinAxis);
        let projection = mat4.create();
        mat4.ortho(projection,
             min == Sizer.width ? -(unitsOnMinAxis / 2) : -(unitPixels / 2), 
             min == Sizer.width ? unitsOnMinAxis  / 2: unitPixels / 2, 
             min == Sizer.height ? -(unitsOnMinAxis / 2) : -(unitPixels / 2),
             min == Sizer.height ? unitsOnMinAxis / 2 : unitPixels / 2, 
             -10, 10);
        return projection;
    }

}