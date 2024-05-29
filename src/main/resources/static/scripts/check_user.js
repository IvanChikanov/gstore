window.addEventListener("load", async ()=>{
let check = await sendUserData();
if(check == 401)
{
    window.location = "https://t.me/Cooperation_chat_minigames_bot/coop_g_store";
}
else{
    let header = document.createElement("H3");
    header.innerText = `Hello ${JSON.parse(window.Telegram.WebApp.initDataUnsafe)}! requestStatus: ${check}`;
    document.body.appendChild(header);
}
});
async function sendUserData()
{
    let req = await fetch("/check", {
    method: "POST",
    body: `{"auth": "${window.Telegram.WebApp.initData}", "data": "${window.Telegram.WebApp.initDataUnsafe}" }`
    });
    return await req.status;
}   