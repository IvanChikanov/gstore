class Sender
{
    static #apiPath = "/inside"
    static async request(body, path = "")
    {
    try{
        let response = await fetch(`${this.#apiPath}/${path}`,
            {
                method: "POST",
                headers: 
                {
                    "Authorization": window.Telegram.WebApp.initData,
                    "Content-Type": "application/json"
                },
                body: body
            }
        );
        return response;
        }
        catch(exeption)
        {
            document.body.innerHTML = `<h3>${exeption}</h3>`
        }
    }
}