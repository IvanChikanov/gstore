export interface StyleFunc{
    (value: string): string;
}

export const gridRows: StyleFunc = (val)=>{ return `grid-template-rows:${val};`}

export const gridCols: StyleFunc = (val)=>{ return `grid-template-columns:${val};`}

export const gridColStart: StyleFunc = (val)=>{ return `grid-column-start:${val};`}

export const gridColend: StyleFunc = (val)=>{ return `grid-column-end:${val};`}

export const gridRowStart: StyleFunc = (val)=>{ return `grid-row-start:${val};`}

export const gridRowEnd: StyleFunc = (val)=>{ return `grid-row-end:${val};`}