import { ICoordinates } from 'app/shared/model/coordinates.model';
import { IBook } from 'app/shared/model/book.model';

export interface ILibrary {
    id?: number;
    name?: string;
    coordinates?: ICoordinates;
    books?: IBook[];
}

export class Library implements ILibrary {
    constructor(public id?: number, public name?: string, public coordinates?: ICoordinates, public books?: IBook[]) {}
}
