"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Ui_1 = require("../abstract/Ui");
const Tags_1 = require("../enums/Tags");
class Grid extends Ui_1.default {
    constructor(row = null, col = null) {
        super(Tags_1.default.DIV);
        this.addClasses(["grid", "fullWidth", "fullHeight"]);
    }
}
exports.default = Grid;
