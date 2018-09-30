import { ICustomer } from 'app/shared/model//customer.model';

export interface ITask {
    id?: number;
    name?: string;
    customer?: ICustomer;
}

export class Task implements ITask {
    constructor(public id?: number, public name?: string, public customer?: ICustomer) {}
}
