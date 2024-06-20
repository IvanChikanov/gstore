import Grid from "./types/Grid"

class Application{
    private eventListener: any;
    private mainGrid: Grid;
    constructor()
    {
        this.mainGrid = new Grid();
        document.body.appendChild(this.mainGrid.getHtml());
    }
}
var app = new Application();