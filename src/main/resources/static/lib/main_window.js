class MainWindow{
    #body;
    #mainGrid;
    #header;
    #settingButton;
    constructor()
    {
        this.#body = document.body;
        this.#createMainGrid();
        this.#createHeader();
        this.#settingButton();
    }
    #createMainGrid()
    {
        this.#mainGrid = document.createElement("DIV");
        this.#mainGrid.classList.add("main");
        this.#body.appendChild(div);
    }
    #createHeader()
    {
        this.#header = document.createElement("DIV");
        this.#header.classList.add("header");
        this.#header.innerText = `Привет, ${window.Telegram.WebApp.user.username}!`;
        this.#mainGrid.appendChild(this.#header);
    }
    #settingButton()
    {
        this.#settingButton = document.createElement("DIV");
        this.#settingButton.classList.add("game_button");
        this.#settingButton.innerText = "*";
        this.#mainGrid.appendChild(this.#settingButton);
    }
}

