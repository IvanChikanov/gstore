import { Pos2D } from "../types/pos2d";
import { ProgramType } from "../enums/pType";



export abstract class AbstractObject{

    abstract readonly pType: ProgramType;

    private static allObjects: Array<AbstractObject> = [];

    protected abstract size: Pos2D;

    protected abstract position: Pos2D;

    constructor()
    {
        AbstractObject.allObjects.push(this);
    }

    public static get AllObjects(){
        return this.allObjects;
    }

    public abstract setProgram(program: WebGL2RenderingContext): void;

    public abstract update(): void;

    public abstract draw(): void;
}