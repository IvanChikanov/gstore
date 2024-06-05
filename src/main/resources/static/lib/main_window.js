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
        this.#mainGrid = Elem.create("DIV", "main")
        this.#body.appendChild(this.#mainGrid);
    }
    #createHeader()
    {
        this.#header = Elem.create("DIV", "header");
        this.#header.innerText = `Привет, ${window.Telegram.WebApp.initDataUnsafe.user.username}!`;
        this.#mainGrid.appendChild(this.#header);
    }
    #settingButtonInit()
    {
        this.#settingButton = document.createElement("DIV");
        this.#settingButton.classList.add("game_button");
        this.#settingButton.innerText = "#";
        this.#mainGrid.appendChild(this.#settingButton);
    }
}

