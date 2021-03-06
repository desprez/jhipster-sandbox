import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSandBoxSharedModule } from 'app/shared';
import { JhipsterSandBoxAdminModule } from 'app/admin/admin.module';
import {
    BlogComponent,
    BlogDetailComponent,
    BlogUpdateComponent,
    BlogDeletePopupComponent,
    BlogDeleteDialogComponent,
    blogRoute,
    blogPopupRoute
} from './';

const ENTITY_STATES = [...blogRoute, ...blogPopupRoute];

@NgModule({
    imports: [JhipsterSandBoxSharedModule, JhipsterSandBoxAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BlogComponent, BlogDetailComponent, BlogUpdateComponent, BlogDeleteDialogComponent, BlogDeletePopupComponent],
    entryComponents: [BlogComponent, BlogUpdateComponent, BlogDeleteDialogComponent, BlogDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSandBoxBlogModule {}
