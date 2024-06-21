export class Sizer{
    public static height: number;
    public static width: number;

    private static rect: DOMRect
    public static init(): void{
        let html = document.documentElement;
        this.height = html.clientHeight;
        this.width = html.clientWidth;
        window.addEventListener("resize", this.init);
    }

    public static calcHeightPercernt(percent: number){
        return this.height / percent;
    }
    public static calcWidthPercernt(percent: number){
        return this.width / percent;
    }
}