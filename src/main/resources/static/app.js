class Application
{
    tg;
    constructor()
    {
        let response = Sender.request("hello", "main");
        this.tg = window.Telegram.WebApp;
        this.tg.ready();
        this.tg.expand();
        let childos = document.createElement("h1");
        childos.innerText = "Hello, " this.tg.initDataUnsafe().user.username; + "!";
        document.body.appendChild(childos);
    }
}
let app = new Application();