import Grid from "./types/Grid"

class Application{
    private eventListener: any;
    private mainGrid: Grid;
    constructor()
    {
        this.mainGrid = new Grid();
    }
}
var app = new Application();