import { ProgramType } from "../enums/pType";
import { simpleShaders } from "../shaders/shader_pairs";

export class ProgramKeeper{

    private createdPrograms: Map<ProgramType, WebGL2RenderingContext> = new Map();
    private context: WebGL2RenderingContext;

    constructor(context: WebGL2RenderingContext){
        this.context = context;
    }

    public getProgram(type: ProgramType)
    {
        return this.createdPrograms.has(type) ? this.createdPrograms.get(type) as WebGL2RenderingContext: this.createProgram(type);
    }

    private createProgram(type: ProgramType){
        let vertex = this.createShader(this.context.VERTEX_SHADER, simpleShaders.vertex);
        let fragment = this.createShader(this.context.FRAGMENT_SHADER, simpleShaders.fragment);
        return this.initProgram(type, vertex, fragment);
    }

    private createShader(type: number, code: string)
    {
        let shader = this.context.createShader(type) as WebGLShader;
        this.context.shaderSource(shader, code);
        this.context.compileShader(shader);

        if(this.context.getShaderParameter(shader, this.context.COMPILE_STATUS)){

            return shader;
        }

        console.warn(this.context.getShaderInfoLog(shader));
        this.context.deleteShader(shader);
        throw new Error(`Shader \n ${code} \n is broken!`);
    }

    private initProgram(name: ProgramType, vs: WebGLShader, fs: WebGLShader)
    {
        let prgrm = this.context.createProgram() as WebGL2RenderingContext;
        this.createdPrograms.set(name, prgrm);
        this.context.attachShader(prgrm, vs);
        this.context.attachShader(prgrm, fs);
        this.context.linkProgram(prgrm);
        if(this.context.getProgramParameter(prgrm, this.context.LINK_STATUS))
        {
            return prgrm;
        }

        console.warn(this.context.getProgramInfoLog(prgrm));
        throw new Error(this.context.getProgramInfoLog(prgrm) as string);
    }
}