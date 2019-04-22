import { IUsers } from 'app/shared/model/users.model';
import { ILibrary } from 'app/shared/model/library.model';
import { IEditor } from 'app/shared/model/editor.model';

export interface ICoordinates {
    id?: number;
    street?: string;
    streetNumber?: string;
    additionalInformation?: string;
    city?: string;
    postalCode?: number;
    phone?: number;
    email?: string;
    users?: IUsers;
    library?: ILibrary;
    editor?: IEditor;
}

export class Coordinates implements ICoordinates {
    constructor(
        public id?: number,
        public street?: string,
        public streetNumber?: string,
        public additionalInformation?: string,
        public city?: string,
        public postalCode?: number,
        public phone?: number,
        public email?: string,
        public users?: IUsers,
        public library?: ILibrary,
        public editor?: IEditor
    ) {}
}
