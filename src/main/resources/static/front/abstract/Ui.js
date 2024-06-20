"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
class Ui {
    constructor(elemetnt) {
        this.html = document.createElement(elemetnt);
    }
    addClasses(classNames) {
        this.foreachString(classNames, (val) => this.html.classList.add(val));
    }
    removeClasses(classNames) {
        this.foreachString(classNames, (val) => this.html.classList.remove(val));
    }
    addChilds(uiElements) {
        uiElements.forEach(ui => this.html.appendChild(ui.html));
    }
    foreachString(classNames, fc) {
        classNames.forEach(className => fc(className));
    }
}
exports.default = Ui;
