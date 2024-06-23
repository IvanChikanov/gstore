import Tags from "../enums/Tags";
import { Ui } from "./Ui";

export class GLScreen extends Ui{
    private ctx: WebGL2RenderingContext;
    constructor(w: number, h: number)
    {
        super(Tags.CNV);
        this.setSize(w, h);
        let c = this.html as HTMLCanvasElement;
        this.ctx = c.getContext("webgl2") as WebGL2RenderingContext;
    }
    private setSize(w: number, h: number)
    {
        this.html.setAttribute("width", `${w}`);
        this.html.setAttribute("height", `${h}`);
    }


}