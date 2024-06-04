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
        this.#settingButtonInit();
    }
    #createMainGrid()
    {
        this.#mainGrid = document.createElement("DIV");
        this.#mainGrid.classList.add("main");
        this.#body.appendChild(this.#mainGrid);
    }
    #createHeader()
    {
        this.#header = document.createElement("DIV");
        this.#header.classList.add("header");
        this.#header.innerText = `Привет, ${window.Telegram.WebApp.initDataUnsafe.user.username}!`;
        this.#mainGrid.appendChild(this.#header);
    }
    #settingButtonInit()
    {
        this.#settingButton = document.createElement("DIV");
        this.#settingButton.classList.add("game_button");
        this.#settingButton.innerText = "*";
        this.#mainGrid.appendChild(this.#settingButton);
    }
}

