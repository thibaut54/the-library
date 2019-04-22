import { IUsers } from 'app/shared/model/users.model';

export interface IRole {
    id?: number;
    role?: string;
    users?: IUsers[];
}

export class Role implements IRole {
    constructor(public id?: number, public role?: string, public users?: IUsers[]) {}
}
