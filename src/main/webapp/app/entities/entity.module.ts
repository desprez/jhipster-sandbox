import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSandBoxOwnerModule } from './owner/owner.module';
import { JhipsterSandBoxPetModule } from './pet/pet.module';
import { JhipsterSandBoxBlogModule } from './blog/blog.module';
import { JhipsterSandBoxEntryModule } from './entry/entry.module';
import { JhipsterSandBoxTagModule } from './tag/tag.module';
import { JhipsterSandBoxCustomerModule } from './customer/customer.module';
import { JhipsterSandBoxTaskModule } from './task/task.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSandBoxOwnerModule,
        JhipsterSandBoxPetModule,
        JhipsterSandBoxBlogModule,
        JhipsterSandBoxEntryModule,
        JhipsterSandBoxTagModule,
        JhipsterSandBoxCustomerModule,
        JhipsterSandBoxTaskModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSandBoxEntityModule {}
