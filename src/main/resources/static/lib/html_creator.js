class Elem
{
    static create(type, className = null)
    {
        let elem = document.createElement(type);
        if(className != null)
        {
            elem.className = className;
        }
        return elem;
    } 
}
