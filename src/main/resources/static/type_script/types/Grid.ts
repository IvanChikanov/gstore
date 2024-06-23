import { Ui } from "./Ui";
import Tags from "../enums/Tags";
import { Style } from "../enums/styles";

class Grid extends Ui{
    constructor(row: string = "", col: string = "")
    {
        super(Tags.DIV);
        this.addClasses(["grid"]);
    }
    public setGrid(rowString: string, colString: string)
    {
        this.addStyle([
            [Style.GRID_ROWS, rowString],
            [Style.GRID_COLS, colString]
        ]);
    }
}
export default Grid;