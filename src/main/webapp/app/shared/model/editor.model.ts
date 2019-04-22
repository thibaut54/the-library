import { IBook } from 'app/shared/model/book.model';
import { ICoordinates } from 'app/shared/model/coordinates.model';

export interface IEditor {
    id?: number;
    name?: string;
    books?: IBook[];
    coordinates?: ICoordinates;
}

export class Editor implements IEditor {
    constructor(public id?: number, public name?: string, public books?: IBook[], public coordinates?: ICoordinates) {}
}
