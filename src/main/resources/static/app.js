class Application
{
    tg;
    constructor()
    {
        let response = Sender.request("hello", "main");
        this.tg = = window.Telegram.WebApp;
        this.tg.expand();
    }
}
let app = new Application();