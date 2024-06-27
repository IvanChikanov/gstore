import { Pos2D } from "../types/pos2d";
import { ProgramType } from "../enums/pType";
import { AbstractObject } from "./abstractObject";

export class TextureSquare extends AbstractObject{

    pType: ProgramType = ProgramType.TEXTURE_SQUARE;

    private program: WebGL2RenderingContext| undefined;

    protected size: Pos2D = Pos2D.create(1, 1);

    protected position: Pos2D = Pos2D.create(0, 0);

    public setProgram(program: WebGL2RenderingContext): void {

        this.program = program;
    }

    public update(): void {
        throw new Error("Method not implemented.");
    }

    public draw(): void {
        throw new Error("Method not implemented.");
    }

    private getVertexPosition()
    {
        let halfHeight = this.size.y / 2;
        let halfWidth = this.size.x / 2;
        return [
            this.position.x - halfWidth, this.position.y + halfHeight,
            this.position.x - halfWidth, this.position.y - halfHeight,
            this.position.x + halfWidth, this.position.y - halfHeight,
            this.position.x + halfWidth, this.position.y + halfHeight
        ];
    }
    

}