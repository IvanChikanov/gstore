window.addEventListener("load", async ()=>{
let check = await sendUserData();
if(check == 401)
{
    window.location = "https://t.me/Cooperation_chat_minigames_bot/coop_g_store";
}
else{
    let header = document.createElement("H3");
    header.innerText = `Hello ${window.Telegram.WebApp.initDataUnsafe.user.username}! requestStatus: ${check} ${window.Telegram.WebApp.initDataUnsafe.toString()}`;
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