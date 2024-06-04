class Application
{
    tg;
    constructor()
    {
        this.tg = window.Telegram.WebApp;
        this.tg.ready();
        this.tg.expand();
        this.startApp();
    
    }
    async startApp()
    {
        let childos = document.createElement("h1");
        try
        {
            let response = await Sender.request("hello", "main");
            childos.innerText = "Hello, " + this.tg.initDataUnsafe.user.username + "!" + await response.text();
            
        }
        catch(ex)
        {
            childos.innerText = ex;
        }
        finally{
            document.body.appendChild(childos);
        }
    }
}