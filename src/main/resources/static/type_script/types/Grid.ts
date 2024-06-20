import Ui from "../abstract/Ui";
import Tags from "../enums/Tags";

class Grid extends Ui{
    constructor(row: string = "", col: string = "")
    {
        super(Tags.DIV);
        this.addClasses(["grid", "fullWidth", "fullHeight"]);
    }
}
export default Grid;