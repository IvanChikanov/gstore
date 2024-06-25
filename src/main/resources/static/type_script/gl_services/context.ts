export class Ctx{
    private static ctx: WebGL2RenderingContext;
    public static set(canvas: HTMLCanvasElement)
    {
        this.ctx = canvas.getContext("webgl2") as WebGL2RenderingContext;
    }

    public static get(){
        return this.ctx;
    }
}