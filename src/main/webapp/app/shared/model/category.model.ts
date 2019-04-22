import { IBook } from 'app/shared/model/book.model';

export interface ICategory {
    id?: number;
    category?: string;
    books?: IBook[];
}

export class Category implements ICategory {
    constructor(public id?: number, public category?: string, public books?: IBook[]) {}
}
