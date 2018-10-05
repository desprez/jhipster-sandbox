import { NgModule } from '@angular/core';
import { PanelComponent } from 'app/shared/panel/panel.component';
import { JhipsterSandBoxSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhipsterSandBoxSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, PanelComponent],
    exports: [JhipsterSandBoxSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, PanelComponent]
})
export class JhipsterSandBoxSharedCommonModule {}
