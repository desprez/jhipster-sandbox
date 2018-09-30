import { NgModule } from '@angular/core';
// import { PanelComponent } from 'app/shared/panel/panelComponent.component';
import { JhipsterSandBoxSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhipsterSandBoxSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
    exports: [JhipsterSandBoxSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipsterSandBoxSharedCommonModule {}
