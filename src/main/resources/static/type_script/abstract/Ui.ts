import { StyleFunc } from "../storage/styles";

abstract class Ui{

    protected html: HTMLElement;

    constructor(elemetnt: string)
    {
        this.html = document.createElement(elemetnt);
    }

    public getHtml(){
        return this.html;
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

    public setStyles(styles: string[])
    {
        
    }

}
export default Ui;
