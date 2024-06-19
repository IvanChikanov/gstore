abstract class Ui{

    protected html: HTMLElement;

    constructor(elemetnt: string)
    {
        this.html = document.createElement(elemetnt);
    }


    public addClasses(classNames: string[]): void{
        this.foreachString(classNames, (val: string)=>
            this.html.classList.add(val));
    }

    public removeClasses(classNames: string[]): void{
        this.foreachString(classNames, (val: string)=>
            this.html.classList.remove(val));
    }

    public addChilds(uiElements: Ui[]): void{
        uiElements.forEach(ui => this.html.appendChild(ui.html));
    }
    

    private foreachString(classNames: string[], fc:(str: string)=> void ): void{
        classNames.forEach(className => fc(className));
    }

}
export default Ui;
