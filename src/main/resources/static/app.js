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
        //let childos = document.createElement("h1");
        try
        {
            this.window = new MainWindow();
            let response = await Sender.request("hello", "main");
            //childos.innerText = "Hello, " + this.tg.initDataUnsafe.user.username + "!" + await response.text();
        }
        catch(ex)
        {
            
            document.body.innerHTML = `<h3>${ex}</h3>`;
        }
    }
}