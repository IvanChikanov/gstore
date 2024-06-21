import { Ui } from "./Ui";
import Tags from "../enums/Tags";

class Grid extends Ui{
    constructor(row: string = "", col: string = "")
    {
        super(Tags.DIV);
    }
}
export default Grid;