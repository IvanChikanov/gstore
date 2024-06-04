class Application
{
    constructor()
    {
        let response = Sender.request("hello", "main");
    }
}
let app = new Application();