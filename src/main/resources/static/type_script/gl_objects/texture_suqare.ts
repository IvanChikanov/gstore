import { ProgramType } from "../enums/pType";
import { AbstractObject } from "./abstractObject";

export class TextureSquare extends AbstractObject{

    pType: ProgramType = ProgramType.TEXTURE_SQUARE;

    private program: WebGL2RenderingContext| undefined;

    public setProgram(program: WebGL2RenderingContext): void {
        
        this.program = program;
    }

    public update(): void {
        throw new Error("Method not implemented.");
    }

    public draw(): void {
        throw new Error("Method not implemented.");
    }
    

}