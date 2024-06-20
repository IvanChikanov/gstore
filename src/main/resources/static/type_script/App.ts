import { TelegramWebApps } from "./declare/tg";
import Grid from "./types/Grid"

class Application{
    private eventListener: any;
    private mainGrid: Grid;
    //private webApp: TelegramWebApps.WebApp;
    constructor()
    {
        this.mainGrid = new Grid();
        document.body.appendChild(this.mainGrid.getHtml());
        console.log(Telegram.WebApp.initDataUnsafe.user?.usernames);
    }
}
var app = new Application();