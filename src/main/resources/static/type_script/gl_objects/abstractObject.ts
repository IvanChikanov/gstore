import { ProgramType } from "../enums/pType";



export abstract class AbstractObject{

    abstract readonly pType: ProgramType;

    private static allObjects: Array<AbstractObject> = [];

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