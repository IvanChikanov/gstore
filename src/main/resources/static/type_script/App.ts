import { Ui } from "./types/Ui";
import { Style } from "./enums/styles";
import Grid from "./types/Grid"
import { Sizer } from "./types/Sizer";
import { Pos2D } from "./types/pos2d";

class Application{
    private eventListener: any;
    private mainGrid: Grid = new Grid();
    private panelGrid: Grid = new Grid();

    constructor()
    {
        Sizer.init();
        this.mainInit();
        this.mainGrid.addChilds([this.panelGrid]);
    }
    private mainInit()
    {
        document.body.appendChild(this.mainGrid.getHtml());
        let per : number = Sizer.calcHeightPercernt(15);
        this.mainGrid.setGrid(`${per}px ${Sizer.height - per}px`, `${Sizer.width}px`,);
        this.createPanel(per);
    }
    private createPanel(per : number){
        this.panelGrid.setGrid(`${Sizer.width - per}px ${per}px`, `${per}px`);
        this.panelGrid.setGridPosition(Pos2D.create(1, 1), Pos2D.create(2, 3));
        let userButton = new Ui("DIV");
        let settingButton = new Ui("DIV");
        userButton.addStyle([
            [Style.BACKGROUND, Telegram.WebApp.themeParams.bg_color as string],
            [Style.COLOR, Telegram.WebApp.themeParams.text_color as string]
        ]);
        userButton.setText(Telegram.WebApp.initDataUnsafe.user?.username as string);

        settingButton.addStyle([
            [Style.BACKGROUND, Telegram.WebApp.themeParams.button_color as string],
            [Style.COLOR, Telegram.WebApp.themeParams.button_text_color as string]
        ]);
        settingButton.setText("S");
        let game = new Ui("DIV");
        game.setText("G");
        game.setGridPosition(Pos2D.create(2, 1), Pos2D.create(3, 2));
        this.panelGrid.addChilds([userButton, settingButton]);
        this.mainGrid.addChilds([game]);
    }
}
window.addEventListener("load", ()=>{
    var app = new Application();
});
