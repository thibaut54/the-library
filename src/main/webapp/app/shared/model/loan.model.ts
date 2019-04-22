import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';
import { IUsers } from 'app/shared/model/users.model';

export interface ILoan {
    id?: number;
    startDate?: Moment;
    returned?: boolean;
    initialEndDate?: Moment;
    extendedEndDate?: Moment;
    book?: IBook;
    users?: IUsers;
}

export class Loan implements ILoan {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public returned?: boolean,
        public initialEndDate?: Moment,
        public extendedEndDate?: Moment,
        public book?: IBook,
        public users?: IUsers
    ) {
        this.returned = this.returned || false;
    }
}
