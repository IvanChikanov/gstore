(()=>{"use strict";const t=class{constructor(t){this.html=document.createElement(t)}getHtml(){return this.html}addClasses(t){this.foreachString(t,(t=>this.html.classList.add(t)))}removeClasses(t){this.foreachString(t,(t=>this.html.classList.remove(t)))}addChilds(t){t.forEach((t=>this.html.appendChild(t.html)))}foreachString(t,s){t.forEach((t=>s(t)))}};var s;!function(t){t.DIV="DIV",t.BUTTON="BUTTON",t.H1="H1",t.H2="H2",t.H3="H3",t.P="P",t.TABLE="TABLE"}(s||(s={}));const e=s,i=class extends t{constructor(t="",s=""){super(e.DIV),this.addClasses(["grid","fullWidth","fullHeight"])}};new class{constructor(){this.mainGrid=new i,document.body.appendChild(this.mainGrid.getHtml())}}})();