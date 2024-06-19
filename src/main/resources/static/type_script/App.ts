import MainGrid from "./types/MainGrid"

class Application{
    private eventListener: any;
    private mainGrid: MainGrid;
    constructor()
    {
        this.mainGrid = new MainGrid();
    }
}

var app = new Application();