import { ProgramType } from "enums/porgramTypes";

export abstract class AbstractObject{

    abstract readonly pType: string;

    private static allObjects: Array<AbstractObject>;

    constructor()
    {
        AbstractObject.allObjects.push(this);
    }

    public abstract update(): void;

    public abstract draw(): void;
}