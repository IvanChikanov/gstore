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
                    "User-Game": `${window.Telegram.WebApp.initDataUnsafe.user.id}&${window.Telegram.WebApp.initDataUnsafe.start_param}`,
                    "Content-Type": "application/json"
                },
                body: body
            }
        );
        if(response.status == 401)
        {
            //document.location = "https://t.me/Cooperation_chat_minigames_bot";
            return response;
        }
        else
        {
            return response;
        }
    }
}


