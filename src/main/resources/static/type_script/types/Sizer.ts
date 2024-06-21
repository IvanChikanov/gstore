export class Sizer{
    public static height: number;
    public static width: number;

    private static rect: DOMRect
    public static init(): void{
        this.rect = document.documentElement.getBoundingClientRect();
        console.log(this.rect);
        this.height = this.rect.height;
        this.width = this.rect.width;
        window.addEventListener("resize", this.init);
    }

    public static calcHeightPercernt(percent: number){
        return this.height / percent;
    }
    public static calcWidthPercernt(percent: number){
        return this.width / percent;
    }
}