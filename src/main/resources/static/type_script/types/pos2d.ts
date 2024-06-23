export class Pos2D{
    public x: number;
    public y: number;
    constructor(x: number, y: number)
    {
        this.x = x;
        this.y = y;
    }
    public static create(x: number, y: number){
        return new Pos2D(x, y);
    }
}