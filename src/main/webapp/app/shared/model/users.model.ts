import { Moment } from 'moment';
import { ILoan } from 'app/shared/model/loan.model';
import { IRole } from 'app/shared/model/role.model';
import { ICoordinates } from 'app/shared/model/coordinates.model';

export interface IUsers {
    id?: number;
    password?: string;
    firstName?: string;
    lastName?: string;
    userName?: string;
    registrationDate?: Moment;
    loans?: ILoan[];
    roles?: IRole[];
    coordinates?: ICoordinates;
}

export class Users implements IUsers {
    constructor(
        public id?: number,
        public password?: string,
        public firstName?: string,
        public lastName?: string,
        public userName?: string,
        public registrationDate?: Moment,
        public loans?: ILoan[],
        public roles?: IRole[],
        public coordinates?: ICoordinates
    ) {}
}
