class Application
{
    static tg;
    window;
    constructor()
    {
        this.tg = window.Telegram.WebApp;
        this.tg.ready();
        this.tg.expand();
        this.startApp();
    }
    async startApp()
    {
        try
        {
            this.window = new MainWindow();
            let response = await Sender.request("hello", "main");
        }
        catch(ex)
        {
            
            document.body.innerHTML = `<h3>${ex}</h3>`;
        }
    }
}