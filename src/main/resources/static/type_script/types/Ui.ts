import { Style } from "../enums/styles";
import { Pos2D } from "./pos2d";

export class Ui{

    protected html: HTMLElement;

    protected styles: Map<string, String>;

    constructor(elemetnt: string)
    {
        this.html = document.createElement(elemetnt);
        this.styles = new Map();
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

    public addStyle(style: string[][])
    {
        for(let s of style)
        {
            this.styles.set(s[0], s[1]);
        }  
        this.updateCss();
    }

    public removeStyle(name: string[]){
        for(let n of name){
            if(this.styles.has(n))
                this.styles.delete(n);
        }
        this.updateCss();
    }

    public setText(text: string)
    {
        this.html.innerText = text;
    }

    public setGridPosition(start: Pos2D, end: Pos2D):void
    {
        this.addStyle([
            [Style.GRID_ROW_START, `${start.x}`],
            [Style.GRID_ROW_END, `${end.x}`],
            [Style.GRID_COL_START, `${start.y}`],
            [Style.GRID_COL_END, `${end.y}`]
        ]);
    }

    private foreachString(classNames: string[], fc:(str: string)=> void ): void{
        classNames.forEach(className => fc(className));
    }
    private updateCss(): void
    {
        let cssText = "";
        this.styles.forEach((v, k) => {cssText += `${k}:${v}; `; console.log(cssText)});
        this.html.style.cssText = cssText;
    }

}