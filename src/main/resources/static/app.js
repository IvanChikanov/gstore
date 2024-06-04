class Application
{
    tg;
    constructor()
    {
        this.tg = window.Telegram.WebApp;
        this.tg.ready();
        this.tg.expand();
    
    }
    async startApp()
    {
        let response = await Sender.request("hello", "main");
        let childos = document.createElement("h1");
        childos.innerText = "Hello, " + this.tg.initDataUnsafe().user.username + "!" + await response.text();
        document.body.appendChild(childos);
    }
}
new Application();