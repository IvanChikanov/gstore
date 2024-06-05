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
    }
    #createMainGrid()
    {
        this.#mainGrid = Elem.create("DIV", "main")
        this.#body.appendChild(this.#mainGrid);
    }
    #createHeader()
    {
        this.#header = Elem.create("DIV", "user_panel");
        this.#header.innerText = `Привет, ${window.Telegram.WebApp.initDataUnsafe.user.username}!`;
        this.#mainGrid.appendChild(this.#header);
    }
}

