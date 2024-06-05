class MainWindow{
    #body;
    #mainGrid;
    #header;
    #playWindow;
    constructor()
    {
        this.#body = document.body;
        this.#createMainGrid();
        this.#createHeader();
        this.#createPlayWindow();
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
    #createPlayWindow()
    {
        this.#playWindow = Elem.create("DIV", "game_window");
        this.#mainGrid.appendChild(this.#playWindow);
    }
}

