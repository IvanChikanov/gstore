import { gridCols, gridRows } from "./storage/styles";
import Grid from "./types/Grid"
import { Sizer } from "./types/Sizer";

class Application{
    private eventListener: any;
    private mainGrid: Grid;
    private panelGrid: Grid;

    constructor()
    {
        Sizer.init();
        this.mainGrid = new Grid();
        document.body.appendChild(this.mainGrid.getHtml());
        let name = Telegram.WebApp.initDataUnsafe.user?.first_name;
        this.mainGrid.getHtml().innerText = name ? name : "ull";
        this.mainGrid.setStyles([gridRows("1fr 15fr")]);
        this.createPanel();
    }
    private createPanel(){
        this.panelGrid = new Grid();
        let per: number = Sizer.calcWidthPercernt(10);
        this.panelGrid.setStyles([gridCols(`${Sizer.width - per}px ${per}px`)]);
        let d = document.createElement("div");
        d.style.background = <string>Telegram.WebApp.themeParams.bg_color;
        let dd = document.createElement("div");
        dd.style.background = <string>Telegram.WebApp.themeParams.button_color;
        this.panelGrid.getHtml().appendChild(d);
        this.panelGrid.getHtml().appendChild(dd);
    }
}
var app = new Application();