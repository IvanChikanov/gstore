class Application
{
    tg;
    constructor()
    {
        this.tg = window.Telegram.WebApp;
        this.tg.ready();
        this.tg.expand();
        let childos = document.createElement("h1");
        childos.innerText = "Hello, " + this.tg.initDataUnsafe().user.username + "!";
        document.body.appendChild(childos);
        let response = Sender.request("hello", "main");
    }
}
window.onload = ()=>{
    new Application();
}