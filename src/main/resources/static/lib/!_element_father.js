class Element
{
    #html;
    constructor(type)
    {
        this.#html = document.createElement(type);
    }
    getHTML()
    {
        return this.#html;
    }
    setChild(element)
    {
        this.#html.appendChild(element);
    }
}