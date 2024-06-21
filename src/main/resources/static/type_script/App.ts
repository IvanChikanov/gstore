import { Ui } from "./types/Ui";
import { Style } from "./enums/styles";
import Grid from "./types/Grid"
import { Sizer } from "./types/Sizer";

class Application{
    private eventListener: any;
    private mainGrid: Grid = new Grid();
    private panelGrid: Grid = new Grid();

    constructor()
    {
        Sizer.init();
        this.mainInit();
        this.createPanel();
        this.mainGrid.addChilds([this.panelGrid]);
    }
    private mainInit()
    {
        document.body.appendChild(this.mainGrid.getHtml());
        let per : number = Sizer.width / 15;
        this.mainGrid.addStyle([[Style.GRID_ROWS, `${per}px ${Sizer.width - per}px`]]);
    }
    private createPanel(){
        let per: number = Sizer.calcWidthPercernt(10);
        this.panelGrid.addStyle([
            [Style.GRID_COLS, `${Sizer.width - per}px ${per}px`],
            [Style.GRID_ROW_START, "1"],
            [Style.GRID_ROW_END, "3"],
            [Style.GRID_COL_START, "1"],
            [Style.GRID_COL_END, "2"]
        ]);
        let userButton = new Ui("DIV");
        let settingButton = new Ui("DIV");
        userButton.addStyle([
            [Style.BACKGROUND, Telegram.WebApp.themeParams.bg_color as string],
            [Style.COLOR, Telegram.WebApp.themeParams.text_color as string]
        ]);
        userButton.setText(Telegram.WebApp.initDataUnsafe.user?.usernames as string);

        settingButton.addStyle([
            [Style.BACKGROUND, Telegram.WebApp.themeParams.button_color as string],
            [Style.COLOR, Telegram.WebApp.themeParams.button_text_color as string]
        ]);
        settingButton.setText("S");
        let game = new Ui("DIV");
        game.setText("G");
        this.panelGrid.addChilds([userButton, settingButton, game]);
    }
}
var app = new Application();