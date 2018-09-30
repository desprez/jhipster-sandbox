import { ITask } from 'app/shared/model//task.model';

export interface ICustomer {
    id?: number;
    name?: string;
    tasks?: ITask[];
}

export class Customer implements ICustomer {
    constructor(public id?: number, public name?: string, public tasks?: ITask[]) {}
}
