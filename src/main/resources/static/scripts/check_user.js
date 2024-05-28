let header = document.createElement("h3");
header.innerText = `Hello ${window.Telegram.WebApp.initDataUnsafe.user.username}!`;
document.body.addChild(header);