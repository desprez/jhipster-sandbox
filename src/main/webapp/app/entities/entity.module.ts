import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSandBoxCustomerModule } from './customer/customer.module';
import { JhipsterSandBoxTaskModule } from './task/task.module';
import { JhipsterSandBoxOwnerModule } from './owner/owner.module';
import { JhipsterSandBoxPetModule } from './pet/pet.module';
import { JhipsterSandBoxBlogModule } from './blog/blog.module';
import { JhipsterSandBoxEntryModule } from './entry/entry.module';
import { JhipsterSandBoxTagModule } from './tag/tag.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSandBoxCustomerModule,
        JhipsterSandBoxTaskModule,
        JhipsterSandBoxOwnerModule,
        JhipsterSandBoxPetModule,
        JhipsterSandBoxBlogModule,
        JhipsterSandBoxEntryModule,
        JhipsterSandBoxTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSandBoxEntityModule {}
