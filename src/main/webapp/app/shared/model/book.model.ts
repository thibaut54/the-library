import { Moment } from 'moment';
import { ILoan } from 'app/shared/model/loan.model';
import { IAuthor } from 'app/shared/model/author.model';
import { ICategory } from 'app/shared/model/category.model';
import { ILibrary } from 'app/shared/model/library.model';
import { IEditor } from 'app/shared/model/editor.model';

export interface IBook {
    id?: number;
    title?: string;
    language?: string;
    isbn?: number;
    publicationDate?: Moment;
    numberOfPages?: number;
    loans?: ILoan[];
    authors?: IAuthor[];
    categories?: ICategory[];
    libraries?: ILibrary[];
    editor?: IEditor;
}

export class Book implements IBook {
    constructor(
        public id?: number,
        public title?: string,
        public language?: string,
        public isbn?: number,
        public publicationDate?: Moment,
        public numberOfPages?: number,
        public loans?: ILoan[],
        public authors?: IAuthor[],
        public categories?: ICategory[],
        public libraries?: ILibrary[],
        public editor?: IEditor
    ) {}
}
