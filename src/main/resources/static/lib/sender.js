class Sender
{
    static #apiPath = "/inside"
    static async request(body, path = "")
    {
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
}