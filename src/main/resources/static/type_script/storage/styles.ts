export interface StyleFunc{
    (value: string): string;
}

export const gridRows: StyleFunc = (val)=>{ return `grid-template-rows:${val};`}