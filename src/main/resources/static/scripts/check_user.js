window.addEventListener("load", ()=>{
let header = document.createElement("H3");
header.innerText = `Hello ${window.Telegram.WebApp.initDataUnsafe}!`;
document.body.addChild(header);
});