import { ProgramType } from "../enums/pType";
import { TextureSquare } from "../gl_objects/texture_suqare";

export class GLObjectFactory{

    public create(type: ProgramType){

        switch(type){

            case ProgramType.TEXTURE_SQUARE:
                return new TextureSquare();

            default:
                throw Error("Object type is not defined!");
        }
    }
}