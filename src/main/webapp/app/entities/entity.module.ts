import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSandBoxCustomerModule } from './customer/customer.module';
import { JhipsterSandBoxTaskModule } from './task/task.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSandBoxCustomerModule,
        JhipsterSandBoxTaskModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    exports: [
        JhipsterSandBoxCustomerModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSandBoxEntityModule {}
