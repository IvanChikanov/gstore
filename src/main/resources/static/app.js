class Application
{
    constructor()
    {
        let response = Sender.request("hello", "main");
        if(response.status == 401)
        {
            document.location = "https://t.me/Cooperation_chat_minigames_bot";
        }
    }
}
let app = new Application();