import { gridRows } from "./storage/styles";
import Grid from "./types/Grid"

class Application{
    private eventListener: any;
    private mainGrid: Grid;
    //private webApp: TelegramWebApps.WebApp;
    constructor()
    {
        this.mainGrid = new Grid();
        document.body.appendChild(this.mainGrid.getHtml());
        let name = Telegram.WebApp.initDataUnsafe.user?.first_name;
        this.mainGrid.getHtml().innerText = name ? name : "ull";
        this.mainGrid.addClasses([gridRows("1fr 15fr")]);
        console.log(Telegram.WebApp.initDataUnsafe.user?.usernames);
    }
}
var app = new Application();