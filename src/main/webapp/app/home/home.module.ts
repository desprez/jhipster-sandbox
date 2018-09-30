import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSandBoxSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';

import { JhipsterSandBoxEntityModule } from './../entities/entity.module';

@NgModule({
    imports: [JhipsterSandBoxSharedModule, JhipsterSandBoxEntityModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSandBoxHomeModule {}
